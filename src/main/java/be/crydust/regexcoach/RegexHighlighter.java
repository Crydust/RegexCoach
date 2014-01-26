/*
 * Copyright 2014, Kristof Neirynck
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.crydust.regexcoach;

import java.awt.Color;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

public class RegexHighlighter implements EventBus.Handler {

    private static final DefaultHighlighter.DefaultHighlightPainter orange = new DefaultHighlighter.DefaultHighlightPainter(Color.ORANGE);
    private static final DefaultHighlighter.DefaultHighlightPainter yellow = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
    private static final DefaultHighlighter.DefaultHighlightPainter red = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
    private static final DefaultHighlighter.DefaultHighlightPainter gray = new DefaultHighlighter.DefaultHighlightPainter(Color.GRAY);

    //Deal with concurrent UI updates by tracking if we need to do an update and if we are currently in an update
    private final AtomicBoolean doUpdate = new AtomicBoolean(false);
    private final AtomicBoolean inHighlights = new AtomicBoolean(false);
    private volatile HighlighterUiState lastUiState = null;
    private final Gui swing;

    @Override
    public void handleEvent() {
        doHighlights();
    }

    public RegexHighlighter(Gui swing) {
        this.swing = swing;
    }

    /**
     * Does the work of handling concurrent updates, seeing if an update is
     * needed and then if it is actually doing the modifications to the UI.
     *
     * IMPORTANT: This class must never read directly from the UI, it creates a
     * new UiState object and then reads ALL UI state from that object. If new
     * UI elements are added they must be tracked in the UiState object
     */
    public void doHighlights() {
        //Makr that an update to the UI might be needed
        doUpdate.set(true);

        //Try setting inHighlights to true to see if we should do the ui update
        if (!inHighlights.compareAndSet(false, true)) {
            //Some other thread is in the inHighlights loop, give up and go home
            return;
        }

        try {
            //Keep checking to see if the UI has changed as long as doUpdate is true
            while (doUpdate.compareAndSet(true, false)) {
                HighlighterUiState currentState = new HighlighterUiState(swing);
                if (currentState.equals(lastUiState)) {
                    //Nothing changed since the last update, don't bother doing anything
                    return;
                }
                HighlighterUiState prevUiState = lastUiState;
                lastUiState = currentState;
                updateState(currentState);
                updateGui(swing, currentState, prevUiState);
            }
        } catch (PatternSyntaxException e) {
            try {
                swing.getRegexPane().getHighlighter().addHighlight(e.getIndex(), e.getIndex() + 2, red);
            } catch (BadLocationException ex) {
                throw new UnrecoverableException(ex);
            }
            swing.getRegexStatus().setText(e.getDescription());
        } catch (NullPointerException | BadLocationException ex) {
            throw new UnrecoverableException(ex);
        } finally {
            //Make sure we note that we're done updating
            inHighlights.set(false);
        }
    }

    /**
     * Reads from and writes to a UiState.
     * Is not private for easier testing, don't use this method outside of this class.
     *
     * @param currentState will be altered
     * @throws NullPointerException
     */
    static void updateState(HighlighterUiState currentState) throws NullPointerException {
        //trim the target string
        String target = currentState.getTarget().substring(currentState.getTargetStart(), currentState.getTargetEnd());

        //There is a regex and a target
        if (currentState.getRegex().length() > 0) {
            //create the pattern & matcher
            Pattern pattern = Pattern.compile(currentState.getRegex(), currentState.getPatternFlags());
            Matcher matcher = pattern.matcher(target);

            //Count the number of matches
            while (matcher.find()) {
                currentState.setMatchCount(currentState.getMatchCount() + 1);
            }
            matcher.reset();

            //Find the correct match
            for (int match = 0; match < currentState.getMatchIndex() - 1; match++) {
                matcher.find();
            }
            if (matcher.find()) {
                //Do matching of the regex selection
                Matcher selectionMatcher = null;
                if (currentState.getSelectionRegex() != null) {
                    Pattern selectionPattern = Pattern.compile(currentState.getSelectionRegex(), currentState.getPatternFlags());
                    selectionMatcher = selectionPattern.matcher(matcher.group(0));
                    currentState.setSelectionMatched(selectionMatcher.find());
                }

                //Record group count
                currentState.setGroupCount(matcher.groupCount());

                //Record match
                currentState.setMatchStart(currentState.getTargetStart() + matcher.start());
                currentState.setMatchEnd(currentState.getTargetStart() + matcher.end());

                //Record highlight
                if (currentState.isHighlightSelection() && currentState.isSelectionMatched()) {
                    if (selectionMatcher == null) {
                        throw new NullPointerException("selectionMatcher is null");
                    }
                    currentState.setHighlightStart(currentState.getMatchStart() + selectionMatcher.start());
                    currentState.setHighlightEnd(currentState.getMatchStart() + selectionMatcher.end());
                } else if (currentState.isHighlightGroup()) {
                    currentState.setHighlightStart(currentState.getTargetStart() + matcher.start(currentState.getHighlightGroupNumber()));
                    currentState.setHighlightEnd(currentState.getTargetStart() + matcher.end(currentState.getHighlightGroupNumber()));
                }
            }
        }
    }

    /**
     * Reads UiState and writes to gui.
     *
     * @param swing
     * @param currentState
     * @param prevUiState
     * @throws BadLocationException
     */
    private static void updateGui(Gui swing, HighlighterUiState currentState, HighlighterUiState prevUiState) throws BadLocationException {
        //Remove all highlights
        swing.getRegexPane().getHighlighter().removeAllHighlights();
        swing.getTargetPane().getHighlighter().removeAllHighlights();
        swing.getRegexStatus().setText(" ");
        swing.getTargetStatus().setText(" "); //Add the user's selections back after clearing the highlights
        if (currentState.getRegexSelectStart() < currentState.getRegexSelectEnd()) {
            if (currentState.getRegexSelectCaret() == currentState.getRegexSelectStart()) {
                swing.getRegexPane().getCaret().setDot(currentState.getRegexSelectEnd());
                swing.getRegexPane().getCaret().moveDot(currentState.getRegexSelectStart());
            } else {
                swing.getRegexPane().select(currentState.getRegexSelectStart(), currentState.getRegexSelectEnd());
            }
            swing.getRegexPane().getCaret().setSelectionVisible(true);

        }
        if (currentState.getTargetSelectStart() < currentState.getTargetSelectEnd()) {
            if (currentState.getTargetSelectCaret() == currentState.getTargetSelectStart()) {
                swing.getTargetPane().getCaret().setDot(currentState.getTargetSelectEnd());
                swing.getTargetPane().getCaret().moveDot(currentState.getTargetSelectStart());
            } else {
                swing.getTargetPane().select(currentState.getTargetSelectStart(), currentState.getTargetSelectEnd());
            }
            swing.getTargetPane().getCaret().setSelectionVisible(true);
        }

        //Update endOfString spinner
        int targetStartMax = Math.max(currentState.getTargetEnd(), 0);
        ((SpinnerNumberModel) swing.getEndOfString().getModel()).setMaximum(currentState.getTarget().length());
        if (prevUiState == null || prevUiState.getTarget().length() <= currentState.getTargetEnd()) {
            swing.getEndOfString().setValue(currentState.getTarget().length());
            targetStartMax = currentState.getTarget().length();
        }
        ((SpinnerNumberModel) swing.getEndOfString().getModel()).setMinimum(currentState.getTargetStart());

        //Update startOfString spinner
        ((SpinnerNumberModel) swing.getStartOfString().getModel()).setMaximum(targetStartMax);
        if (currentState.getTargetStart() > targetStartMax) {
            swing.getStartOfString().setValue(targetStartMax);
        }

        //Count matches & update match spinner
        ((SpinnerNumberModel) swing.getMatchNumber().getModel()).setMaximum(currentState.getMatchCount());
        swing.getMatchCount().setText(Integer.toString(currentState.getMatchCount()));
        if (currentState.getMatchCount() > 0 && currentState.getMatchIndex() > currentState.getMatchCount()) {
            swing.getMatchNumber().setValue(currentState.getMatchCount());
        }

        swing.getHighlightSelection().setEnabled(currentState.isSelectionMatched());

        //Update group count spinner
        ((SpinnerNumberModel) swing.getHighlightGroupNumber().getModel()).setMaximum(currentState.getGroupCount());
        swing.getGroupCount().setText(Integer.toString(currentState.getGroupCount()));
        if (currentState.getHighlightGroupNumber() > currentState.getGroupCount()) {
            swing.getHighlightGroupNumber().setValue(currentState.getGroupCount());
        }

        //Highlight the areas that are being ignored
        swing.getTargetPane().getHighlighter().addHighlight(0, currentState.getTargetStart(), gray);
        swing.getTargetPane().getHighlighter().addHighlight(currentState.getTargetEnd(), currentState.getTarget().length(), gray);

        if (currentState.getHighlightStart() != currentState.getHighlightEnd()) {
            swing.getTargetPane().getHighlighter().addHighlight(currentState.getHighlightStart(), currentState.getHighlightEnd(), orange);
        }

        if (currentState.getMatchCount() > 0) {
            swing.getTargetPane().getHighlighter().addHighlight(currentState.getMatchStart(), currentState.getMatchEnd(), yellow);
            swing.getTargetStatus().setText(String.format(
                    "Match #%d from %d to %d.", currentState.getMatchIndex(), currentState.getMatchStart(), currentState.getMatchEnd()));
        } else {
            swing.getTargetStatus().setText("No match.");
        }
    }
}
