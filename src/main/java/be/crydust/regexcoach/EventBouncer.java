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
import javax.swing.Timer;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author kristof
 */
public class EventBouncer extends KeyAdapter implements ActionListener, ChangeListener, CaretListener {

    private final Timer timer;

    public EventBouncer(final Handler handler) {
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.handleEvent();
                timer.stop();
            }
        });
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

    private void onEventRecieved() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    public static interface Handler {

        public void handleEvent();
    }
}
