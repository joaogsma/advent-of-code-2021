package jgsma.adventofcode

import scala.annotation.tailrec

object LineParser {
  private val closingChars: Map[Char, Char] = Map('(' -> ')', '[' -> ']', '{' -> '}', '<' -> '>')

  def parseLine(line: String): Line = parseLine(line.iterator, List.empty)

  @tailrec
  private def parseLine(chars: Iterator[Char], expected: List[Char]): Line = (chars.nextOption(), expected) match {
    case (None, Nil) => CompleteLine()
    case (None, missing) => IncompleteLine(missing)
    case (Some(c), _) if isOpeningChar(c) => parseLine(chars, closingChars(c) :: expected)
    case (Some(c), head :: tail) => c match {
      case `head` => parseLine(chars, tail)
      case _ => CorruptedLine(c)
    }
  }

  private def isOpeningChar: Char => Boolean = closingChars.keySet.contains
}
