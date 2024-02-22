package org.example.helpers;

public class LogoDesignHelper {

    public static String version = "0.7.0";

    public static void logo() {

        System.out.println(ChatColor.YELLOW + "==============================");
        System.out.println(ChatColor.YELLOW + "|                            |");
        System.out.println(ChatColor.YELLOW + "|       DockSabbath          |");
        System.out.println(ChatColor.YELLOW + "|                            |");
        System.out.println(ChatColor.YELLOW + "==============================\n");

        System.out.println("░█▀▄░█▀█░█▀▀░█░█░█▀▀░█▀█░█▀▄░█▀▄░█▀█░▀█▀░█░█\n" +
                "░█░█░█░█░█░░░█▀▄░▀▀█░█▀█░█▀▄░█▀▄░█▀█░░█░░█▀█\n" +
                "░▀▀░░▀▀▀░▀▀▀░▀░▀░▀▀▀░▀░▀░▀▀░░▀▀░░▀░▀░░▀░░▀░▀");

        System.out.println(ChatColor.YELLOW + "VERSION: "+version+"\n");
    }
}
