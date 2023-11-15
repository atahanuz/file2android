## --Work in progress

# File2Android

 File2Android is a Java program to easily send files to your Android device from your computer. It works on MacOS, Windows, Linux or any environment that supports Java.

You've probably experienced how much of a pain it is to transfer a file to your Android device. Using a cable is cumbersome, and online file transfer methods such as Gmail have file size limits and block some file extensions suspecting malware. There are many third-party software and apps, but most of them are frustrating to use, if not outright scams. I created File2Android to make transferring any kind of file(s) of any size extremely easy.

## Features
- No limit on file size
- As fast as possible, uses the FTP protocol which has zero speed overhead
- Supports any kind of files of any count

 ## Installation
 
Download the File2Android.jar. You can run it by double clicking or running this command in terminal
```
java -jar File2Android.jar
```
Java Runtime Enviroment must be installed on your system to run File2Android or any Java program. You can easily install it from here
https://www.java.com/tr/download/manual.jsp

File2Android uses FTP protocol to transfer files to Android. You need to create a FTP server on your Android phone, which is very easy with this free app I found https://play.google.com/store/apps/details?id=com.medhaapps.wififtpserver

After opening the app click START and the server is ready. Now open File2Android and set the FTP Server URL to be the same with Android. Note that the computer and the Android device must be on the same network.

![Alt Text](https://i.imgur.com/UED9YKz.jpg)

Now there are three ways you can add a file 

- You can enter a file's absolute path and click "Add"
- You can drag file(s) to the drop area.
- You can click the drag area which opens a prompt to select files

  You can add as many file as you want. All added files will be displayed.

  Then you can click transfer to transfer all files to your Android device.<br>
<img src="https://i.imgur.com/1HrvLib.png" width="500" height="500" alt="Alt">
<br>
You should see that the transfer is completed. The program transfers the file to your Android device's home directory so you can easily view and use them from your file browser.

<img src="https://i.imgur.com/h7mQCwi.jpg" width="150" height="240" alt="Alt">

## Planned in future versions
- Transfer directories (currently only supports individual files)
- Option tp transfer over SFTP if you value security
- Real-time logging of the transfer speed and progression
- Develop my own Android app for the FTP server
- Make a command line version of the program
- Make the program a generalized file transferrer between Android and Android, PC to PC etc.

## Contact
Create an Issue in this repo from [here](https://github.com/atahanuz/file2android/issues/new)
You can mail me at atahanuz23@gmail.com too but a GitHub issue is preferred as it sends a notification to my phone so I can response immediately.
