package org.example;

import org.example.core.Fastcommand;
import org.example.helpers.ChatColor;
import org.example.helpers.LogoDesignHelper;
import org.example.services.Combo.ComboController;
import org.example.services.git.GitCmd;
import org.example.services.git.GitHttp;
import org.example.services.http.HttpServiceBase;
import org.example.services.sdbu.SdbuController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Fastcommand> comanders = new ArrayList<Fastcommand>();

    public static void onEnable() {

        LogoDesignHelper.logo();

        HttpServiceBase.start();


        GitHttp gitHttp = new GitHttp();
        gitHttp.init();


        comanders.add(new ComboController());
        comanders.add(new GitCmd());
        comanders.add(new SdbuController());

        ComboController.ShowAllNs();

        //SertificateCheckrt.Generate();
    }


    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {

                // Код выполняемый при закрытии приложения
                System.out.println("Приложение завершено");
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
        System.out.println("Disabling");
       // ComboController.StopAll();

        System.out.println("DISABLED APP");
    }


    public static void run() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();

            if (command.equals("stop")) {
                onDisable();
                return;
            }

            if (command.equals("help")) {
                for (Fastcommand comander : comanders) {
                    comander.sendHelpCommand(new String[0]);
                }
                continue;
            }

            boolean isClosed = false;
            for (Fastcommand comander : comanders) {
                if (comander.input(command)) {
                    isClosed = true;
                    break;
                }
            }

            if (!isClosed) {
                System.out.println(ChatColor.RED + "Command not found" + ChatColor.WHITE + " use command /help");
             /*   for (Fastcommand comander : comanders) {
                    comander.sendHelpCommand(new String[0]);
                }*/
            }
        }

    }
}