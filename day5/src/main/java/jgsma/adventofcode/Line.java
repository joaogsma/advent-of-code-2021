package jgsma.adventofcode;

import java.util.ArrayList;
import java.util.List;

public class Line {
  private final Point p0;
  private final Point p1;

  Line(Point p0, Point p1) {
    this.p0 = p0;
    this.p1 = p1;
  }

  public boolean isAxisParallel() {
    return p0.x == p1.x || p0.y == p1.y;
  }

  public List<Point> coverage() {
    Point vector = new Point(p1.x - p0.x, p1.y - p0.y);
    Point normalizedVector = boxNormalizeVector(vector);

    List<Point> coverage = new ArrayList<>();
    Point current = p0;
    do {
      coverage.add(current);
      current = new Point(current.x + normalizedVector.x, current.y + normalizedVector.y);
    } while (current.x != p1.x || current.y != p1.y);
    coverage.add(p1);

    return coverage;
  }

  public String toString() {
    return "Line(" + p0 + ", " + p1 + ")";
  }

  private Point boxNormalizeVector(Point vector) {
    return new Point(
        vector.x == 0 ? 0 : vector.x / Math.abs(vector.x),
        vector.y == 0 ? 0 : vector.y / Math.abs(vector.y));
  }
}
