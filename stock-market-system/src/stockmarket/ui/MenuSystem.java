package stockmarket.ui;

import stockmarket.models.Stock;
import stockmarket.datastructures.CustomHashMap;
import stockmarket.datastructures.CustomArrayList;
import stockmarket.services.DataManager;
import stockmarket.services.PortfolioManager;
import stockmarket.services.StockService;
import stockmarket.algorithms.StockSearch;
import stockmarket.algorithms.BestBuySellFinder;
import java.util.Scanner;

public class MenuSystem {
    private Scanner scanner;
    private PortfolioManager portfolioManager;
    private CustomHashMap<String, Stock> currentStocks;

    public MenuSystem() {
        this.scanner = new Scanner(System.in);
        this.portfolioManager = new PortfolioManager(100000.0);
        DataManager.initializeData();
        this.currentStocks = DataManager.loadCurrentStocks();
    }

    public void start() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("           WELCOME TO STOCK MARKET ANALYSIS & TRADING SYSTEM");
        System.out.println("=".repeat(80));
        System.out.println("Starting Portfolio: $100,000");

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    buyStockMenu();
                    break;
                case 2:
                    sellStockMenu();
                    break;
                case 3:
                    viewPortfolio();
                    break;
                case 4:
                    displayAllStocks();
                    break;
                case 5:
                    displaySortedStocks();
                    break;
                case 6:
                    viewStockHistory();
                    break;
                case 7:
                    advanceTime();
                    break;
                case 8:
                    System.out.println("\nThank you for using the Stock Market System!");
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid choice! Please try again.");
            }
        }
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                               MAIN MENU");
        System.out.println("=".repeat(80));
        System.out.println("1. Buy Stock");
        System.out.println("2. Sell Stock");
        System.out.println("3. View Portfolio");
        System.out.println("4. Display All Stocks");
        System.out.println("5. Display Stocks Sorted by Price");
        System.out.println("6. View Stock History & Best Buy/Sell Days");
        System.out.println("7. Advance to Next Day");
        System.out.println("8. Exit");
        System.out.println("=".repeat(80));
    }

    private void buyStockMenu() {
        System.out.print("\nEnter stock ticker symbol: ");
        String ticker = scanner.nextLine().toUpperCase().trim();

        Stock stock = StockSearch.searchByTicker(currentStocks, ticker);
        if (stock == null) {
            System.out.println("Stock not found!");
            return;
        }

        System.out.println("\nStock Details:");
        System.out.println(stock);
        System.out.printf("Current Price: $%.2f\n", stock.getAvgPrice());

        int quantity = getIntInput("Enter quantity to buy: ");
        if (quantity <= 0) {
            System.out.println("Invalid quantity!");
            return;
        }

        portfolioManager.buyStock(stock, quantity);
    }

    private void sellStockMenu() {
        System.out.print("\nEnter stock ticker symbol: ");
        String ticker = scanner.nextLine().toUpperCase().trim();

        Stock stock = StockSearch.searchByTicker(currentStocks, ticker);
        if (stock == null) {
            System.out.println("Stock not found!");
            return;
        }

        System.out.println("\nStock Details:");
        System.out.println(stock);
        System.out.printf("Current Price: $%.2f\n", stock.getAvgPrice());

        int quantity = getIntInput("Enter quantity to sell: ");
        if (quantity <= 0) {
            System.out.println("Invalid quantity!");
            return;
        }

        portfolioManager.sellStock(stock, quantity);
    }

    private void viewPortfolio() {
        portfolioManager.displayPortfolio(currentStocks);
    }

    private void displayAllStocks() {
        StockService.displayAllStocks(currentStocks);
    }

    private void displaySortedStocks() {
        System.out.println("\n1. Sort Low to High");
        System.out.println("2. Sort High to Low");
        int choice = getIntInput("Enter choice: ");

        boolean ascending = (choice == 1);
        StockService.displayStocksSortedByPrice(currentStocks, ascending);
    }

    private void viewStockHistory() {
        System.out.print("\nEnter stock ticker symbol: ");
        String ticker = scanner.nextLine().toUpperCase().trim();

        CustomArrayList<Stock> history = DataManager.loadStockHistory(ticker);

        if (history.isEmpty()) {
            System.out.println("No history found for " + ticker);
            return;
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("                      HISTORY FOR " + ticker);
        System.out.println("=".repeat(80));

        for (int i = 0; i < history.size(); i++) {
            System.out.println(history.get(i));
        }

        BestBuySellFinder.BuySellResult result = BestBuySellFinder.findBestBuySellDays(history);

        if (result != null) {
            System.out.println("\n" + "=".repeat(80));
            System.out.println("                    BEST BUY/SELL OPPORTUNITY");
            System.out.println("=".repeat(80));
            System.out.printf("Buy Date:  %s (Day %d)\n", result.buyDate, result.buyDay);
            System.out.printf("Sell Date: %s (Day %d)\n", result.sellDate, result.sellDay);
            System.out.printf("Maximum Profit: $%.2f per share\n", result.profit);
            System.out.println("=".repeat(80));
        } else {
            System.out.println("\nNo profitable buy/sell opportunity found in history.");
        }
    }

    private void advanceTime() {
        System.out.println("\nAdvancing to next trading day...");
        DataManager.advanceDay();
        currentStocks = DataManager.loadCurrentStocks();
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }
}