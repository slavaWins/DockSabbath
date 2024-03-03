package org.example;

import org.example.core.Fastcommand;
import org.example.helpers.*;
import org.example.services.Combo.ComboController;
import org.example.services.api.ClientReadingApi;
import org.example.services.git.GitCmd;
import org.example.services.git.GitHttp;
import org.example.services.http.HttpServiceBase;
import org.example.services.sdbu.SdbuController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Fastcommand> commandServicesHandlers = new ArrayList<Fastcommand>();
    static boolean isDisabled = false;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                // Код выполняемый при закрытии приложения
                System.out.println(Lang.t("app.stopping", "Начата безопасная остановка приложения. Сейчас будут остановлены все контейнеры и неймспейсы."));
                onDisable();
            }
        });

        onEnable();
        run();
    }


    public static void onEnable() {
        LogoDesignHelper.logo();

        MainConfig.init();

        HttpServiceBase.start();

        new GitHttp().init();
        new ClientReadingApi().init();


        commandServicesHandlers.add(new ComboController());
        commandServicesHandlers.add(new GitCmd());
        commandServicesHandlers.add(new SdbuController());
    }


    public static void onDisable() {
        if (isDisabled) return;
        isDisabled = true;
        ComboController.StopAll();
        System.out.println("Disabled.");
    }


    public static void commandHandle(String command) {
        if (command.equals("stop")) {
            onDisable();
            return;
        }

        if (command.equals("stop-safely")) {
            isDisabled = true;
            return;
        }

        if (command.equals("help")) {
            for (Fastcommand comander : commandServicesHandlers) {
                comander.sendHelpCommand(new String[0]);
            }
            return;
        }

        boolean isClosed = false;
        for (Fastcommand comander : commandServicesHandlers) {
            if (comander.input(command)) {
                isClosed = true;
                return;
            }
        }

        if (!isClosed) {
            System.out.println(ChatColor.RED + Lang.t("notfoundcomand", "Команда не найдена!") + ChatColor.WHITE + " Use command /help");
        }
    }

    public static void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();
            if (isDisabled) {
                break;
            }
            commandHandle(command);
        }

    }
}