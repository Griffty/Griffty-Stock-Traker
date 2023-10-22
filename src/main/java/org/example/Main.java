package org.example;

import org.example.users.BotUser;
import org.example.users.Stock;
import org.example.users.Transaction;

import java.io.File;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        switch (args[0]){
            case "console" -> ConsoleInterface.getInstance();
            case "server" -> {
                ServerInterface.getInstance();
            }
            case "test" -> test();
            default -> throw new IllegalArgumentException("Wrong argument provided - " + args[0]);
        }
        Scanner serverCommands = new Scanner(System.in);

        while (true){
            String command = serverCommands.nextLine().toLowerCase();
            if (command.equals("quit")){
                System.exit(69);
            }
            if (command.contains("givemoney")){
                System.out.println("username:");
                String name = serverCommands.nextLine().toLowerCase();
                if (!FileHandler.getInstance().isUserInSystem(name)){
                    System.out.println("amount");
                    int amount = serverCommands.nextInt();
                    BotUser botUser = FileHandler.getInstance().deserializeUser(name, "?");
                    botUser.setMoney(botUser.getMoney() + amount);
                    FileHandler.getInstance().serializeUser(botUser);
                }else {
                    System.out.println("User is in system, cannot deserialize save file.");
                }
            }
            if (command.equals("resetdatabase")){
                System.out.println("It will delete all user files, are you sure you want to continue? y/n");
                String sure = serverCommands.nextLine();
                while (!sure.equals("y") && !sure.equals("n")){
                    System.out.println("Cannot recognize answer use \"y\" or \"n\"");
                    sure = serverCommands.nextLine();
                }
                if (sure.equals("y")){
                    System.out.println("Write security pin");
                    String pin = serverCommands.nextLine();
                    if (pin.equals("0007")){
                        resetDataBase();
                    }
                }
            }
        }
    }

    private static void resetDataBase() {
        if (FileHandler.getInstance().isAnyUserInSystem()){
            System.out.println("You can't reset database now, someone is using the bot. Try in few seconds");
            return;
        }
        File saveDir = new File(FileHandler.getInstance().getSaveDirectory());

        for (File file : Objects.requireNonNull(saveDir.listFiles())){
            if (file.delete()){
                System.out.println("Cannot delete " + file.getName());
            }
        }
        System.exit(70);
    }

    private static void test() {
        Transaction transaction = new Transaction(new Date().getTime(), new Stock("AMZN", 10), "griffty", HTTPHandler.getInstance().getStockPrice("AMZN"), 10 * HTTPHandler.getInstance().getStockPrice("AMZN"), true);
    }
}