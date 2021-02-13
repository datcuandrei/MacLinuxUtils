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
import java.io.*;
import java.util.Arrays;

public class sensors {
    JPanel sensorsCelsius = new JPanel();

    public void setSensorsCelsius(JPanel sensorsCelsius) {
        this.sensorsCelsius = sensorsCelsius;
    }

    public JPanel getSensorsCelsius() throws IOException {
        sensorsCelsius.setPreferredSize(new Dimension(235,120));
        File smckeys = new File("/sys/devices/platform/applesmc.768");
        String[] getSMCKeys = smckeys.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains("temp") && name.contains("label");
            }
        });
        String humanReadable = new String();
        JLabel smcLabel = new JLabel();
        JLabel tempLabel = new JLabel();
        for(String labelkey:getSMCKeys){
            BufferedReader readKey = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey));
            String key = readKey.readLine();
            switch (key){
                case "TC0D":
                    humanReadable = "CPU 1";
                    BufferedReader readValueCPU1 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempCPU1 = readValueCPU1.readLine().substring(0,2);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(tempCPU1+"°C");
                    break;
                case "TC1D":
                    humanReadable = "CPU 2";
                    BufferedReader readValueCPU2 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempCPU2 = readValueCPU2.readLine().substring(0,2);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(tempCPU2+"°C");
                    break;
                case "TG1D":
                    humanReadable = "GPU";
                    BufferedReader readValueGPU = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempGPU = readValueGPU.readLine().substring(0,2);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(tempGPU+"°C");
                    break;
                case "Ts0S":
                    humanReadable = "RAM";
                    BufferedReader readValueMem = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempMem = readValueMem.readLine().substring(0,2);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(tempMem+"°C");
                    break;
                case "TB0T":
                    humanReadable = "Battery";
                    BufferedReader readValueBattery = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempBattery = readValueBattery.readLine().substring(0,2);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(tempBattery+"°C");
                    break;
                case "TH0P":
                    humanReadable = "HDD Bay 1";
                    BufferedReader readValueHDDBay1 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempHDDBay1 = readValueHDDBay1.readLine().substring(0,2);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(tempHDDBay1+"°C");
                    break;
                case "TH1P":
                    humanReadable = "HDD Bay 2";
                    BufferedReader readValueHDDBay2 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempHDDBay2 = readValueHDDBay2.readLine().substring(0,2);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(tempHDDBay2+"°C");
                    break;
                case "TH2P":
                    humanReadable = "HDD Bay 3";
                    BufferedReader readValueHDDBay3 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempHDDBay3 = readValueHDDBay3.readLine().substring(0,2);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(tempHDDBay3+"°C");
                    break;
                case "TH3P":
                    humanReadable = "HDD Bay 4";
                    BufferedReader readValueHDDBay4 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempHDDBay4 = readValueHDDBay4.readLine().substring(0,2);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(tempHDDBay4+"°C");
                    break;
                case "TO0P":
                    humanReadable = "Optical Drive";
                    BufferedReader readValueopticaldrive = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempopticaldrive = readValueopticaldrive.readLine().substring(0,2);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(tempopticaldrive+"°C");
                    break;
            }
            sensorsCelsius.add(smcLabel);
            Font smcLabelFont = smcLabel.getFont();
            smcLabel.setFont(new Font(smcLabelFont.getName(),Font.BOLD,20));
            sensorsCelsius.add(tempLabel);
            Font tempLabelFont = tempLabel.getFont();
            tempLabel.setFont(new Font(tempLabelFont.getName(),Font.PLAIN,20));
        }
        return sensorsCelsius;
    }

    JPanel sensorsFahrenheit = new JPanel();

    public void setSensorsFahrenheit(JPanel sensorsFahrenheit) {
        this.sensorsFahrenheit = sensorsFahrenheit;
    }

    public JPanel getSensorsFahrenheit() throws IOException {
        sensorsFahrenheit.setPreferredSize(new Dimension(235,120));
        File smckeys = new File("/sys/devices/platform/applesmc.768");
        String[] getSMCKeys = smckeys.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains("temp") && name.contains("label");
            }
        });
        String humanReadable = new String();
        JLabel smcLabel = new JLabel();
        JLabel tempLabel = new JLabel();
        for(String labelkey:getSMCKeys){
            BufferedReader readKey = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey));
            String key = readKey.readLine();
            switch (key){
                case "TC0D":
                    humanReadable = "CPU 1";
                    BufferedReader readValueCPU1 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempCPU1 = readValueCPU1.readLine().substring(0,2);
                    double fahrenheitCPU1 = Integer.parseInt(tempCPU1) * 1.8000 +32;
                    String finalFahrenheitCPU1 = String.valueOf(fahrenheitCPU1).substring(0,3);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(finalFahrenheitCPU1+"°F");
                    break;
                case "TC1D":
                    humanReadable = "CPU 2";
                    BufferedReader readValueCPU2 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempCPU2 = readValueCPU2.readLine().substring(0,2);
                    double fahrenheitCPU2 = Integer.parseInt(tempCPU2) * 1.8000 +32;
                    String finalFahrenheitCPU2 = String.valueOf(fahrenheitCPU2).substring(0,3);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(finalFahrenheitCPU2+"°F");
                    break;
                case "TG1D":
                    humanReadable = "GPU";
                    BufferedReader readValueGPU = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempGPU = readValueGPU.readLine().substring(0,2);
                    double fahrenheitGPU = Integer.parseInt(tempGPU) * 1.8000 +32;
                    String finalFahrenheitGPU = String.valueOf(fahrenheitGPU).substring(0,3);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(finalFahrenheitGPU+"°F");
                    break;
                case "Ts0S":
                    humanReadable = "RAM";
                    BufferedReader readValueMem = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempMem = readValueMem.readLine().substring(0,2);
                    double fahrenheitMem = Integer.parseInt(tempMem) * 1.8000 +32;
                    String finalFahrenheitMem = String.valueOf(fahrenheitMem).substring(0,3);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(finalFahrenheitMem+"°F");
                    break;
                case "TB0T":
                    humanReadable = "Battery";
                    BufferedReader readValueBattery = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempBattery = readValueBattery.readLine().substring(0,2);
                    double fahrenheitBattery = Integer.parseInt(tempBattery) * 1.8000 +32;
                    String finalFahrenheitBattery = String.valueOf(fahrenheitBattery).substring(0,3);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(finalFahrenheitBattery+"°F");
                    break;
                case "TH0P":
                    humanReadable = "HDD Bay 1";
                    BufferedReader readValueHDDBay1 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempHDDBay1 = readValueHDDBay1.readLine().substring(0,2);
                    double fahrenheitHDDBay1 = Integer.parseInt(tempHDDBay1) * 1.8000 +32;
                    String finalFahrenheitHDDBay1 = String.valueOf(fahrenheitHDDBay1).substring(0,3);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(finalFahrenheitHDDBay1+"°F");
                    break;
                case "TH1P":
                    humanReadable = "HDD Bay 2";
                    BufferedReader readValueHDDBay2 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempHDDBay2 = readValueHDDBay2.readLine().substring(0,2);
                    double fahrenheitHDDBay2 = Integer.parseInt(tempHDDBay2) * 1.8000 +32;
                    String finalFahrenheitHDDBay2 = String.valueOf(fahrenheitHDDBay2).substring(0,3);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(finalFahrenheitHDDBay2+"°F");
                    break;
                case "TH2P":
                    humanReadable = "HDD Bay 3";
                    BufferedReader readValueHDDBay3 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempHDDBay3 = readValueHDDBay3.readLine().substring(0,2);
                    double fahrenheitHDDBay3 = Integer.parseInt(tempHDDBay3) * 1.8000 +32;
                    String finalFahrenheitHDDBay3 = String.valueOf(fahrenheitHDDBay3).substring(0,3);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(finalFahrenheitHDDBay3+"°F");
                    break;
                case "TH3P":
                    humanReadable = "HDD Bay 4";
                    BufferedReader readValueHDDBay4 = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempHDDBay4 = readValueHDDBay4.readLine().substring(0,2);
                    double fahrenheitHDDBay4 = Integer.parseInt(tempHDDBay4) * 1.8000 +32;
                    String finalFahrenheitHDDBay4 = String.valueOf(fahrenheitHDDBay4).substring(0,3);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(finalFahrenheitHDDBay4+"°F");
                    break;
                case "TO0P":
                    humanReadable = "Optical Drive";
                    BufferedReader readValueopticaldrive = new BufferedReader(new FileReader("/sys/devices/platform/applesmc.768/"+labelkey.substring(labelkey.indexOf("temp"),labelkey.lastIndexOf("_label"))+"_input"));
                    String tempopticaldrive = readValueopticaldrive.readLine().substring(0,2);
                    double fahrenheitopticaldrive = Integer.parseInt(tempopticaldrive) * 1.8000 +32;
                    String finalFahrenheitopticaldrive = String.valueOf(fahrenheitopticaldrive).substring(0,3);
                    smcLabel = new JLabel(humanReadable + " :       ");
                    tempLabel = new JLabel(finalFahrenheitopticaldrive+"°F");
                    break;
            }
            sensorsFahrenheit.add(smcLabel);
            Font smcLabelFont = smcLabel.getFont();
            smcLabel.setFont(new Font(smcLabelFont.getName(),Font.BOLD,20));
            sensorsFahrenheit.add(tempLabel);
            Font tempLabelFont = tempLabel.getFont();
            tempLabel.setFont(new Font(tempLabelFont.getName(),Font.PLAIN,20));
        }
        return sensorsFahrenheit;
    }
}
