package org.example.services.cmd;

import org.example.core.Fastcommand;
import org.example.service.ns.NsParse;
import org.example.service.ns.NsProcessed;

import java.util.List;

public class ComboCmd extends Fastcommand {


    public ComboCmd() {
        super("combo");


        CommandElemet com = new CommandElemet();
        com.subcommond = "ns";
        com.descrip = "Получить все стейджы или неймспейсы";
        com.event = this::GetNs;
        commands.add(com);

        com = new CommandElemet();
        com.subcommond = "up";
        com.descrip = "Запустить композ";
        com.event = this::Up;
        com.arguments.add("ns");
        commands.add(com);


        com = new CommandElemet();
        com.subcommond = "build";
        com.descrip = "Билдить композ";
        com.event = this::Build;
        com.arguments.add("ns");
        commands.add(com);


        com = new CommandElemet();
        com.subcommond = "run";
        com.descrip = "Запустить композ Run";
        com.event = this::Run;
        com.arguments.add("ns");
        commands.add(com);

    }

    private void GetNs(String[] args) {

        sendMessage("Список всех простратва: ");
        for (String name : NsParse.getNs()) {
            sendMessage(name);
        }

    }
    public static void StopAll() {


        for (String name : NsParse.getNs()) {
            NsProcessed.anonimProcess(name, "docker-compose stop");
        }
    }

    private void Up(String[] strings) {

        sendMessage("Запуск нса");
        NsProcessed.addProcess(strings[0], "docker-compose up");
    }

    private void Build(String[] strings) {

        sendMessage("Билд нса");
        NsProcessed.addProcess(strings[0], "docker-compose build");
    }

    private void Run(String[] strings) {

        sendMessage("Запуск нса ауснс " + strings[0]);
        Process process = NsProcessed.getProccesByNs(strings[0]);

        if (process == null) {
            sendMessage("Не найден процесс ");
            return;
        }
        NsProcessed.executeProcessAsync(process);
    }


}
