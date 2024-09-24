//package com.cardanoj;


//----------------------------- Preliminary Code Segments -----------------------------

//import java.util.Scanner;
//
//import static java.lang.System.exit;
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        Scanner sc = new Scanner(System.in);
//
//        System.out.println("Choose an option : ");
//        System.out.println("1. Login to account");
//        System.out.println("2. Create account");
//        System.out.println("3. Exit");
//
//        int option = sc.nextInt();
//        sc.nextLine();
//
//        switch (option) {
//            case 1:
//                loginAccount();
//                break;
//            case 2:
//                createAccount();
//                break;
//            case 3:
//                exit(0);
//            default:
//                System.out.println("Invalid option selected.");
//        }
//    }
//
//    private static void createAccount () {
//        AccountGen.genAcccount();
//    }
//
//    private static void loginAccount () {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter mnemonic phrase:");
//        String mnemonic = sc.nextLine();
//
//        SimpleTransfer simpleTransfer = new SimpleTransfer();
//        try {
//            simpleTransfer.transfer(mnemonic);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}








//----------------------------- Prototype Code Segments -----------------------------


//// Instantiating KoiosBackendService with base URL and API token
//        String baseUrl = Constants.KOIOS_PREVIEW_URL;
//        String apiToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZGRyIjoic3Rha2UxdTluODJkZnllbGZsbm5lMng1M2c1NjR6ODR0N21jZ3psZzV2c3A3eXZjcThhbHFneTB1dTkiLCJleHAiOjE3NDM3NDMxMDUsInRpZXIiOjEsInByb2pJRCI6Ik43UWZWTkNBaW5paGw0OFMifQ.olGonO7qoAhLLUzGjDw4njEFNGigSNlTzLzosMOX4KY";
//
////         BackendService koiosBackendService = new KoiosBackendService(baseUrl, apiToken);
//
//        String blockHash = "7c104c615c419d503aa0eb739fa0907160526083a8b24e09127507f048eba4c2" ;
//
//        // Block Service
////        BlockApiServiceImpl blockApiServiceImpl = new BlockApiServiceImpl(baseUrl, apiToken) ;
////        blockApiServiceImpl.fetchBlockInfo();
//
//        // Transaction Service
//        TransactionApiServiceImpl transactionApiService = new TransactionApiServiceImpl(baseUrl, apiToken);
//        transactionApiService.fetchTxInfo();
//    }
