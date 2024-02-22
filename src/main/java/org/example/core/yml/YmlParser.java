package org.example.core.yml;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;

public class YmlParser {


    public static void update(YmlConfig config) {

        Map<String, Object> data = config.config;


        Yaml yaml = new Yaml();
        FileWriter writer = null;
        try {
            writer = new FileWriter(config.fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        yaml.dump(data, writer);
    }

    public static YmlConfig read(String fileName) {

        File fileNameFile = new File(fileName);
        if (!fileNameFile.exists()) {
            System.out.println("no finded config file " + fileNameFile);
            return null;
        }

        Yaml yaml = new Yaml();
        try {
            FileInputStream input = new FileInputStream(fileName); // Поменяйте на путь к вашему файлу

            // читаем данные из файла в Map
            Map<String, Object> data = yaml.load(input);


            // выводим данные на экран
             //System.out.println(data);
            return new YmlConfig(data, fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
