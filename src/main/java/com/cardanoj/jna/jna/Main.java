package com.cardanoj.jna.jna;

import com.cardanoj.jna.jna.CLI.CliMain;
import com.cardanoj.jna.jna.blockfrost.BlockFrostMain;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose an option: ");
        System.out.println("1. Submit using CardanoJ-Api");
        System.out.println("2. Submit using Blockfrost-Api");

        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                submitUsingCardanojApi();
                break;
            case 2:
                submitUsingBlockfrostApi();
                break;
            case 3:
                System.out.println("Invalid choice!");
                break;
        }
        sc.close();
    }

    public static void submitUsingCardanojApi() {
        CliMain.cliMain();
    }

    public static void submitUsingBlockfrostApi() throws Exception {
        BlockFrostMain.blockFrostMain();
    }
}
