<h1 align="center">
  <br>
  <img src="img/baner.png" alt="ReAssets" width="76%">
  <br>
  DockSabbath
</h1>

<center>
<small  >
This is like kubectl, but for the dumb and the poor. I made it for myself.
</small>
</center>

## About DockSabbath
DockSabbath is a simple analog of kubectl with a minimal set of commands for convenient execution. The application is a shell for docker-compose and allows running multiple compositions in parallel. For convenience, the application can download the latest branch version from GitHub, deploy it to the server, and run it automatically.

##   Installation on Linux

Install Java

    wget https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.deb
    sudo apt install ./jdk-21_linux-x64_bin.deb
    java --version

Download the latest release and upload it to a convenient directory on the server. Then navigate to the directory and start the application with the following command:

> java -jar DockSabbath-1.0-SNAPSHOT-jar-with-dependencies.jar -nogui

After the first launch, the application will generate tokens for Git and the client API. To view them, open the config.json file.
 

## Use
