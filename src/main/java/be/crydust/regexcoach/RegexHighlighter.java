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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

public class RegexHighlighter extends KeyAdapter implements ActionListener, ChangeListener, CaretListener {

    private final DefaultHighlighter.DefaultHighlightPainter orange = new DefaultHighlighter.DefaultHighlightPainter(Color.ORANGE);
    private final DefaultHighlighter.DefaultHighlightPainter yellow = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
    private final DefaultHighlighter.DefaultHighlightPainter red = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
    private final DefaultHighlighter.DefaultHighlightPainter gray = new DefaultHighlighter.DefaultHighlightPainter(Color.GRAY);

    //Deal with concurrent UI updates by tracking if we need to do an update and if we are currently in an update
    private final AtomicBoolean doUpdate = new AtomicBoolean(false);
    private final AtomicBoolean inHighlights = new AtomicBoolean(false);
    private volatile UiState lastUiState = null;
    private final Gui swing;

    @Override
    public void actionPerformed(ActionEvent e) {
        doHighlights();
    }

    @Override
    public void keyReleased(KeyEvent event) {
        doHighlights();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        doHighlights();
    }

    @Override
    public void caretUpdate(CaretEvent e) {
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
                UiState currentState = new UiState(swing);
                if (currentState.equals(lastUiState)) {
                    //Nothing changed since the last update, don't bother doing anything
                    return;
                }
                UiState prevUiState = lastUiState;
                lastUiState = currentState;

                //trim the target string
                String target = currentState.target.substring(currentState.targetStart, currentState.targetEnd);

                //There is a regex and a target
                if (currentState.regex.length() > 0) {
                    //create the pattern & matcher
                    Pattern pattern = Pattern.compile(currentState.regex, currentState.patternFlags);
                    Matcher matcher = pattern.matcher(target);

                    //Count the number of matches
                    while (matcher.find()) {
                        currentState.matchCount++;
                    }
                    matcher.reset();

                    //Find the correct match
                    for (int match = 0; match < currentState.matchIndex - 1; match++) {
                        matcher.find();
                    }
                    if (matcher.find()) {
                        //Do matching of the regex selection
                        Matcher selectionMatcher = null;
                        if (currentState.selectionRegex != null) {
                            Pattern selectionPattern = Pattern.compile(currentState.selectionRegex, currentState.patternFlags);
                            selectionMatcher = selectionPattern.matcher(matcher.group(0));
                            currentState.selectionMatched = selectionMatcher.find();
                        }

                        //Record group count
                        currentState.groupCount = matcher.groupCount();

                        //Record match
                        currentState.matchStart = currentState.targetStart + matcher.start();
                        currentState.matchEnd = currentState.targetStart + matcher.end();

                        //Record highlight
                        if (currentState.highlightSelection && currentState.selectionMatched) {
                            if (selectionMatcher == null) {
                                throw new NullPointerException("selectionMatcher is null");
                            }
                            currentState.highlightStart = currentState.matchStart + selectionMatcher.start();
                            currentState.highlightEnd = currentState.matchStart + selectionMatcher.end();
                        } else if (currentState.highlightGroup) {
                            currentState.highlightStart = currentState.targetStart + matcher.start(currentState.highlightGroupNumber);
                            currentState.highlightEnd = currentState.targetStart + matcher.end(currentState.highlightGroupNumber);
                        }
                    }
                }

                //Remove all highlights
                swing.getRegexPane().getHighlighter().removeAllHighlights();
                swing.getTargetPane().getHighlighter().removeAllHighlights();
                swing.getRegexStatus().setText(" ");
                swing.getTargetStatus().setText(" "); //Add the user's selections back after clearing the highlights
                if (currentState.regexSelectStart < currentState.regexSelectEnd) {
                    if (currentState.regexSelectCaret == currentState.regexSelectStart) {
                        swing.getRegexPane().getCaret().setDot(currentState.regexSelectEnd);
                        swing.getRegexPane().getCaret().moveDot(currentState.regexSelectStart);
                    } else {
                        swing.getRegexPane().select(currentState.regexSelectStart, currentState.regexSelectEnd);
                    }
                    swing.getRegexPane().getCaret().setSelectionVisible(true);

                }
                if (currentState.targetSelectStart < currentState.targetSelectEnd) {
                    if (currentState.targetSelectCaret == currentState.targetSelectStart) {
                        swing.getTargetPane().getCaret().setDot(currentState.targetSelectEnd);
                        swing.getTargetPane().getCaret().moveDot(currentState.targetSelectStart);
                    } else {
                        swing.getTargetPane().select(currentState.targetSelectStart, currentState.targetSelectEnd);
                    }
                    swing.getTargetPane().getCaret().setSelectionVisible(true);
                }

                //Update endOfString spinner
                int targetStartMax = Math.max(currentState.targetEnd, 0);
                ((SpinnerNumberModel) swing.getEndOfString().getModel()).setMaximum(currentState.target.length());
                if (prevUiState == null || prevUiState.target.length() <= currentState.targetEnd) {
                    swing.getEndOfString().setValue(currentState.target.length());
                    targetStartMax = currentState.target.length();
                }
                ((SpinnerNumberModel) swing.getEndOfString().getModel()).setMinimum(currentState.targetStart);

                //Update startOfString spinner
                ((SpinnerNumberModel) swing.getStartOfString().getModel()).setMaximum(targetStartMax);
                if (currentState.targetStart > targetStartMax) {
                    swing.getStartOfString().setValue(targetStartMax);
                }

                //Count matches & update match spinner
                ((SpinnerNumberModel) swing.getMatchNumber().getModel()).setMaximum(currentState.matchCount);
                swing.getMatchCount().setText(Integer.toString(currentState.matchCount));
                if (currentState.matchCount > 0 && currentState.matchIndex > currentState.matchCount) {
                    swing.getMatchNumber().setValue(currentState.matchCount);
                }

                swing.getHighlightSelection().setEnabled(currentState.selectionMatched);

                //Update group count spinner
                ((SpinnerNumberModel) swing.getHighlightGroupNumber().getModel()).setMaximum(currentState.groupCount);
                swing.getGroupCount().setText(Integer.toString(currentState.groupCount));
                if (currentState.highlightGroupNumber > currentState.groupCount) {
                    swing.getHighlightGroupNumber().setValue(currentState.groupCount);
                }

                //Highlight the areas that are being ignored
                swing.getTargetPane().getHighlighter().addHighlight(0, currentState.targetStart, gray);
                swing.getTargetPane().getHighlighter().addHighlight(currentState.targetEnd, currentState.target.length(), gray);

                if (currentState.highlightStart != currentState.highlightEnd) {
                    swing.getTargetPane().getHighlighter().addHighlight(currentState.highlightStart, currentState.highlightEnd, orange);
                }

                if (currentState.matchCount > 0) {
                    swing.getTargetPane().getHighlighter().addHighlight(currentState.matchStart, currentState.matchEnd, yellow);
                    swing.getTargetStatus().setText(String.format(
                            "Match #%d from %d to %d.",
                            currentState.matchIndex,
                            currentState.matchStart,
                            currentState.matchEnd));
                } else {
                    swing.getTargetStatus().setText("No match.");
                }
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
}
