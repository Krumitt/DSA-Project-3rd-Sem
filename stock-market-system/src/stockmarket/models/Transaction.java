package stockmarket.models;
//to record trade history of the portfolio
public class Transaction {
    private String ticker;
    private String type;
    private int quantity;
    private double price;
    private String date;
//transaction object that stores all the essential data of the stocks
    public Transaction(String ticker, String type, int quantity, double price, String date) {
        this.ticker = ticker;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }
//to get ticker symbol of the stock
    public String getTicker() {
        return ticker;
    }
//to get buy or sell type of transaction
    public String getType() {
        return type;
    }
//to get number of shares involved in the transaction
    public int getQuantity() {
        return quantity;
    }
//to get share price of the stock
    public double getPrice() {
        return price;
    }
//to get the date of transaction
    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("%s - %s %d shares of %s @ $%.2f",
                date, type, quantity, ticker, price);
    }
}