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
import javax.swing.JRadioButton;
import javax.swing.text.BadLocationException;

/**
 * Encapsulates the state of the UI which is polled in the constructor. All of
 * the final state fields are included in the equals method so two UI states can
 * be compared for change.
 *
 * IMPORTANT: This class MUST NEVER change the state of the UI
 */
public class SplitterUiState {

    private final String regex;
    private final String target;
    private final int patternFlags;
    private final int splitLimit;
    private final String divider;

    private String splitString;

    public SplitterUiState(Gui swing) {
        // note: get the text from the underlying document,
        // otherwise carriage return/line feeds different when using the JTextPane text
        try {
            regex = GuiReader.readRegex(swing);
            target = GuiReader.readTarget(swing);
        } catch (BadLocationException ex) {
            throw new UnrecoverableException(ex);
        }

        // Build regex flags
        patternFlags = GuiReader.readPatternFlags(swing);

        int newSplitLimit;
        try {
            newSplitLimit = Integer.valueOf(swing.getSplitLimitTextField().getText());
        } catch (NumberFormatException e) {
            newSplitLimit = 0;
        }
        splitLimit = newSplitLimit;

        divider = GuiReader.getSelectedButtonText(swing.getDividerButtonGroup());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.regex);
        hash = 29 * hash + Objects.hashCode(this.target);
        hash = 29 * hash + this.patternFlags;
        hash = 29 * hash + Objects.hashCode(this.divider);
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
        final SplitterUiState other = (SplitterUiState) obj;
        if (!Objects.equals(this.regex, other.regex)) {
            return false;
        }
        if (!Objects.equals(this.target, other.target)) {
            return false;
        }
        if (this.patternFlags != other.patternFlags) {
            return false;
        }
        if (!Objects.equals(this.divider, other.divider)) {
            return false;
        }
        return true;
    }

    public String getRegex() {
        return regex;
    }

    public String getTarget() {
        return target;
    }

    public int getPatternFlags() {
        return patternFlags;
    }

    public String getDivider() {
        return divider;
    }

    public int getSplitLimit() {
        return splitLimit;
    }

    public String getSplitString() {
        return splitString;
    }

    public void setSplitString(String splitString) {
        this.splitString = splitString;
    }

}
