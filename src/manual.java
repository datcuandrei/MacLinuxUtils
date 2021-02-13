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

// Please note that manual mode is experimental,everything here can drastically change with updates
// in order to bring it to a stable state.
package andreid;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class manual{
    TimerTask manual;
    //ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    Timer timer = new Timer();

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setManual(TimerTask manual) {
        this.manual = manual;
    }

    String fan;
    String lowTemp;
    String lowRPM;
    String highTemp;
    String highRPM;
    String maxTemp;
    String maxRPM;

    public TimerTask getManual(String fan, String lowTemp, String lowRPM, String highTemp, String highRPM, String maxTemp, String maxRPM) {

        manual = new TimerTask() {
            @Override
            public void run() {
                String[] setManual = {"/bin/bash","-c","echo 1 > /sys/devices/platform/applesmc.768/" + fan.substring(0, 4) + "_manual"};
                try {
                    Process p = Runtime.getRuntime().exec(setManual);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("\n\n\n\nChecking...");
                File smckeys = new File("/sys/devices/platform/applesmc.768");
                String[] getSMCKeys = smckeys.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.contains("temp") && name.contains("label");
                    }
                });
                String tempCPU1 = null;
                String tempCPU2 = null;
                for (String labelkey : getSMCKeys) {
                    BufferedReader readKey = null;
                    String key = null;
                    try {
                        readKey = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/" + labelkey));
                        key = readKey.readLine();
                    } catch (IOException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    switch (key) {
                        case "TC0D":
                            BufferedReader readValueCPU1 = null;
                            try {
                                readValueCPU1 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/" + labelkey.substring(labelkey.indexOf("temp"), labelkey.lastIndexOf("_label")) + "_input"));
                                tempCPU1 = readValueCPU1.readLine().substring(0, 2);
                                System.out.println("Temp 1 : " + tempCPU1);
                            } catch (FileNotFoundException fileNotFoundException) {
                                fileNotFoundException.printStackTrace();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            break;
                        case "TC1D":
                            BufferedReader readValueCPU2 = null;
                            try {
                                readValueCPU2 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/" + labelkey.substring(labelkey.indexOf("temp"), labelkey.lastIndexOf("_label")) + "_input"));
                                tempCPU2 = readValueCPU2.readLine().substring(0, 2);
                            } catch (IOException fileNotFoundException) {
                                fileNotFoundException.printStackTrace();
                            }
                            break;
                    }
                }

                if (Integer.parseInt(tempCPU1) < Integer.parseInt(highTemp)) {
                    String[] root = {"/bin/bash", "-c", "echo " + lowRPM + " > /sys/devices/platform/applesmc.768/" + fan};
                    try {
                        Process p = Runtime.getRuntime().exec(root);
                        System.out.println("Setting low...");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else if (Integer.parseInt(tempCPU1) > Integer.parseInt(highTemp) && Integer.parseInt(tempCPU1) < Integer.parseInt(maxTemp)) {
                    String[] root = {"/bin/bash", "-c", "echo " + highRPM + " > /sys/devices/platform/applesmc.768/" + fan};
                    try {
                        Process p = Runtime.getRuntime().exec(root);
                        System.out.println("Setting high...");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else if (Integer.parseInt(tempCPU1) > Integer.parseInt(highTemp) && Integer.parseInt(tempCPU1) > Integer.parseInt(maxTemp)) {
                    String[] root = {"/bin/bash", "-c", "echo " + maxRPM + " > /sys/devices/platform/applesmc.768/" + fan};
                    try {
                        Process p = Runtime.getRuntime().exec(root);
                        System.out.println("Setting max...");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        };
        //executorService = Executors.newScheduledThreadPool(1);
        //executorService.scheduleAtFixedRate(manual, 0, 3, TimeUnit.SECONDS);
        timer.schedule(manual,0,3000);
        return manual;
    }
    public void shutDownManual(){
        //executorService.shutdownNow();
        getTimer().cancel();
        getTimer().purge();
        //System.out.println("Manual service killed...");
    }

}
