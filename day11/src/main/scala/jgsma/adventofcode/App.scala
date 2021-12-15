package jgsma.adventofcode

import scala.language.reflectiveCalls
import scala.io.Source

object App {
  def main(args: Array[String]): Unit = {
    using(Source.fromResource("input.txt")) { source => partOne(source.getLines.map(parseLine)) }
    using(Source.fromResource("input.txt")) { source => partTwo(source.getLines.map(parseLine)) }
  }

  private def partOne(lines: Iterator[Iterator[Int]]): Unit = {
    val grid: OctopusGrid = new OctopusGrid(10, 10, lines.flatten)
    println(s"Part one: ${grid.advanceSteps(100)}")
  }

  private def partTwo(lines: Iterator[Iterator[Int]]): Unit = {
    val grid: OctopusGrid = new OctopusGrid(10, 10, lines.flatten)
    var steps: Int = 0
    while (!grid.isSynchronized) {
      grid.advanceStep()
      steps += 1
    }
    println(s"Part two: $steps")
  }

  private def parseLine(line: String): Iterator[Int] = line.iterator.map(_ - '0')

  private def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B = {
    try {
      f(resource)
    } finally {
      resource.close();
    }
  }
}
