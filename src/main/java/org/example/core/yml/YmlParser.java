package org.example.core.yml;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class YmlParser {


    public static YmlConfig read(String fileName) {

        File fileNameFile = new File(fileName);
        if (!fileNameFile.exists()) return null;

        Yaml yaml = new Yaml();
        try {
            FileInputStream input = new FileInputStream(fileName); // Поменяйте на путь к вашему файлу

            // читаем данные из файла в Map
            Map<String, Object> data = yaml.load(input);
            // выводим данные на экран
            // System.out.println(data);
            return new YmlConfig(data, fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
