package stockmarket.algorithms;

import stockmarket.models.Stock;
import stockmarket.datastructures.CustomArrayList;

public class BestBuySellFinder {

    public static class BuySellResult {
        public int buyDay;
        public int sellDay;
        public double profit;
        public String buyDate;
        public String sellDate;

        public BuySellResult(int buyDay, int sellDay, double profit, String buyDate, String sellDate) {
            this.buyDay = buyDay;
            this.sellDay = sellDay;
            this.profit = profit;
            this.buyDate = buyDate;
            this.sellDate = sellDate;
        }
    }

    public static BuySellResult findBestBuySellDays(CustomArrayList<Stock> history) {
        if (history.size() < 2) {
            return null;
        }

        double minPrice = history.get(0).getLowPrice();
        int minPriceDay = 0;
        double maxProfit = 0;
        int buyDay = 0;
        int sellDay = 0;

        for (int i = 1; i < history.size(); i++) {
            Stock currentStock = history.get(i);
            double currentPrice = currentStock.getHighPrice();
            double currentProfit = currentPrice - minPrice;

            if (currentProfit > maxProfit) {
                maxProfit = currentProfit;
                buyDay = minPriceDay;
                sellDay = i;
            }

            if (currentStock.getLowPrice() < minPrice) {
                minPrice = currentStock.getLowPrice();
                minPriceDay = i;
            }
        }

        if (maxProfit <= 0) {
            return null;
        }

        return new BuySellResult(buyDay, sellDay, maxProfit,
                history.get(buyDay).getDate(),
                history.get(sellDay).getDate());
    }
}