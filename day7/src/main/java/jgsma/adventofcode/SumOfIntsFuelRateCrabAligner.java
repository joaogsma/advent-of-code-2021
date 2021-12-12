package jgsma.adventofcode;

import java.util.Map;

class SumOfIntsFuelRateCrabAligner extends CrabAligner {
  public SumOfIntsFuelRateCrabAligner(Map<Integer, Integer> crabsByPosition) {
    super(crabsByPosition);
  }

  protected int fuelCost(int distance) {
    return distance * (distance + 1) / 2;
  }
}
