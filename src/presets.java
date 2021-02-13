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

import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class presets {
    JFrame frame;
    JComboBox presetList;
    File getPresets = new File("/opt/maclinuxutils/presets");
    JButton saveCurrentConfig = new JButton("Save current configuration");
    JButton loadConfig = new JButton("Load selected preset");
    JButton loadConfigStartup = new JButton("Load selected preset at startup");
    JButton deleteConfig = new JButton("Delete selected preset");

    public void setLoadConfigStartup(JButton loadConfigStartup) {
        this.loadConfigStartup = loadConfigStartup;
    }

    public JButton getLoadConfigStartup() {
        loadConfigStartup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = String.valueOf(presetList.getSelectedItem());
                FileWriter setStart = null;
                try {
                    setStart = new FileWriter("/opt/maclinuxutils/preferences/startup.txt");
                    setStart.write(selectedItem);
                    setStart.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                JOptionPane.showMessageDialog(null,selectedItem + " will run at startup");
            }
        });
        return loadConfigStartup;
    }

    public void setDeleteConfig(JButton deleteConfig) {
        this.deleteConfig = deleteConfig;
    }

    public JButton getDeleteConfig() {
        deleteConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this preset?", "Delete",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    String deleteItem = String.valueOf(presetList.getSelectedItem());
                    if (deleteItem.equals("system")) {
                        JOptionPane.showMessageDialog(null, "Cannot delete system backup!");
                    } else {
                        File itemLoc = new File("/opt/maclinuxutils/presets/"+deleteItem);
                        try {
                            FileUtils.deleteDirectory(itemLoc);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("deleted : " + deleteItem);
                        JOptionPane.showMessageDialog(null,"Preset deleted successfully");
                    }
                }
            }
        });
        return deleteConfig;
    }

    public void setPresetList(JComboBox presetList) {
        this.presetList = presetList;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void defaultConfig() throws IOException {
        File fans = new File("/sys/devices/platform/applesmc.768/");
        String[] getFans = fans.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains("output");
            }
        });
        File defaultConfig = new File("/opt/maclinuxutils/presets/system/");
        defaultConfig.mkdirs();
        for(String fan:getFans) {
            boolean checkFan = new File("/sys/devices/platform/applesmc.768/"+fan).exists();
            BufferedReader fanCurrent = null;
            if(checkFan == true){
                try {
                    fanCurrent = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+fan));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            File saveFan = new File("/opt/maclinuxutils/presets/system/"+fan+".txt");
            try {
                FileWriter presetFanValue = new FileWriter(saveFan);
                presetFanValue.write(fanCurrent.readLine());
                presetFanValue.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String[] getManuals = fans.list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.contains("manual") || s.contains("min") || s.contains("max");
            }
        });
        for(String manual:getManuals){
            boolean checkMan = new File("/sys/devices/platform/applesmc.768/"+manual).exists();
            BufferedReader man = null;
            if(checkMan == true){
                man = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+manual));
            }
            FileWriter manw = new FileWriter(new File("/opt/maclinuxutils/presets/system/"+manual+".txt"));
            manw.write(man.readLine());
            manw.close();
        }
        boolean checkTurbo = new File("/sys/devices/system/cpu/intel_pstate/no_turbo").exists();
        if (checkTurbo == true){
            BufferedReader readTurbo = new BufferedReader(new FileReader("/sys/devices/system/cpu/intel_pstate/no_turbo"));
            FileWriter systurbo = new FileWriter(new File("/opt/maclinuxutils/presets/system/no_turbo"));
            systurbo.write(readTurbo.readLine());
            systurbo.close();
        }
    }

    public JComboBox getPresetList() {
        presetList = new JComboBox(getPresets.list());
        return presetList;
    }

    public void setLoadConfig(JButton loadConfig) {
        this.loadConfig = loadConfig;
    }

    public JButton getLoadConfig() {
        loadConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String selectedPreset = String.valueOf(presetList.getSelectedItem());
                startup load = new startup();
                try {
                    load.load(selectedPreset);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null,"Preset loaded successfully");
            }
        });
        return loadConfig;
    }

    public void setSaveCurrentConfig(JButton saveCurrentConfig) {
        this.saveCurrentConfig = saveCurrentConfig;
    }

    public JButton getSaveCurrentConfig() {
        saveCurrentConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame saveFrame = new JFrame("Save config");
                saveFrame.setVisible(true);
                saveFrame.setLocationRelativeTo(null);
                saveFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
                saveFrame.setSize(new Dimension(200,120));

                JTextField enterName = new JTextField("Insert preset name here");
                JLabel requireName = new JLabel("Enter a name for your preset : ");
                JButton saveConfig = new JButton("Proceed");

                saveFrame.add(requireName);
                saveFrame.add(enterName);
                saveFrame.add(saveConfig);

                saveConfig.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        File fans = new File("/sys/devices/platform/applesmc.768/");
                        String[] getFans = fans.list(new FilenameFilter() {
                            @Override
                            public boolean accept(File dir, String name) {
                                return name.contains("output");
                            }
                        });
                        File presetSaveName = new File("/opt/maclinuxutils/presets/"+enterName.getText());
                        presetSaveName.mkdirs();
                        for(String fan:getFans) {
                            boolean checkFan = new File("/sys/devices/platform/applesmc.768/"+fan).exists();
                            BufferedReader fanCurrent = null;
                            if(checkFan == true){
                                try {
                                    fanCurrent = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+fan));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                            File saveFan = new File("/opt/maclinuxutils/presets/"+enterName.getText()+"/"+fan);
                            try {
                                FileWriter presetFanValue = new FileWriter(saveFan);
                                presetFanValue.write(fanCurrent.readLine());
                                presetFanValue.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            File fanMode = new File("/opt/maclinuxutils/preferences/"+fan.substring(0,4)+"Mode.txt");
                            File createFanMode = new File("/opt/maclinuxutils/presets/"+enterName.getText()+"/"+fan.substring(0,4)+"Mode.txt");
                            try {
                                FileUtils.copyFile(fanMode,createFanMode);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        String[] getManuals = fans.list(new FilenameFilter() {
                            @Override
                            public boolean accept(File file, String s) {
                                return s.contains("manual");
                            }
                        });
                        for(String manual:getManuals){
                            boolean checkMan = new File("/sys/devices/platform/applesmc.768/"+manual).exists();
                            BufferedReader man = null;
                            if(checkMan == true){
                                try {
                                    man = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+manual));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                            FileWriter manw = null;
                            try {
                                manw = new FileWriter(new File("/opt/maclinuxutils/presets/"+enterName.getText()+"/"+manual));
                                manw.write(man.readLine());
                                manw.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        boolean checkTurbo = new File("/sys/devices/system/cpu/intel_pstate/no_turbo").exists();
                        BufferedReader turboStatus = null;
                        if(checkTurbo == true){
                            try {
                                turboStatus = new BufferedReader(new FileReader("/sys/devices/system/cpu/intel_pstate/no_turbo"));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            File saveTb = new File("/opt/maclinuxutils/presets/"+enterName.getText()+"/no_turbo");
                            try {
                                FileWriter noTurbo = new FileWriter(saveTb);
                                noTurbo.write(turboStatus.readLine());
                                noTurbo.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        File srcManualFolder = new File("/opt/maclinuxutils/manual");
                        File createManualFolder = new File("/opt/maclinuxutils/presets/"+enterName.getText()+"/manual");
                        createManualFolder.mkdirs();
                        try {
                            FileUtils.copyDirectory(srcManualFolder,createManualFolder);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        JOptionPane.showMessageDialog(null,"Preset saved as : " + enterName.getText(),"Succes!",JOptionPane.INFORMATION_MESSAGE);
                    }
                });
            }
        });
        return saveCurrentConfig;
    }
    public JFrame getFrame() {
        JTextPane title = new JTextPane();
        title.setContentType("text/html");
        title.setText("<html><head></head><body><div align=\"center\"><h1>Presets</h1><h2>Create or modify user-made presets.</h2></div></body></html>");
        title.setEditable(false);
        frame = new JFrame("Presets");
        frame.setVisible(true);
        frame.setSize(new Dimension(350,330));
        frame.setLocationRelativeTo(null);
        FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
        layout.setHgap(100);
        layout.setVgap(10);
        frame.setLayout(layout);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.add(title);
        frame.add(getPresetList());
        frame.add(getLoadConfig());
        frame.add(getLoadConfigStartup());
        frame.add(getSaveCurrentConfig());
        frame.add(getDeleteConfig());
        return frame;
    }

}
