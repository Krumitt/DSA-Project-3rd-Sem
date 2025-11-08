package stockmarket.benchmark;

import stockmarket.models.Stock;
import stockmarket.datastructures.CustomHashMap;
import stockmarket.datastructures.CustomArrayList;
import stockmarket.algorithms.StockSearch;
import stockmarket.algorithms.BestBuySellFinder;
import stockmarket.algorithms.StockSorter;
import stockmarket.algorithms.PriceSimulator;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BenchmarkRunner {

    public static void runBenchmarks() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                         STARTING BENCHMARKS");
        System.out.println("=".repeat(80));

        int[] inputSizes = { 10, 50, 100, 500, 1000 };

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("benchmark_results.csv"));
            writer.write("Function,InputSize,TimeMs,MemoryKB\n");

            for (int size : inputSizes) {
                System.out.println("\nTesting with input size: " + size);

                benchmarkStockSearch(writer, size);
                benchmarkBestBuySell(writer, size);
                benchmarkStockSort(writer, size);
            }

            writer.close();
            System.out.println("\n" + "=".repeat(80));
            System.out.println("Benchmarks complete! Results saved to benchmark_results.csv");
            System.out.println("=".repeat(80));

        } catch (IOException e) {
            System.err.println("Error writing benchmark results: " + e.getMessage());
        }
    }

    private static void benchmarkStockSearch(BufferedWriter writer, int size) throws IOException {
        CustomHashMap<String, Stock> stockMap = generateStockData(size);

        for (int i = 0; i < 100; i++) {
            StockSearch.searchByTicker(stockMap, "STOCK" + (i % size));
        }

        Runtime runtime = Runtime.getRuntime();
        System.gc();
        long memBefore = runtime.totalMemory() - runtime.freeMemory();

        long startTime = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            StockSearch.searchByTicker(stockMap, "STOCK" + (i % size));
        }

        long endTime = System.nanoTime();
        long memAfter = runtime.totalMemory() - runtime.freeMemory();

        double timeMs = (endTime - startTime) / 1000000.0;
        long memoryKB = (memAfter - memBefore) / 1024;

        writer.write(String.format("StockSearch,%d,%.4f,%d\n", size, timeMs, memoryKB));
        System.out.printf("  Stock Search: %.4f ms, %d KB\n", timeMs, memoryKB);
    }

    private static void benchmarkBestBuySell(BufferedWriter writer, int size) throws IOException {
        CustomArrayList<Stock> history = generateStockHistory(size);

        for (int i = 0; i < 10; i++) {
            BestBuySellFinder.findBestBuySellDays(history);
        }

        Runtime runtime = Runtime.getRuntime();
        System.gc();
        long memBefore = runtime.totalMemory() - runtime.freeMemory();

        long startTime = System.nanoTime();

        for (int i = 0; i < 100; i++) {
            BestBuySellFinder.findBestBuySellDays(history);
        }

        long endTime = System.nanoTime();
        long memAfter = runtime.totalMemory() - runtime.freeMemory();

        double timeMs = (endTime - startTime) / 1000000.0;
        long memoryKB = (memAfter - memBefore) / 1024;

        writer.write(String.format("BestBuySell,%d,%.4f,%d\n", size, timeMs, memoryKB));
        System.out.printf("  Best Buy/Sell: %.4f ms, %d KB\n", timeMs, memoryKB);
    }

    private static void benchmarkStockSort(BufferedWriter writer, int size) throws IOException {
        CustomArrayList<Stock> stocks = generateStockList(size);

        for (int i = 0; i < 10; i++) {
            CustomArrayList<Stock> copy = copyStockList(stocks);
            StockSorter.sortByPrice(copy, true);
        }

        Runtime runtime = Runtime.getRuntime();
        System.gc();
        long memBefore = runtime.totalMemory() - runtime.freeMemory();

        long startTime = System.nanoTime();

        for (int i = 0; i < 10; i++) {
            CustomArrayList<Stock> copy = copyStockList(stocks);
            StockSorter.sortByPrice(copy, true);
        }

        long endTime = System.nanoTime();
        long memAfter = runtime.totalMemory() - runtime.freeMemory();

        double timeMs = (endTime - startTime) / 1000000.0;
        long memoryKB = (memAfter - memBefore) / 1024;

        writer.write(String.format("StockSort,%d,%.4f,%d\n", size, timeMs, memoryKB));
        System.out.printf("  Stock Sort: %.4f ms, %d KB\n", timeMs, memoryKB);
    }

    private static CustomHashMap<String, Stock> generateStockData(int size) {
        CustomHashMap<String, Stock> map = new CustomHashMap<>();
        for (int i = 0; i < size; i++) {
            String ticker = "STOCK" + i;
            Stock stock = new Stock("Company " + i, ticker, "01-01-2025",
                    100.0 + i, 95.0 + i);
            map.put(ticker, stock);
        }
        return map;
    }

    private static CustomArrayList<Stock> generateStockHistory(int size) {
        CustomArrayList<Stock> history = new CustomArrayList<>();
        double high = 100.0;
        double low = 95.0;

        for (int i = 0; i < size; i++) {
            history.add(new Stock("TestStock", "TEST", "Day" + i, high, low));
            double[] newPrices = PriceSimulator.simulateNextDayPrices(high, low);
            high = newPrices[0];
            low = newPrices[1];
        }
        return history;
    }

    private static CustomArrayList<Stock> generateStockList(int size) {
        CustomArrayList<Stock> list = new CustomArrayList<>();
        for (int i = 0; i < size; i++) {
            double price = Math.random() * 500 + 50;
            list.add(new Stock("Company" + i, "STK" + i, "01-01-2025",
                    price, price * 0.95));
        }
        return list;
    }

    private static CustomArrayList<Stock> copyStockList(CustomArrayList<Stock> original) {
        CustomArrayList<Stock> copy = new CustomArrayList<>();
        for (int i = 0; i < original.size(); i++) {
            Stock s = original.get(i);
            copy.add(new Stock(s.getName(), s.getTicker(), s.getDate(),
                    s.getHighPrice(), s.getLowPrice()));
        }
        return copy;
    }
}