package org.example.services.Combo;

import org.example.core.Fastcommand;
import org.example.helpers.Lang;
import org.example.repositories.ComposesFilesRepository;
import org.example.repositories.NsAttachRepository;
import org.example.services.docker_parser.contracts.NsInfoContract;
import org.example.services.procces.NsProcessed;
import org.example.views.PodView;

import java.util.List;

public class ComboController extends Fastcommand {

    private static ComboController instance;

    public static ComboController getInstance() {
        return instance;
    }

    public ComboController() {
        super("Управление неймспейсами");

        instance = this;

        CommandElemet com = new CommandElemet();
        com.subcommond = "ns";
        com.descrip = "Показать все нсы";
        com.event = this::ShowAllNs;
        commands.add(com);

        com = new CommandElemet();
        com.subcommond = "pods";
        com.descrip = "Показать состояние подов";
        com.event = this::Pods;
        com.arguments.add("ns");
        commands.add(com);

        com = new CommandElemet();
        com.subcommond = "up";
        com.descrip = "Запустить композ";
        com.event = this::Up;
        com.arguments.add("ns");
        commands.add(com);

        com = new CommandElemet();
        com.subcommond = "stop";
        com.descrip = "Остановить ns";
        com.event = this::Stop;
        com.arguments.add("ns");
        commands.add(com);

        com = new CommandElemet();
        com.subcommond = "stopall";
        com.descrip = "Остановить все ns";
        com.event = this::StopAllCommand;
        commands.add(com);

        com = new CommandElemet();
        com.subcommond = "upall";
        com.descrip = "Запустить все композы";
        com.event = this::UpAll;
        commands.add(com);


        com = new CommandElemet();
        com.subcommond = "build";
        com.descrip = "Билдить коhмпоз";
        com.event = this::Build;
        com.arguments.add("ns");
        commands.add(com);

    }

    private void StopAllCommand(String[] strings) {
        StopAll();
    }

    public static void ShowAllNs() {
        List<NsInfoContract> nsInfos = NsAttachRepository.attacingAllData();

        PodView.ns(nsInfos);
    }

    private void ShowAllNs(String[] args) {
        ShowAllNs();
    }

    public static void StopAll() {


        for (String name : ComposesFilesRepository.getNs()) {
            NsProcessed.anonimProcess(name, "docker-compose stop", false, 0);
        }
    }

    public void Up(String[] strings) {

        sendMessage(Lang.t("up.all","Запуск неймспейса " )+ strings[0]);
        NsProcessed.addProcess(strings[0], "docker-compose up", false);
    }


    public void Pods(String[] strings) {

        List<NsInfoContract> nsInfos = NsAttachRepository.attacingAllData();

        for (NsInfoContract ns : nsInfos) {
            if (!ns.name.equalsIgnoreCase(strings[0])) continue;
           // System.out.println("/pods " + ns.name);
            PodView.pods(ns);
            return;
        }
        sendMessage(Lang.t("ns.not","Не найден неймспейс " )+ strings[0]);

    }

    public void Stop(String[] strings) {

        sendMessage(Lang.t("up.stoping","Остановка неймспейса " )+ strings[0]);
        NsProcessed.addProcess(strings[0], "docker-compose stop", false);
    }


    public void UpAll(String[] strings) {
        sendMessage(Lang.t("up.all","Запуск всех неймспейсов"));

        for (String name : ComposesFilesRepository.getNs()) {

            NsProcessed.addProcess(name, "docker-compose up", false);
        }
    }

    public void Build(String[] strings) {

        sendMessage(Lang.t("up.build","Билдинг неймспейса " )+ strings[0]);

        NsProcessed.addProcess(strings[0], "docker-compose build", false);
    }


}
