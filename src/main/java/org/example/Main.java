package org.example;

import org.example.core.ChatColor;
import org.example.core.Fastcommand;
import org.example.services.Combo.ComposerCmd;
import org.example.core.http_server.HttpServiceMain;
import org.example.services.auto.AutoComboCmd;
import org.example.services.git.GitCmd;
import org.example.services.git.GitHttp;
import org.example.services.nsconfigs.NsConfigsCmd;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Fastcommand> comanders = new ArrayList<Fastcommand>();

    public static void onEnable() {


        HttpServiceMain.start();


        GitHttp gitHttp = new GitHttp();
        gitHttp.init();


        comanders.add(new ComposerCmd());
        comanders.add(new NsConfigsCmd());
        comanders.add(new GitCmd());
        comanders.add(new AutoComboCmd());
        NsConfigsCmd.getInstance().GetNs(new String[0]);

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
        ComposerCmd.StopAll();

        System.out.println("DISABLED APP");
    }


    public static void run() {

        Scanner scanner = new Scanner(System.in);
        System.out.println(ChatColor.YELLOW + "====Starting====");

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