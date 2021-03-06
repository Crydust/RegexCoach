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
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class Gui extends javax.swing.JFrame {

    /**
     * Creates new form Gui
     */
    public Gui() {
        initComponents();
    }

    public void initBehaviour() {
        // Create the controllers
        RegexHighlighter highlighter = new RegexHighlighter(this);
        RegexReplacer replacer = new RegexReplacer(this);
        RegexSplitter splitter = new RegexSplitter(this);
        
        // listeners are bundled in "busses"
        // which hide the event listener implementation from the controllers
        EventBus commonEventBus = new EventBus()
                .addHandler(highlighter)
                .addHandler(replacer)
                .addHandler(splitter);
        EventBus highlighterEventBus = new EventBus(highlighter);
        EventBus replacerEventBus = new EventBus(replacer);
        EventBus splitterEventBus = new EventBus(splitter);

        // Common
        // ======
        
        // Text area mod listeners
        this.getRegexPane().getDocument().addDocumentListener(commonEventBus);
        this.getTargetPane().getDocument().addDocumentListener(commonEventBus);

        // Regex Options listeners
        this.getRegexOptCaseInsensitive().addActionListener(commonEventBus);
        this.getRegexOptMultiline().addActionListener(commonEventBus);
        this.getRegexOptDotAll().addActionListener(commonEventBus);
        this.getRegexOptComments().addActionListener(commonEventBus);
        this.getRegexOptCanonEq().addActionListener(commonEventBus);
        this.getRegexOptLiteral().addActionListener(commonEventBus);
        this.getRegexOptUnicodeCase().addActionListener(commonEventBus);
        this.getRegexOptUnixLines().addActionListener(commonEventBus);

        // Highlighter
        // ===========
        
        // Selection listeners
        this.getHighlightSelection().addActionListener(highlighterEventBus);
        this.getHighlightNone().addActionListener(highlighterEventBus);
        this.getHighlightGroup().addActionListener(highlighterEventBus);
        this.getHighlightGroupNumber().addChangeListener(highlighterEventBus);

        // Text area mod listeners
        this.getRegexPane().addKeyListener(highlighterEventBus);
        this.getRegexPane().addKeyListener(highlighterEventBus);
        this.getRegexPane().addCaretListener(highlighterEventBus);

        // Match change listeners
        this.getMatchNumber().addChangeListener(highlighterEventBus);
        this.getStartOfString().addChangeListener(highlighterEventBus);
        this.getEndOfString().addChangeListener(highlighterEventBus);

        // Replacer
        // ========
        
        // Text area mod listeners
        this.getReplacementPane().getDocument().addDocumentListener(replacerEventBus);

        // Splitter
        // ========
        
        // Divider radio buttons listeners
        Enumeration<AbstractButton> dividerButtons = this.getDividerButtonGroup().getElements();
        while (dividerButtons.hasMoreElements()) {
            dividerButtons.nextElement().addActionListener(splitterEventBus);
        }

        // Split limit listeners
        this.getSplitLimitTextField().getDocument().addDocumentListener(splitterEventBus);

        // Context menu
        // ============
        
        // Add contextmenu to every textcomponent
        ContextMenuMouseListener contextMenu = new ContextMenuMouseListener();
        this.getRegexPane().addMouseListener(contextMenu);
        this.getTargetPane().addMouseListener(contextMenu);
        this.getReplacementPane().addMouseListener(contextMenu);
        this.getSubstitutionArea().addMouseListener(contextMenu);
        this.getSplitArea().addMouseListener(contextMenu);
        this.getSplitLimitTextField().addMouseListener(contextMenu);

        // Resize and center the window
        this.pack();
        this.setLocationRelativeTo(null);

        // tell all controllers that the application has started
        commonEventBus.actionPerformed(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        highlightButtonGroup = new javax.swing.ButtonGroup();
        dividerButtonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        regexPane = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
        regexStatus = new javax.swing.JLabel();
        regexOptCaseInsensitive = new javax.swing.JCheckBox();
        regexOptMultiline = new javax.swing.JCheckBox();
        regexOptDotAll = new javax.swing.JCheckBox();
        regexOptComments = new javax.swing.JCheckBox();
        regexOptCanonEq = new javax.swing.JCheckBox();
        regexOptLiteral = new javax.swing.JCheckBox();
        regexOptUnicodeCase = new javax.swing.JCheckBox();
        regexOptUnixLines = new javax.swing.JCheckBox();
        regexOptUnicodeCharacterClass = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        targetPane = new javax.swing.JTextPane();
        targetStatus = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        controlPanelWrapper = new javax.swing.JPanel();
        controlPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        highlightSelection = new javax.swing.JRadioButton();
        highlightNone = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        highlightGroup = new javax.swing.JRadioButton();
        highlightGroupNumber = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        groupCount = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jPanel11 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        matchNumber = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        matchCount = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        startOfString = new javax.swing.JSpinner();
        jPanel13 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        endOfString = new javax.swing.JSpinner();
        replacePanel = new javax.swing.JPanel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        replacementPane = new javax.swing.JTextPane();
        replacementStatus = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        substitutionArea = new javax.swing.JTextArea();
        splitPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        splitArea = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jLabel13 = new javax.swing.JLabel();
        splitLimitTextField = new javax.swing.JTextField();

        setTitle("The Java Regex Coach");
        setMinimumSize(new java.awt.Dimension(600, 600));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jSplitPane2.setDividerLocation(150);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Regular expression:");
        jPanel3.add(jLabel1, java.awt.BorderLayout.NORTH);

        regexPane.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        jScrollPane1.setViewportView(regexPane);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        regexStatus.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(regexStatus, gridBagConstraints);

        regexOptCaseInsensitive.setText("i");
        regexOptCaseInsensitive.setToolTipText("Case Insensitive");
        regexOptCaseInsensitive.setActionCommand("");
        jPanel2.add(regexOptCaseInsensitive, new java.awt.GridBagConstraints());

        regexOptMultiline.setText("m");
        regexOptMultiline.setToolTipText("Multline");
        regexOptMultiline.setActionCommand("");
        regexOptMultiline.setName(""); // NOI18N
        jPanel2.add(regexOptMultiline, new java.awt.GridBagConstraints());

        regexOptDotAll.setText("s");
        regexOptDotAll.setToolTipText("Dot All");
        regexOptDotAll.setActionCommand("");
        regexOptDotAll.setName(""); // NOI18N
        jPanel2.add(regexOptDotAll, new java.awt.GridBagConstraints());

        regexOptComments.setText("x");
        regexOptComments.setToolTipText("Comments");
        jPanel2.add(regexOptComments, new java.awt.GridBagConstraints());

        regexOptCanonEq.setText("ce");
        regexOptCanonEq.setToolTipText("Canonical Eq");
        jPanel2.add(regexOptCanonEq, new java.awt.GridBagConstraints());

        regexOptLiteral.setText("lit");
        regexOptLiteral.setToolTipText("Literal");
        jPanel2.add(regexOptLiteral, new java.awt.GridBagConstraints());

        regexOptUnicodeCase.setText("u");
        regexOptUnicodeCase.setToolTipText("Unicode-Aware Case");
        jPanel2.add(regexOptUnicodeCase, new java.awt.GridBagConstraints());

        regexOptUnixLines.setText("d");
        regexOptUnixLines.setToolTipText("Unix Lines");
        jPanel2.add(regexOptUnixLines, new java.awt.GridBagConstraints());

        regexOptUnicodeCharacterClass.setText("U");
        regexOptUnicodeCharacterClass.setToolTipText("Unicode character classes");
        jPanel2.add(regexOptUnicodeCharacterClass, new java.awt.GridBagConstraints());

        jPanel3.add(jPanel2, java.awt.BorderLayout.SOUTH);

        jSplitPane2.setTopComponent(jPanel3);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel2.setText("Target string:");
        jPanel4.add(jLabel2, java.awt.BorderLayout.NORTH);

        targetPane.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        jScrollPane3.setViewportView(targetPane);

        jPanel4.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        targetStatus.setText(" ");
        jPanel4.add(targetStatus, java.awt.BorderLayout.SOUTH);

        jSplitPane2.setRightComponent(jPanel4);

        jSplitPane1.setLeftComponent(jSplitPane2);

        controlPanelWrapper.setLayout(new java.awt.BorderLayout());

        controlPanel.setLayout(new java.awt.GridLayout(2, 4));

        jLabel3.setText("Highllight:");
        controlPanel.add(jLabel3);

        highlightButtonGroup.add(highlightSelection);
        highlightSelection.setText("selection");
        highlightSelection.setEnabled(false);
        controlPanel.add(highlightSelection);

        highlightButtonGroup.add(highlightNone);
        highlightNone.setText("nothing");
        controlPanel.add(highlightNone);

        highlightButtonGroup.add(highlightGroup);
        highlightGroup.setSelected(true);
        highlightGroup.setText("Group");
        jPanel7.add(highlightGroup);

        highlightGroupNumber.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1, 1));
        jPanel7.add(highlightGroupNumber);

        jLabel4.setText("/");
        jPanel7.add(jLabel4);

        groupCount.setText("0");
        jPanel7.add(groupCount);

        controlPanel.add(jPanel7);
        controlPanel.add(filler2);

        jLabel5.setText("Match");
        jPanel11.add(jLabel5);

        matchNumber.setModel(new javax.swing.SpinnerNumberModel(0, 0, 0, 1));
        jPanel11.add(matchNumber);

        jLabel6.setText("/");
        jPanel11.add(jLabel6);

        matchCount.setText("0");
        jPanel11.add(matchCount);

        controlPanel.add(jPanel11);

        jLabel7.setText("String Start");
        jPanel12.add(jLabel7);

        startOfString.setModel(new javax.swing.SpinnerNumberModel(0, 0, 0, 1));
        jPanel12.add(startOfString);

        controlPanel.add(jPanel12);

        jLabel8.setText("String End");
        jPanel13.add(jLabel8);

        endOfString.setModel(new javax.swing.SpinnerNumberModel(0, 0, 0, 1));
        jPanel13.add(endOfString);

        controlPanel.add(jPanel13);

        controlPanelWrapper.add(controlPanel, java.awt.BorderLayout.NORTH);

        jTabbedPane1.addTab("Control", controlPanelWrapper);

        replacePanel.setLayout(new java.awt.BorderLayout());

        jSplitPane3.setDividerLocation(100);
        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel9.setText("Replacement string:");
        jPanel5.add(jLabel9, java.awt.BorderLayout.NORTH);

        replacementPane.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        jScrollPane2.setViewportView(replacementPane);

        jPanel5.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        replacementStatus.setText(" ");
        jPanel5.add(replacementStatus, java.awt.BorderLayout.SOUTH);

        jSplitPane3.setLeftComponent(jPanel5);

        jPanel6.setLayout(new java.awt.BorderLayout());

        jLabel11.setText("Replacement result:");
        jPanel6.add(jLabel11, java.awt.BorderLayout.NORTH);

        substitutionArea.setColumns(20);
        substitutionArea.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        substitutionArea.setRows(5);
        substitutionArea.setMargin(new java.awt.Insets(3, 3, 3, 3));
        jScrollPane4.setViewportView(substitutionArea);

        jPanel6.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jSplitPane3.setRightComponent(jPanel6);

        replacePanel.add(jSplitPane3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Replace", replacePanel);

        splitPanel.setLayout(new java.awt.BorderLayout());

        jLabel10.setText("Split string:");
        splitPanel.add(jLabel10, java.awt.BorderLayout.NORTH);

        splitArea.setColumns(20);
        splitArea.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        splitArea.setRows(5);
        splitArea.setMargin(new java.awt.Insets(3, 3, 3, 3));
        jScrollPane5.setViewportView(splitArea);

        splitPanel.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jPanel8.setLayout(new java.awt.GridBagLayout());

        jLabel12.setText("Divider:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel8.add(jLabel12, gridBagConstraints);

        dividerButtonGroup.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("|");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel8.add(jRadioButton1, gridBagConstraints);

        dividerButtonGroup.add(jRadioButton2);
        jRadioButton2.setText("¦");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel8.add(jRadioButton2, gridBagConstraints);

        dividerButtonGroup.add(jRadioButton3);
        jRadioButton3.setText("¬");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel8.add(jRadioButton3, gridBagConstraints);

        dividerButtonGroup.add(jRadioButton4);
        jRadioButton4.setText("¶");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel8.add(jRadioButton4, gridBagConstraints);

        dividerButtonGroup.add(jRadioButton5);
        jRadioButton5.setText("®");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel8.add(jRadioButton5, gridBagConstraints);

        dividerButtonGroup.add(jRadioButton6);
        jRadioButton6.setText("©");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel8.add(jRadioButton6, gridBagConstraints);

        dividerButtonGroup.add(jRadioButton7);
        jRadioButton7.setText("¥");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel8.add(jRadioButton7, gridBagConstraints);

        dividerButtonGroup.add(jRadioButton8);
        jRadioButton8.setText("block");
        jRadioButton8.setToolTipText("(=u2588)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel8.add(jRadioButton8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.weightx = 1.0;
        jPanel8.add(filler1, gridBagConstraints);

        jLabel13.setText("Limit:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel8.add(jLabel13, gridBagConstraints);

        splitLimitTextField.setMinimumSize(new java.awt.Dimension(30, 19));
        splitLimitTextField.setPreferredSize(new java.awt.Dimension(30, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel8.add(splitLimitTextField, gridBagConstraints);

        splitPanel.add(jPanel8, java.awt.BorderLayout.SOUTH);

        jTabbedPane1.addTab("Split", splitPanel);

        jSplitPane1.setRightComponent(jTabbedPane1);

        jPanel1.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            // ignore
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Gui().setVisible(true);
            }
        });
    }

    public JSpinner getEndOfString() {
        return endOfString;
    }

    public JLabel getGroupCount() {
        return groupCount;
    }

    public ButtonGroup getHighlightButtonGroup() {
        return highlightButtonGroup;
    }

    public JRadioButton getHighlightGroup() {
        return highlightGroup;
    }

    public JSpinner getHighlightGroupNumber() {
        return highlightGroupNumber;
    }

    public JRadioButton getHighlightNone() {
        return highlightNone;
    }

    public JRadioButton getHighlightSelection() {
        return highlightSelection;
    }

    public JLabel getMatchCount() {
        return matchCount;
    }

    public JSpinner getMatchNumber() {
        return matchNumber;
    }

    public JCheckBox getRegexOptCanonEq() {
        return regexOptCanonEq;
    }

    public JCheckBox getRegexOptCaseInsensitive() {
        return regexOptCaseInsensitive;
    }

    public JCheckBox getRegexOptComments() {
        return regexOptComments;
    }

    public JCheckBox getRegexOptDotAll() {
        return regexOptDotAll;
    }

    public JCheckBox getRegexOptLiteral() {
        return regexOptLiteral;
    }

    public JCheckBox getRegexOptMultiline() {
        return regexOptMultiline;
    }

    public JCheckBox getRegexOptUnicodeCase() {
        return regexOptUnicodeCase;
    }

    public JCheckBox getRegexOptUnixLines() {
        return regexOptUnixLines;
    }

    public JCheckBox getRegexOptUnicodeCharacterClass() {
        return regexOptUnicodeCharacterClass;
    }

    public JTextPane getRegexPane() {
        return regexPane;
    }

    public JLabel getRegexStatus() {
        return regexStatus;
    }

    public JSpinner getStartOfString() {
        return startOfString;
    }

    public JTextPane getTargetPane() {
        return targetPane;
    }

    public JLabel getTargetStatus() {
        return targetStatus;
    }

    public JTextPane getReplacementPane() {
        return replacementPane;
    }

    public JTextArea getSubstitutionArea() {
        return substitutionArea;
    }

    public JLabel getReplacementStatus() {
        return replacementStatus;
    }

    public ButtonGroup getDividerButtonGroup() {
        return dividerButtonGroup;
    }

    public JTextArea getSplitArea() {
        return splitArea;
    }

    public JTextField getSplitLimitTextField() {
        return splitLimitTextField;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controlPanel;
    private javax.swing.JPanel controlPanelWrapper;
    private javax.swing.ButtonGroup dividerButtonGroup;
    private javax.swing.JSpinner endOfString;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JLabel groupCount;
    private javax.swing.ButtonGroup highlightButtonGroup;
    private javax.swing.JRadioButton highlightGroup;
    private javax.swing.JSpinner highlightGroupNumber;
    private javax.swing.JRadioButton highlightNone;
    private javax.swing.JRadioButton highlightSelection;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel matchCount;
    private javax.swing.JSpinner matchNumber;
    private javax.swing.JCheckBox regexOptCanonEq;
    private javax.swing.JCheckBox regexOptCaseInsensitive;
    private javax.swing.JCheckBox regexOptComments;
    private javax.swing.JCheckBox regexOptDotAll;
    private javax.swing.JCheckBox regexOptLiteral;
    private javax.swing.JCheckBox regexOptMultiline;
    private javax.swing.JCheckBox regexOptUnicodeCase;
    private javax.swing.JCheckBox regexOptUnicodeCharacterClass;
    private javax.swing.JCheckBox regexOptUnixLines;
    private javax.swing.JTextPane regexPane;
    private javax.swing.JLabel regexStatus;
    private javax.swing.JPanel replacePanel;
    private javax.swing.JTextPane replacementPane;
    private javax.swing.JLabel replacementStatus;
    private javax.swing.JTextArea splitArea;
    private javax.swing.JTextField splitLimitTextField;
    private javax.swing.JPanel splitPanel;
    private javax.swing.JSpinner startOfString;
    private javax.swing.JTextArea substitutionArea;
    private javax.swing.JTextPane targetPane;
    private javax.swing.JLabel targetStatus;
    // End of variables declaration//GEN-END:variables
}
