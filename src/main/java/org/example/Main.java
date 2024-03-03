package org.example;

import org.example.contracts.CoreAppCommandsEnum;
import org.example.core.Fastcommand;
import org.example.helpers.*;
import org.example.services.Combo.ComboController;
import org.example.services.api.ClientReadingApiHttpModule;
import org.example.services.git.GitCmd;
import org.example.services.git.GitHttpModule;
import org.example.services.http.HttpService;
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


    private static void onEnable() {
        LogoDesignHelper.showProgammLogo();

        AppConfiguration.init();

        HttpService.start();
        new GitHttpModule().init();
        new ClientReadingApiHttpModule().init();

        commandServicesHandlers.add(new ComboController());
        commandServicesHandlers.add(new GitCmd());
        commandServicesHandlers.add(new SdbuController());
    }
    

    private static void run() {
        Scanner scannerConsoleWrite = new Scanner(System.in);

        while (true) {
            String commandFromConsoleLine = scannerConsoleWrite.nextLine();
            if (isDisabled) {
                break;
            }
            commandHandle(commandFromConsoleLine);
        }

    }

    /**
     * Может выполняться с удаленного клиента
     *
     * @param command
     */
    public static void commandHandle(String command) {
        if (command.equals(CoreAppCommandsEnum.stop.toString())) {
            onDisable();
            return;
        }

        if (command.equals(CoreAppCommandsEnum.stop_safly.toString())) {
            isDisabled = true;
            return;
        }

        if (command.equals(CoreAppCommandsEnum.help.toString())) {
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

    private static void onDisable() {
        if (isDisabled) return;
        isDisabled = true;
        ComboController.StopAll();
        System.out.println("Disabled.");
    }

}