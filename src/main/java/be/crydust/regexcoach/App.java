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

import javax.swing.WindowConstants;

public class App implements Runnable {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new App());
    }

    @Override
    public void run() {
        Gui swing = new Gui();
        swing.initBehaviour();
        swing.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        swing.setVisible(true);
    }
}
