package jgsma.adventofcode;

import java.util.Map;

public class ConstantFuelRateCrabAligner extends CrabAligner {
  public ConstantFuelRateCrabAligner(Map<Integer, Integer> crabsByPosition) {
    super(crabsByPosition);
  }

  protected int fuelCost(int distance) {
    return distance;
  }
}
