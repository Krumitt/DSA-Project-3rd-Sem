package stockmarket.services;

import stockmarket.models.Stock;
import stockmarket.datastructures.CustomHashMap;
import stockmarket.datastructures.CustomArrayList;
import stockmarket.algorithms.PriceSimulator;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataManager {

    private static final String CSV_FILE = "data/stock_data.csv";
    private static final String INITIAL_DATA_FILE = "data/market2.txt";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // --------------------------------------------------------
    // INITIALIZE CSV ON FIRST RUN
    // --------------------------------------------------------
    public static void initializeData() {
        File csv = new File(CSV_FILE);

        if (!csv.exists()) {
            System.out.println("Creating fresh stock market data...");
            loadInitialData();
        } else {
            System.out.println("Stock data loaded from existing CSV.");
        }
    }

    // --------------------------------------------------------
    // LOAD INITIAL TEXT FILE INTO CSV
    // --------------------------------------------------------
    private static void loadInitialData() {
        try {
            File dir = new File("data");
            if (!dir.exists())
                dir.mkdirs();

            File source = new File(INITIAL_DATA_FILE);
            if (!source.exists()) {
                System.err.println("ERROR: data/market2.txt missing!");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(source));
            BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE));

            writer.write("Name,Ticker,Date,HighPrice,LowPrice" + System.lineSeparator());

            String line;
            int count = 0;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;

                String[] p = line.split(",");
                if (p.length < 5)
                    continue;

                writer.write(line + System.lineSeparator());
                count++;
            }

            reader.close();
            writer.close();

            System.out.println("Loaded " + count + " stocks from initial data.");

        } catch (Exception e) {
            System.err.println("Error loading initial data: " + e.getMessage());
        }
    }

    // --------------------------------------------------------
    // LOAD LATEST STOCKS (ONE PER TICKER)
    // --------------------------------------------------------
    public static CustomHashMap<String, Stock> loadCurrentStocks() {
        CustomHashMap<String, Stock> latest = new CustomHashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE));
            reader.readLine(); // header

            String line;
            while ((line = reader.readLine()) != null) {
                Stock s = parseLine(line);
                if (s != null) {
                    // last occurrence = latest date (CSV always sorted by date)
                    latest.put(s.getTicker(), s);
                }
            }

            reader.close();

        } catch (Exception e) {
            System.err.println("Error loading stock list: " + e.getMessage());
        }

        return latest;
    }

    // --------------------------------------------------------
    // LOAD COMPLETE HISTORY FOR ONE TICKER
    // --------------------------------------------------------
    public static CustomArrayList<Stock> loadStockHistory(String ticker) {
        CustomArrayList<Stock> history = new CustomArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE));
            reader.readLine(); // header

            String line;
            while ((line = reader.readLine()) != null) {
                Stock s = parseLine(line);
                if (s != null && s.getTicker().equalsIgnoreCase(ticker)) {
                    history.add(s);
                }
            }

            reader.close();

        } catch (Exception e) {
            System.err.println("Error loading history: " + e.getMessage());
        }

        return history;
    }

    // --------------------------------------------------------
    // PARSE CSV LINE INTO STOCK OBJECT
    // --------------------------------------------------------
    private static Stock parseLine(String line) {
        try {
            String[] p = line.split(",");
            if (p.length < 5)
                return null;

            String name = p[0].trim();
            String ticker = p[1].trim();
            String date = p[2].trim();

            double high = Double.parseDouble(p[3].trim());
            double low = Double.parseDouble(p[4].trim());

            return new Stock(name, ticker, date, high, low);
        } catch (Exception e) {
            System.err.println("Skipping bad CSV line: " + line);
            return null;
        }
    }

    // --------------------------------------------------------
    // ADVANCE MARKET BY ONE DAY
    // --------------------------------------------------------
    public static void advanceDay() {
        CustomHashMap<String, Stock> current = loadCurrentStocks();

        if (current.isEmpty()) {
            System.err.println("No stocks found.");
            return;
        }

        String nextDate = computeNextDate();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true));

            CustomArrayList<String> tickers = current.keySet();

            for (int i = 0; i < tickers.size(); i++) {
                String ticker = tickers.get(i);
                Stock s = current.get(ticker);

                double[] np = PriceSimulator.simulateNextDayPrices(
                        s.getHighPrice(),
                        s.getLowPrice());

                // safety: no stock falls to zero or below
                np[0] = Math.max(np[0], 1);
                np[1] = Math.max(np[1], 1);

                String line = String.format(
                        "%s,%s,%s,%.2f,%.2f%s",
                        s.getName(), ticker, nextDate, np[0], np[1],
                        System.lineSeparator());

                writer.write(line);
            }

            writer.close();

            System.out.println("Market advanced to " + nextDate);

        } catch (Exception e) {
            System.err.println("Error advancing day: " + e.getMessage());
        }
    }

    // --------------------------------------------------------
    // GET NEXT DATE BASED ON LAST CSV ENTRY
    // --------------------------------------------------------
    private static String computeNextDate() {
        String lastDate = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE));
            reader.readLine(); // skip header

            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 3) {
                    lastDate = p[2].trim();
                }
            }
            reader.close();

        } catch (Exception ignored) {
        }

        if (lastDate == null) {
            return LocalDate.now().format(DATE_FORMAT);
        }

        LocalDate d = LocalDate.parse(lastDate, DATE_FORMAT);
        d = d.plusDays(1);

        return d.format(DATE_FORMAT);
    }
}
