package jgsma.adventofcode

import scala.language.reflectiveCalls
import scala.io.Source

object App {
  def main(args: Array[String]): Unit = {
    using(Source.fromResource("input.txt")) { source => partOne(source.getLines.map(parseLine)) }
    using(Source.fromResource("input.txt")) { source => partTwo(source.getLines.map(parseLine)) }
  }

  private def partOne(noteEntries: Iterator[NoteEntry]): Unit = {
    val result: Int =
        noteEntries
            .flatMap(_.outputDigits)
            .map(_.length)
            .count(n => n == 2 || n == 4 || n == 3 || n == 7)
    println(s"Part one: $result")
  }

  private def partTwo(noteEntries: Iterator[NoteEntry]): Unit = {
    val result =
        noteEntries
            .map { noteEntry =>
              val decoder: Decoder = new Decoder(noteEntry.signalPatterns)
              val reconstructor: DigitReconstructor = new DigitReconstructor(decoder.decode())
              noteEntry.outputDigits
                  .map(reconstructor.reconstruct)
                  .reduce(_ * 10 + _)
            }
            .sum
    println(s"Part two: $result")
  }

  private def parseLine(line: String) = {
    val splitLine: Seq[String] = line.split(" ");
    NoteEntry(splitLine.take(10), splitLine.drop(11));
  }

  private def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B = {
    try {
      f(resource);
    } finally {
      resource.close();
    }
  }
}
