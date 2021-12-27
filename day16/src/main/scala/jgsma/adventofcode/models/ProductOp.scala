package jgsma.adventofcode.models

case class ProductOp(
    override val version: Int,
    override val children: IndexedSeq[Packet]
) extends Operator(version, children) {
  override def eval: BigInt = children.view.map(_.eval).product
}
