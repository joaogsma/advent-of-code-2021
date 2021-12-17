import NativePackagerHelper._

enablePlugins(JavaAppPackaging)

name := "Day12"
organization := "jgsma.adventofcode"
version := "1.0.0"
scalaVersion := "2.13.6"
Universal / mappings ++= directory(sourceDirectory.value / "main/resources")
