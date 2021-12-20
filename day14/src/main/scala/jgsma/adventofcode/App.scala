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
      val lines: Iterator[String] = source.getLines()
      val polymerTemplate: String = lines.next()
      val insertionRules: Map[(Char, Char), Char] = parseInsertionRules(lines.drop(1))
      partOne(polymerTemplate, insertionRules)
    }
    using(Source.fromResource("input.txt")) { source =>
      val lines: Iterator[String] = source.getLines()
      val polymerTemplate: String = lines.next()
      val insertionRules: Map[(Char, Char), Char] = parseInsertionRules(lines.drop(1))
      partTwo(polymerTemplate, insertionRules)
    }
  }

  private def partOne(polymerTemplate: String, insertionRules: Map[(Char, Char), Char]): Unit = {
    val charCount: Map[Char, Long] =
      new PolymerExpander(polymerTemplate, insertionRules).expand(10).countChars
    println(s"Part one: ${charCount.values.max - charCount.values.min}")
  }

  private def partTwo(polymerTemplate: String, insertionRules: Map[(Char, Char), Char]): Unit = {
    val charCount: Map[Char, Long] =
        new PolymerExpander(polymerTemplate, insertionRules).expand(40).countChars
    println(s"Part two: ${charCount.values.max - charCount.values.min}")
  }

  private def parseInsertionRules(lines: Iterator[String]): Map[(Char, Char), Char] =
      lines.foldLeft(Map.empty)((acc, line) => acc + ((line(0) -> line(1)) -> line.last))

  private def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B = {
    try {
      f(resource)
    } finally {
      resource.close();
    }
  }
}
