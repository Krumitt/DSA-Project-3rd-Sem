package stockmarket.models;

public class Transaction {
    private String ticker;
    private String type;
    private int quantity;
    private double price;
    private String date;

    public Transaction(String ticker, String type, int quantity, double price, String date) {
        this.ticker = ticker;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    public String getTicker() {
        return ticker;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("%s - %s %d shares of %s @ $%.2f",
                date, type, quantity, ticker, price);
    }
}