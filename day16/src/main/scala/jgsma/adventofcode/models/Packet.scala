package jgsma.adventofcode.models

abstract class Packet(val version: Int) {
  def eval: BigInt
}
