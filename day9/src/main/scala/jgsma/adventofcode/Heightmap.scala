package jgsma.adventofcode

import scala.collection.mutable

class Heightmap private (private val data: IndexedSeq[Int], private val totalColumns: Int) {
  private val totalRows = data.size / totalColumns;

  def get(row: Int, col: Int): Int = {
    if (!isInBounds(row, col)) {
      throw new IllegalArgumentException("Out of bounds")
    }
    data(row * totalColumns + col)
  }

  def findRiskLevelSum(): Int = {
    findLocalMinima().map(e => get(e._1, e._2) + 1).sum
  }

  def findBasinSizes(): Seq[Int] = {
    findLocalMinima()
        .map { case (row, col) =>
          val visited: mutable.Set[(Int, Int)] = mutable.Set.empty
          val bfsQueue: mutable.Queue[(Int, Int)] = mutable.Queue(row -> col)
          var size: Int = 0;
          while (bfsQueue.nonEmpty) {
            val position: (Int, Int) = bfsQueue.dequeue()
            visited.add(position)
            size += 1
            getNeighbours(position._1, position._2)
                .filterNot(visited.contains)
                .filter(e => isInBasinBounds(e._1, e._2))
                .foreach { neighbour =>
                  bfsQueue.enqueue(neighbour)
                  visited.add(neighbour)
                }
          }
          size
        }
  }

  private def isInBounds(row: Int, col: Int): Boolean = {
    row >= 0 && row < totalRows && col >= 0 && col < totalColumns
  }

  private def findLocalMinima(): Seq[(Int, Int)] = {
    for {
      row <- 0 until totalRows
      col <- 0 until totalColumns
      if isLocalMinimum(row, col)
    } yield (row, col)
  }

  private def isLocalMinimum(row: Int, col: Int): Boolean = {
    if (!isInBounds(row, col)) {
      throw new IllegalArgumentException("Out of bounds")
    }
    getNeighbours(row, col)
        .filter(e => isInBounds(e._1, e._2))
        .forall(e => get(e._1, e._2) > get(row, col))
  }

  private def getNeighbours(row: Int, col: Int): Seq[(Int, Int)] =
      Seq(row -> (col - 1), row -> (col + 1), (row - 1) -> col, (row + 1) -> col)

  private def isInBasinBounds(row: Int, col: Int): Boolean = {
    isInBounds(row, col) && get(row, col) < 9
  }
}

object Heightmap {
  def apply(values: Iterator[Iterator[Int]]): Heightmap = {
    val heightmapData: mutable.ArrayBuffer[Int] = mutable.ArrayBuffer.empty
    heightmapData ++= values.next()
    val totalColumns = heightmapData.size
    values.foreach(heightmapData ++= _)
    new Heightmap(heightmapData.toIndexedSeq, totalColumns)
  }
}
