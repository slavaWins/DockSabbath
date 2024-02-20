package org.example.services.git;

import org.example.core.Fastcommand;
import org.example.services.ns.NsProcessed;
import org.example.services.nsconfigs.NsFileReplecer;

public class GitCmd extends Fastcommand {


    private static GitCmd instance;

    public static GitCmd getInstance() {
        return instance;
    }


    public GitCmd() {
        super("git");


        instance = this;


        CommandElemet com;

        com = new CommandElemet();
        com.subcommond = "download";
        com.descrip = "Скачать последнию версию ветки и загрузить в /ns-files";
        com.event = this::Download;
        com.arguments.add("ns");
        commands.add(com);


    }


    public void Download(String[] strings) {

        sendMessage("Скачиание с гита");
        GitDownload.downloadGitFromSettings(strings[0]);
        NsFileReplecer.Repleing(strings[0]);
        sendMessage("Папка готова");
    }


}
