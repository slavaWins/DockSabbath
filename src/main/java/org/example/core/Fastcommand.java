package org.example.core;


import org.example.helpers.ChatColor;
import org.example.helpers.Lang;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Fastcommand {

    public boolean onlyOp = true;
    private final String section;
    public List<CommandElemet> commands = new ArrayList<>();


    public String[] argToList(String arg){
        String[] list = new String[1];
        list[0] =(arg);
        return list;
    }

    /**
     * Команда которая вызывается если вписать /base по дефалту хелп
     */
    public Consumer<String[]> rootEvent = this::sendHelpCommand;

    public Fastcommand(String rootCommand) {

        this.section = rootCommand;

/*
        CommandElemet com = new CommandElemet();
        com.subcommond = "help";
        com.descrip = "Help";
        com.event = this::sendHelpCommand;
        commands.add(com);*/
    }

    public static class CommandElemet {
        public String subcommond = "listx";
        public String descrip = "Описание команды";
        public List<String> arguments = new ArrayList<>();

        public Consumer<String[]> event;
    }

    public void sendMessage(String msg) {

        System.out.println(  ChatColor.WHITE + msg);

    }

    public void sendHelpCommand(String[] args) {
        String text =  "\n"+ ChatColor.RESET+ Lang.t("section."+getClass().getSimpleName(),section) +ChatColor.RESET;
        for (CommandElemet com : commands) {
            String line ="";

            line += "" + "  " +  ChatColor.GREEN+ com.subcommond;
            for (String arg : com.arguments) {
                line += " <" + arg + ">";
            }

            int margitn = 22;
            for(int i= line.length();i<margitn;i++){
                line += " ";
            }
            line += ChatColor.WHITE+ "   " +  Lang.t("com."+com.subcommond, com.descrip);

            text += "\n" + line;
        }
        sendMessage(text);

    }


    public boolean input(String rawCommand) {

        rawCommand = rawCommand.trim().replace("  ", " ");

        String[] exploded = rawCommand.split(" ");

        String _root = rawCommand;
        if (exploded.length > 0) _root = exploded[0];




        String[] args = new String[Math.max(0, exploded.length - 1)];


        if(exploded.length - 1>0){
             for (int i = 1; i < exploded.length; i++) {
                 args[i-1] = exploded[i ];
             }
        }

        return onCommand(_root, args);


    }

    public boolean onCommand(String subcommand, String[] args) {


        if (args.length == 0) {
           // rootEvent.accept(args);
           // return true;
        }

        for (CommandElemet com : commands) {
            if (!com.subcommond.equalsIgnoreCase(subcommand)) continue;


           // System.out.println(subcommand+" / " + com.arguments.size() + " == "  +  args.length);


            if (com.arguments.size() != args.length ) continue;



            com.event.accept(args);
            return true;
        }

        return false;
    }
}
