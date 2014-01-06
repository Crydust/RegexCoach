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

import java.util.Objects;
import javax.swing.text.BadLocationException;

/**
 * Encapsulates the state of the UI which is polled in the constructor. All of
 * the final state fields are included in the equals method so two UI states can
 * be compared for change.
 *
 * IMPORTANT: This class MUST NEVER change the state of the UI
 */
public class HighlighterUiState {

    private final String regex;
    private final String selectionRegex;
    private final String target;
    private final int regexSelectStart;
    private final int regexSelectEnd;
    private final int regexSelectCaret;
    private final int targetSelectStart;
    private final int targetSelectEnd;
    private final int targetSelectCaret;
    private final int patternFlags;
    private final int targetStart;
    private final int targetEnd;
    private final int matchIndex;
    private final boolean highlightSelection;
    private final boolean highlightGroup;
    private final int highlightGroupNumber;

    private int matchCount = 0;
    private int groupCount = 0;
    private int matchStart = 0;
    private int matchEnd = 0;
    private int highlightStart = 0;
    private int highlightEnd = 0;
    private boolean selectionMatched = false;

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

    public HighlighterUiState(String regex, String selectionRegex, String target, int regexSelectStart, int regexSelectEnd, int regexSelectCaret, int targetSelectStart, int targetSelectEnd, int targetSelectCaret, int patternFlags, int targetStart, int targetEnd, int matchIndex, boolean highlightSelection, boolean highlightGroup, int highlightGroupNumber) {
        this.regex = regex;
        this.selectionRegex = selectionRegex;
        this.target = target;
        this.regexSelectStart = regexSelectStart;
        this.regexSelectEnd = regexSelectEnd;
        this.regexSelectCaret = regexSelectCaret;
        this.targetSelectStart = targetSelectStart;
        this.targetSelectEnd = targetSelectEnd;
        this.targetSelectCaret = targetSelectCaret;
        this.patternFlags = patternFlags;
        this.targetStart = targetStart;
        this.targetEnd = targetEnd;
        this.matchIndex = matchIndex;
        this.highlightSelection = highlightSelection;
        this.highlightGroup = highlightGroup;
        this.highlightGroupNumber = highlightGroupNumber;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.regex);
        hash = 89 * hash + Objects.hashCode(this.selectionRegex);
        hash = 89 * hash + Objects.hashCode(this.target);
        hash = 89 * hash + this.regexSelectStart;
        hash = 89 * hash + this.regexSelectEnd;
        hash = 89 * hash + this.regexSelectCaret;
        hash = 89 * hash + this.patternFlags;
        hash = 89 * hash + this.targetStart;
        hash = 89 * hash + this.targetEnd;
        hash = 89 * hash + this.matchIndex;
        hash = 89 * hash + (this.highlightSelection ? 1 : 0);
        hash = 89 * hash + (this.highlightGroup ? 1 : 0);
        hash = 89 * hash + this.highlightGroupNumber;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HighlighterUiState other = (HighlighterUiState) obj;
        if (!Objects.equals(this.regex, other.regex)) {
            return false;
        }
        if (!Objects.equals(this.selectionRegex, other.selectionRegex)) {
            return false;
        }
        if (!Objects.equals(this.target, other.target)) {
            return false;
        }
        if (this.regexSelectStart != other.regexSelectStart) {
            return false;
        }
        if (this.regexSelectEnd != other.regexSelectEnd) {
            return false;
        }
        if (this.regexSelectCaret != other.regexSelectCaret) {
            return false;
        }
        if (this.patternFlags != other.patternFlags) {
            return false;
        }
        if (this.targetStart != other.targetStart) {
            return false;
        }
        if (this.targetEnd != other.targetEnd) {
            return false;
        }
        if (this.matchIndex != other.matchIndex) {
            return false;
        }
        if (this.highlightSelection != other.highlightSelection) {
            return false;
        }
        if (this.highlightGroup != other.highlightGroup) {
            return false;
        }
        if (this.highlightGroupNumber != other.highlightGroupNumber) {
            return false;
        }
        return true;
    }

    public String getRegex() {
        return regex;
    }

    public String getSelectionRegex() {
        return selectionRegex;
    }

    public String getTarget() {
        return target;
    }

    public int getRegexSelectStart() {
        return regexSelectStart;
    }

    public int getRegexSelectEnd() {
        return regexSelectEnd;
    }

    public int getRegexSelectCaret() {
        return regexSelectCaret;
    }

    public int getTargetSelectStart() {
        return targetSelectStart;
    }

    public int getTargetSelectEnd() {
        return targetSelectEnd;
    }

    public int getTargetSelectCaret() {
        return targetSelectCaret;
    }

    public int getPatternFlags() {
        return patternFlags;
    }

    public int getTargetStart() {
        return targetStart;
    }

    public int getTargetEnd() {
        return targetEnd;
    }

    public int getMatchIndex() {
        return matchIndex;
    }

    public boolean isHighlightSelection() {
        return highlightSelection;
    }

    public boolean isHighlightGroup() {
        return highlightGroup;
    }

    public int getHighlightGroupNumber() {
        return highlightGroupNumber;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

    public int getMatchStart() {
        return matchStart;
    }

    public void setMatchStart(int matchStart) {
        this.matchStart = matchStart;
    }

    public int getMatchEnd() {
        return matchEnd;
    }

    public void setMatchEnd(int matchEnd) {
        this.matchEnd = matchEnd;
    }

    public int getHighlightStart() {
        return highlightStart;
    }

    public void setHighlightStart(int highlightStart) {
        this.highlightStart = highlightStart;
    }

    public int getHighlightEnd() {
        return highlightEnd;
    }

    public void setHighlightEnd(int highlightEnd) {
        this.highlightEnd = highlightEnd;
    }

    public boolean isSelectionMatched() {
        return selectionMatched;
    }

    public void setSelectionMatched(boolean selectionMatched) {
        this.selectionMatched = selectionMatched;
    }
}
