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
import java.io.File;

public class systray {
    SystemTray systray = SystemTray.getSystemTray();
    PopupMenu popup = new PopupMenu();
    Dimension trayIconSize = systray.getTrayIconSize();
    TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage("/opt/maclinuxutils/fanico.png").getScaledInstance(trayIconSize.width,trayIconSize.height,Image.SCALE_SMOOTH));

    MenuItem aboutItem = new MenuItem("About");
    MenuItem fansItem = new MenuItem("Fan Control");
    MenuItem turboItem = new MenuItem("Turbo Boost");
    MenuItem prefsItem = new MenuItem("Presets");
    MenuItem showMain = new MenuItem("Show MacLinuxUtils");
    MenuItem exit = new MenuItem("Exit");

    public void setExit(MenuItem exit) {
        this.exit = exit;
    }

    public MenuItem getExit() {
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        return exit;
    }

    public void setShowMain(MenuItem showMain) {
        this.showMain = showMain;
    }

    public MenuItem getShowMain() {
        showMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                maclinuxutils main = new maclinuxutils();
                main.getFrame();
            }
        });
        return showMain;
    }

    public void setPrefsItem(MenuItem prefsItem) {
        this.prefsItem = prefsItem;
    }

    public MenuItem getPrefsItem() {
        prefsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                presets presets = new presets();
                presets.getFrame();
            }
        });
        return prefsItem;
    }

    public void setTurboItem(MenuItem turboItem) {
        this.turboItem = turboItem;
    }

    public MenuItem getTurboItem() {
        turboItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                turboboost tb = new turboboost();
                tb.getFrame();
            }
        });
        return turboItem;
    }

    public void setFansItem(MenuItem fansItem) {
        this.fansItem = fansItem;
    }

    public MenuItem getFansItem() {
        fansItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fancontrol fancontrol = new fancontrol();
                fancontrol.getFrame();
            }
        });
        return fansItem;
    }

    public void setAboutItem(MenuItem aboutItem) {
        this.aboutItem = aboutItem;
    }

    public MenuItem getAboutItem() {
        aboutItem.addActionListener(new ActionListener() {
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
        return aboutItem;
    }

    public void setPopup(PopupMenu popup) {
        this.popup = popup;
    }

    public PopupMenu getPopup() {
        popup.add(getShowMain());
        popup.addSeparator();
        popup.add(getFansItem());
        popup.add(getTurboItem());
        popup.addSeparator();
        popup.add(getPrefsItem());
        popup.addSeparator();
        popup.add(getAboutItem());
        popup.add(getExit());
        return popup;
    }

    public void setTrayIcon(TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
    }

    public TrayIcon getTrayIcon() {
        trayIcon.setPopupMenu(getPopup());
        return trayIcon;
    }

    public void setSystray(SystemTray systray) {
        this.systray = systray;
    }

    public SystemTray getSystray() {
        try {
            systray.add(getTrayIcon());
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return systray;
    }

}
