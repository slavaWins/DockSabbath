package org.example.service.ns;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NsParse {

    public static File getNsByName(String nsName) {



        for (File file : NsParse.getNsDirs()) {
            System.out.println(file.getName());
            if (file.getName().toLowerCase().equals(nsName.toLowerCase())) {
             return  file;
            }
        }
return  null;
    }
    public static List<File> getNsDirs() {

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
                    if (file.isDirectory()) {
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

    public static List<String> getNs() {

        List<String> folderList = new ArrayList<>();

        for (File file : getNsDirs()) {
            folderList.add(file.getName());
        }
        return folderList;

    }

}
