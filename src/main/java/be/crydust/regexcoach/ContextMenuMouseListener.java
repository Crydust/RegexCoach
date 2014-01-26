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

import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;

/**
 *
 * @author kristof
 */
public class ContextMenuMouseListener extends MouseAdapter {

    private final JPopupMenu popup = new JPopupMenu();

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
            if (!(e.getSource() instanceof JTextComponent)) {
                return;
            }

            JTextComponent textComponent = (JTextComponent) e.getSource();
            textComponent.requestFocus();

            popup.removeAll();

            popup.add(createMenuItem(textComponent, DefaultEditorKit.cutAction, "Cut"));
            popup.add(createMenuItem(textComponent, DefaultEditorKit.copyAction, "Copy"));
            popup.add(createMenuItem(textComponent, DefaultEditorKit.pasteAction, "Paste"));
            popup.addSeparator();
            popup.add(createMenuItem(textComponent, DefaultEditorKit.selectAllAction, "Select All"));

            int nx = e.getX();
            if (nx > 500) {
                nx = nx - popup.getSize().width;
            }
            popup.show(e.getComponent(), nx, e.getY() - popup.getSize().height);
        }
    }

    private static JMenuItem createMenuItem(JTextComponent textComponent, String actionName, String text) {
        Action action = textComponent.getActionMap().get(actionName);
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setText(text);
        return menuItem;
    }

}
