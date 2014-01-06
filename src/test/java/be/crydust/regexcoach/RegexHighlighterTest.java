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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author kristof
 */
public class RegexHighlighterTest {

    String regex = "";
    String selectionRegex = null;
    String target = "";
    int regexSelectStart = 0;
    int regexSelectEnd = 0;
    int regexSelectCaret = 0;
    int targetSelectStart = 0;
    int targetSelectEnd = 0;
    int targetSelectCaret = 0;
    int patternFlags = 0;
    int targetStart = 0;
    int targetEnd = 0;
    int matchIndex = 0;
    boolean highlightSelection = false;
    boolean highlightGroup = false;
    int highlightGroupNumber = 0;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateStateMinimal() throws NullPointerException, BadLocationException {

        HighlighterUiState currentState = new HighlighterUiState(regex, selectionRegex, target, regexSelectStart, regexSelectEnd, regexSelectCaret, targetSelectStart, targetSelectEnd, targetSelectCaret, patternFlags, targetStart, targetEnd, matchIndex, highlightSelection, highlightGroup, highlightGroupNumber);

        RegexHighlighter.updateState(currentState);

        assertThat(currentState.getMatchCount(), is(0));
        assertThat(currentState.getGroupCount(), is(0));
        assertThat(currentState.getMatchStart(), is(0));
        assertThat(currentState.getMatchEnd(), is(0));
        assertThat(currentState.getHighlightStart(), is(0));
        assertThat(currentState.getHighlightEnd(), is(0));
        assertThat(currentState.isSelectionMatched(), is(false));
    }

    @Test
    public void testUpdateStateSimple() throws NullPointerException, BadLocationException {
        regex = "X+";
        target = "aaaXXaaa";
        targetEnd = targetStart + target.length();

        HighlighterUiState currentState = new HighlighterUiState(regex, selectionRegex, target, regexSelectStart, regexSelectEnd, regexSelectCaret, targetSelectStart, targetSelectEnd, targetSelectCaret, patternFlags, targetStart, targetEnd, matchIndex, highlightSelection, highlightGroup, highlightGroupNumber);

        RegexHighlighter.updateState(currentState);

        assertThat(currentState.getMatchCount(), is(1));
        assertThat(currentState.getGroupCount(), is(0));
        assertThat(currentState.getMatchStart(), is(3));
        assertThat(currentState.getMatchEnd(), is(5));
        assertThat(currentState.getHighlightStart(), is(0));
        assertThat(currentState.getHighlightEnd(), is(0));
        assertThat(currentState.isSelectionMatched(), is(false));
    }

    @Test
    public void testUpdateStateOneGroup() throws NullPointerException, BadLocationException {
        regex = "(X+)";
        target = "aaaXXaaa";
        targetEnd = targetStart + target.length();

        HighlighterUiState currentState = new HighlighterUiState(regex, selectionRegex, target, regexSelectStart, regexSelectEnd, regexSelectCaret, targetSelectStart, targetSelectEnd, targetSelectCaret, patternFlags, targetStart, targetEnd, matchIndex, highlightSelection, highlightGroup, highlightGroupNumber);

        RegexHighlighter.updateState(currentState);

        assertThat(currentState.getMatchCount(), is(1));
        assertThat(currentState.getGroupCount(), is(1));
        assertThat(currentState.getMatchStart(), is(3));
        assertThat(currentState.getMatchEnd(), is(5));
        assertThat(currentState.getHighlightStart(), is(0));
        assertThat(currentState.getHighlightEnd(), is(0));
        assertThat(currentState.isSelectionMatched(), is(false));
    }

    @Test
    public void testUpdateStateSelectionMatched() throws NullPointerException, BadLocationException {
        regex = "a(X+)a";
        target = "aaaXXaaa";
        targetEnd = targetStart + target.length();
        regexSelectStart = 1;
        regexSelectEnd = 5;
        selectionRegex = regex.substring(regexSelectStart, regexSelectEnd);
        highlightSelection = true;

        HighlighterUiState currentState = new HighlighterUiState(regex, selectionRegex, target, regexSelectStart, regexSelectEnd, regexSelectCaret, targetSelectStart, targetSelectEnd, targetSelectCaret, patternFlags, targetStart, targetEnd, matchIndex, highlightSelection, highlightGroup, highlightGroupNumber);

        RegexHighlighter.updateState(currentState);

        assertThat(currentState.getMatchCount(), is(1));
        assertThat(currentState.getGroupCount(), is(1));
        assertThat(currentState.getMatchStart(), is(2));
        assertThat(currentState.getMatchEnd(), is(6));
        assertThat(currentState.getHighlightStart(), is(3));
        assertThat(currentState.getHighlightEnd(), is(5));
        assertThat(currentState.isSelectionMatched(), is(true));
    }

    @Ignore(value = "nowhere near done yet")
    @Test
    public void testUpdateStateSelectionMatchedSecondCharacter() throws NullPointerException, BadLocationException {
        regex = "XX";
        target = "aaaXXaaa";
        targetEnd = targetStart + target.length();
        regexSelectStart = 1;
        regexSelectEnd = 2;
        selectionRegex = regex.substring(regexSelectStart, regexSelectEnd);
        highlightSelection = true;

        HighlighterUiState currentState = new HighlighterUiState(regex, selectionRegex, target, regexSelectStart, regexSelectEnd, regexSelectCaret, targetSelectStart, targetSelectEnd, targetSelectCaret, patternFlags, targetStart, targetEnd, matchIndex, highlightSelection, highlightGroup, highlightGroupNumber);

        RegexHighlighter.updateState(currentState);

        assertThat(currentState.getMatchCount(), is(1));
        assertThat(currentState.getGroupCount(), is(0));
        assertThat(currentState.getMatchStart(), is(3));
        assertThat(currentState.getMatchEnd(), is(5));
        assertThat("The second X is selected, but the first X is highlighted.", currentState.getHighlightStart(), is(not(3)));
        assertThat(currentState.getHighlightStart(), is(4));
        assertThat(currentState.getHighlightEnd(), is(5));
        assertThat(currentState.isSelectionMatched(), is(true));
    }

    @Test
    public void testUpdateStateSecondGroup() throws NullPointerException, BadLocationException {
        regex = "(a)(b)";
        target = "ab";
        targetEnd = targetStart + target.length();
        highlightGroup = true;
        highlightGroupNumber = 2;

        HighlighterUiState currentState = new HighlighterUiState(regex, selectionRegex, target, regexSelectStart, regexSelectEnd, regexSelectCaret, targetSelectStart, targetSelectEnd, targetSelectCaret, patternFlags, targetStart, targetEnd, matchIndex, highlightSelection, highlightGroup, highlightGroupNumber);

        RegexHighlighter.updateState(currentState);

        assertThat(currentState.getMatchCount(), is(1));
        assertThat(currentState.getGroupCount(), is(2));
        assertThat(currentState.getMatchStart(), is(0));
        assertThat(currentState.getMatchEnd(), is(2));
        assertThat(currentState.getHighlightStart(), is(1));
        assertThat(currentState.getHighlightEnd(), is(2));
        assertThat(currentState.isSelectionMatched(), is(false));
    }

    @Test
    public void testUpdateStateSecondMatch() throws NullPointerException, BadLocationException {
        regex = "(a)(b)";
        target = "abab";
        targetEnd = targetStart + target.length();
        matchIndex = 2;

        HighlighterUiState currentState = new HighlighterUiState(regex, selectionRegex, target, regexSelectStart, regexSelectEnd, regexSelectCaret, targetSelectStart, targetSelectEnd, targetSelectCaret, patternFlags, targetStart, targetEnd, matchIndex, highlightSelection, highlightGroup, highlightGroupNumber);

        RegexHighlighter.updateState(currentState);

        assertThat(currentState.getMatchCount(), is(2));
        assertThat(currentState.getGroupCount(), is(2));
        assertThat(currentState.getMatchStart(), is(2));
        assertThat(currentState.getMatchEnd(), is(4));
        assertThat(currentState.getHighlightStart(), is(0));
        assertThat(currentState.getHighlightEnd(), is(0));
        assertThat(currentState.isSelectionMatched(), is(false));
    }
}
