# Stock Market Analysis & Trading System

A comprehensive Java-based stock market simulation system with portfolio management, historical analysis, and performance benchmarking.

## Features

- **Portfolio Management**: Buy and sell stocks, track holdings and net worth
- **Real-time Price Simulation**: Advanced algorithm for realistic daily price changes
- **Historical Analysis**: View stock history and find optimal buy/sell opportunities
- **Custom Data Structures**: Implemented from scratch (ArrayList, HashMap, LinkedList)
- **Performance Benchmarking**: Comprehensive analysis with visualization

## Project Structure
```
stock-market-system/
├── src/stockmarket/
│   ├── models/          # Data models (Stock, Portfolio, Transaction)
│   ├── datastructures/  # Custom implementations (ArrayList, HashMap, etc.)
│   ├── algorithms/      # Core algorithms (Search, Sort, Best Buy/Sell)
│   ├── services/        # Business logic (DataManager, PortfolioManager)
│   ├── ui/             # User interface (MenuSystem)
│   ├── benchmark/      # Performance testing
│   └── Main.java       # Entry point
├── data/
│   └── market2.txt     # Initial stock data
└── plot_benchmark.py   # Visualization script
```

## Compilation & Execution

### Step 1: Create Directory Structure
```bash
mkdir -p stock-market-system/src/stockmarket/{models,datastructures,algorithms,services,ui,benchmark}
mkdir -p stock-market-system/{data,bin}
cd stock-market-system
```

### Step 2: Place Your Files
- Copy all Java files to their respective package directories
- Place market2.txt in the data/ folder
- Place plot_benchmark.py in the root directory

### Step 3: Compile
```bash
# From project root
javac -d bin src/stockmarket/models/*.java
javac -d bin -cp bin src/stockmarket/datastructures/*.java
javac -d bin -cp bin src/stockmarket/algorithms/*.java
javac -d bin -cp bin src/stockmarket/services/*.java
javac -d bin -cp bin src/stockmarket/ui/*.java
javac -d bin -cp bin src/stockmarket/benchmark/*.java
javac -d bin -cp bin src/stockmarket/Main.java
```

**OR compile everything at once:**
```bash
javac -d bin -sourcepath src src/stockmarket/Main.java
```

### Step 4: Run Trading System
```bash
java -cp bin stockmarket.Main
# Select option 1
```

### Step 5: Run Benchmarks
```bash
java -cp bin stockmarket.Main
# Select option 2
```

### Step 6: Visualize Results
```bash
pip install pandas matplotlib
python plot_benchmark.py
```

## Complexity Analysis

### 1. Stock Search (Function 1)
- **Algorithm**: HashMap-based lookup
- **Time Complexity**: O(1) average case
- **Space Complexity**: O(n) for storing n stocks
- **Implementation**: Custom HashMap with chaining for collision resolution
- **Use Case**: Finding stocks by ticker symbol instantly

### 2. Best Buy/Sell Finder (Function 2)
- **Algorithm**: Kadane's Algorithm variant (Modified for stock trading)
- **Time Complexity**: O(n) - single pass through history
- **Space Complexity**: O(1) - only storing result indices
- **Implementation**: Tracks minimum price and maximum profit in one iteration
- **Use Case**: Finding maximum profit opportunity from historical data

### 3. Stock Sorter (Function 3)
- **Algorithm**: QuickSort
- **Time Complexity**: O(n log n) average case, O(n²) worst case
- **Space Complexity**: O(log n) - recursion stack depth
- **Implementation**: In-place sorting with pivot partitioning
- **Use Case**: Sorting stocks by current price (ascending/descending)

## Menu Options

1. **Buy Stock** - Purchase shares by entering ticker symbol and quantity
2. **Sell Stock** - Sell shares from your portfolio
3. **View Portfolio** - Display all holdings, profit/loss, and net worth
4. **Display All Stocks** - Show all available stocks with current prices
5. **Display Stocks Sorted by Price** - View stocks sorted ascending or descending
6. **View Stock History & Best Buy/Sell Days** - Analyze historical data for any stock
7. **Advance to Next Day** - Simulate next trading day with randomized prices
8. **Exit** - Close application

## Price Simulation Algorithm

The system uses a **Random Walk with Drift** model for realistic price simulation:
```
Parameters:
- Daily Volatility: 2% (standard deviation)
- Drift: 0.01% (slight upward bias)
- Max Daily Change: ±10%
- Spread Range: 1-4% between high and low
```

**Formula:**
```
change = (gaussian_random * volatility) + drift
new_price = old_price * (1 + change)
```

This creates realistic market movements with:
- Natural price fluctuations
- Occasional large moves
- Prices stay positive
- High/low spread mimics intraday trading

## Custom Data Structures

All data structures implemented **WITHOUT** using Java Collections Framework:

### CustomArrayList
- Dynamic array with automatic resizing
- Doubles capacity when full
- Operations: add O(1), get O(1), remove O(n)

### CustomHashMap
- Hash table with separate chaining
- Load factor: 0.75 (resizes at 75% capacity)
- Operations: put O(1), get O(1), remove O(1) average case

### CustomLinkedList
- Singly linked list
- Operations: addFirst O(1), addLast O(1), get O(n)

## Initial Data Format

The `market2.txt` file format:
```
StockName,Ticker,Date,HighPrice,LowPrice
Apple,AAPL,20-10-2025,172.50,170.10
Microsoft,MSFT,20-10-2025,351.00,348.20
...
```

## Benchmark Results Interpretation

The benchmarking system tests with **5 different input sizes**: 10, 50, 100, 500, 1000 stocks

**Expected Results:**

1. **Stock Search**: 
   - Time should be nearly constant (validates O(1))
   - Space grows linearly with input size

2. **Best Buy/Sell**: 
   - Time grows linearly (validates O(n))
   - Space remains constant

3. **Stock Sort**: 
   - Time grows as n*log(n) (validates O(n log n))
   - Space grows logarithmically (recursion depth)

## Sample Output

### Main Menu
```
================================================================================
           STOCK MARKET ANALYSIS & TRADING SYSTEM
================================================================================

1. Start Trading System
2. Run Benchmarks

Enter choice: 1
```

### Trading Interface
```
================================================================================
                               MAIN MENU
================================================================================
1. Buy Stock
2. Sell Stock
3. View Portfolio
4. Display All Stocks
5. Display Stocks Sorted by Price
6. View Stock History & Best Buy/Sell Days
7. Advance to Next Day
8. Exit
================================================================================
```

### Portfolio Display
```
================================================================================
                              YOUR PORTFOLIO
================================================================================

Cash Balance: $91435.00

Stock Holdings:
--------------------------------------------------------------------------------
Ticker     Quantity     Avg Buy Price   Current Price   P&L            
--------------------------------------------------------------------------------
AAPL       50           $171.30         $173.50         $110.00        
NVDA       10           $453.40         $455.60         $22.00         
--------------------------------------------------------------------------------
Total Profit/Loss: $132.00

Total Net Worth: $100567.00
================================================================================
```

### Best Buy/Sell Analysis
```
================================================================================
                    BEST BUY/SELL OPPORTUNITY
================================================================================
Buy Date:  20-10-2025 (Day 0)
Sell Date: 24-10-2025 (Day 4)
Maximum Profit: $11.20 per share
================================================================================
```

## Dependencies

- **Java**: JDK 8 or higher
- **Python**: 3.x (for visualization only)
- **Python Packages**: 
```bash
  pip install pandas matplotlib
```

## Troubleshooting

### Issue: ClassNotFoundException
**Solution**: Ensure you're running from the correct directory with `-cp bin`

### Issue: market2.txt not found
**Solution**: Place market2.txt in `data/` folder relative to where you run the program

### Issue: CSV not being created
**Solution**: Make sure the `data/` directory exists before running

### Issue: Benchmark graphs not displaying
**Solution**: Install required packages: `pip install matplotlib pandas`

### Issue: Compilation errors
**Solution**: Compile in order (models → datastructures → algorithms → services → ui → benchmark → Main)

## Project Highlights

### Technical Excellence
- ✅ **Zero built-in collections** - All data structures custom-implemented
- ✅ **Optimal algorithms** - Each function uses the most efficient approach
- ✅ **Memory efficient** - Minimal space usage
- ✅ **Real-world simulation** - Realistic stock price movements

### Code Quality
- Clean package structure
- Comprehensive comments
- Error handling
- Input validation
- Modular design

### Educational Value
- Demonstrates DSA concepts in practice
- Shows algorithm complexity in real scenarios
- Benchmarking proves theoretical analysis
- Visual graphs for better understanding

## Future Enhancements

Possible improvements:
- Add transaction history tracking
- Implement stop-loss and limit orders
- Add technical indicators (RSI, MACD, Moving Averages)
- Multi-threaded price simulation
- Persistent storage (database integration)
- Advanced charting (candlestick charts)

## Author

DSA Course Project - Stock Market Analysis System

Created for educational purposes to demonstrate:
- Data structure implementation
- Algorithm design and analysis
- Performance benchmarking
- Software engineering principles

---

**License**: Educational Use Only

**Note**: This is a simulation system for learning purposes. Not intended for actual trading or investment decisions.