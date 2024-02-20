package org.example.services.nsconfigs;

import org.example.core.yml.YmlConfig;
import org.example.core.yml.YmlParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NsHelper {


    public static List<YmlConfig> getNsConfgis() {
        List<File> files = getNsConfgisFiles();

        List<YmlConfig> result = new ArrayList<>();

        for (File file : files) {

            YmlConfig ymlConfig = YmlParser.read(file.getAbsolutePath());
            if (ymlConfig != null) {
                result.add(ymlConfig);
            }

        }
        return result;
    }

    public static List<File> getNsConfgisFiles() {

        List<File> folderList = new ArrayList<>();

        File directory = new File("./ns");

        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Проверяем, что путь ведет к папке
        if (directory.isDirectory()) {

            // Получаем список файлов и папок внутри данной директории
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".yml")) {
                        folderList.add(file);
                    }
                }
            } else {
                System.out.println("Папка пуста.");
            }
        } else {
            System.out.println("Указанный путь не ведет к папке.");
        }
        return folderList;
    }
}
