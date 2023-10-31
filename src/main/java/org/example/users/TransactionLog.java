package org.example.users;

import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;

public class TransactionLog {
    private User user;
    private ArrayList<Transaction> transactions;

    public TransactionLog(){

    }
    public TransactionLog(User user, ArrayList<Transaction> transactions) {
        this.user = user;
        this.transactions = transactions;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}
