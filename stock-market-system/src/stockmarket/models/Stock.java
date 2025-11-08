package stockmarket.models;
//represents basic information about the stock like its name, ticker symbol, date and highest/lowest price
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
//to get the name of stock
    public String getName() {
        return name;
    }
//to get ticker symbol of stock
    public String getTicker() {
        return ticker;
    }
//to get date of stock data
    public String getDate() {
        return date;
    }
//to get highest price of stock
    public double getHighPrice() {
        return highPrice;
    }
//to get lowest price of stock
    public double getLowPrice() {
        return lowPrice;
    }
//to get average price of stock
    public double getAvgPrice() {
        return (highPrice + lowPrice) / 2.0;
    }
//to set date for selling stock
    public void setDate(String date) {
        this.date = date;
    }
//sets the highest selling price for the stock
    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }
//sets the lowest selling price for the stock
    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    @Override
    public String toString() {
        return String.format("%-20s %-8s %12s High: $%-8.2f Low: $%-8.2f",
                name, ticker, date, highPrice, lowPrice);
    }
}