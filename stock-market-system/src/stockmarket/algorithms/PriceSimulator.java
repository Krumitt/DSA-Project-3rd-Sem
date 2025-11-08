package stockmarket.algorithms;

import java.util.Random;

public class PriceSimulator {
    private static Random random = new Random();

    private static final double DAILY_VOLATILITY = 0.02;
    private static final double DRIFT = 0.0001;
    private static final double MIN_CHANGE = -0.10;
    private static final double MAX_CHANGE = 0.10;

    public static double[] simulateNextDayPrices(double previousHigh, double previousLow) {
        double avgPrice = (previousHigh + previousLow) / 2.0;

        double change = (random.nextGaussian() * DAILY_VOLATILITY) + DRIFT;

        change = Math.max(MIN_CHANGE, Math.min(MAX_CHANGE, change));

        double newAvgPrice = avgPrice * (1 + change);

        double spread = avgPrice * (0.01 + random.nextDouble() * 0.03);
        double newHigh = newAvgPrice + (spread / 2);
        double newLow = newAvgPrice - (spread / 2);

        newLow = Math.max(0.01, newLow);
        newHigh = Math.max(newLow + 0.01, newHigh);

        return new double[] { newHigh, newLow };
    }

    public static void setSeed(long seed) {
        random = new Random(seed);
    }
}