package com.cardanoj;

import com.cardanoj.common.model.Networks;
import com.cardanoj.coreapi.account.Account;

public class AccountGen {
    public static void genAcccount() {

        // Creating Account
        Account account = new Account(Networks.preview());
        System.out.println("Account : " + account);
        System.out.println("Mnemonic : " + account.mnemonic());
        main(new String[0]);
    }

    public static void main(String[] args) {
        genAcccount();
    }
}
