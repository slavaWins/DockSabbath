package org.example;

import org.example.core.Fastcommand;
import org.example.helpers.ChatColor;
import org.example.helpers.Lang;
import org.example.helpers.LogoDesignHelper;
import org.example.helpers.MainConfig;
import org.example.services.Combo.ComboController;
import org.example.services.api.ClientReadingApi;
import org.example.services.git.GitCmd;
import org.example.services.git.GitHttp;
import org.example.services.hashing.HashingService;
import org.example.services.http.HttpServiceBase;
import org.example.services.sdbu.SdbuController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Fastcommand> comanders = new ArrayList<Fastcommand>();

    public static void onEnable() {


        LogoDesignHelper.logo();


        MainConfig.get();

        HttpServiceBase.start();


        GitHttp gitHttp = new GitHttp();
        gitHttp.init();

        new ClientReadingApi().init();

        comanders.add(new ComboController());
        comanders.add(new GitCmd());
        comanders.add(new SdbuController());
        // HashingService.main();

        //ComboController.ShowAllNs();

        //SertificateCheckrt.Generate();
    }


    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {

                // Код выполняемый при закрытии приложения
                System.out.println( Lang.t("app.stopping","Начата безопасная остановка приложения. Сейчас будут остановлены все контейнеры и неймспейсы."));
                onDisable();
            }
        });

        onEnable();
        run();


    }

    static boolean isDisbled = false;

    public static void onDisable() {
        if (isDisbled) return;
        isDisbled = true;
        ComboController.StopAll();
        System.out.println("Disabled.");
    }


    public static void commandListener(String command) {
        if (command.equals("stop")) {
            onDisable();
            return;
        }

        if (command.equals("help")) {
            for (Fastcommand comander : comanders) {
                comander.sendHelpCommand(new String[0]);
            }
            return;
        }

        boolean isClosed = false;
        for (Fastcommand comander : comanders) {
            if (comander.input(command)) {
                isClosed = true;
                return;
            }
        }

        if (!isClosed) {
            System.out.println(ChatColor.RED +  Lang.t("notfoundcomand","Команда не найдена!") + ChatColor.WHITE + " Use command /help");
             /*   for (Fastcommand comander : comanders) {
                    comander.sendHelpCommand(new String[0]);
                }*/
        }
    }

    public static void run() {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            String command = scanner.nextLine();
            if(isDisbled){
                System.out.println("CTRL+C to exit");
                continue;
            }
            commandListener(command);


        }

    }
}