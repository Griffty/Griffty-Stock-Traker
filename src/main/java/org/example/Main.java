package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.users.BotUser;

import java.util.ArrayList;

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
    }

    private static void test() {
        BotUser user = new BotUser("asdawdasd", "Griffty", new ArrayList<>(){{add("AMZN"); add("F"); add("APPL");}});
        user.setMoney(1213);
        user.AddStocksToProperty(new Stock("AMZN", 10));
        user.AddStocksToProperty(new Stock("F", 2));
        user.AddStocksToProperty(new Stock("AMZN", 4));
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}