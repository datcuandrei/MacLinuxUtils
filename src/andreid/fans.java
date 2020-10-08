/*
    MacLinuxUtils

	Module name :
		fans.java

	Abstract :
		This Java class is responsible for controlling fans.

	Author :
		Andrei Datcu (datcuandrei) 8-October-2020 (last updated : 8-October-2020).
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
                            changeRPM.setPreferredSize(new Dimension(400,80));
                            changeRPM.setPaintLabels(true);
                            System.out.println("Current value : " + new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+fan)).readLine());

                            ButtonGroup automanual = new ButtonGroup();

                            JRadioButton automaticMode = new JRadioButton("Automatic mode");
                            JRadioButton manualMode = new JRadioButton("Manual mode");

                            automanual.add(automaticMode);
                            automanual.add(manualMode);

                            BufferedReader modesBr = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/" + fan.substring(0, 4) + "_manual"));
                            String autoOrmanual = modesBr.readLine();

                            switch (autoOrmanual){
                                case "0" :
                                    automaticMode.setSelected(true);
                                    break;
                                case "1" :
                                    manualMode.setSelected(true);
                                    break;
                            }

                            automaticMode.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent actionEvent) {
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

                            manualMode.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent actionEvent) {
                                    try {
                                        if(checkFan == true){
                                            String[] root = {"/bin/bash","-c","echo 1 > /sys/devices/platform/applesmc.768/" + fan.substring(0, 4) + "_manual"};
                                            Process p = Runtime.getRuntime().exec(root);
                                            JOptionPane.showMessageDialog(null, fan.substring(0,1).toUpperCase()+fan.substring(1,3)+" "+fan.substring(3,4)+"'s speed is now set to manual!", "Success!",JOptionPane.INFORMATION_MESSAGE);
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            JButton confirm = new JButton("Confirm Changes");
                            confirm.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        String rpmValue = String.valueOf(changeRPM.getValue());
                                        String[] root = {"/bin/bash", "-c", "echo " + rpmValue + " > /sys/devices/platform/applesmc.768/"+fan};
                                        Process p = Runtime.getRuntime().exec(root);
                                        JOptionPane.showMessageDialog(null, "Fan speed set to : "+rpmValue, "Fan speed changed!",JOptionPane.INFORMATION_MESSAGE);
                                    }catch (IOException exception) {
                                        exception.printStackTrace();
                                    }
                                }
                            });
                            editFrame.add(changeRPM);
                            editFrame.add(automaticMode);
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
