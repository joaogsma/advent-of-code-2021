package jgsma.adventofcode

import java.io.BufferedReader

import jgsma.adventofcode.models.EqualToOp
import jgsma.adventofcode.models.GreaterThanOp
import jgsma.adventofcode.models.LesserThanOp
import jgsma.adventofcode.models.Literal
import jgsma.adventofcode.models.MaximumOp
import jgsma.adventofcode.models.MinimumOp
import jgsma.adventofcode.models.Packet
import jgsma.adventofcode.models.ProductOp
import jgsma.adventofcode.models.SumOp

import scala.collection.mutable
import scala.io.BufferedSource

class PacketFactory {
  import PacketFactory._

  def read(it: Iterator[Char]): Packet = {
    val version: Int = Integer.parseInt(take(it, 3).mkString, 2)
    val typeId: Int = Integer.parseInt(take(it, 3).mkString, 2)
    typeId match
      case LITERAL_TYPE_ID => Literal(version, readLiteralValue(it))
      case SUM_TYPE_ID => SumOp(version, readChildren(it))
      case PRODUCT_TYPE_ID => ProductOp(version, readChildren(it))
      case MINIMUM_TYPE_ID => MinimumOp(version, readChildren(it))
      case MAXIMUM_TYPE_ID => MaximumOp(version, readChildren(it))
      case GREATER_THAN_TYPE_ID => GreaterThanOp(version, readChildren(it))
      case LESS_THAN_TYPE_ID => LesserThanOp(version, readChildren(it))
      case EQUAL_TO_TYPE_ID => EqualToOp(version, readChildren(it))
  }

  private def readLiteralValue(it: Iterator[Char]): BigInt = {
    val builder = new StringBuilder()
    while it.next() == '1' do
      builder ++= it.take(4)
    builder ++= it.take(4)
    BigInt(builder.mkString, 2)
  }

  private def readChildren(it: Iterator[Char]): Vector[Packet] = {
    val lengthTypeId: Int = Integer.parseInt(it.next().toString, 2)
    lengthTypeId match {
      case TOTAL_BYTES_LENGTH_TYPE_ID =>
        val childrenLength: Int = Integer.parseInt(take(it, 15).mkString, 2)
        val childrenIt: Iterator[Char] = take(it, childrenLength)
        val builder: mutable.ListBuffer[Packet] = mutable.ListBuffer.empty
        while childrenIt.hasNext do
          builder += read(childrenIt)
        builder.toVector
      case NUMBER_OF_CHILDREN_LENGTH_TYPE_ID =>
        val nChildren: Int = Integer.parseInt(take(it, 11).mkString, 2)
        Iterator.fill(nChildren)(read(it)).toVector
    }
  }

  private def take(it: Iterator[Char], n: Int): Iterator[Char] = Iterator.fill(n)(it.next())
}

object PacketFactory {
  private val SUM_TYPE_ID = 0
  private val PRODUCT_TYPE_ID = 1
  private val MINIMUM_TYPE_ID = 2
  private val MAXIMUM_TYPE_ID = 3
  private val LITERAL_TYPE_ID = 4
  private val GREATER_THAN_TYPE_ID = 5
  private val LESS_THAN_TYPE_ID = 6
  private val EQUAL_TO_TYPE_ID = 7

  private val TOTAL_BYTES_LENGTH_TYPE_ID = 0
  private val NUMBER_OF_CHILDREN_LENGTH_TYPE_ID = 1
}
