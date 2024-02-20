package org.example.services.auto;

import org.example.core.ChatColor;
import org.example.core.Fastcommand;
import org.example.services.Combo.ComposerCmd;
import org.example.services.git.GitCmd;
import org.example.services.git.GitDownload;
import org.example.services.ns.NsProcessed;
import org.example.services.nsconfigs.NsFileReplecer;

import java.util.Timer;
import java.util.TimerTask;

public class AutoComboCmd extends Fastcommand {


    public AutoComboCmd() {
        super("combo");

        instance = this;

        CommandElemet com = new CommandElemet();
        com.subcommond = "sdbu";
        com.descrip = "Остановить, скачать с гита, отбилдить и запустить. Полный цикл обновы";
        com.event = this::Sdbu;
        com.arguments.add("ns");
        commands.add(com);


    }

    public static AutoComboCmd instance;

    public static AutoComboCmd getInstance() {
        return instance;
    }

    public void Sdbu(String[] strings) {


        String ns = strings[0];

        sendMessage("Sdbu для " + ns);
        sendMessage("Сейчас будет запущен");

        ComposerCmd composerCmd = ComposerCmd.getInstance();


        sendMessage("Скачивание новой версии " + ns);
        if (!GitDownload.downloadGitFromSettings(ns)) {

            sendMessage(ChatColor.RED + "Не удалось скачать гит " + ns);
            return;
        }

        sendMessage("Остановка нса " + ns);
        composerCmd.Stop(argToList(ns));

        NsFileReplecer.Repleing(ns);

        sendMessage("Билдинг запустить через неск сек ");

        sendMessage("Билдинг и поднятие " + ns);
        composerCmd.Build(argToList(ns));

        NsProcessed.addProcess(strings[0], "docker-compose up --build");

    }


}
