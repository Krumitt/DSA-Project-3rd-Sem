package stockmarket.models;

public class Stock {
    private String name;
    private String ticker;
    private String date;
    private double highPrice;
    private double lowPrice;

    public Stock(String name, String ticker, String date, double highPrice, double lowPrice) {
        this.name = name;
        this.ticker = ticker;
        this.date = date;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
    }

    public String getName() {
        return name;
    }

    public String getTicker() {
        return ticker;
    }

    public String getDate() {
        return date;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public double getAvgPrice() {
        return (highPrice + lowPrice) / 2.0;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    @Override
    public String toString() {
        return String.format("%-20s %-8s %12s High: $%-8.2f Low: $%-8.2f",
                name, ticker, date, highPrice, lowPrice);
    }
}