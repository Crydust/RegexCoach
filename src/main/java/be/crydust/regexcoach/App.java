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

public class App {

    public static void main(String[] args) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Gui swing = new Gui();
                RegexHighlighter highlighter = new RegexHighlighter(swing);
                RegexReplacer replacer = new RegexReplacer(swing);

                // Text area mod listeners
                swing.getRegexPane().addKeyListener(highlighter);
                swing.getRegexPane().addCaretListener(highlighter);
                swing.getTargetPane().addKeyListener(highlighter);

                // Regex Options listeners
                swing.getRegexOptCaseInsensitive().addActionListener(highlighter);
                swing.getRegexOptMultiline().addActionListener(highlighter);
                swing.getRegexOptDotAll().addActionListener(highlighter);
                swing.getRegexOptComments().addActionListener(highlighter);
                swing.getRegexOptCanonEq().addActionListener(highlighter);
                swing.getRegexOptLiteral().addActionListener(highlighter);
                swing.getRegexOptUnicodeCase().addActionListener(highlighter);
                swing.getRegexOptUnixLines().addActionListener(highlighter);

                // Match change listeners
                swing.getMatchNumber().addChangeListener(highlighter);
                swing.getStartOfString().addChangeListener(highlighter);
                swing.getEndOfString().addChangeListener(highlighter);

                // Selection listeners
                swing.getHighlightSelection().addActionListener(highlighter);
                swing.getHighlightNone().addActionListener(highlighter);
                swing.getHighlightGroup().addActionListener(highlighter);
                swing.getHighlightGroupNumber().addChangeListener(highlighter);

                
                // Text area mod listeners
                swing.getRegexPane().addKeyListener(replacer);
                swing.getTargetPane().addKeyListener(replacer);
                swing.getReplacementPane().addKeyListener(replacer);

                // Regex Options listeners
                swing.getRegexOptCaseInsensitive().addActionListener(replacer);
                swing.getRegexOptMultiline().addActionListener(replacer);
                swing.getRegexOptDotAll().addActionListener(replacer);
                swing.getRegexOptComments().addActionListener(replacer);
                swing.getRegexOptCanonEq().addActionListener(replacer);
                swing.getRegexOptLiteral().addActionListener(replacer);
                swing.getRegexOptUnicodeCase().addActionListener(replacer);
                swing.getRegexOptUnixLines().addActionListener(replacer);

                
                
                swing.pack();
                swing.setLocationRelativeTo(null);
                swing.setVisible(true);
                highlighter.doHighlights();
            }
        });
    }
}
