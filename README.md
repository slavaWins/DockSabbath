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


## NS конфигруация

В папке /ns лежат конфиги в которых описывается откуда берется неймпспейс\композ.
Например у нас есть репозиторий https://github.com/slavaWins/demo-back-docksab и мы хотим подключить его, и назвать backend-service. Создаем файл backend-service.yml и указываем в нем такие настройки:

```
name: "backend-service"
git:
  owner: "slavaWins"
  repo: "demo-back-docksab"
  branch: "master"
  token: "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C262WI7lBNad"
  path: "/"



replaces-files:
  - "/docker-compose.yml"
  - "/README.md"

replaces:
  localhost:5124: "@ip:5124"
  "http://frontend-service": "http://@ip"
  "MY_IP_EXAMPLE": "@ip"
```

git token - это персональный токен от гита

replaces-files - это список файлов в которых нужно заменить строчки, например ip, токены, порты.
replaces - это строчки которые будут заменены