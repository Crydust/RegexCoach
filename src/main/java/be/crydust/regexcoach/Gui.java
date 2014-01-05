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

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextPane;

public class Gui extends javax.swing.JFrame {

    /**
     * Creates new form Gui
     */
    public Gui() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        highlightButtonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        regexPane = new javax.swing.JTextPane();
        regexStatus = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        targetPane = new javax.swing.JTextPane();
        targetStatus = new javax.swing.JLabel();
        bottomPanel = new javax.swing.JPanel();
        regexOptCaseInsensitive = new javax.swing.JCheckBox();
        regexOptMultiline = new javax.swing.JCheckBox();
        regexOptDotAll = new javax.swing.JCheckBox();
        regexOptComments = new javax.swing.JCheckBox();
        regexOptCanonEq = new javax.swing.JCheckBox();
        regexOptLiteral = new javax.swing.JCheckBox();
        regexOptUnicodeCase = new javax.swing.JCheckBox();
        regexOptUnixLines = new javax.swing.JCheckBox();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("The Java Regex Coach");
        setPreferredSize(new java.awt.Dimension(600, 600));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jSplitPane2.setDividerLocation(200);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Regular expression:");
        jPanel3.add(jLabel1, java.awt.BorderLayout.NORTH);

        jScrollPane1.setViewportView(regexPane);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        regexStatus.setText(" ");
        jPanel3.add(regexStatus, java.awt.BorderLayout.SOUTH);

        jSplitPane2.setTopComponent(jPanel3);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel2.setText("Target string:");
        jPanel4.add(jLabel2, java.awt.BorderLayout.NORTH);

        jScrollPane3.setViewportView(targetPane);

        jPanel4.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        targetStatus.setText(" ");
        jPanel4.add(targetStatus, java.awt.BorderLayout.SOUTH);

        jSplitPane2.setRightComponent(jPanel4);

        jPanel1.add(jSplitPane2, java.awt.BorderLayout.CENTER);

        bottomPanel.setLayout(new java.awt.GridLayout(4, 4));

        regexOptCaseInsensitive.setText("Case Insensitive");
        regexOptCaseInsensitive.setActionCommand("");
        bottomPanel.add(regexOptCaseInsensitive);

        regexOptMultiline.setText("Multline");
        regexOptMultiline.setActionCommand("");
        regexOptMultiline.setName(""); // NOI18N
        bottomPanel.add(regexOptMultiline);

        regexOptDotAll.setText("Dot All");
        regexOptDotAll.setActionCommand("");
        regexOptDotAll.setName(""); // NOI18N
        bottomPanel.add(regexOptDotAll);

        regexOptComments.setText("Comments");
        bottomPanel.add(regexOptComments);

        regexOptCanonEq.setText("Canonical Eq");
        bottomPanel.add(regexOptCanonEq);

        regexOptLiteral.setText("Literal");
        bottomPanel.add(regexOptLiteral);

        regexOptUnicodeCase.setText("Unicode-Aware Case");
        bottomPanel.add(regexOptUnicodeCase);

        regexOptUnixLines.setText("Unix Lines");
        bottomPanel.add(regexOptUnixLines);

        jLabel3.setText("Highllight:");
        bottomPanel.add(jLabel3);

        highlightButtonGroup.add(highlightSelection);
        highlightSelection.setText("selection");
        highlightSelection.setEnabled(false);
        bottomPanel.add(highlightSelection);

        highlightButtonGroup.add(highlightNone);
        highlightNone.setText("nothing");
        bottomPanel.add(highlightNone);

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

        bottomPanel.add(jPanel7);
        bottomPanel.add(filler2);

        jLabel5.setText("Match");
        jPanel11.add(jLabel5);

        matchNumber.setModel(new javax.swing.SpinnerNumberModel(0, 0, 0, 1));
        jPanel11.add(matchNumber);

        jLabel6.setText("/");
        jPanel11.add(jLabel6);

        matchCount.setText("0");
        jPanel11.add(matchCount);

        bottomPanel.add(jPanel11);

        jLabel7.setText("String Start");
        jPanel12.add(jLabel7);

        startOfString.setModel(new javax.swing.SpinnerNumberModel(0, 0, 0, 1));
        jPanel12.add(startOfString);

        bottomPanel.add(jPanel12);

        jLabel8.setText("String End");
        jPanel13.add(jLabel8);

        endOfString.setModel(new javax.swing.SpinnerNumberModel(0, 0, 0, 1));
        jPanel13.add(endOfString);

        bottomPanel.add(jPanel13);

        jPanel1.add(bottomPanel, java.awt.BorderLayout.SOUTH);

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JSpinner endOfString;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JLabel groupCount;
    private javax.swing.ButtonGroup highlightButtonGroup;
    private javax.swing.JRadioButton highlightGroup;
    private javax.swing.JSpinner highlightGroupNumber;
    private javax.swing.JRadioButton highlightNone;
    private javax.swing.JRadioButton highlightSelection;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JLabel matchCount;
    private javax.swing.JSpinner matchNumber;
    private javax.swing.JCheckBox regexOptCanonEq;
    private javax.swing.JCheckBox regexOptCaseInsensitive;
    private javax.swing.JCheckBox regexOptComments;
    private javax.swing.JCheckBox regexOptDotAll;
    private javax.swing.JCheckBox regexOptLiteral;
    private javax.swing.JCheckBox regexOptMultiline;
    private javax.swing.JCheckBox regexOptUnicodeCase;
    private javax.swing.JCheckBox regexOptUnixLines;
    private javax.swing.JTextPane regexPane;
    private javax.swing.JLabel regexStatus;
    private javax.swing.JSpinner startOfString;
    private javax.swing.JTextPane targetPane;
    private javax.swing.JLabel targetStatus;
    // End of variables declaration//GEN-END:variables
}
