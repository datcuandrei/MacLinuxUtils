/*
    MacLinuxUtils

	Module name :
		maclinuxutils.java

	Abstract :
		This Java class is the main class for the program.

	Author :
		Andrei Datcu (datcuandrei) 23-August-2020 (last updated : 8-October-2020).
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
    private JButton attributionButton;

    public maclinuxutils() {
        button1.addActionListener(actionEvent -> {
            fancontrol fanObj = new fancontrol();
            fanObj.getFrame();
        });

        // Turbo Boost Switching

        turboBoostSwitcherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                turboboost turboObj = new turboboost();
                turboObj.getFrame();
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
                    String root[] = {"/bin/bash","-c","sudo -u " + username + " -H xdg-open https://datcuandrei.github.io/"};
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
        attributionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame attribution = new JFrame("Attribution");
                attribution.setVisible(true);
                attribution.setSize(500,250);
                attribution.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                attribution.setLocationRelativeTo(null);
                attribution.setLayout(new FlowLayout(FlowLayout.CENTER));

                JTextPane text = new JTextPane();
                text.setContentType("text/html");
                text.setText("<html><head></head><body><div align=\"center\"><h1>Attribution</h1><p>• MacLinuxUtils's UI would have not been possible without <b>FlatLaf</b> library.</p><p>• Special thanks to the <b>>Unsupported Macs</b> community.</p><br/><p><b>FlatLaf</b> : https://www.formdev.com/flatlaf/</p><p><b>Unsupported Macs</b> : https://discord.gg/XbbWAsE</p></div></body></html>");
                text.setEditable(false);

                attribution.add(text);
            }
        });
    }

    // Main method

    public static void main(String args[]) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel( new FlatDarculaLaf() ); // for dark mode = FlatDarculaLaf ; for light mode = FlatLightLaf.

        // Checking if the computer is a Mac.

        boolean checkIfMac = new File("/sys/devices/platform/applesmc.768").exists();

        if (checkIfMac == false){
            boolean checkIfWindows = new File("C:\\Windows").exists();

            boolean checkIfOSX = new File("/System/Library").exists();

            if(checkIfOSX == true){
                JOptionPane.showMessageDialog(null, "This program was created for Linux!", "Oops!",JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }else if(checkIfWindows == true){
                JOptionPane.showMessageDialog(null, "This program was created for Linux!", "Oops!",JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }else{
                JOptionPane.showMessageDialog(null, "This program is for Intel-based Apple Macs only!", "Oops!",JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
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
