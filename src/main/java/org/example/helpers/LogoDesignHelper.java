package org.example.helpers;

public class LogoDesignHelper {

    public static String version = "0.7.1";

    public static void showProgammLogo() {

        System.out.println(ChatColor.YELLOW + "             ==============================");
        System.out.println(ChatColor.YELLOW + "             |                            |");
        System.out.println(ChatColor.YELLOW + "             |       DockSabbath          |");
        System.out.println(ChatColor.YELLOW + "             |                            |");
        System.out.println(ChatColor.YELLOW + "             ============================== ");

        System.out.println(
                "   ___           __    ____     __   __        __  __ \n" +
                        "  / _ \\___  ____/ /__ / __/__ _/ /  / /  ___ _/ /_/ / \n" +
                        " / // / _ \\/ __/  '_/_\\ \\/ _ `/ _ \\/ _ \\/ _ `/ __/ _ \\\n" +
                        "/____/\\___/\\__/_/\\_\\/___/\\_,_/_.__/_.__/\\_,_/\\__/_//_/\n" +
                        "                                                      ");

        System.out.println(ChatColor.YELLOW + "VERSION: "+version+"\n");
    }
}
