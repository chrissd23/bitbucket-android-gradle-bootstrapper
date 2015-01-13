package de.bootstrap;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String username = args[0];

        String password = args[1];

        String filePath = args[2];

        String outputPath = args[3];

        BootstrapTask.bootstrap(username, password, filePath, outputPath);

    }
}
