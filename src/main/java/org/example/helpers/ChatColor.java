package org.example.helpers;

public enum ChatColor {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    BRIGHT_BLACK("\u001B[90m");

    private final String code;

    ChatColor(String code) {
        this.code = code;
    }


    @Override
    public String toString() {
        return code;
    }
}