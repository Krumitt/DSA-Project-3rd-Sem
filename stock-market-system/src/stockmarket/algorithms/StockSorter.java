package stockmarket.algorithms;

import stockmarket.models.Stock;
import stockmarket.datastructures.CustomArrayList;

public class StockSorter {

    public static void sortByPrice(CustomArrayList<Stock> stocks, boolean ascending) {
        if (stocks.size() <= 1)
            return;
        quickSort(stocks, 0, stocks.size() - 1, ascending);
    }

    private static void quickSort(CustomArrayList<Stock> stocks, int low, int high, boolean ascending) {
        if (low < high) {
            int pivotIndex = partition(stocks, low, high, ascending);
            quickSort(stocks, low, pivotIndex - 1, ascending);
            quickSort(stocks, pivotIndex + 1, high, ascending);
        }
    }

    private static int partition(CustomArrayList<Stock> stocks, int low, int high, boolean ascending) {
        Stock pivot = stocks.get(high);
        double pivotPrice = pivot.getHighPrice();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            double currentPrice = stocks.get(j).getHighPrice();
            boolean shouldSwap = ascending ? (currentPrice <= pivotPrice) : (currentPrice >= pivotPrice);

            if (shouldSwap) {
                i++;
                swap(stocks, i, j);
            }
        }
        swap(stocks, i + 1, high);
        return i + 1;
    }

    private static void swap(CustomArrayList<Stock> stocks, int i, int j) {
        Stock temp = stocks.get(i);
        stocks.set(i, stocks.get(j));
        stocks.set(j, temp);
    }
}