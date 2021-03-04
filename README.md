# <div align = center><img src="https://raw.githubusercontent.com/datcuandrei/MacLinuxUtils/master/resources/ban.png" /></div>
MacLinuxUtils is the free and open-source alternative of macOS apps like Mac Fan Control,smcFanControl and Turbo Boost Switcher,but for Linux.MacLinuxUtils aim is to bring everything you love about macOS utilities for all Linux distros,in one package.

Table of Contents
=================

  * [Features](#features)
    * [Graphical User Interface](#graphical-user-interface)
    * [Fan Control](#fan-control)
    * [Controlling the fans](#controlling-the-fans)
    * [Manual Mode (Experimental)](#manual-mode-experimental)
    * [Presets](#presets)
    * [Turbo Boost Switcher](#turbo-boost-switcher)
    * [System tray icon](#system-tray-icon)
    * [Experimental mode](#experimental-mode)
  * [Requirements](#requirements)
  * [Download](#download)
  * [How to install](#how-to-install)
    * [Portable use](#portable-use)
    * [Permission denied](#permission-denied)
    * [Why is it asking for my password?](#why-is-it-asking-for-my-password-)
  * [Issues](#issues)
  * [LICENSE](#license)
  * [Mentions of MacLinuxUtils](#mentions-of-maclinuxutils)

# Features
### Graphical User Interface
MacLinuxUtils is the only fan controller and turbo boost switcher for Macs running Linux that has a GUI.
<img src="https://raw.githubusercontent.com/datcuandrei/MacLinuxUtils/master/captures/mainmenunew.png" width=50% height=50% />

*also has dark mode*

<img src="https://raw.githubusercontent.com/datcuandrei/MacLinuxUtils/master/captures/mainmenudarknew.png" width=50% height=50% />

### Fan Control
Just like the name implies,you can can control your Mac's fans,monitor temperatures(2 CPUs,GPU,RAM,Battery,4 HDD Bays and Optical Drive.Please note that applesmc.768 on Linux might not identify all of these sensors,even if your Mac has them,it depends on your machine!) and fan speeds.

<img src="https://raw.githubusercontent.com/datcuandrei/MacLinuxUtils/master/captures/fancontrol.png" width=30% height=30% />

The biggest highlight about the fan control is that it supports an unlimited number of fans,as long as the SMC recognizes them!
Here is a screenshot of MacLinuxUtils operating 4 fans :

<img src="https://raw.githubusercontent.com/datcuandrei/MacLinuxUtils/master/captures/4fans.png" />

### Controlling the fans
Once you click the `Edit` button for a fan,you will be greeted with this menu :

<img src="https://raw.githubusercontent.com/datcuandrei/MacLinuxUtils/master/captures/fanmenuwithmanual.png" />

You can set an automatic speed(which is set by the SMC itself),a constant speed with the help of the slider and manual mode(which is only working under experimental mode for the time being).

### Manual mode (Experimental)
Manual mode is a more advanced automatic mode.It let's you set fan speeds for certain temperatures.

<img src="https://raw.githubusercontent.com/datcuandrei/MacLinuxUtils/master/captures/manualmode.png" width=70% height=70%/>

This feature is currently work-in-progress,so it was included with v2.3.0 under experimental mode for users to test.Any issues that the user finds it is recommended that it is reported in the Issues section.

### Presets 
Loving the current setup you have on MacLinuxUtils?Now you can save it and load it everytime you want,even at startup!
Note : Under experimental mode,there are still issues when switching from a preset that has manual mode to a preset that has automatic/constant mode,hence why manual mode is not present in the stable build.

<img src="https://raw.githubusercontent.com/datcuandrei/MacLinuxUtils/master/captures/presets.png" />

### Turbo Boost Switcher
Intel Turbo Boost technology accelerates processor and graphics performance for peak loads, automatically allowing processor cores to run faster than the rated operating frequency if they’re operating below power, current, and temperature specification limits.When disabled,it keeps your Mac(assuming it supports Turbo Boost)cooler and saves battery.

<img src="https://raw.githubusercontent.com/datcuandrei/MacLinuxUtils/master/captures/turboboost.png" />

### System tray icon
Once you start MacLinuxUtils,you will see this icon <img src="https://raw.githubusercontent.com/datcuandrei/MacLinuxUtils/master/resources/fanico.png" width=5% height=5%/> in your taskbar or top bar(depending on your DE or WM).By right clicking on it,you interact with the program without using the main menu.

<b>IMPORTANT</b> : Certain DE's(for example : GNOME) removed support for legacy tray,which is what MacLinuxUtils is using.Therefore,there is no support on those DE's for system tray.

### Experimental mode
Experimental mode is a special mode created for users that want to test new features that are still work-in-progress.Please note that this is a standalone build;it doesn't depend in any way on the stable build.Keep reading for finding out how to launch MacLinuxUtils under experimental mode.Thank you, @mariobrostech for suggesting the experimental setting.

# Requirements
- Latest Java version.

# Download 
- Installing the latest version of Java is as simple as opening the terminal and typing :

##### Debian,Ubuntu-based :
```bash
$ sudo apt-get update
$ sudo apt install default-jdk
```

##### Arch-based :
```bash
$ sudo pacman -S jdk-openjdk
```

##### Fedora, Oracle Linux, Red Hat Enterprise Linux, etc. : 
```bash
$ sudo dnf search openjdk
```
Choose the desired JDK and then install it :

```bash
$ sudo dnf install java-11-openjdk.x86_64
```

- After installing Java,download the latest version available in [releases](https://github.com/datcuandrei/MacLinuxUtils/releases).
- Extract it where you want the application to be installed.

# How to install
- To install the application,open terminal,``cd path/to/extracted/app``,and then run :
```bash
$ sudo sh installmlu
```

After the installation is done,simply run `maclinuxutils`,or search for it using an application finder.
If the app doesn't launch,use `sudo java -jar /opt/mlu.jar` in the terminal instead.

- To uninstall the application,open terminal,``cd path/to/extracted/app``,and then run :
```bash
$ sudo sh uninstallmlu
```

## Portable use
To use MacLinuxUtils without installing it,after extracting it,open terminal and
```bash
$ cd path/to/extracted/app
$ sudo java -jar mlu.jar
```
or 

```bash
$ cd path/to/extracted/app
$ sudo java -jar mluexp.jar
```
for experimental mode.

To start the application under experimental mode,use the `-e` argument :
```bash
$ maclinuxutils -e
```

## Permission denied
This error occures when the user does not have enough privileges to access the program.
In this case,we need to get ownership of the app by typing :
```bash
$ chmod +x /bin/maclinuxutils
$ chmod +x /opt/mlu.jar
$ chmod +x /opt/mluexp.jar
```
or if you run MacLinuxUtils as portable :
```bash
$ cd path/to/extracted/app
$ chmod +x mlu.jar
$ chmod +x mluexp.jar
```
After that you can run the app using the commands provided above.

## Why is it asking for my password ?
Just like macOS alternatives,the application requires your password to execute operations.
After typing your password in the terminal,the application will run under `super-user` mode(also known as `root`).The super-user mode allows it to modify the system files needed in order to do what it does.For more information of what it modifies,you can check the source code. 

# Issues
If you find issues while running the app,please report them in the [issues](https://github.com/datcuandrei/MacLinuxUtils/issues) section.

# LICENSE
This project is licensed under the Apache 2.0 license. View LICENSE.md to learn more.

# Attribution
- MacLinuxUtils's UI would have not been possible without [FlatLaf](https://www.formdev.com/flatlaf/) library.
- Managing the files was done with the help of [Apache Commons IO](https://commons.apache.org/proper/commons-io/) 
- Special thanks to the [Unsupported Macs](https://discord.gg/XbbWAsE) community.

# Mentions of MacLinuxUtils
- Wolfie's Tech Blog : [Running Debian on my Macbook](https://wolfiestech.blogspot.com/2020/10/running-debian-on-my-macbook.html)
- Gitbook : [Fan Control for Macs running Linux](https://datcu-andrei-2.gitbook.io/maclinuxfancontrol/)
