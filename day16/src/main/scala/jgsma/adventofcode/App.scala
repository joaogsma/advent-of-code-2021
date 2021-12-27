package jgsma.adventofcode

import jgsma.adventofcode.models.Literal
import jgsma.adventofcode.models.Operator
import jgsma.adventofcode.models.Packet

import scala.annotation.tailrec
import scala.collection.SeqView
import scala.collection.immutable.Queue
import scala.collection.mutable
import scala.language.reflectiveCalls
import scala.io.Source

object App {
  def main(args: Array[String]): Unit = {
    using(Source.fromResource("input.txt")) { source => partOne(source.flatMap(hexToBinary)) }
    using(Source.fromResource("input.txt")) { source => partTwo(source.flatMap(hexToBinary)) }
  }

  private def partOne(it: Iterator[Char]): Unit = {
    val packet = new PacketFactory().read(it)
    println(s"Part one: ${addVersions(packet)}")
  }

  private def partTwo(it: Iterator[Char]): Unit = {
    val packet = new PacketFactory().read(it)
    println(s"Part two: ${packet.eval}")
  }

  private def hexToBinary(hexChar: Char): String = {
    val binaryStr = Integer.parseInt(hexChar.toString, 16).toBinaryString
    s"000$binaryStr".takeRight(4)
  }

  private def addVersions(packet: Packet): Int = packet match {
    case Literal(version, _) => version
    case op: Operator => op.version + op.children.view.map(addVersions).sum
  }

  private def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B = {
    try {
      f(resource)
    } finally {
      resource.close();
    }
  }
}
