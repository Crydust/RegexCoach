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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author kristof
 */
public class EventBus extends KeyAdapter implements ActionListener, ChangeListener, CaretListener, DocumentListener {

    private final Set<Handler> handlers;

    public EventBus() {
        handlers = Collections.newSetFromMap(new WeakHashMap<Handler, Boolean>());
    }

    public EventBus(final Handler handler) {
        this();
        addHandler(handler);
    }

    public final EventBus addHandler(final Handler handler) {
        synchronized (handlers) {
            handlers.add(handler);
        }
        return this;
    }

    private void onEventRecieved() {
        synchronized (handlers) {
            for (Handler handler : handlers) {
                handler.handleEvent();
            }
        }
    }

    public static interface Handler {

        public void handleEvent();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        onEventRecieved();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        onEventRecieved();
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        onEventRecieved();
    }

    @Override
    public void keyReleased(KeyEvent event) {
        onEventRecieved();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        onEventRecieved();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        onEventRecieved();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        onEventRecieved();
    }

}
