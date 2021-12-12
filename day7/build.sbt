import NativePackagerHelper._

enablePlugins(JavaAppPackaging);

// Configs related to Java-only project
crossPaths := false;
autoScalaLibrary := false;
Compile / compile / javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-g:lines");
Compile / unmanagedSourceDirectories := (Compile / javaSource).value :: Nil

name := "Day7";
organization := "jgsma.adventofcode";
version := "1.0.0";
Compile / mainClass := Some("jgsma.adventofcode.App");
Universal / mappings ++= directory(sourceDirectory.value / "main/resources")
javafmtOnCompile := true;