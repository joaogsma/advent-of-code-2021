package jgsma.adventofcode.models

abstract class Operator(version: Int, val children: IndexedSeq[Packet]) extends Packet(version)
