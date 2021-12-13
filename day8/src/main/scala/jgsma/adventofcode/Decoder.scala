package jgsma.adventofcode

import scala.annotation.tailrec
import scala.collection.mutable;

class Decoder(private val signalPatterns: Seq[String]) {
  import Decoder._

  private val possibilities: mutable.Map[Char, Set[Position.Value]] =
      mutable.Map(ALL_WIRES.toSeq.map(_ -> Position.values) :_*)
  private val solvedWires: mutable.Map[Char, Position.Value] = mutable.Map.empty

  def decode(): Map[Char, Position.Value] = {
    handleShortSignalPatterns()
    handleLength6SignalPatterns()
    solvedWires.toMap
  }

  private def handleShortSignalPatterns(): Unit = {
    signalPatterns
        .filter(_.length <= 4)
        .foreach { signalPattern =>
          val values: Set[Position.Value] = signalPattern match {
            case str if str.length == 2 => Set(Position.TopRight, Position.BottomRight)
            case str if str.length == 3 =>
              Set(Position.Top, Position.TopRight, Position.BottomRight)
            case str if str.length == 4 =>
              Set(Position.TopLeft, Position.Middle, Position.TopRight, Position.BottomRight)
          }
          signalPattern.foreach(narrowDown(_, values))
          ALL_WIRES.toIterator
              .filterNot(c => signalPattern.contains(c))
              .foreach(dismiss(_, values))
          checkFinalStates()
        }
  }

  private def handleLength6SignalPatterns(): Unit = {
    val commonWires: Set[Char] =
        signalPatterns
            .filter(_.length == 6)
            .map(_.toSet)
            .reduce(_ intersect _)
    val forbiddenPositions: Set[Position.Value] =
        Set(Position.TopRight, Position.Middle, Position.BottomLeft)
    commonWires.foreach(dismiss(_, forbiddenPositions))
    checkFinalStates()
  }

  private def narrowDown(wire: Char, values: Set[Position.Value]): Unit = {
    if (possibilities contains wire) {
      possibilities.update(wire, possibilities(wire) intersect values)
    }
  }

  private def dismiss(wire: Char, values: Set[Position.Value]): Unit = {
    if (possibilities contains wire) {
      possibilities.update(wire, possibilities(wire) diff values)
    }
  }

  @tailrec
  private def checkFinalStates(): Unit = {
    val solvedWireOpt: Option[(Char, Position.Value)] =
        possibilities.find(_._2.size == 1).map(e => e._1 -> e._2.head)
    solvedWireOpt match {
      case None =>
      case Some((solvedWire, position)) =>
        solvedWires.put(solvedWire, position)
        possibilities.remove(solvedWire)
        ALL_WIRES.foreach(dismiss(_, Set(position)))
        checkFinalStates()
    }
  }
}

object Decoder {
  private val ALL_WIRES: Set[Char] = Set('a', 'b', 'c', 'd', 'e', 'f', 'g')
}
