/*
    Copyright 2020-2021 Andrei Datcu.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */
package andreid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class turboboost {
    JFrame frame = new JFrame("Turbo Boost Switcher");

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JFrame getFrame() {
        boolean tbCompatible = new File("/sys/devices/system/cpu/intel_pstate/no_turbo").exists();

        if (tbCompatible == false){
            JOptionPane.showMessageDialog(null, "Your Mac doesn't support Turbo Boost switching on Linux.", "Oops!",JOptionPane.ERROR_MESSAGE);
        }else {
            try {
                frame.setVisible(true);
                frame.setSize(450, 300);
                frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
                layout.setHgap(100);
                layout.setVgap(10);
                frame.setLayout(layout);
                JTextPane title = new JTextPane();
                title.setContentType("text/html");
                title.setText("<html><head></head><body><div align=\"center\"><h1>Turbo Boost Switcher</h1><h2>Reduce the load on your Mac by disabling Turbo Boost.</h2></div></body></html>");
                title.setEditable(false);
                frame.add(title);

                ButtonGroup radios = new ButtonGroup();

                JRadioButton enableBoost = new JRadioButton("Enable Turbo Boost");
                JRadioButton disableBoost = new JRadioButton("Disable Turbo Boost");

                radios.add(enableBoost);
                radios.add(disableBoost);

                final String[] enableOrdisable = new String[1];
                final BufferedReader[] readStatus = {new BufferedReader(new FileReader("/sys/devices/system/cpu/intel_pstate/no_turbo"))};
                String finalStatus = readStatus[0].readLine();

                switch (finalStatus) {
                    case "0":
                        enableOrdisable[0] = "enabled";
                        break;
                    case "1":
                        enableOrdisable[0] = "disabled";
                        break;
                }

                JLabel turboStatus = new JLabel("Turbo Boost is : " + enableOrdisable[0]);
                enableBoost.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            String[] root = {"/bin/bash", "-c", "echo 0 > /sys/devices/system/cpu/intel_pstate/no_turbo"};
                            Process p = Runtime.getRuntime().exec(root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        turboStatus.setText("Turbo Boost is : " + enableOrdisable[0]);
                        SwingUtilities.updateComponentTreeUI(frame);
                        JOptionPane.showMessageDialog(null, "Turbo Boost is now enabled!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                disableBoost.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            String[] root = {"/bin/bash", "-c", "echo 1 > /sys/devices/system/cpu/intel_pstate/no_turbo"};
                            Process p = Runtime.getRuntime().exec(root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        turboStatus.setText("Turbo Boost is : " + enableOrdisable[0]);
                        SwingUtilities.updateComponentTreeUI(frame);
                        JOptionPane.showMessageDialog(null, "Turbo Boost is now disabled!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                    }
                });

                JButton refreshStatus = new JButton("Refresh status");
                refreshStatus.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            readStatus[0] = new BufferedReader(new FileReader("/sys/devices/system/cpu/intel_pstate/no_turbo"));
                            String reStatus = readStatus[0].readLine();
                            switch (reStatus) {
                                case "0":
                                    enableOrdisable[0] = "enabled";
                                    break;
                                case "1":
                                    enableOrdisable[0] = "disabled";
                                    break;
                            }
                            turboStatus.setText("Turbo Boost is : " + enableOrdisable[0]);
                            SwingUtilities.updateComponentTreeUI(frame);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                frame.add(enableBoost);
                frame.add(disableBoost);
                frame.add(turboStatus);
                frame.add(refreshStatus);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return frame;
    }
}
