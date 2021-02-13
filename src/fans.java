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

public class fans {
     JPanel fansPanel = new JPanel();
    public void setFansPanel(JPanel fansPanel) {
        this.fansPanel = fansPanel;
    }

    public JPanel getFansPanel() throws IOException {
        fansPanel.setPreferredSize(new Dimension(230,150));
        File fans = new File("/sys/devices/platform/applesmc.768/");
        String[] getFans = fans.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains("output");
            }
        });
        JLabel currentFanRPM = null;
        for(String fan:getFans){
            boolean checkFan = new File("/sys/devices/platform/applesmc.768/"+fan).exists();
            JLabel fanLabel = new JLabel(fan.substring(0,1).toUpperCase()+fan.substring(1,3)+" "+fan.substring(3,4));
            Font fanLabelFont = fanLabel.getFont();
            fanLabel.setFont(new Font(fanLabelFont.getName(),Font.BOLD,fanLabel.getFont().getSize()));
            BufferedReader fanCurrent = null;
            if(checkFan == true){
                fanCurrent = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+fan));
            }
            currentFanRPM = new JLabel("RPM : "+ fanCurrent.readLine());
            JButton edit = new JButton("Control");
            fansPanel.add(fanLabel);
            fansPanel.add(currentFanRPM);
            fansPanel.add(edit);
            edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame editFrame = new JFrame("Control " + fan.substring(0,1).toUpperCase()+fan.substring(1,3)+" "+fan.substring(3,4));
                    editFrame.setSize(420,300);
                    editFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                    editFrame.setLocationRelativeTo(null);
                    FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
                    layout.setHgap(100);
                    layout.setVgap(10);
                    editFrame.setLayout(layout);
                    editFrame.setVisible(true);
                    JTextPane title = new JTextPane();
                    title.setContentType("text/html");
                    title.setText("<html><head></head><body><div align=\"center\"><h2>Fan control for "+ fan.substring(0,1).toUpperCase()+fan.substring(1,3)+" "+fan.substring(3,4)+"</h2></div></body></html>");
                    title.setEditable(false);
                    editFrame.add(title);
                    BufferedReader fanMinbr;
                    int minFan;

                    BufferedReader fanMaxbr;
                    int maxFan;

                    BufferedReader finalFanCurrent;
                    int finFan;
                    if(checkFan){
                        try {
                            File setFanMode = new File("/opt/maclinuxutils/preferences/"+fan.substring(0,4)+"Mode.txt");
                            fanMinbr = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/" + fan.substring(0, 4) + "_min"));
                            minFan = Integer.parseInt(fanMinbr.readLine());

                            fanMaxbr = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/" + fan.substring(0, 4) + "_max"));
                            maxFan = Integer.parseInt(fanMaxbr.readLine());

                            finalFanCurrent = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+fan));
                            finFan = Integer.parseInt(new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+fan)).readLine());

                            JSlider changeRPM = new JSlider(minFan, maxFan);
                            changeRPM.setValue(finFan);
                            changeRPM.setOrientation(JSlider.HORIZONTAL);
                            changeRPM.setMinorTickSpacing(minFan/10);
                            changeRPM.setMajorTickSpacing(maxFan/10);
                            changeRPM.setPreferredSize(new Dimension(400,70));
                            changeRPM.setPaintLabels(true);
                            System.out.println("Current value : " + new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+fan)).readLine());

                            manual manObj = new manual();

                            ButtonGroup automanual = new ButtonGroup();

                            JRadioButton automaticMode = new JRadioButton("Automatic mode");
                            JRadioButton constantMode = new JRadioButton("Constant RPM");
                            JRadioButton manualMode = new JRadioButton("Manual mode");

                            automanual.add(automaticMode);
                            automanual.add(constantMode);
                            // Experimental mode
                            automanual.add(manualMode);

                            File fanMode = new File("/opt/maclinuxutils/preferences/"+fan.substring(0,4)+"Mode.txt");
                            if(!fanMode.exists()){
                                FileWriter writeFanMode = new FileWriter("/opt/maclinuxutils/preferences/"+fan.substring(0,4)+"Mode.txt");
                                writeFanMode.write("none");
                                writeFanMode.close();
                            }
                            BufferedReader modesBr = new BufferedReader(new FileReader(fanMode));
                            String autoOrmanual = modesBr.readLine();

                            switch (autoOrmanual){
                                case "automatic" :
                                    automaticMode.setSelected(true);
                                    break;
                                case "constant" :
                                    constantMode.setSelected(true);
                                    break;
                                case "manual" :
                                    manualMode.setSelected(true);
                                    break;
                                case "none":
                                    System.out.println("No option selected.");
                                    break;
                            }

                            automaticMode.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent actionEvent) {
                                    manObj.shutDownManual();
                                    try {
                                        FileWriter setFanMode = new FileWriter("/opt/maclinuxutils/preferences/"+fan.substring(0,4)+"Mode.txt");
                                        setFanMode.write("automatic");
                                        setFanMode.close();
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                    try {
                                        if(checkFan == true) {
                                            String[] root = {"/bin/bash", "-c", "echo 0 > /sys/devices/platform/applesmc.768/" + fan.substring(0, 4) + "_manual"};
                                            Process p = Runtime.getRuntime().exec(root);
                                            JOptionPane.showMessageDialog(null, fan.substring(0,1).toUpperCase()+fan.substring(1,3)+" "+fan.substring(3,4)+"'s speed is now set to automatic!", "Success!",JOptionPane.INFORMATION_MESSAGE);
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            constantMode.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent actionEvent) {
                                    manObj.shutDownManual();
                                    try {
                                        FileWriter setFanMode = new FileWriter("/opt/maclinuxutils/preferences/"+fan.substring(0,4)+"Mode.txt");
                                        setFanMode.write("constant");
                                        setFanMode.close();
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                    try {
                                        if(checkFan == true){
                                            String[] root = {"/bin/bash","-c","echo 1 > /sys/devices/platform/applesmc.768/" + fan.substring(0, 4) + "_manual"};
                                            Process p = Runtime.getRuntime().exec(root);
                                            JOptionPane.showMessageDialog(null, fan.substring(0,1).toUpperCase()+fan.substring(1,3)+" "+fan.substring(3,4)+"'s speed is now set to constant!", "Success!",JOptionPane.INFORMATION_MESSAGE);
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            manualMode.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        FileWriter setFanMode = new FileWriter("/opt/maclinuxutils/preferences/"+fan.substring(0,4)+"Mode.txt");
                                        setFanMode.write("manual");
                                        setFanMode.close();
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                    File manualMode = new File("/opt/maclinuxutils/manual/");
                                    boolean checkManMode = manualMode.exists();
                                    if (!checkManMode){
                                        manualMode.mkdirs();
                                    }

                                    File fanFolder = new File("/opt/maclinuxutils/manual/"+fan.substring(0,4)+"/");
                                    fanFolder.mkdirs();

                                    JFrame manualFrame = new JFrame("Manual mode");
                                    manualFrame.setSize(new Dimension(700,400));
                                    manualFrame.setVisible(true);
                                    FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
                                    layout.setHgap(100);
                                    layout.setVgap(10);
                                    manualFrame.setLayout(layout);
                                    manualFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                                    manualFrame.setLocationRelativeTo(null);

                                    JLabel lowTempLabel = new JLabel("Low Temp: ");
                                    JLabel lowRPMLabel = new JLabel("Low RPM : ");
                                    JLabel highRPMLabel = new JLabel("High RPM: ");
                                    JLabel highTempLabel = new JLabel("High Temp: ");
                                    JLabel maxRPMLabel = new JLabel("Max RPM: ");
                                    JLabel maxTempLabel = new JLabel("Max Temp: ");

                                    JTextField lowTemp = new JTextField("Input val");
                                    JTextField highTemp = new JTextField("Input val");
                                    JTextField maxTemp = new JTextField("Input val");

                                    JTextField lowRPM = new JTextField("Input val");
                                    JTextField highRPM = new JTextField("Input val");
                                    JTextField maxRPM = new JTextField("Input val");

                                    JTextArea thresholds = new JTextArea("Low : temperature below which fan speed will be at minimum\n" +
                                            " High : fan will increase speed when higher than this temperature\n" +
                                            " Max : fan will run at full speed above this temperature");
                                    thresholds.setEditable(false);
                                    JTextPane title = new JTextPane();
                                    title.setContentType("text/html");
                                    title.setText("<html><head></head><body><div align=\"center\"><h1>Manual mode</h1><h2>Set different fan speeds for cetain temperatures.</h2></div></body></html>");
                                    title.setEditable(false);

                                    JButton saveManual = new JButton("Save changes");
                                    saveManual.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            try {
                                                FileWriter lowTempFile = new FileWriter("/opt/maclinuxutils/manual/"+fan.substring(0,4)+"/lowTemp.txt",false);
                                                FileWriter lowRPMFile = new FileWriter("/opt/maclinuxutils/manual/"+fan.substring(0,4)+"/lowRPM.txt",false);
                                                FileWriter highTempFile = new FileWriter("/opt/maclinuxutils/manual/"+fan.substring(0,4)+"/highTemp.txt",false);
                                                FileWriter highRPMFile = new FileWriter("/opt/maclinuxutils/manual/"+fan.substring(0,4)+"/highRPM.txt",false);
                                                FileWriter maxTempFile = new FileWriter("/opt/maclinuxutils/manual/"+fan.substring(0,4)+"/maxTemp.txt",false);
                                                FileWriter maxRPMFile = new FileWriter("/opt/maclinuxutils/manual/"+fan.substring(0,4)+"/maxRPM.txt",false);
                                                FileWriter fanFile = new FileWriter("/opt/maclinuxutils/manual/"+fan.substring(0,4)+"/fan.txt",false);
                                                fanFile.write(fan.substring(0,4));
                                                fanFile.close();
                                                lowTempFile.write(lowTemp.getText());
                                                lowTempFile.close();
                                                lowRPMFile.write(lowRPM.getText());
                                                lowRPMFile.close();
                                                highTempFile.write(highTemp.getText());
                                                highTempFile.close();
                                                highRPMFile.write(highRPM.getText());
                                                highRPMFile.close();
                                                maxTempFile.write(maxTemp.getText());
                                                maxTempFile.close();
                                                maxRPMFile.write(maxRPM.getText());
                                                maxRPMFile.close();
                                            } catch (IOException ioException) {
                                                ioException.printStackTrace();
                                            }
                                            manObj.getManual(fan,lowTemp.getText(),lowRPM.getText(),highTemp.getText(),highRPM.getText(),maxTemp.getText(),maxRPM.getText());
                                            JOptionPane.showMessageDialog(null, "Manual settings saved!", "Fan speed changed!",JOptionPane.INFORMATION_MESSAGE);
                                        }
                                    });
                                    manualFrame.add(title);
                                    manualFrame.add(thresholds);

                                    manualFrame.add(lowTempLabel);
                                    manualFrame.add(lowTemp);
                                    manualFrame.add(lowRPMLabel);
                                    manualFrame.add(lowRPM);

                                    manualFrame.add(highTempLabel);
                                    manualFrame.add(highTemp);
                                    manualFrame.add(highRPMLabel);
                                    manualFrame.add(highRPM);

                                    manualFrame.add(maxTempLabel);
                                    manualFrame.add(maxTemp);
                                    manualFrame.add(maxRPMLabel);
                                    manualFrame.add(maxRPM);
                                    manualFrame.add(saveManual);
                                }
                            });
                            JButton confirm = new JButton("Confirm Changes");
                            confirm.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        if (manualMode.isSelected()){
                                            FileWriter setFanMode = new FileWriter("/opt/maclinuxutils/preferences/"+fan.substring(0,4)+"Mode.txt");
                                            setFanMode.write("manual");
                                            setFanMode.close();
                                            JOptionPane.showMessageDialog(null, "Fan set to manual configuration", "Fan speed changed!",JOptionPane.INFORMATION_MESSAGE);

                                        }else if(constantMode.isSelected()){
                                            manObj.shutDownManual();
                                            FileWriter setFanMode = new FileWriter("/opt/maclinuxutils/preferences/"+fan.substring(0,4)+"Mode.txt");
                                            setFanMode.write("constant");
                                            setFanMode.close();
                                            String rpmValue = String.valueOf(changeRPM.getValue());
                                            String[] root = {"/bin/bash", "-c", "echo " + rpmValue + " > /sys/devices/platform/applesmc.768/"+fan};
                                            Process p = Runtime.getRuntime().exec(root);
                                            JOptionPane.showMessageDialog(null, "Fan speed set to : "+rpmValue, "Fan speed changed!",JOptionPane.INFORMATION_MESSAGE);
                                        }else if(automaticMode.isSelected()){
                                            manObj.shutDownManual();
                                            FileWriter setFanMode = new FileWriter("/opt/maclinuxutils/preferences/"+fan.substring(0,4)+"Mode.txt");
                                            setFanMode.write("automatic");
                                            setFanMode.close();
                                            JOptionPane.showMessageDialog(null, "Fan speed set to automatic", "Fan speed changed!",JOptionPane.INFORMATION_MESSAGE);
                                        }
                                    }catch (IOException exception) {
                                        exception.printStackTrace();
                                    }
                                }
                            });
                            editFrame.add(changeRPM);
                            editFrame.add(automaticMode);
                            editFrame.add(constantMode);
                            // Experimental mode
                            editFrame.add(manualMode);
                            editFrame.add(confirm);
                        }catch (IOException ie){
                            ie.printStackTrace();
                        }
                    }
                }
            });
        }
        return fansPanel;
    }
}
