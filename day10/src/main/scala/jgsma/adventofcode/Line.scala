package jgsma.adventofcode

sealed abstract class Line
case class IncompleteLine(missingChars: Seq[Char]) extends Line
case class CorruptedLine(badChar: Char) extends Line
case class CompleteLine() extends Line
