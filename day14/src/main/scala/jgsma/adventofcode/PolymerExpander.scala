package jgsma.adventofcode

import jgsma.adventofcode.PolymerExpander._

import scala.annotation.tailrec

class PolymerExpander private (
    val polymerTemplate: Seq[PolymerPair],
    val rules: Map[(Char, Char), Char]) {

  def this(polymerTemplate: String, rules: Map[(Char, Char), Char]) = {
    this(
        polymerTemplate
            .iterator
            .sliding(2)
            .map { case Seq(a, b) => PolymerPair(a -> b, 1L) }
            .toSeq,
        rules)
  }

  def countChars: Map[Char, Long] = {
    polymerTemplate
        .flatMap(pp => Iterator(pp.pair(0) -> pp.repeatedTimes, pp.pair(1) -> pp.repeatedTimes))
        .groupMapReduce(_._1)(_._2)(_ + _)
        .view
        .mapValues(e => e / 2 + e % 2)
        .toMap
  }

  def expand(times: Int): PolymerExpander = {
    new PolymerExpander(doSteps(polymerTemplate, times), rules)
  }

  @tailrec
  private def doSteps(pairs: Seq[PolymerPair], remaining: Int): Seq[PolymerPair] = {
    if remaining == 0 then return pairs
    val expandedPairs = pairs.flatMap(expandPair)
    doSteps(removeRedundancies(expandedPairs), remaining - 1)
  }

  private def expandPair(pair: PolymerPair): Seq[PolymerPair] = {
    rules.get(pair.pair) match {
      case None => Seq(pair)
      case Some(char) =>
        val pair0: (Char, Char) = pair.pair.copy(_2 = char)
        val pair1: (Char, Char) = pair.pair.copy(_1 = char)
        Seq(pair.copy(pair = pair0), pair.copy(pair = pair1))
    }
  }

  private def removeRedundancies(polymer: Seq[PolymerPair]): Seq[PolymerPair] = {
    polymer
        .groupMapReduce(_.pair)(_.repeatedTimes)(_ + _)
        .iterator
        .map((pair, count) => PolymerPair(pair, count))
        .toSeq
  }
}

object PolymerExpander {
  case class PolymerPair(pair: (Char, Char), repeatedTimes: Long)
}
