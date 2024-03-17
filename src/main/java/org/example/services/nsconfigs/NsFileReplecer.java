package org.example.services.nsconfigs;

import org.example.core.yml.YmlConfig;
import org.example.helpers.FileHelper;
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
            System.out.println("Not exist directory "+ns);
            return;
        }


        for (String filePath : config.getList("replaces-files")) {



            File composeFile = new File(dir, filePath);
            if (!composeFile.exists()) {
                System.out.println("ERROR replace file file:  " + filePath);
               continue;
            }

            String contentFile = FileHelper.readFile(composeFile);

            for (Map.Entry<String, String> entry : config.getDictionary("replaces").entrySet()) {
                String to = entry.getValue();
                if (to.indexOf("@ip")>-1) {
                    to = to.replace("@ip", IpHelper.getIp());
                }
                for(int i=0;i<5;i++) {
                    contentFile = contentFile.replaceAll(entry.getKey(), to);
                }
            }
            FileHelper.writeFile(composeFile, contentFile);

        }

        System.out.println("FILRE REPLACED! docker-compose file");

    }
}
