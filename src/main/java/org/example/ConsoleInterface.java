package org.example;

import java.util.Scanner;

public class ConsoleInterface extends ProgramInterface {
    private Scanner userInput = new Scanner(System.in);
    public static ConsoleInterface getInstance() {
        if (self == null){
            self = new ConsoleInterface();
        }
        return (ConsoleInterface) self;
    }
    @Deprecated
     private ConsoleInterface(){
        throw new UnsupportedOperationException("This initialization method isn't currently supported");
        /*         super();
         System.out.println("""
                    _____ _______ ____   _____ _  __     _______ _____            _____ _  ________ _____ \s
                   / ____|__   __/ __ \\ / ____| |/ /    |__   __|  __ \\     /\\   / ____| |/ /  ____|  __ \\\s
                  | (___    | | | |  | | |    | ' /        | |  | |__) |   /  \\ | |    | ' /| |__  | |__) |
                   \\___ \\   | | | |  | | |    |  <         | |  |  _  /   / /\\ \\| |    |  < |  __| |  _  /\s
                   ____) |  | | | |__| | |____| . \\        | |  | | \\ \\  / ____ \\ |____| . \\| |____| | \\ \\\s
                  |_____/   |_|  \\____/ \\_____|_|\\_\\       |_|  |_|  \\_\\/_/    \\_\\_____|_|\\_\\______|_|  \\_\\
                                                                                                          \s
                                     \s
                 """);
         while (openMenu()) {
             System.out.println("\n\n\n\n");
         }*/
     }
//    private boolean openMenu() {
//
//        System.out.println("""
//                Main Menu:
//                 1. Get current price for subscribed stocks
//                 2. Add new stock to subscription list
//                 3. Remove stock to subscription list
//                 4. Check individual stock price
//                 q. quit
//
//                 !To choose option type symbol before option!""");
//        String action = userInput.nextLine();
//
//        switch (action) {
//            case "q" -> {
//                return false;
//            }
//            case "1" -> checkAllSubscribedStocks();
//            case "2" -> addStockToSubscriptionList();
//            case "3" -> removeStockFromSubscriptionList();
//            case "4" -> checkIndividualStockPrice();
//        }
//        return true;
//    }
//    protected void checkAllSubscribedStocks() {
//        userInput = new Scanner(System.in);
//        if (subscribedTokens.size() < 1){
//            System.out.println("You don't have any subscribed stocks");
//            continueExecution();
//            return;
//        }
//        System.out.println("List of prices for all subscribed stock:");
//        for (String symbol : subscribedTokens) {
//            System.out.println(httpHandler.getShortStockPriceInfo(symbol));
//        }
//        continueExecution();
//    }
//
//    protected void addStockToSubscriptionList() {
//        userInput = new Scanner(System.in);
//        System.out.println("What stock do you want to add: (type q to Quit)");
//        String ans = userInput.nextLine();
//        if (subscribedTokens.contains(ans)){
//            System.out.println("You already added this stock");
//            continueExecution();
//            return;
//        }
//        if (ans.equals("q")){
//            return;
//        }
//        String s = httpHandler.getShortStockPriceInfo(ans);
//        if (s.contains("Cannot")){
//            System.out.println("Cannot add this stock to you subscription list. Try again");
//            continueExecution();
//            addStockToSubscriptionList();
//            return;
//        }
//        subscribedTokens.add(ans);
//        System.out.println("\nStock " + ans + " was added");
//        saveTokens();
//        continueExecution();
//    }
//    protected void removeStockFromSubscriptionList() {
//        userInput = new Scanner(System.in);
//        System.out.println("List of all subscribed stock:");
//        int i = 0;
//        for (String symbol : subscribedTokens) {
//            System.out.print(symbol + ", ");
//            if (i > 10){
//                System.out.println();
//                i = 0;
//            }
//            i++;
//        }
//        System.out.println("\n Which one do you want to remove: ");
//        String ans = userInput.nextLine();
//        if (subscribedTokens.remove(ans)){
//            System.out.println(ans + " was removed");
//            saveTokens();
//            return;
//        }
//        System.out.println("Cannot remove this stock from you subscription list. Try again");
//        continueExecution();
//        removeStockFromSubscriptionList();
//    }
//
//    protected void checkIndividualStockPrice() {
//        userInput = new Scanner(System.in);
//        System.out.println("What stock do you want to check: ");
//        String ans = userInput.nextLine();
//        System.out.println("\n\n" + httpHandler.getShortStockPriceInfo(ans));
//        continueExecution();
//    }
//    private void continueExecution(){
//        System.out.println("\n\n Type anything to continue");
//        userInput.next();
//        userInput = new Scanner(System.in);
//    }
}
