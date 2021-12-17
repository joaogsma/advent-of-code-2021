package jgsma.adventofcode

import scala.language.reflectiveCalls
import scala.io.Source
import scala.util.matching.Regex

object App {
  private val FOLD_REGEX: Regex = "^fold along (?<axis>\\w)=(?<threshold>\\d+)$".r

  case class Point(x: Int, y: Int)

  def main(args: Array[String]): Unit = {
    using(Source.fromResource("input.txt")) { source =>
      val iterators: (Iterator[String], Iterator[String]) =
          source.getLines().filterNot(_.isEmpty).partition(FOLD_REGEX.matches)
      val foldCommands: Iterator[Point => Point] = iterators._1.map(parseFoldCommand)
      val points: Iterator[Point] = iterators._2.map(parsePoint)
      partOne(foldCommands, points)
    }
    using(Source.fromResource("input.txt")) { source =>
      val iterators: (Iterator[String], Iterator[String]) =
        source.getLines().filterNot(_.isEmpty).partition(FOLD_REGEX.matches)
      val foldCommands: Iterator[Point => Point] = iterators._1.map(parseFoldCommand)
      val points: Iterator[Point] = iterators._2.map(parsePoint)
      partTwo(foldCommands, points)
    }
  }

  private def partOne(foldCommands: Iterator[Point => Point], points: Iterator[Point]): Unit = {
    val result: Int = points.map(foldCommands.next()).distinct.count(_ => true)
    println(s"Part one: $result")
  }

  private def partTwo(foldCommands: Iterator[Point => Point], points: Iterator[Point]): Unit = {
    val transformedPoints: Set[Point] =
        foldCommands
            .foldLeft(points)((acc, foldCommand) => acc.map(foldCommand))
            .toSet
    printImage(transformedPoints)
  }

  private def parsePoint(line: String): Point = {
    val coord: Array[Int] = line.split(",").map(_.toInt)
    Point(coord(0), coord(1))
  }

  private def parseFoldCommand(line: String): Point => Point = {
    FOLD_REGEX.findFirstMatchIn(line) match {
      case Some(matchData) =>
        matchData.group("axis") match {
          case "x" => foldAlongXAxis(matchData.group("threshold").toInt)
          case "y" => foldAlongYAxis(matchData.group("threshold").toInt)
        }
      case None => throw new IllegalArgumentException("Invalid input")
    }
  }

  private def foldAlongXAxis(threshold: Int)(p: Point): Point =
      p.copy(x = Math.min(p.x, 2 * threshold - p.x))

  private def foldAlongYAxis(threhold: Int)(p: Point): Point =
      p.copy(y = Math.min(p.y, 2 * threhold - p.y))

  private def printImage(points: Set[Point]): Unit = {
    val maxX: Int = points.iterator.map(_.x).max
    val maxY: Int = points.iterator.map(_.y).max
    for (y <- 0 to maxY) {
      for (x <- 0 to maxX) {
        val char: Char = if (points contains Point(x, y)) '#' else '.'
        print(char)
      }
      println()
    }
  }

  private def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B = {
    try {
      f(resource)
    } finally {
      resource.close();
    }
  }
}
