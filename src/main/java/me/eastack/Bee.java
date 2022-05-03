package me.eastack;

import picocli.CommandLine;

public class Bee {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new BeeCommand()).execute(args);
        System.exit(exitCode);
    }
}
