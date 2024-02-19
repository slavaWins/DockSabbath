package org.example.services.git;

import org.example.core.Fastcommand;
import org.example.services.ns.NsProcessed;

public class GitCmd extends Fastcommand {


    public GitCmd() {
        super("git");


        CommandElemet com;

        com = new CommandElemet();
        com.subcommond = "download";
        com.descrip = "Скачать последнию версию ветки и загрузить в /ns-files";
        com.event = this::Download;
        com.arguments.add("ns");
        commands.add(com);


    }


    private void Download(String[] strings) {

        sendMessage("Скачиание с гита");
        GitDownload.downloadGitFromSettings(strings[0]);

        sendMessage("Папка готова");
    }


}
