package jgsma.adventofcode.models

case class GreaterThanOp(
    override val version: Int,
    override val children: IndexedSeq[Packet]
) extends Operator(version, children) {
  override def eval: BigInt = if children(0).eval > children(1).eval then 1 else 0
}
