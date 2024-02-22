package org.example.services.procces;

import org.example.helpers.ChatColor;
import org.example.helpers.Lang;
import org.example.repositories.ComposesFilesRepository;

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

    public static ProccesedResponseContract addProcess(String nsName, String cmdParts, boolean logInString) {

        ProccesedResponseContract process = anonimProcess(nsName, cmdParts, logInString, 0);
        if(process==null){
            System.out.println(Lang.t("procces.null","Не найден процессс для ")+ nsName);
            return null;
        }
        processes.add(process.process);

        return process;
    }

    public static ProccesedResponseContract anonimProcess(String nsName, String cmdPartsFull, boolean logInString, int dropOnLines) {

        ProccesedResponseContract responseContract = new ProccesedResponseContract();

        List<String> cmdParts = new ArrayList<>();
        for (String part : cmdPartsFull.trim().split(" ")) {
            cmdParts.add(part);
        }


        File folder = ComposesFilesRepository.getNsByName(nsName);

        if (folder == null) {

            System.out.println(Lang.t("ns.not","Не найден неймспейс " ) +nsName);

            return null;
        }


        //  System.out.println(ChatColor.BRIGHT_BLACK + " STARTING");

        ProcessBuilder processBuilder = new ProcessBuilder(cmdParts)
                .directory(folder)
                .redirectError(ProcessBuilder.Redirect.INHERIT);

        if (!logInString) {
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        }

        Process muProccess = null;

        try {
            //  process = processBuilder.start();

            muProccess = processBuilder.start();
            responseContract.process = muProccess;

            if (logInString) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(muProccess.getInputStream()));
                String line;
                int lineNumber = 0;
                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    responseContract.text += "\n" + line;
                    //   System.out.println("---" + line);
                    if (dropOnLines > 0 && lineNumber > dropOnLines) {
                        muProccess.destroy();
                        break;
                    }
                }
            }

            if (logInString) {
                muProccess.waitFor();
            }

            if (!logInString) {
                muProccess.onExit().thenAccept((Process xmuProccess) -> {
                    System.out.println(ChatColor.GREEN + Lang.t("process","Процесс: ") + nsName + " -- " + cmdPartsFull);
                    System.out.println(Lang.t("process.end","Процесс завершился"));
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return responseContract;
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
