package jgsma.adventofcode;

public class Point {
  public final int x;
  public final int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public boolean equals(Object other) {
    if (!(other instanceof Point)) return false;
    if (other == this) return true;
    Point otherPoint = (Point) other;
    return otherPoint.x == x && otherPoint.y == y;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + x;
    result = prime * result + y;
    return result;
  }

  public String toString() {
    return "Point(" + x + ", " + y + ")";
  }
}
