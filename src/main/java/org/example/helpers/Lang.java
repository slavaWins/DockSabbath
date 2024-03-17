package org.example.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Lang {

    public static String lang = null;

    public static Map<String, String> words = new HashMap<String, String>();


    private static void load() {

       // System.out.println("LOADING lang ");
        String file = FileHelper.readFile(new File("./lang/" + getLang() + ".json"));

        if (file == null) file = FileHelper.readFile(new File("./lang/en.json"));
        if (file == null) file = FileHelper.readFile(new File("./lang/ru.json"));

        if (file != null) {
           // System.out.println(file);
            words = new Gson().fromJson(file, words.getClass());
        }
    }

    private static void save() {


        String c = new GsonBuilder().setPrettyPrinting().create().toJson(words);
        FileHelper.writeFile(new File("./lang/" + getLang() + ".json"), c);

    }

    public static String t(String key, String text) {


        if (words.containsKey(key)) {
            return words.get(key);
        }
        getLang();
        if (words.containsKey(key))    return words.get(key);

        words.put(key, text);
        save();
        return  text;
    }

    public static String getLang() {

        if (lang != null) return lang;

        lang = AppConfiguration.get().lang ;
        if(lang==null)lang="en";
        load();
        return lang;
    }

}
