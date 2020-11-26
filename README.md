# <div align = center><img src="https://raw.githubusercontent.com/datcuandrei/MacLinuxUtils/master/resources/banner.png" /></div>
On macOS,there are tools for controlling the fan speeds,switching turbo boost on or off,etc.Sadly,Mac users on Linux have no alternatives for such tools.
MacLinuxUtils's aim is to bring the functionality and ease of use of those tools on Linux distros,all in one package.

## LICENSE
This project is licensed under the GNU GPL v3 license. View LICENSE.md to learn more.

## Requirements:
- Latest Java version.

## Download/How to use : 
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
- To start the application,open terminal,``cd path/to/extracted/app``,and then run :
```bash
$ sh maclinuxutils.sh
```
It can also run under dark mode :
```bash
$ sh maclinuxutils.sh d
```

## Permission denied : 
This error occures when the user does not have enough privileges to access the program.
In this case,we need to get ownership of the app by typing :
```bash
$ chmod +x maclinuxutils.sh
```
After that you can run the app using the commands provided above.

## Why is it asking for my password ?
Just like macOS alternatives,the application requires your password to execute operations.
After typing your password in the terminal,the application will run under `super-user` mode(also known as `root`).The super-user mode allows it to modify the system files needed in order to do what it does.For more information of what it modifies,you can check the source code. 

## Issues : 
If you find issues while running the app,please report them in the [issues](https://github.com/datcuandrei/MacLinuxUtils/issues) section.

## Attribution : 
- MacLinuxUtils's UI would have not been possible without [FlatLaf](https://www.formdev.com/flatlaf/) library.
- Special thanks to the [Unsupported Macs](https://discord.gg/XbbWAsE) community.

## Mentions of MacLinuxUtils : 
- Wolfie's Tech Blog : [Running Debian on my Macbook](https://wolfiestech.blogspot.com/2020/10/running-debian-on-my-macbook.html)
- Gitbook : [Fan Control for Macs running Linux](https://datcu-andrei-2.gitbook.io/maclinuxfancontrol/)
