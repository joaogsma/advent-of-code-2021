package jgsma.adventofcode

class DigitReconstructor(private val decodedWires: Map[Char, Position.Value]) {
  import DigitReconstructor._

  def reconstruct(signalPattern: String): Int = {
    DIGIT_MAPPING(signalPattern.map(decodedWires.apply).toSet)
  }
}

object DigitReconstructor {
  private val DIGIT_MAPPING: Map[Set[Position.Value], Int] =
      Map(
        Position.values -> 8,
        (Position.values - Position.Middle) -> 0,
        (Position.values - Position.TopRight) -> 6,
        (Position.values - Position.BottomLeft) -> 9,
        (Position.values diff Set(Position.TopLeft, Position.BottomRight)) -> 2,
        (Position.values diff Set(Position.TopLeft, Position.BottomLeft)) -> 3,
        (Position.values diff Set(Position.TopRight, Position.BottomLeft)) -> 5,
        Set(Position.TopRight, Position.BottomRight) -> 1,
        Set(Position.Top, Position.TopRight, Position.BottomRight) -> 7,
        Set(Position.TopLeft, Position.Middle, Position.TopRight, Position.BottomRight) -> 4)
}
