package jgsma.adventofcode.models

case class Literal(override val version: Int, value: BigInt) extends Packet(version) {
  override def eval: BigInt = value
}
