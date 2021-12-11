package jgsma.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
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
    Simulation simulation = new Simulation(readInput("input.txt"));
    simulation.advanceDays(80);
    System.out.println("Part one: " + simulation.getPopulationCount());
  }

  private void partTwo() throws IOException {
    Simulation simulation = new Simulation(readInput("input.txt"));
    simulation.advanceDays(256);
    System.out.println("Part two: " + simulation.getPopulationCount());
  }

  private List<SpawningEvent> readInput(String filename) throws IOException {
    try (Stream<String> lines = readResource(filename)) {
      return parseInput(lines)
          .map(
              dayNumber ->
                  ImmutableSpawningEvent.builder().dayNumber(dayNumber + 1).spawnCount(1).build())
          .collect(Collectors.toList());
    }
  }

  private Stream<String> readResource(String filename) {
    final InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename);
    return new BufferedReader(new InputStreamReader(is)).lines();
  }

  private Stream<Integer> parseInput(Stream<String> lines) {
    return lines
        .flatMap(line -> Arrays.stream(line.split(",")))
        .map(Integer::parseInt)
        .peek(
            e -> {
              if (e < 0) {
                throw new IllegalArgumentException("Inputs must not be negative");
              }
            });
  }
}
