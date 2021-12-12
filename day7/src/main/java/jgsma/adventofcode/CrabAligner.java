package jgsma.adventofcode;

import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class CrabAligner {
  protected final Map<Integer, Integer> crabsByPosition;

  public CrabAligner(Map<Integer, Integer> crabsByPosition) {
    this.crabsByPosition = crabsByPosition;
  }

  public long run() {
    final int smallestCostPosition = gradientBinarySearch();
    return cost(smallestCostPosition);
  }

  private int gradientBinarySearch() {
    final IntSummaryStatistics statistics =
        crabsByPosition.keySet().stream().collect(Collectors.summarizingInt(e -> e));
    final int begin = statistics.getMin();
    final int end = statistics.getMax() + 1;
    return gradientBinarySearch(begin, end);
  }

  private int gradientBinarySearch(int begin, int end) {
    final int currentPosition = begin + (end - begin) / 2;
    final int gradient = gradient(currentPosition);
    if (gradient == 0) {
      return currentPosition;
    }
    return gradientBinarySearch(
        gradient < 0 ? begin : currentPosition, gradient > 0 ? end : currentPosition);
  }

  private long cost(int pos) {
    return crabsByPosition.entrySet().stream()
        .map(entry -> entry.getValue() * fuelCost(Math.abs(entry.getKey() - pos)))
        .collect(Collectors.summingLong(e -> e));
  }

  protected abstract int fuelCost(int distance);

  private int gradient(int pos) {
    final long leftCost = cost(pos - 1);
    final long rightCost = cost(pos + 1);
    final long currentCost = cost(pos);
    return leftCost < currentCost ? -1 : (rightCost < currentCost ? 1 : 0);
  }
}
