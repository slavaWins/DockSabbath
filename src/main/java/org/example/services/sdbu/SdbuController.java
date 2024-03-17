package org.example.services.sdbu;

import org.example.core.BaseCommandController;
import org.example.helpers.ChatColor;
import org.example.helpers.Lang;
import org.example.services.Combo.ComboController;
import org.example.services.git.GitDownload;
import org.example.services.nsconfigs.NsFileReplecer;
import org.example.services.procces.NsProcessed;

public class SdbuController extends BaseCommandController {


    public SdbuController() {
        super("Автоматизация");

        instance = this;

        CommandElemet com = new CommandElemet();
        com.subcommond = "sdbu";
        com.descrip = "Остановить, скачать с гита, отбилдить и запустить. Полный цикл обновы";
        com.event = this::Sdbu;
        com.arguments.add("ns");
        commands.add(com);


    }

    public static SdbuController instance;

    public static SdbuController getInstance() {
        return instance;
    }

    public void Sdbu(String[] strings) {


        String ns = strings[0];

        sendMessage(Lang.t("sdbu.pre","Сейчас будет запущен полный цикл sdbu(обновления с гита и перезапуск) для: ")+ns);

        ComboController composerCmd = ComboController.getInstance();


        sendMessage(Lang.t("git.start","Скачивание новой версии репозитория для ns: ") + ns);
        if (!GitDownload.downloadGitFromSettings(ns)) {

            sendMessage(ChatColor.RED +Lang.t("git.error", "Не удалось скачать репозитория для: ") + ns);
            return;
        }

        sendMessage(Lang.t("ns.stop","Остановка неймспейса " )+ ns);
        composerCmd.Stop(argStringToArray(ns));

        NsFileReplecer.Repleing(ns);

        sendMessage(Lang.t("ns.build.wait","Билдинг запустить через несколько сек. "));

        composerCmd.Build(argStringToArray(ns));

        NsProcessed.addProcess(strings[0], "docker-compose up --build", false);

    }


}
