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

import javax.swing.text.BadLocationException;

/**
 * Encapsulates the state of the UI which is polled in the constructor. All of
 * the final state fields are included in the equals method so two UI states can
 * be compared for change.
 *
 * IMPORTANT: This class MUST NEVER change the state of the UI
 */
public class HighlighterUiState {

    final String regex;
    final String selectionRegex;
    final String target;
    final int regexSelectStart;
    final int regexSelectEnd;
    final int regexSelectCaret;
    final int targetSelectStart;
    final int targetSelectEnd;
    final int targetSelectCaret;
    final int patternFlags;
    final int targetStart;
    final int targetEnd;
    final int matchIndex;
    final boolean highlightSelection;
    final boolean highlightGroup;
    final int highlightGroupNumber;

    int matchCount = 0;
    int groupCount = 0;
    int matchStart = 0;
    int matchEnd = 0;
    int highlightStart = 0;
    int highlightEnd = 0;
    boolean selectionMatched = false;

    public HighlighterUiState(Gui swing) {
        // note: get the text from the underlying document,
        // otherwise carriage return/line feeds different when using the JTextPane text
        try {
            regex = GuiReader.readRegex(swing);
            target = GuiReader.readTarget(swing);
        } catch (BadLocationException ex) {
            throw new UnrecoverableException(ex);
        }
        //Capture selected range for the regex
        regexSelectStart = swing.getRegexPane().getSelectionStart();
        regexSelectEnd = swing.getRegexPane().getSelectionEnd();
        regexSelectCaret = swing.getRegexPane().getCaret().getDot();

        //Capture selected range for the target
        targetSelectStart = swing.getTargetPane().getSelectionStart();
        targetSelectEnd = swing.getTargetPane().getSelectionEnd();
        targetSelectCaret = swing.getTargetPane().getCaret().getDot();

        // Generate regex for of just the selected portion
        int regexSelectedPortionStart = swing.getRegexPane().getSelectionStart();
        int regexSelectedPortionEnd = Math.min(swing.getRegexPane().getSelectionEnd(), regex.length());
        if (regexSelectedPortionStart < regexSelectedPortionEnd) {
            selectionRegex = regex.substring(regexSelectedPortionStart, regexSelectedPortionEnd);
        } else {
            selectionRegex = null;
        }

        //Build regex flags
        patternFlags = GuiReader.readPatternFlags(swing);

        //Get trim start/end
        targetStart = Math.min((Integer) swing.getStartOfString().getValue(), target.length());
        targetEnd = Math.min((Integer) swing.getEndOfString().getValue(), target.length());

        //Get match index
        matchIndex = (Integer) swing.getMatchNumber().getValue();

        //Get highlight info
        highlightSelection = swing.getHighlightSelection().isSelected();
        highlightGroup = swing.getHighlightGroup().isSelected();
        highlightGroupNumber = (Integer) swing.getHighlightGroupNumber().getValue();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (highlightGroup ? 1231 : 1237);
        result = prime * result + highlightGroupNumber;
        result = prime * result + (highlightSelection ? 1231 : 1237);
        result = prime * result + matchIndex;
        result = prime * result + patternFlags;
        result = prime * result + ((regex == null) ? 0 : regex.hashCode());
        result = prime * result + regexSelectCaret;
        result = prime * result + regexSelectEnd;
        result = prime * result + regexSelectStart;
        result = prime * result + ((selectionRegex == null) ? 0 : selectionRegex.hashCode());
        result = prime * result + ((target == null) ? 0 : target.hashCode());
        result = prime * result + targetEnd;
        result = prime * result + targetStart;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        HighlighterUiState other = (HighlighterUiState) obj;
        if (highlightGroup != other.highlightGroup) {
            return false;
        }
        if (highlightGroupNumber != other.highlightGroupNumber) {
            return false;
        }
        if (highlightSelection != other.highlightSelection) {
            return false;
        }
        if (matchIndex != other.matchIndex) {
            return false;
        }
        if (patternFlags != other.patternFlags) {
            return false;
        }
        if (regex == null) {
            if (other.regex != null) {
                return false;
            }
        } else if (!regex.equals(other.regex)) {
            return false;
        }
        if (regexSelectCaret != other.regexSelectCaret) {
            return false;
        }
        if (regexSelectEnd != other.regexSelectEnd) {
            return false;
        }
        if (regexSelectStart != other.regexSelectStart) {
            return false;
        }
        if (selectionRegex == null) {
            if (other.selectionRegex != null) {
                return false;
            }
        } else if (!selectionRegex.equals(other.selectionRegex)) {
            return false;
        }
        if (target == null) {
            if (other.target != null) {
                return false;
            }
        } else if (!target.equals(other.target)) {
            return false;
        }
        if (targetEnd != other.targetEnd) {
            return false;
        }
        if (targetStart != other.targetStart) {
            return false;
        }
        return true;
    }
}
