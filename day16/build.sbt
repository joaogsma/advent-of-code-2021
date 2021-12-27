import NativePackagerHelper._

enablePlugins(JavaAppPackaging)

name := "Day16"
organization := "jgsma.adventofcode"
version := "1.0.0"
scalaVersion := "3.1.0"
Universal / mappings ++= directory(sourceDirectory.value / "main/resources")
