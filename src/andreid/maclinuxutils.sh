#!/bin/bash

#	MacLinuxUtils
#	
#	Module name : 
#		maclinuxutils.sh
#	
#	Abstract : 
#		This script is responsible for launching MacLinuxUtils.
#
#	Author : 
#		Andrei Datcu (datcuandrei) 27-August-2020 (last updated : 7-September-2020).


echo "##########################################################################"
echo "MacLinuxUtils will now ask you for your password.
This is required for the program in order to run properly.If you have already entered it,the program will run without prompting it again."
echo "##########################################################################"

sudo su <<HERE

echo "Running MacLinuxUtils..."
echo "You may close the terminal window."

if [ "$1" = "d" ]; then 
    java -jar dark.jar
else
    java -jar light.jar    
fi

HERE
