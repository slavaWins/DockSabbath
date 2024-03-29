package org.example.core.yml;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class YmlConfig {

    public String fileName;
    public String name;
    public final Map<String, Object> config;



    public void update(){
        Map<String, Object> data = config;


        Yaml yaml = new Yaml();



        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        yaml.dump(data, writer);
    }


    public YmlConfig(Map<String, Object> data, String fileName) {
        this.config = data;
        this.fileName = fileName;
        name = fileName;
        File f = new File(fileName);

        name = f.getName().replace(".yml", "").toLowerCase();

    }

    public String get(String key) {
        String[] keyComponents = key.split("\\."); // Разбиваем ключ на компоненты
        Object value = config;
        for (String component : keyComponents) {
            if (value instanceof Map) {
                value = ((Map) value).get(component);  // Получаем значение по ключу
            } else {
                return null; // Если не удалось получить значение, возвращаем null
            }
        }
        return (String) value; // Преобразуем к строке и возвращаем
    }

    public boolean getBool(String key) {
        String[] keyComponents = key.split("\\."); // Разбиваем ключ на компоненты
        Object value = config;
        for (String component : keyComponents) {
            if (value instanceof Map) {
                value = ((Map) value).get(component);  // Получаем значение по ключу
            } else {
                return false; // Если не удалось получить значение, возвращаем null
            }
        }
        return (boolean) value; // Преобразуем к строке и возвращаем
    }


    public Map<String, String> getDictionary(String key) {
        Object value = config.get(key);
        if (value instanceof Map) {
            return (Map<String, String>) value;
        }
        return null;
    }

    public List<String> getList(String key) {
        Object value = config.get(key);
        if (value instanceof List<?>) {
            return (List<String>) value;
        }
        return null;
    }

}
