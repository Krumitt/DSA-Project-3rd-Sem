package stockmarket;

import stockmarket.ui.MenuSystem;
import stockmarket.benchmark.BenchmarkRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n" + "=".repeat(80));
        System.out.println("           STOCK MARKET ANALYSIS & TRADING SYSTEM");
        System.out.println("=".repeat(80));
        System.out.println("\n1. Start Trading System");
        System.out.println("2. Run Benchmarks");
        System.out.print("\nEnter choice: ");

        String choice = scanner.nextLine().trim();

        if (choice.equals("2")) {
            BenchmarkRunner.runBenchmarks();
            System.out.println("\nRun 'running python plot_benchmark.py' to visualize results....");
            try {
                // Command you want to run
                ProcessBuilder pb = new ProcessBuilder("python", "plot_benchmark.py");

                // If your script is not in the working directory, set the path:
                // pb.directory(new File("C:/path/to/script"));

                pb.redirectErrorStream(true); // Merge stdout + stderr

                Process process = pb.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line); // Output from the python script
                }

                int exitCode = process.waitFor();
                System.out.println("Process exited with code: " + exitCode);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            MenuSystem menu = new MenuSystem();
            menu.start();
        }

        scanner.close();
    }
}