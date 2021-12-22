package jgsma.adventofcode

import scala.collection.mutable

class Dijkstra(grid: IterableOnce[IterableOnce[Int]], val totalRows: Int, val totalCols: Int) {
  import Dijkstra._

  private val data: Vector[Int] = grid.iterator.flatten.toVector

  def getShortestPath(origin: (Int, Int), dest: (Int, Int)): Path = {
    val visitedNodes: mutable.Set[(Int, Int)] = mutable.Set.empty
    val paths: mutable.Map[(Int, Int), LinkedPath] = mutable.Map(origin -> LinkedPath(origin, 0))
    val unvisitedNodesHeap: mutable.PriorityQueue[(Int, Int)] =
        mutable.PriorityQueue(origin)((a, b) => paths(b).totalDistance - paths(a).totalDistance)
    while unvisitedNodesHeap.nonEmpty && !visitedNodes.contains(dest) do
      handleNode(unvisitedNodesHeap.dequeue(), visitedNodes, unvisitedNodesHeap, paths)
    if visitedNodes contains dest then
      return buildPath(paths, dest)
    throw new IllegalStateException(s"Could not find node $dest")
  }

  private def handleNode(
      node: (Int, Int),
      visitedNodes: mutable.Set[(Int, Int)],
      unvisitedNodesHeap: mutable.PriorityQueue[(Int, Int)],
      paths: mutable.Map[(Int, Int), LinkedPath]
  ): Unit = {
    if visitedNodes contains node then return
    visitedNodes += node
    getNeighbours(node)
        .filterNot(visitedNodes.contains)
        .foreach(handleEdge(node, _, paths, unvisitedNodesHeap))
    for neighbour <- getNeighbours(node) if !visitedNodes.contains(neighbour) do
      handleEdge(node, neighbour, paths, unvisitedNodesHeap)
  }

  private def handleEdge(
      origin: (Int, Int),
      dest: (Int, Int),
      paths: mutable.Map[(Int, Int), LinkedPath],
      unvisitedNodesHeap: mutable.PriorityQueue[(Int, Int)]): Unit = {
    val existingTentativeDistance: Int =
      paths.get(dest).map(_.totalDistance).getOrElse(Integer.MAX_VALUE)
    val newTentativeDistance: Int = paths(origin).totalDistance + data(toIndex(dest))
    if newTentativeDistance < existingTentativeDistance then
      paths += dest -> LinkedPath(origin, newTentativeDistance)
    unvisitedNodesHeap.enqueue(dest)
  }

  private def buildPath(paths: mutable.Map[(Int, Int), LinkedPath], dest: (Int, Int)): Path = {
    val nodes: Vector[(Int, Int)] =
      Iterator
          .iterate(dest)(
            node => if paths(node).previous != node then paths(node).previous else (-1, -1))
          .takeWhile(isInBounds)
          .toVector
    Path(nodes, paths(dest).totalDistance)
  }

  private def toIndex(row: Int, col: Int): Int = row * totalCols + col

  private def toIndex(coord: (Int, Int)): Int = toIndex(coord(0), coord(1))

  private def toCoordinates(index: Int): (Int, Int) = (index / totalCols, index % totalCols)

  private def isInBounds(row: Int, col: Int): Boolean =
    0 <= row && row < totalRows && 0 <= col && col < totalCols

  private def getNeighbours(row: Int, col: Int): Seq[(Int, Int)] =
    Seq((row - 1, col), (row, col - 1), (row, col + 1), (row + 1, col)).filter(isInBounds)

  private def getNeighbours(pos: (Int, Int)): Seq[(Int, Int)] = getNeighbours(pos(0), pos(1))
}

object Dijkstra {
  case class LinkedPath(previous: (Int, Int), totalDistance: Int)
}
