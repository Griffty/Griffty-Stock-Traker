package org.example.users;

import java.util.ArrayList;
import java.util.List;

public class StockPack extends ArrayList<Stock> {
    public StockPack(){}
    public Stock get(String symbol) {
        for(Stock stock : this){
            if (stock.symbol.equals(symbol)){
                return stock;
            }
        }
        return null;
    }
    public int addS(Stock newStock) {
        Stock stock = get(newStock.symbol);
        if (stock == null){
            add(newStock);
            return 1;
        }
        stock.amount += newStock.amount;
        return 0;
    }
    public int removeS(Stock newStock) {
        Stock stock = get(newStock.symbol);
        if (stock.amount < newStock.amount){
            return 1;
        }
        stock.amount -= newStock.amount;
        return 0;
    }

    public List<String> getAllStockSymbols() {
        List list = new ArrayList();
        for (Stock stock : this){
            list.add(stock.symbol);
        }
        return list;
    }
}
