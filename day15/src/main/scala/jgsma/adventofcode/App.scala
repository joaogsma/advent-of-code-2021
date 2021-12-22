package jgsma.adventofcode

import scala.annotation.tailrec
import scala.collection.SeqView
import scala.collection.immutable.Queue
import scala.collection.mutable
import scala.language.reflectiveCalls
import scala.io.Source

object App {
  def main(args: Array[String]): Unit = {
    using(Source.fromResource("input.txt")) { source =>
      val input: Seq[Seq[Int]] = source.getLines().map(parseLine).toVector
      partOne(input)
    }
    using(Source.fromResource("input.txt")) { source =>
      val input: Seq[Seq[Int]] = source.getLines().map(parseLine).toVector
      partTwo(input)
    }
  }

  private def partOne(grid: Seq[Seq[Int]]): Unit = {
    val totalRows = grid.length
    val totalCols = grid.head.length
    val dijkstra = new Dijkstra(grid, totalRows, totalCols)
    val result = dijkstra.getShortestPath((0, 0), (totalRows - 1, totalCols - 1)).distance
    println(s"Part one: $result")
  }

  private def partTwo(gridPart: Seq[Seq[Int]]): Unit = {
    val fullGrid = buildFullGrid(gridPart)
    val totalRows = fullGrid.length
    val totalCols = fullGrid.head.length
    val dijkstra = new Dijkstra(fullGrid, fullGrid.length, fullGrid.head.length)
    val result = dijkstra.getShortestPath((0, 0), (totalRows - 1, totalCols - 1)).distance
    println(s"Part two: $result")
  }

  private def parseLine(line: String): Seq[Int] = line.map(_ - '0')

  private def buildFullGrid(gridPart: Seq[Seq[Int]]): Seq[Seq[Int]] = {
    val gridPartTotalRows = gridPart.length
    val gridPartTotalCols = gridPart.head.length
    val finalGridTotalRows = gridPart.length * 5
    val finalGridTotalCols = gridPart.head.length * 5

    val fullGrid = mutable.ArrayBuffer.fill(finalGridTotalRows, finalGridTotalCols)(0)
    for (row <- 0 until finalGridTotalRows; col <- 0 until finalGridTotalCols) do
      val gridPartValue = gridPart(row % (finalGridTotalRows / 5))(col % (finalGridTotalCols / 5))
      val unwrappedValue = gridPartValue + (row / gridPartTotalRows) + (col / gridPartTotalCols)
      fullGrid(row)(col) = if unwrappedValue < 10 then unwrappedValue else unwrappedValue - 9

    fullGrid.map(_.toSeq).toSeq
  }

  private def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B = {
    try {
      f(resource)
    } finally {
      resource.close();
    }
  }
}
