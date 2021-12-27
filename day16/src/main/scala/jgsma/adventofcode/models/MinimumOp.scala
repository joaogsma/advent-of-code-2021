package jgsma.adventofcode.models

case class MinimumOp(
    override val version: Int,
    override val children: IndexedSeq[Packet]
) extends Operator(version, children) {
  override def eval: BigInt = children.view.map(_.eval).min
}
