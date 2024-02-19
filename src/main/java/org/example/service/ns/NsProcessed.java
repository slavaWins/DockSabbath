package org.example.service.ns;

import org.example.core.ChatColor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class NsProcessed {
    public static List<Process> processes = new ArrayList<>();


    public static void Stop() {

        for (Process process : processes) {
            PrintWriter writer = new PrintWriter(process.getOutputStream());

            writer.println("docker-compose stop");

            //writer.flush();
        }

    }

    public static Process addProcess(String nsName, String cmdParts) {

        Process process = anonimProcess(nsName, cmdParts);
        processes.add(process);

        return process;
    }

    public static Process anonimProcess(String nsName, String cmdPartsFull) {

        List<String> cmdParts = new ArrayList<>();
        for (String part : cmdPartsFull.split(" ")) {
            cmdParts.add(part);
        }


        File folder = NsParse.getNsByName(nsName);

        if (folder == null) {
            System.out.println("Не найдена ns с именем " + nsName);
            return null;
        }

        System.out.println(ChatColor.BRIGHT_BLACK + " STARTING");

        ProcessBuilder processBuilder = new ProcessBuilder(cmdParts)
                .directory(folder)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectError(ProcessBuilder.Redirect.INHERIT);

        Process process = null;

        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

        return process;
    }


    public static void executeProcessAsync(Process process) {
        CompletableFuture<Void> futures = CompletableFuture.runAsync(() -> {
            try {
                // Выполнение команды или другой логики для каждого процесса
                // process.getOutputStream(), process.getInputStream() позволяет взаимодействовать с процессом
                // Например:
                // process.waitFor(); можно использовать для дожидания завершения процесса
                // process.exitValue(); можно использовать для получения кода завершения процесса

                System.out.println("Command executed in process: " + process.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public static Process getProccesByNs(String string) {
        for (Process process : processes) {
            if (process.toString().contains(string)) {
                return process;
            }
        }
        return null;
    }
}
