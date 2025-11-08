package stockmarket.services;

import stockmarket.models.Stock;
import stockmarket.datastructures.CustomHashMap;
import stockmarket.datastructures.CustomArrayList;
import stockmarket.algorithms.StockSorter;

public class StockService {
//for displaying all avaliable stocks currently in the system
    public static void displayAllStocks(CustomHashMap<String, Stock> stocks) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                           ALL AVAILABLE STOCKS");
        System.out.println("=".repeat(80));

        CustomArrayList<String> tickers = stocks.keySet();
//for empty stock list
        if (tickers.isEmpty()) {
            System.out.println("No stocks available.");
            return;
        }
// loops through all tickers to display stock details
        for (int i = 0; i < tickers.size(); i++) {
            String ticker = tickers.get(i);
            Stock stock = stocks.get(ticker);
            System.out.println(stock);
        }
        System.out.println("=".repeat(80));
    }

    public static void displayStocksSortedByPrice(CustomHashMap<String, Stock> stockMap, boolean ascending) {
        CustomArrayList<Stock> stocks = stockMap.values();
        StockSorter.sortByPrice(stocks, ascending); //displays stocks pased on ascending order of price

        System.out.println("\n" + "=".repeat(80));
        System.out.println(
                "                    STOCKS SORTED BY PRICE " + (ascending ? "(LOW TO HIGH)" : "(HIGH TO LOW)"));
        System.out.println("=".repeat(80));
// displays each stock after sorting based on price
        for (int i = 0; i < stocks.size(); i++) {
            System.out.println(stocks.get(i));
        }
        System.out.println("=".repeat(80));
    }
}