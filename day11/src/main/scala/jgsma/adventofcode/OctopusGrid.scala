package jgsma.adventofcode

import scala.collection.mutable

class OctopusGrid(private val totalRows: Int, totalCols: Int, initialData: Iterator[Int]) {
  private val data: Array[Int] = Array.from(initialData)

  if (data.length != totalRows * totalCols) {
    throw new IllegalArgumentException("Wrong number of entries in grid")
  }

  def advanceStep(): Int = {
    var count: Int = data.count(_ == 9)
    val bfsQueue: mutable.Queue[Int] = mutable.Queue.empty

    data.mapInPlace(_ + 1)
    data.indices.filter(data(_) == 10).flatMap(neighbours).foreach(bfsQueue.enqueue)

    while (bfsQueue.nonEmpty) {
      val current: Int = bfsQueue.dequeue()
      data(current) match {
        case 10 =>
        case 9 =>
          data(current) += 1
          count += 1
          bfsQueue.enqueueAll(neighbours(current))
        case _ => data(current) += 1
      }
    }

    data.indices.filter(data(_) == 10).foreach(data(_) = 0)
    count
  }

  def advanceSteps(steps: Int): Int = (0 until steps).map(_ => advanceStep()).sum

  def isSynchronized: Boolean = data.forall(_ == 0)

  private def neighbours(idx: Int): Seq[Int] = {
    val centerRow: Int = idx / totalCols
    val centerCol: Int = idx % totalCols
    for {
      row <- centerRow - 1 to centerRow + 1
      col <- centerCol - 1 to centerCol + 1
      if row != centerRow || col != centerCol
      if row >= 0 && row < totalRows && col >= 0 && col < totalCols
    } yield row * totalCols + col
  }
}
