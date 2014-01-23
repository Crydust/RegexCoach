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

import java.util.Enumeration;
import java.util.regex.Pattern;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.text.BadLocationException;

/**
 *
 * @author kristof
 */
public final class GuiReader {

    private GuiReader() {
    }

    /**
     * Build regex flags
     *
     * @param swing
     * @return
     */
    public static int readPatternFlags(Gui swing) {
        int patternFlagsBuilder = 0;
        if (swing.getRegexOptCaseInsensitive().isSelected()) {
            patternFlagsBuilder |= Pattern.CASE_INSENSITIVE;
        }
        if (swing.getRegexOptMultiline().isSelected()) {
            patternFlagsBuilder |= Pattern.MULTILINE;
        }
        if (swing.getRegexOptDotAll().isSelected()) {
            patternFlagsBuilder |= Pattern.DOTALL;
        }
        if (swing.getRegexOptComments().isSelected()) {
            patternFlagsBuilder |= Pattern.COMMENTS;
        }
        if (swing.getRegexOptCanonEq().isSelected()) {
            patternFlagsBuilder |= Pattern.CANON_EQ;
        }
        if (swing.getRegexOptLiteral().isSelected()) {
            patternFlagsBuilder |= Pattern.LITERAL;
        }
        if (swing.getRegexOptUnicodeCase().isSelected()) {
            patternFlagsBuilder |= Pattern.UNICODE_CASE;
        }
        if (swing.getRegexOptUnixLines().isSelected()) {
            patternFlagsBuilder |= Pattern.UNIX_LINES;
        }
        if (swing.getRegexOptUnicodeCharacterClass().isSelected()) {
            patternFlagsBuilder |= Pattern.UNICODE_CHARACTER_CLASS;
        }
        return patternFlagsBuilder;
    }

    public static String readTarget(Gui swing) throws BadLocationException {
        return swing.getTargetPane().getDocument().getText(0, swing.getTargetPane().getDocument().getLength());
    }

    public static String readRegex(Gui swing) throws BadLocationException {
        return swing.getRegexPane().getDocument().getText(0, swing.getRegexPane().getDocument().getLength());
    }

    public static String readReplacement(Gui swing) throws BadLocationException {
        return swing.getReplacementPane().getDocument().getText(0, swing.getReplacementPane().getDocument().getLength());
    }

    public static String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }
}
