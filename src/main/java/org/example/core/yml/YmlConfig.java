package org.example.core.yml;

import java.util.Map;

public class YmlConfig {

    final Map<String, Object> config;

    public YmlConfig(Map<String, Object> config) {
        this.config = config;
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
}
