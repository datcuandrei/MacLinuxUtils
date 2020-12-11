/*
    Copyright 2020 Andrei Datcu.

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
import java.io.*;

public class startup {
    public void load(String selectedPreset) throws IOException {
        File locationOfPreset = new File("/opt/maclinuxutils/presets/" + selectedPreset);
        String[] filesInPreset = locationOfPreset.list();
        String[] getFanName = locationOfPreset.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains("output");
            }
        });
        for (String fan : getFanName) {
            boolean checkFan = new File("/sys/devices/platform/applesmc.768/"+fan).exists();
            String[] getFansMode = locationOfPreset.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.contains("Mode");
                }
            });
            for (String fanModes : getFansMode) {
                try {
                    BufferedReader readFanMode = new BufferedReader(new FileReader("/opt/maclinuxutils/presets/" + selectedPreset + "/" + fanModes));
                    String fanMode = readFanMode.readLine();
                    manual manObj = new manual();
                    File manualFolder = new File("/opt/maclinuxutils/presets/" + selectedPreset + "/manual/" + fan.substring(0, 4));

                    BufferedReader lowTemp = new BufferedReader(new FileReader("/opt/maclinuxutils/presets/" + selectedPreset + "/manual/" + fan.substring(0, 4) + "/lowTemp.txt"));
                    BufferedReader lowRPM = new BufferedReader(new FileReader("/opt/maclinuxutils/presets/" + selectedPreset + "/manual/" + fan.substring(0, 4) + "/lowRPM.txt"));
                    BufferedReader highTemp = new BufferedReader(new FileReader("/opt/maclinuxutils/presets/" + selectedPreset + "/manual/" + fan.substring(0, 4) + "/highTemp.txt"));
                    BufferedReader highRPM = new BufferedReader(new FileReader("/opt/maclinuxutils/presets/" + selectedPreset + "/manual/" + fan.substring(0, 4) + "/highRPM.txt"));
                    BufferedReader maxTemp = new BufferedReader(new FileReader("/opt/maclinuxutils/presets/" + selectedPreset + "/manual/" + fan.substring(0, 4) + "/maxTemp.txt"));
                    BufferedReader maxRPM = new BufferedReader(new FileReader("/opt/maclinuxutils/presets/" + selectedPreset + "/manual/" + fan.substring(0, 4) + "/maxRPM.txt"));

                    switch (fanMode) {
                        case ("automatic"):
                            manObj.getTimer().cancel();
                            manObj.getTimer().purge();
                            try {
                                if(checkFan == true) {
                                    String[] root = {"/bin/bash", "-c", "echo 0 > /sys/devices/platform/applesmc.768/" + fan.substring(0, 4) + "_manual"};
                                    Process p = Runtime.getRuntime().exec(root);
                                    JOptionPane.showMessageDialog(null, fan.substring(0,1).toUpperCase()+fan.substring(1,3)+" "+fan.substring(3,4)+"'s speed is now set to automatic!", "Success!",JOptionPane.INFORMATION_MESSAGE);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case ("constant"):

                            File[] getFans = locationOfPreset.listFiles(new FileFilter() {
                                @Override
                                public boolean accept(File pathname) {
                                    String name = pathname.getName();
                                    return name.contains("fan") && name.contains("output") || name.contains("manual") && pathname.isFile();
                                }
                            });
                            for (File all : getFans) {
                                System.out.println(all.getName());
                                try {
                                    BufferedReader readValues = new BufferedReader(new FileReader(all));
                                    String value = readValues.readLine();
                                    String[] root = {"/bin/bash", "-c", "echo " + value + " > /sys/devices/platform/applesmc.768/" + all.getName()};
                                    Process p = Runtime.getRuntime().exec(root);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        case ("manual"):
                            // Experimental mode
                            //manObj.getManual(fan, lowTemp.readLine(), lowRPM.readLine(), highTemp.readLine(), highRPM.readLine(), maxTemp.readLine(), maxRPM.readLine());
                            JOptionPane.showMessageDialog(null,"This preset uses manual mode.\nPlease restart MacLinuxUtils in experimental mode to use this feature.");
                            break;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                BufferedReader readTurbo = new BufferedReader(new FileReader("/opt/maclinuxutils/presets/" + selectedPreset + "/no_turbo"));
                String tbVal = readTurbo.readLine();
                String[] root = {"/bin/bash", "-c", "echo " + tbVal + " > /sys/devices/system/cpu/intel_pstate/no_turbo"};
                Process p = Runtime.getRuntime().exec(root);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}