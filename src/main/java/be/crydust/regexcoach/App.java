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

public class App implements Runnable {

    public static void main(String[] args) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new App());
    }

    @Override
    public void run() {
        Gui swing = new Gui();
        
        RegexHighlighter highlighter = new RegexHighlighter(swing);
        RegexReplacer replacer = new RegexReplacer(swing);

        EventBouncer highlighterEventBouncer = new EventBouncer(highlighter);
        EventBouncer replacerEventBouncer = new EventBouncer(replacer);
        
        // Text area mod listeners
        swing.getRegexPane().addKeyListener(highlighterEventBouncer);
        swing.getRegexPane().addCaretListener(highlighterEventBouncer);
        swing.getTargetPane().addKeyListener(highlighterEventBouncer);

        // Regex Options listeners
        swing.getRegexOptCaseInsensitive().addActionListener(highlighterEventBouncer);
        swing.getRegexOptMultiline().addActionListener(highlighterEventBouncer);
        swing.getRegexOptDotAll().addActionListener(highlighterEventBouncer);
        swing.getRegexOptComments().addActionListener(highlighterEventBouncer);
        swing.getRegexOptCanonEq().addActionListener(highlighterEventBouncer);
        swing.getRegexOptLiteral().addActionListener(highlighterEventBouncer);
        swing.getRegexOptUnicodeCase().addActionListener(highlighterEventBouncer);
        swing.getRegexOptUnixLines().addActionListener(highlighterEventBouncer);

        // Match change listeners
        swing.getMatchNumber().addChangeListener(highlighterEventBouncer);
        swing.getStartOfString().addChangeListener(highlighterEventBouncer);
        swing.getEndOfString().addChangeListener(highlighterEventBouncer);

        // Selection listeners
        swing.getHighlightSelection().addActionListener(highlighterEventBouncer);
        swing.getHighlightNone().addActionListener(highlighterEventBouncer);
        swing.getHighlightGroup().addActionListener(highlighterEventBouncer);
        swing.getHighlightGroupNumber().addChangeListener(highlighterEventBouncer);

        // Text area mod listeners
        swing.getRegexPane().addKeyListener(replacerEventBouncer);
        swing.getTargetPane().addKeyListener(replacerEventBouncer);
        swing.getReplacementPane().addKeyListener(replacerEventBouncer);

        // Regex Options listeners
        swing.getRegexOptCaseInsensitive().addActionListener(replacerEventBouncer);
        swing.getRegexOptMultiline().addActionListener(replacerEventBouncer);
        swing.getRegexOptDotAll().addActionListener(replacerEventBouncer);
        swing.getRegexOptComments().addActionListener(replacerEventBouncer);
        swing.getRegexOptCanonEq().addActionListener(replacerEventBouncer);
        swing.getRegexOptLiteral().addActionListener(replacerEventBouncer);
        swing.getRegexOptUnicodeCase().addActionListener(replacerEventBouncer);
        swing.getRegexOptUnixLines().addActionListener(replacerEventBouncer);

        swing.pack();
        swing.setLocationRelativeTo(null);
        swing.setVisible(true);
        highlighter.doHighlights();
    }
}
