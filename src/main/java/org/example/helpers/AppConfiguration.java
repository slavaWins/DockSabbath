package org.example.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.core.yml.YmlConfig;

import java.io.File;

public class AppConfiguration {


    public YmlConfig config;
    public boolean analyticsEnabled;
    public boolean gitEnabled = false;
    public String githubWebhookToken;
    public String clientApiToken;
    public boolean clientApiEnabled = false;
    public String lang = "en";

    public static boolean isBadToken(String token) {
        if (token == null) return true;
        return token.length() < 44;
    }

    public void check() {
        boolean isUpdate = false;

        if (isBadToken(clientApiToken)) {
            clientApiToken = RandomStringGeneratorHelper.generateRandomString(153);
            isUpdate = true;
        }

        if (isBadToken(githubWebhookToken)) {
            githubWebhookToken = RandomStringGeneratorHelper.generateRandomString(60);
            isUpdate = true;
        }

        if (isUpdate) {
            System.out.println(ChatColor.BLUE+  "Configuration tokens have been regenerated to more complex ones. Update the git webhook and client keys. You can view the tokens in the config.json file"+ChatColor.RESET);
            String json = new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(this);

            IoHelper.writeFile(new File("./config.json"), json);
        }
    }


    public static void init() {
        get();
    }

    public static AppConfiguration get() {

        AppConfiguration appConfiguration = null;

        String file = IoHelper.readFile(new File("./config.json"));

        appConfiguration = new Gson().fromJson(file, AppConfiguration.class);
        appConfiguration.check();

        return appConfiguration;

    }
}
