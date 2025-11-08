package stockmarket.services;

import stockmarket.models.Portfolio;
import stockmarket.models.Stock;
import stockmarket.datastructures.CustomHashMap;
import stockmarket.datastructures.CustomArrayList;

public class PortfolioManager {
    private Portfolio portfolio;

    public PortfolioManager(double initialCash) {
        this.portfolio = new Portfolio(initialCash);
    }

    public boolean buyStock(Stock stock, int quantity) {
        if (stock == null) {
            System.out.println("Stock not found!");
            return false;
        }

        double price = stock.getAvgPrice();
        boolean success = portfolio.buyStock(stock.getTicker(), quantity, price);

        if (success) {
            System.out.printf("\nSuccessfully bought %d shares of %s at $%.2f per share\n",
                    quantity, stock.getTicker(), price);
            System.out.printf("Total cost: $%.2f\n", quantity * price);
            System.out.printf("Remaining cash: $%.2f\n", portfolio.getCashBalance());
        } else {
            System.out.println("\nInsufficient funds!");
        }

        return success;
    }

    public boolean sellStock(Stock stock, int quantity) {
        if (stock == null) {
            System.out.println("Stock not found!");
            return false;
        }

        double price = stock.getAvgPrice();
        boolean success = portfolio.sellStock(stock.getTicker(), quantity, price);

        if (success) {
            System.out.printf("\nSuccessfully sold %d shares of %s at $%.2f per share\n",
                    quantity, stock.getTicker(), price);
            System.out.printf("Total revenue: $%.2f\n", quantity * price);
            System.out.printf("New cash balance: $%.2f\n", portfolio.getCashBalance());
        } else {
            System.out.println("\nInsufficient shares to sell!");
        }

        return success;
    }

    public void displayPortfolio(CustomHashMap<String, Stock> currentPrices) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                              YOUR PORTFOLIO");
        System.out.println("=".repeat(80));

        System.out.printf("\nCash Balance: $%.2f\n\n", portfolio.getCashBalance());

        CustomHashMap<String, Integer> holdings = portfolio.getHoldings();
        CustomHashMap<String, Double> avgPrices = portfolio.getAvgBuyPrice();

        if (holdings.isEmpty()) {
            System.out.println("No stock holdings.");
        } else {
            System.out.println("Stock Holdings:");
            System.out.println("-".repeat(80));
            System.out.printf("%-10s %-12s %-15s %-15s %-15s\n",
                    "Ticker", "Quantity", "Avg Buy Price", "Current Price", "P&L");
            System.out.println("-".repeat(80));

            CustomArrayList<String> tickers = holdings.keySet();
            double totalPL = 0;

            for (int i = 0; i < tickers.size(); i++) {
                String ticker = tickers.get(i);
                Integer qty = holdings.get(ticker);
                Double avgBuy = avgPrices.get(ticker);
                Stock stock = currentPrices.get(ticker);

                if (stock != null) {
                    double currentPrice = stock.getAvgPrice();
                    double pl = (currentPrice - avgBuy) * qty;
                    totalPL += pl;

                    System.out.printf("%-10s %-12d $%-14.2f $%-14.2f $%-14.2f\n",
                            ticker, qty, avgBuy, currentPrice, pl);
                }
            }

            System.out.println("-".repeat(80));
            System.out.printf("Total Profit/Loss: $%.2f\n", totalPL);
        }

        double netWorth = portfolio.calculateNetWorth(currentPrices);
        System.out.printf("\nTotal Net Worth: $%.2f\n", netWorth);
        System.out.println("=".repeat(80));
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }
}