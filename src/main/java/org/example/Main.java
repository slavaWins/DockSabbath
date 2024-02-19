package org.example;

import org.example.core.ChatColor;
import org.example.core.Fastcommand;
import org.example.services.cmd.ComboCmd;
import org.example.services.http_service.HttpServiceMain;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ChatColor.YELLOW + "====Starting====");


        HttpServiceMain.start();

        List<Fastcommand> comanders = new ArrayList<>();

        comanders.add(new ComboCmd());

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