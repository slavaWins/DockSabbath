package org.example.services.git;

import org.example.core.Fastcommand;
import org.example.helpers.Lang;
import org.example.services.nsconfigs.NsFileReplecer;

public class GitCmd extends Fastcommand {


    private static GitCmd instance;

    public static GitCmd getInstance() {
        return instance;
    }


    public GitCmd() {
        super("Гитхаб и загрузка репозиториев");


        instance = this;


        CommandElemet com;

        com = new CommandElemet();
        com.subcommond = "gitupd";
        com.descrip = "Скачать последнию версию ветки и загрузить в /ns-files";
        com.event = this::Download;
        com.arguments.add("ns");
        commands.add(com);


    }


    public void Download(String[] strings) {

        sendMessage(Lang.t("get.from", "Скачивание новой версии ветки с репозитория для ") + strings[0]);
        if (!GitDownload.downloadGitFromSettings(strings[0])) {

            sendMessage(Lang.t("git.notfound", "Не удалось скачать репозиторий для ")+ strings[0]);
            return;
        }
        NsFileReplecer.Repleing(strings[0]);
        sendMessage(Lang.t("git.downloaded", "Репозиторий скачен и обновлен"));
    }


}
