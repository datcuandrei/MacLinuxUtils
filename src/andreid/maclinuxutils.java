/*
    MacLinuxUtils

	Module name :
		maclinuxutils.java

	Abstract :
		This Java class is the main and only class for the program.Everything is here.

	Author :
		Andrei Datcu (datcuandrei) 23-August-2020 (last updated : 7-September-2020).
*/

package andreid;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class maclinuxutils {
    private JButton button1;
    private JPanel panel1;
    private JButton turboBoostSwitcherButton;
    private JButton aboutButton;
    private JButton issuesButton;
    private JButton updatesButton;

    public maclinuxutils() {

        // Fan Control (a lot of checks but it works)

        button1.addActionListener(actionEvent -> {
            try {
                JFrame frame = new JFrame("Fan Control");
                frame.setVisible(true);
                frame.setSize(225,300);
                frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setLayout(new FlowLayout(FlowLayout.CENTER));
                JButton confirm = new JButton("Confirm Changes");


                // Fan 1 -->

                boolean checkFan = new File("/sys/devices/platform/applesmc.768/fan1_output").exists();

                JLabel fan1;
                BufferedReader fanMinbr;
                int minFan;

                BufferedReader fanMaxbr;
                int maxFan;

                BufferedReader[] fanCurrentbr = new BufferedReader[0];
                String currentFan = null;

                JSlider changeRPM = null;
                JLabel currentRPMLabel = null;

                if(checkFan){
                    fan1 = new JLabel("Fan 1");
                    fanMinbr = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/fan1_min"));
                    minFan = Integer.parseInt(fanMinbr.readLine());

                    fanMaxbr = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/fan1_max"));
                    maxFan = Integer.parseInt(fanMaxbr.readLine());

                    fanCurrentbr = new BufferedReader[]{new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/fan1_output"))};
                    currentFan = fanCurrentbr[0].readLine();

                    changeRPM = new JSlider(minFan,maxFan);
                    currentRPMLabel = new JLabel("Current RPM is : " + currentFan);

                    frame.add(fan1);
                    frame.add(changeRPM);
                    frame.add(currentRPMLabel);
                }

                // <--

                // Fan 2 -->

                boolean checkFan2 = new File("/sys/devices/platform/applesmc.768/fan2_output").exists();

                JLabel fan2;
                BufferedReader fanMinbr2;
                int minFan2;

                BufferedReader fanMaxbr2;
                int maxFan2;

                BufferedReader[] fanCurrentbr2 = new BufferedReader[0];
                String currentFan2 = null;

                JSlider changeRPM2 = null;
                JLabel currentRPMLabel2 = null;

                if(checkFan2 == true){
                    fan2 = new JLabel("Fan 2");
                    fanMinbr2 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/fan2_min"));
                    minFan2 = Integer.parseInt(fanMinbr2.readLine());

                    fanMaxbr2 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/fan2_max"));
                    maxFan2 = Integer.parseInt(fanMaxbr2.readLine());

                    fanCurrentbr2 = new BufferedReader[]{new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/fan2_output"))};
                    currentFan2 = fanCurrentbr2[0].readLine();

                    changeRPM2 = new JSlider(minFan2,maxFan2);
                    currentRPMLabel2 = new JLabel("Current RPM is : " + currentFan2);

                    frame.add(fan2);
                    frame.add(changeRPM2);
                    frame.add(currentRPMLabel2);
                    frame.setSize(225,330);
                }

                // <--

                // Fan 3 -->

                boolean checkFan3 = new File("/sys/devices/platform/applesmc.768/fan3_output").exists();

                JLabel fan3;
                BufferedReader fanMinbr3;
                int minFan3;

                BufferedReader fanMaxbr3;
                int maxFan3;

                BufferedReader[] fanCurrentbr3 = new BufferedReader[0];
                String currentFan3 = null;

                JSlider changeRPM3 = null;
                JLabel currentRPMLabel3 = null;

                if(checkFan3 == true) {
                    fan3 = new JLabel("Fan 3");
                    fanMinbr3 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/fan3_min"));
                    minFan3 = Integer.parseInt(fanMinbr3.readLine());

                    fanMaxbr3 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/fan3_max"));
                    maxFan3 = Integer.parseInt(fanMaxbr3.readLine());

                    fanCurrentbr3 = new BufferedReader[]{new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/fan3_output"))};
                    currentFan3 = fanCurrentbr3[0].readLine();

                    changeRPM2 = new JSlider(minFan3, maxFan3);
                    currentRPMLabel2 = new JLabel("Current RPM is : " + currentFan3);

                    frame.add(fan3);
                    frame.add(changeRPM3);
                    frame.add(currentRPMLabel3);
                }

                if (checkFan2 == true && checkFan3 == true){
                    frame.setSize(225,360);
                }

                // <--

                final BufferedReader[] cpuTemp = {new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/temp5_input"))};
                String readingCPUTemp = cpuTemp[0].readLine().substring(0,2);

                int cpuTempint = Integer.parseInt(readingCPUTemp);

                if(cpuTempint > 99){
                    readingCPUTemp = cpuTemp[0].readLine().substring(0,3);
                }

                JLabel CPU = new JLabel("CPU : ");
                JLabel cpuTempLabel = new JLabel(readingCPUTemp+"°C");

                Font font = cpuTempLabel.getFont();
                cpuTempLabel.setFont(new Font(font.getName(),Font.PLAIN,30));
                CPU.setFont(new Font(font.getName(),Font.PLAIN,30));

                changeRPM.setValue(Integer.parseInt(currentFan));

                ButtonGroup automanual = new ButtonGroup();

                JRadioButton automaticMode = new JRadioButton("Automatic mode");
                JRadioButton manualMode = new JRadioButton("Manual mode");

                JCheckBox cTof = new JCheckBox("Check temperature in °F");
                cTof.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        double fahrenheit = cpuTempint * 1.8000 +32;
                        String finalFahrenheit = String.valueOf(fahrenheit).substring(0,3);
                        cpuTempLabel.setText(finalFahrenheit+"°F");
                        SwingUtilities.updateComponentTreeUI(frame);
                    }
                });

                JCheckBox fToc = new JCheckBox("Check temperature in °C");
                String finalReadingCPUTemp = readingCPUTemp;
                fToc.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        cpuTempLabel.setText(finalReadingCPUTemp +"°C");
                        SwingUtilities.updateComponentTreeUI(frame);
                    }
                });

                automanual.add(automaticMode);
                automanual.add(manualMode);

                ButtonGroup conversions = new ButtonGroup();

                conversions.add(cTof);
                conversions.add(fToc);


                BufferedReader modesBr = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/fan1_manual"));
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
                                String[] root = {"/bin/bash", "-c", "echo 0 > /sys/devices/platform/applesmc.768/fan1_manual"};
                                Process p = Runtime.getRuntime().exec(root);
                            }
                            if(checkFan2 == true){
                                String[] root2 = {"/bin/bash", "-c", "echo 0 > /sys/devices/platform/applesmc.768/fan2_manual"};
                                Process p2 = Runtime.getRuntime().exec(root2);
                            }
                            if(checkFan3 == true){
                                String[] root3 = {"/bin/bash", "-c", "echo 0 > /sys/devices/platform/applesmc.768/fan3_manual"};
                                Process p3 = Runtime.getRuntime().exec(root3);
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
                                String[] root = {"/bin/bash","-c","echo 1 > /sys/devices/platform/applesmc.768/fan1_manual"};
                                Process p = Runtime.getRuntime().exec(root);
                            }
                            if(checkFan2 == true){
                                String[] root2 = {"/bin/bash","-c","echo 1 > /sys/devices/platform/applesmc.768/fan2_manual"};
                                Process p2 = Runtime.getRuntime().exec(root2);
                            }
                            if(checkFan3 == true){
                                String[] root3 = {"/bin/bash","-c","echo 1 > /sys/devices/platform/applesmc.768/fan3_manual"};
                                Process p3 = Runtime.getRuntime().exec(root3);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                JButton refreshTemps = new JButton("⟳");

                // Fan 1 -->
                JLabel finalCurrentRPMLabel = currentRPMLabel;
                BufferedReader[] finalFanCurrentbr = fanCurrentbr;
                // <--

                // Fan 2 -->
                JLabel finalCurrentRPMLabel2 = currentRPMLabel2;
                BufferedReader[] finalFanCurrentbr2 = fanCurrentbr2;
                // <--

                // Fan 3 -->
                JLabel finalCurrentRPMLabel3 = currentRPMLabel3;
                BufferedReader[] finalFanCurrentbr3 = fanCurrentbr3;
                // <--

                refreshTemps.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            cpuTemp[0] = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/temp5_input"));
                            String rereadingCPUTemp = cpuTemp[0].readLine().substring(0,2);
                            if(cpuTempint > 99){
                                rereadingCPUTemp = cpuTemp[0].readLine().substring(0,3);
                            }
                            double fahrenheit = Integer.valueOf(rereadingCPUTemp) * 1.8000 +32;
                            String finalFahrenheit = String.valueOf(fahrenheit).substring(0,3);
                            if(cTof.isSelected()){
                                cpuTempLabel.setText(finalFahrenheit+"°F");
                            }else{
                                cpuTempLabel.setText(rereadingCPUTemp+"°C");
                            }

                            // Fan 1 -->
                            if(checkFan == true) {
                                BufferedReader newFanCurrentBr = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/fan1_output"));
                                String recurrentFan = newFanCurrentBr.readLine();
                                finalFanCurrentbr[0] = newFanCurrentBr;
                                finalCurrentRPMLabel.setText("Current RPM is : " + recurrentFan);
                            }
                            // <--

                            // Fan 2 -->
                            if(checkFan2 == true) {
                                BufferedReader newFanCurrentBr2 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/fan2_output"));
                                String recurrentFan2 = newFanCurrentBr2.readLine();
                                finalFanCurrentbr2[0] = newFanCurrentBr2;
                                finalCurrentRPMLabel2.setText("Current RPM is : " + recurrentFan2);
                            }
                            // <--

                            // Fan 3 -->
                            if(checkFan3 == true) {
                                BufferedReader newFanCurrentBr3 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/fan3_output"));
                                String recurrentFan3 = newFanCurrentBr3.readLine();
                                finalFanCurrentbr3[0] = newFanCurrentBr3;
                                finalCurrentRPMLabel3.setText("Current RPM is : " + recurrentFan3);
                            }
                            // <--


                            SwingUtilities.updateComponentTreeUI(frame);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                frame.add(CPU);
                frame.add(cpuTempLabel);
                frame.add(cTof);
                frame.add(fToc);
                frame.add(automaticMode);
                frame.add(manualMode);
                frame.add(confirm);
                frame.add(refreshTemps);

                JSlider finalChangeRPM = changeRPM;
                JSlider finalChangeRPM2 = changeRPM2;
                JSlider finalChangeRPM3 = changeRPM3;

                confirm.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String rpmValue = null;
                        String rpmValue2 = null;
                        String rpmValue3 = null;
                        if(checkFan == true) {
                            rpmValue = String.valueOf(finalChangeRPM.getValue());
                        }
                        if(checkFan2 == true) {
                            rpmValue2 = String.valueOf(finalChangeRPM2.getValue());
                        }
                        if(checkFan3 == true) {
                            rpmValue3 = String.valueOf(finalChangeRPM3.getValue());
                        }
                        try {
                            if(checkFan == true) {
                                String[] root = {"/bin/bash","-c","echo "+rpmValue+" > /sys/devices/platform/applesmc.768/fan1_output"};
                                Process p = Runtime.getRuntime().exec(root);
                            }
                            if(checkFan2 == true) {
                                String[] root2 = {"/bin/bash","-c","echo "+rpmValue2+" > /sys/devices/platform/applesmc.768/fan2_output"};
                                Process p2 = Runtime.getRuntime().exec(root2);
                            }
                            if(checkFan2 == true) {
                                String[] root3 = {"/bin/bash","-c","echo "+rpmValue3+" > /sys/devices/platform/applesmc.768/fan3_output"};
                                Process p3 = Runtime.getRuntime().exec(root3);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            }); }
            catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Turbo Boost Switching

        turboBoostSwitcherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                JFrame frame = new JFrame("Turbo Boost Switcher");
                frame.setVisible(true);
                frame.setSize(210,120);
                frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setLayout(new FlowLayout(FlowLayout.CENTER));

                ButtonGroup radios = new ButtonGroup();

                JRadioButton enableBoost = new JRadioButton("Enable Turbo Boost");
                JRadioButton disableBoost = new JRadioButton("Disable Turbo Boost");

                radios.add(enableBoost);
                radios.add(disableBoost);

                    final String[] enableOrdisable = new String[1];
                    final BufferedReader[] readStatus = {new BufferedReader(new FileReader("/sys/devices/system/cpu/intel_pstate/no_turbo"))};
                String finalStatus = readStatus[0].readLine();

                switch (finalStatus){
                    case "0" :
                        enableOrdisable[0] = "enabled";
                        break;
                    case "1" :
                        enableOrdisable[0] = "disabled";
                        break;
                }

                JLabel turboStatus = new JLabel("Turbo Boost is : " + enableOrdisable[0]);
                enableBoost.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            String[] root = {"/bin/bash","-c","echo 0 > /sys/devices/system/cpu/intel_pstate/no_turbo"};
                            Process p = Runtime.getRuntime().exec(root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(null, "Turbo Boost is now enabled!", "Success!",JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                disableBoost.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            String[] root = {"/bin/bash","-c","echo 1 > /sys/devices/system/cpu/intel_pstate/no_turbo"};
                            Process p = Runtime.getRuntime().exec(root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(null, "Turbo Boost is now disabled!", "Success!",JOptionPane.INFORMATION_MESSAGE);
                    }
                });

                JButton refreshStatus = new JButton("⟳");
                    refreshStatus.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            try {
                                readStatus[0] = new BufferedReader(new FileReader("/sys/devices/system/cpu/intel_pstate/no_turbo"));
                                String reStatus = readStatus[0].readLine();
                                switch (reStatus){
                                    case "0" :
                                        enableOrdisable[0] = "enabled";
                                        break;
                                    case "1" :
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
        });

        // Getting username (this is required in order to be able to execute the bash commands to open links in su.)

        String homePath = "/home";
        File file = new File(homePath);
        String[] allUsers = file.list();

        String username = allUsers[0];

        // Author button

        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String root[] = {"/bin/bash","-c","sudo -u " + username + " -H xdg-open https://github.com/datcuandrei/"};
                    Process p = Runtime.getRuntime().exec(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Issues button.

        issuesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String root[] = {"/bin/bash","-c","sudo -u " + username + " -H xdg-open https://github.com/datcuandrei/MacLinuxUtils/issues"};
                    Process p = Runtime.getRuntime().exec(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //  Updates button.

        updatesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String root[] = {"/bin/bash","-c","sudo -u " + username + " -H xdg-open https://github.com/datcuandrei/MacLinuxUtils/releases"};
                    Process p = Runtime.getRuntime().exec(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Main method

    public static void main(String args[]) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel( new FlatLightLaf() ); // for dark mode = FlatDarculaLaf ; for light mode = FlatLightLaf.

        // Checking if the computer is a Mac.

        boolean checkIfMac = new File("/sys/devices/platform/applesmc.768").exists();

        if (checkIfMac == false){
            JFrame noMac = new JFrame("Oops!");
            noMac.setVisible(true);
            noMac.setSize(210,120);
            noMac.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            noMac.setLocationRelativeTo(null);
            noMac.setLayout(new FlowLayout(FlowLayout.CENTER));
            JLabel message = new JLabel("This program is for Intel-based Apple Macs only.");
            JButton exit = new JButton("Exit");

            exit.addActionListener(actionEvent -> System.exit(0));

            noMac.add(message);
            noMac.add(exit);
        }
        else {
            JFrame frame = new JFrame("MacLinuxUtils");
            frame.setContentPane(new maclinuxutils().panel1);
            frame.setVisible(true);
            frame.setSize(600, 300);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
        }
    }
}
