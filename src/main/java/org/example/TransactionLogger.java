package org.example;

import net.dv8tion.jda.api.entities.User;
import org.example.users.BotUser;
import org.example.users.Stock;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransactionLogger {
    private static TransactionLogger self;
    public static TransactionLogger getInstance() {
        if (self == null){
            self = new TransactionLogger();
        }
        return self;
    }
    public ArrayList<String> getLatestTransactionLogs(BotUser user){
        File logFile = FileHandler.getInstance().getFileFromSaveDirectory(user.name, user.discordId, ".userLog");
        ArrayList<String> logs = new ArrayList<>();
        try {
            BufferedReader logReader = new BufferedReader(new FileReader(logFile));
            logReader.readLine();
            String line = logReader.readLine();
            while (line != null){
                logs.add(line);
                line = logReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return logs;
    }
    public void logTransaction(BotUser user, Stock stock, float price, boolean bought){
        File logFile = FileHandler.getInstance().getFileFromSaveDirectory(user.name, user.discordId, ".userLog");
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
            BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFile, true));
            logWriter.newLine();
            logWriter.write(formatter.format(new Date()) + ": " + user.name + " " + (bought ? "bought" : "sold") + " " + stock.getAmount()
                            + " " + stock.getSymbol() + "; stock price: " + price + "; total: " + (price*stock.getAmount()) + "; money left: " + user.getMoney());
            logWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
