package org.example.services.nsconfigs;

import org.example.core.yml.YmlConfig;
import org.example.helpers.IoHelper;
import org.example.helpers.IpHelper;
import org.example.repositories.ComposesFilesRepository;
import org.example.repositories.NsConfigsRepository;

import java.io.File;
import java.util.Map;

public class NsFileReplecer {


    public static void Repleing(String ns) {

        YmlConfig config = null;
        for (YmlConfig _config : NsConfigsRepository.getNsConfgis()) {
            if (_config.name.equalsIgnoreCase(ns)) {
                config = _config;
                break;
            }
        }

        if (config == null) {
            System.out.println("ERROR config file");
            return;
        }

        File dir = ComposesFilesRepository.getNsByName(ns);
        if (dir == null) {
            System.out.println("ERROR Dir");
            return;
        }


        for (String filePath : config.getList("replaces-files")) {



            File composeFile = new File(dir, filePath);
            if (!composeFile.exists()) {
                System.out.println("ERROR replace file file:  " + filePath);
               continue;
            }

            String contentFile = IoHelper.readFile(composeFile);

            for (Map.Entry<String, String> entry : config.getDictionary("replaces").entrySet()) {
                String to = entry.getValue();
                if (to.indexOf("@ip")>-1) {
                    to = to.replace("@ip", IpHelper.getIp());
                }
                contentFile = contentFile.replace(entry.getKey(), to);
            }
            IoHelper.writeFile(composeFile, contentFile);

        }

        System.out.println("FILRE REPLACED! docker-compose file");

    }
}
