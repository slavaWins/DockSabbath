package org.example;

import org.example.core.ChatColor;
import org.example.core.Fastcommand;
import org.example.services.cmd.ComboCmd;
import org.example.core.http_server.HttpServiceMain;
import org.example.services.git.GitCmd;
import org.example.services.git.GitDownload;
import org.example.services.git.GitHttp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Fastcommand> comanders = new ArrayList<Fastcommand>();

    public static void onEnable() {


        HttpServiceMain.start();


        GitHttp gitHttp = new GitHttp();
        gitHttp.init();


        comanders.add(new ComboCmd());
        comanders.add(new GitCmd());
    }


    public static void main(String[] args) {

        onEnable();
        run();
    }


    public static void run() {

        Scanner scanner = new Scanner(System.in);
        System.out.println(ChatColor.YELLOW + "====Starting====");

        while (true) {
            String command = scanner.nextLine();

            if (command.equals("stop")) {
                ComboCmd.StopAll();
                break;
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