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
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

public class RegexReplacer implements EventBus.Handler {

    private static final DefaultHighlighter.DefaultHighlightPainter red = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);

    //Deal with concurrent UI updates by tracking if we need to do an update and if we are currently in an update
    private final AtomicBoolean doUpdate = new AtomicBoolean(false);
    private final AtomicBoolean inReplace = new AtomicBoolean(false);
    private volatile ReplacerUiState lastUiState = null;
    private final Gui swing;

    @Override
    public void handleEvent() {
        doReplace();
    }

    public RegexReplacer(Gui swing) {
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
    public void doReplace() {
        //Makr that an update to the UI might be needed
        doUpdate.set(true);

        //Try setting inHighlights to true to see if we should do the ui update
        if (!inReplace.compareAndSet(false, true)) {
            //Some other thread is in the inHighlights loop, give up and go home
            return;
        }

        try {
            //Keep checking to see if the UI has changed as long as doUpdate is true
            while (doUpdate.compareAndSet(true, false)) {
                ReplacerUiState currentState = new ReplacerUiState(swing);
                if (currentState.equals(lastUiState)) {
                    //Nothing changed since the last update, don't bother doing anything
                    return;
                }
                lastUiState = currentState;

                updateState(currentState);
                updateGui(swing, currentState);

            }
        } catch (BadLocationException ex) {
            throw new UnrecoverableException(ex);
        } finally {
            //Make sure we note that we're done updating
            inReplace.set(false);
        }
    }

    /**
     * Reads from and writes to a UiState. Is not private for easier testing,
     * don't use this method outside of this class.
     *
     * @param currentState will be altered
     * @throws NullPointerException
     */
    static void updateState(ReplacerUiState currentState) throws NullPointerException {
        //There is a regex and a target
        try {
            if (currentState.getRegex().length() > 0) {
                //create the pattern & matcher
                Pattern pattern = Pattern.compile(currentState.getRegex(), currentState.getPatternFlags());
                Matcher matcher = pattern.matcher(currentState.getTarget());

                StringBuffer stringBuffer = new StringBuffer();
                while (matcher.find()) {
                    matcher.appendReplacement(stringBuffer, currentState.getReplacement());
                }
                matcher.appendTail(stringBuffer);
                currentState.setSubstitution(stringBuffer.toString());
            }
        } catch (StringIndexOutOfBoundsException e) {
//                    currentState.setSubstitution("#ERROR StringIndexOutOfBoundsException " + e.getMessage());
            currentState.setSubstitution("");
            currentState.setStatus(e.getMessage());
            currentState.setHighlightLastCharacter(true);
        } catch (PatternSyntaxException e) {
            currentState.setSubstitution("");
            currentState.setStatus(e.getDescription());
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            currentState.setSubstitution("");
            currentState.setStatus(e.getMessage());
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
    private static void updateGui(Gui swing, ReplacerUiState currentState) throws BadLocationException {
        swing.getReplacementPane().getHighlighter().removeAllHighlights();
        if (currentState.isHighlightLastCharacter()) {
            swing.getReplacementPane().getHighlighter().addHighlight(currentState.getReplacement().length() - 1, currentState.getReplacement().length(), red);
        }
        swing.getSubstitutionArea().setText(currentState.getSubstitution());
        swing.getReplacementStatus().setText(currentState.getStatus());
    }
}
