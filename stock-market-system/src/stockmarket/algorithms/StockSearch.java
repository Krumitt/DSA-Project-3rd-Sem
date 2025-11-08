package stockmarket.algorithms;

import stockmarket.models.Stock;
import stockmarket.datastructures.CustomHashMap;

public class StockSearch {

    public static Stock searchByTicker(CustomHashMap<String, Stock> stockMap, String ticker) {
        return stockMap.get(ticker.toUpperCase());
    }
}