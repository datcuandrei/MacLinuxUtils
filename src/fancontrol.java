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
import java.io.*;

public class fancontrol {
    JFrame frame = new JFrame("Fan Control");

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JFrame getFrame() {
        try {
            frame.setVisible(true);
            frame.setSize(485,700);
            frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
            layout.setHgap(100);
            layout.setVgap(10);
            frame.setLayout(layout);
            JTextPane title = new JTextPane();
            title.setContentType("text/html");
            title.setText("<html><head></head><body><div align=\"center\"><h1>Fan Control</h1><h2>Monitor and control the speed of your fans.</h2></div></body></html>");
            title.setEditable(false);
            frame.add(title);

            // Fan Control -->

            fans getFans = new fans();
            JScrollPane fansScroll = new JScrollPane(getFans.getFansPanel());
            fansScroll.setPreferredSize(new Dimension(230,150));
            frame.add(fansScroll);
            JButton refreshFans = new JButton("Refresh RPM");
            refreshFans.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fans reGetFans = new fans();
                    try {
                        fansScroll.setViewportView(reGetFans.getFansPanel());
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    SwingUtilities.updateComponentTreeUI(fansScroll);
                    SwingUtilities.updateComponentTreeUI(frame);
                }
            });

            // <--

            sensors sensors = new sensors();
            JScrollPane sensorsPane = new JScrollPane(sensors.getSensorsCelsius());

            JCheckBox cTof = new JCheckBox("Check temperature in °F");
            cTof.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        sensorsPane.setViewportView(sensors.getSensorsFahrenheit());
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    SwingUtilities.updateComponentTreeUI(frame);
                }
            });

            JCheckBox fToc = new JCheckBox("Check temperature in °C");
            fToc.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        sensorsPane.setViewportView(sensors.getSensorsCelsius());
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    SwingUtilities.updateComponentTreeUI(frame);
                }
            });
            sensorsPane.setPreferredSize(new Dimension(235,120));
            ButtonGroup conversions = new ButtonGroup();

            conversions.add(cTof);
            conversions.add(fToc);
            
            JButton refreshTemps = new JButton("Refresh temps");
            refreshTemps.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sensors rereadSensors = new sensors();
                    if(fToc.isSelected()){
                        try {
                            sensorsPane.setViewportView(rereadSensors.getSensorsCelsius());
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                        SwingUtilities.updateComponentTreeUI(sensorsPane);
                        SwingUtilities.updateComponentTreeUI(frame);
                    }else if(cTof.isSelected()){
                        try {
                            sensorsPane.setViewportView(rereadSensors.getSensorsFahrenheit());
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                        SwingUtilities.updateComponentTreeUI(sensorsPane);
                        SwingUtilities.updateComponentTreeUI(frame);
                    }else{
                        try {
                            sensorsPane.setViewportView(rereadSensors.getSensorsCelsius());
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                        SwingUtilities.updateComponentTreeUI(sensorsPane);
                        SwingUtilities.updateComponentTreeUI(frame);
                    }
                }
            });
            JTextPane temperature = new JTextPane();
            temperature.setContentType("text/html");
            temperature.setText("<html><head></head><body><div align=\"center\"><h1>Temperature</h1><h2>Monitor the temperature of your Mac's components.</h2></div></body></html>");
            temperature.setEditable(false);

            frame.add(refreshFans);
            frame.add(temperature);
            frame.add(sensorsPane);
            frame.add(cTof);
            frame.add(fToc);
            frame.add(refreshTemps);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return frame;
    }
}
