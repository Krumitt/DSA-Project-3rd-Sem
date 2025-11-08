import pandas as pd
import matplotlib.pyplot as plt
import os

# load benchmark data
df = pd.read_csv("benchmark_results.csv")

# set up a 2x3 plot layout
fig, axes = plt.subplots(2, 3, figsize=(18, 10))
fig.suptitle("Stock Market System - Benchmark Results", fontsize=16, fontweight="bold")

functions = ["StockSearch", "BestBuySell", "StockSort"]
colors = ["#2E86AB", "#A23B72", "#F18F01"]

for idx, func in enumerate(functions):
    func_data = df[df["Function"] == func]

   # time complexity
    ax_time = axes[0, idx]
    ax_time.plot(
        func_data["InputSize"],
        func_data["TimeMs"],
        marker="o",
        linewidth=2,
        markersize=8,
        color=colors[idx],
    )
    ax_time.set_title(f"{func} - Time Complexity", fontweight="bold")
    ax_time.set_xlabel("Input Size")
    ax_time.set_ylabel("Time (ms)")
    ax_time.grid(True, alpha=0.3)
    ax_time.set_facecolor("#f8f9fa")

     # space complexity
    ax_space = axes[1, idx]
    ax_space.plot(
        func_data["InputSize"],
        func_data["MemoryKB"],
        marker="s",
        linewidth=2,
        markersize=8,
        color=colors[idx],
    )
    ax_space.set_title(f"{func} - Space Complexity", fontweight="bold")
    ax_space.set_xlabel("Input Size")
    ax_space.set_ylabel("Memory (KB)")
    ax_space.grid(True, alpha=0.3)
    ax_space.set_facecolor("#f8f9fa")

plt.tight_layout()
plt.savefig("benchmark_results.png", dpi=300, bbox_inches="tight")
print("Benchmark visualization saved as 'benchmark_results.png'")
plt.show()
