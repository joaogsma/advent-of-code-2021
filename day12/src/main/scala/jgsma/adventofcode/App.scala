package jgsma.adventofcode

import scala.language.reflectiveCalls
import scala.io.Source

object App {
  def main(args: Array[String]): Unit = {
    using(Source.fromResource("input.txt")) { source =>
      partOne(source.getLines.foldLeft(Map.empty[String, Set[String]])(parseLine))
    }
    using(Source.fromResource("input.txt")) { source =>
      partTwo(source.getLines.foldLeft(Map.empty[String, Set[String]])(parseLine))
    }
  }

  private def partOne(caveSystem: Map[String, Set[String]]): Unit = {
    val result: Int =
        generatePaths("start", caveSystem, Set("start"), canRevisitSmallCave = false)
            .count(_ => true)
    println(s"Part one: $result")
  }

  private def partTwo(caveSystem: Map[String, Set[String]]): Unit = {
    val result: Int =
        generatePaths("start", caveSystem, Set("start"), canRevisitSmallCave = true)
            .distinct
            .count(_ => true)
    println(s"Part two: $result")
  }

  private def parseLine(acc: Map[String, Set[String]], line: String): Map[String, Set[String]] = {
    val caves: Array[String] = line.split("-")
    val caveANeighbours: Set[String] = acc.getOrElse(caves(0), Set.empty) + caves(1)
    val caveBNeighbours: Set[String] = acc.getOrElse(caves(1), Set.empty) + caves(0)
    acc ++ Map(caves(0) -> caveANeighbours, caves(1) -> caveBNeighbours)
  }

  private def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B = {
    try {
      f(resource)
    } finally {
      resource.close();
    }
  }

  private def generatePaths(
      currentCave: String,
      caveSystem: Map[String, Set[String]],
      forbiddenCaves: Set[String],
      canRevisitSmallCave: Boolean
  ): List[List[String]] = {
    if (currentCave == "end") {
      return List(List(currentCave))
    }

    val isLargeCave: Boolean = currentCave == currentCave.toUpperCase
    val neighbours: List[String] =
        caveSystem(currentCave).toList.sorted.filterNot(forbiddenCaves.contains)
    if (isLargeCave) {
      return neighbours
          .flatMap(generatePaths(_, caveSystem, forbiddenCaves, canRevisitSmallCave))
          .map(currentCave :: _)
    }
    if (!canRevisitSmallCave || currentCave == "start") {
      return neighbours
          .flatMap(generatePaths(_, caveSystem, forbiddenCaves + currentCave, canRevisitSmallCave))
          .map(currentCave :: _)
    }

    neighbours
        .flatMap { neighbour =>
          val revisitingPath: List[List[String]] =
            generatePaths(neighbour, caveSystem, forbiddenCaves, canRevisitSmallCave = false)
          val notRevisitingPath: List[List[String]] =
              generatePaths(neighbour, caveSystem, forbiddenCaves + currentCave, canRevisitSmallCave = true)
          revisitingPath ++ notRevisitingPath
        }
        .map(currentCave :: _)
  }
}
