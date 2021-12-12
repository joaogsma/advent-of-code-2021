package jgsma.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

class App {
  public static void main(String[] args) throws IOException {
    App app = new App();
    app.run();
  }

  public void run() throws IOException {
    partOne();
    partTwo();
  }

  private void partOne() throws IOException {
    Map<Integer, Integer> crabsByPosition = readInput("input.txt");
    CrabAligner aligner = new ConstantFuelRateCrabAligner(crabsByPosition);
    System.out.println("Part one: " + aligner.run());
  }

  private void partTwo() throws IOException {
    Map<Integer, Integer> crabsByPosition = readInput("input.txt");
    CrabAligner aligner = new SumOfIntsFuelRateCrabAligner(crabsByPosition);
    System.out.println("Part two: " + aligner.run());
  }

  private Map<Integer, Integer> readInput(String filename) throws IOException {
    try (Stream<String> lines = readResource(filename)) {
      return parseInput(lines)
          .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1)));
    }
  }

  private Stream<String> readResource(String filename) {
    final InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename);
    return new BufferedReader(new InputStreamReader(is)).lines();
  }

  private Stream<Integer> parseInput(Stream<String> lines) {
    return lines.flatMap(line -> Arrays.stream(line.split(","))).map(Integer::parseInt);
  }
}
