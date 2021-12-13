package jgsma.adventofcode

object Scores {
  def scoreCorruptedLines(lines: Iterator[CorruptedLine]): Int = {
    lines
        .map {
          case CorruptedLine(')') => 3
          case CorruptedLine(']') => 57
          case CorruptedLine('}') => 1197
          case CorruptedLine('>') => 25137
          case CorruptedLine(c) => throw new IllegalStateException("Unknown bad character " + c)
        }
        .sum
  }

  def scoreIncompleteLines(lines: Iterator[IncompleteLine]): Long = {
    val sortedLineScores: IndexedSeq[Long] =
        lines
            .map { case IncompleteLine(missingChars) =>
              missingChars
                .map {
                  case ')' => 1L
                  case ']' => 2L
                  case '}' => 3L
                  case '>' => 4L
                }
                .foldLeft(0L)(_ * 5 + _)
            }
            .toIndexedSeq
            .sorted
    sortedLineScores(sortedLineScores.size / 2)
  }
}
