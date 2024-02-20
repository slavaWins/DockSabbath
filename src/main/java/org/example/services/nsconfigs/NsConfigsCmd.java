package org.example.services.nsconfigs;

import org.example.core.Fastcommand;
import org.example.core.yml.YmlConfig;
import org.example.services.ns.NsParse;

import java.util.Map;

public class NsConfigsCmd extends Fastcommand {

    private static NsConfigsCmd instance;

    public static NsConfigsCmd getInstance() {
        return instance;
    }

    public NsConfigsCmd() {
        super("ns");

        instance = this;

        CommandElemet com = new CommandElemet();
        com.subcommond = "list";
        com.descrip = "Получить все конфиги ns";
        com.event = this::GetNs;
        commands.add(com);


    }

    public void GetNs(String[] args) {

        sendMessage("Список всех простратв: ");
        for (YmlConfig config : NsHelper.getNsConfgis()) {

            String msg = "";

            msg += ("- " + config.name);
            msg += ("\n repos: " + config.get("git.owner") + "/" + config.get("git.repo"));
            msg += ("\n branch: " + config.get("git.branch"));

            System.out.println( config.getList("replaces-files"));

            msg += "\n\n Replace files:";
            for (String entry : config.getList("replaces-files")) {
                msg += "\n - " + entry ;
            }

            msg += "\n\n Replace paterns:";
            for (Map.Entry<String, String> entry : config.getDictionary("replaces").entrySet()) {
                msg += ("\n REPLACE: " + entry.getKey() + " -> " + entry.getValue());
            }

            sendMessage(msg);

        }
    }

}
