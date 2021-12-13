package jgsma.adventofcode

import scala.language.reflectiveCalls
import scala.io.Source

object App {
  def main(args: Array[String]): Unit = {
    using(Source.fromResource("input.txt")) { source => partOne(source.getLines) }
    using(Source.fromResource("input.txt")) { source => partTwo(source.getLines) }
  }

  private def partOne(lines: Iterator[String]): Unit = {
    val corruptedLines: Iterator[CorruptedLine] =
        lines
            .map(LineParser.parseLine)
            .filter(_.isInstanceOf[CorruptedLine])
            .asInstanceOf[Iterator[CorruptedLine]]
    println(s"Part one: ${Scores.scoreCorruptedLines(corruptedLines)}")
  }

  private def partTwo(lines: Iterator[String]): Unit = {
    val incompleteLines: Iterator[IncompleteLine] =
        lines
            .map(LineParser.parseLine)
            .filter(_.isInstanceOf[IncompleteLine])
            .asInstanceOf[Iterator[IncompleteLine]]
    println(s"Part two: ${Scores.scoreIncompleteLines(incompleteLines)}")
  }

  private def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B = {
    try {
      f(resource);
    } finally {
      resource.close();
    }
  }
}
