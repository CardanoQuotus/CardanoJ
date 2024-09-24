package com.cardanoj.jna.jna.CLI;

import com.cardanoj.jna.exception.CborSerializationException;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class CliMain {
    public static void cliMain() {
        Scanner scanner = new Scanner(System.in);

        String senderAddress = "addr_test1qrnzwcuxuheze4x2raqfjyncxxrux794wgz2c8lex2my50mxw56jfn7nl88j5dfz3f42y02hahss973geqrugesq0m7qe8ft2g";
        System.out.println("Sender Address = addr_test1qrnzwcuxuheze4x2raqfjyncxxrux794wgz2c8lex2my50mxw56jfn7nl88j5dfz3f42y02hahss973geqrugesq0m7qe8ft2g");

//        String receiverAddress = "addr_test1qqv5auuws3t8x2qjccc3u5g0tfcvwjnjvantxs83k6p8kmvjzgrfuhh95jay0p0he8kmxgsfh7s0m6jyjhnyw4azpwxs0ay5zm";

        System.out.print("Enter receiver address: ");
        String receiverAddress = scanner.nextLine();
        System.out.print("Enter amount to send (in lovelace): ");
        String amount = scanner.nextLine();

        // Submit
        try {
            SubmitTransactionCLI.submitTransaction(senderAddress, receiverAddress, amount);
        } catch (Exception e) {
            System.err.println("Error during transaction submission: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException, CborSerializationException {
        cliMain();
    }

}
