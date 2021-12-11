import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Map;
import java.util.function.Function;

class App {
  private static Pattern LINE_PATTERN =
      Pattern.compile("\\s*(?<x0>\\d+),(?<y0>\\d+)\\s+->\\s+(?<x1>\\d+),(?<y1>\\d+)\\s*");

  public static void main(String[] args) throws IOException {
    App app = new App();
    app.run();
  }

  public void run() throws IOException {
    try (Stream<String> lines = readResource("input.txt")) {
      partOne(lines.map(this::parseLine));
    }
    try (Stream<String> lines = readResource("input.txt")) {
      partTwo(lines.map(this::parseLine));
    }
  }

  public Stream<String> readResource(String filename) {
    final InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename);
    return new BufferedReader(new InputStreamReader(is)).lines();
  }

  private void partOne(Stream<Line> lines) {
    Map<Point, Long> intersectionCount =
        lines
            .filter(Line::isAxisParallel)
            .flatMap(line -> line.coverage().stream())
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    final long result = intersectionCount.values().stream().filter(e -> e >= 2).count();
    System.out.println("Part one: " + result);
  }

  private void partTwo(Stream<Line> lines) {
    Map<Point, Long> intersectionCount =
        lines
            .flatMap(line -> line.coverage().stream())
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    final long result = intersectionCount.values().stream().filter(e -> e >= 2).count();
    System.out.println("Part two: " + result);
  }

  private Line parseLine(String line) {
    final Matcher matcher = LINE_PATTERN.matcher(line);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Malformed line: " + line);
    }
    final Point p0 =
        new Point(Integer.parseInt(matcher.group("x0")), Integer.parseInt(matcher.group("y0")));
    final Point p1 =
        new Point(Integer.parseInt(matcher.group("x1")), Integer.parseInt(matcher.group("y1")));
    return new Line(p0, p1);
  }
}
