package jgsma.adventofcode

import scala.language.reflectiveCalls
import scala.io.Source

object App {
  def main(args: Array[String]): Unit = {
    using(Source.fromResource("input.txt")) { source => partOne(source.getLines.map(parseLine)) }
    using(Source.fromResource("input.txt")) { source => partTwo(source.getLines.map(parseLine)) }
  }

  private def partOne(input: Iterator[Iterator[Int]]): Unit = {
    println(s"Part one: ${Heightmap(input).findRiskLevelSum()}")
  }

  private def partTwo(input: Iterator[Iterator[Int]]): Unit = {
    val result: Int = Heightmap(input).findBasinSizes().sorted.takeRight(3).product
    println(s"Part two: $result")
  }

  private def parseLine(line: String): Iterator[Int] = {
    line.toIterator.map(_ - '0')
  }

  private def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B = {
    try {
      f(resource);
    } finally {
      resource.close();
    }
  }
}
