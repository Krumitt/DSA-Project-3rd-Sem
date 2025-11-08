package stockmarket.models;

import stockmarket.datastructures.CustomHashMap;
import stockmarket.datastructures.CustomArrayList;
//tracks current stock holdings, average buy and sell prices and avaliable cash
public class Portfolio {
    private CustomHashMap<String, Integer> holdings;
    private double cashBalance;
    private CustomHashMap<String, Double> avgBuyPrice;

    public Portfolio(double initialCash) {
        this.holdings = new CustomHashMap<>();
        this.avgBuyPrice = new CustomHashMap<>();
        this.cashBalance = initialCash;
    }
//buys a specified quantity of stock at a particular price
    public boolean buyStock(String ticker, int quantity, double pricePerShare) {
        double totalCost = quantity * pricePerShare;
        if (totalCost > cashBalance) {
            return false;
        }

        cashBalance -= totalCost;

        Integer currentQty = holdings.get(ticker);
        if (currentQty == null) {
            holdings.put(ticker, quantity);
            avgBuyPrice.put(ticker, pricePerShare);
        } else {
            Double currentAvg = avgBuyPrice.get(ticker);
            double newAvg = ((currentAvg * currentQty) + (pricePerShare * quantity)) / (currentQty + quantity);
            holdings.put(ticker, currentQty + quantity);
            avgBuyPrice.put(ticker, newAvg);
        }
        return true;
    }
//sells a amount of stock at a particular price
    public boolean sellStock(String ticker, int quantity, double pricePerShare) {
        Integer currentQty = holdings.get(ticker);
        if (currentQty == null || currentQty < quantity) {
            return false;
        }
        //adds sale value to cash balance
        cashBalance += quantity * pricePerShare;
        
        if (currentQty == quantity) {
            holdings.remove(ticker);
            avgBuyPrice.remove(ticker);
        } else {
            holdings.put(ticker, currentQty - quantity);
        }
        return true;
    }
//to display the current net worth of the portfolio
    public double getCashBalance() {
        return cashBalance;
    }

    public double calculateNetWorth(CustomHashMap<String, Stock> currentPrices) {
        double netWorth = cashBalance;
        CustomArrayList<String> tickers = holdings.keySet();
        for (int i = 0; i < tickers.size(); i++) {
            String ticker = tickers.get(i);
            Integer qty = holdings.get(ticker);
            Stock stock = currentPrices.get(ticker);
            if (stock != null) {
                netWorth += qty * stock.getAvgPrice();
            }
        }
        return netWorth;
    }
//return a map of current stock holdings
    public CustomHashMap<String, Integer> getHoldings() {
        return holdings;
    }
//returns a map of the average buying prices
    public CustomHashMap<String, Double> getAvgBuyPrice() {
        return avgBuyPrice;
    }
}