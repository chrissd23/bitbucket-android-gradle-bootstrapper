package de.bootstrap;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanInput = new Scanner(System.in);

        System.out.print("Please enter your bitbucket username: ");
        String username = scanInput.nextLine();


        System.out.print("Please enter your password: ");
        String password = scanInput.nextLine();

        System.out.print("Please enter your absolute Path to configuration file: ");
        String filePath = scanInput.nextLine();

        System.out.print("Please enter your destination Path for the output file: ");
        String outputPath = scanInput.nextLine();

        BootstrapTask.bootstrap(username, password, filePath, outputPath);

    }
}
