package jgsma.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class App {
  public static void main(String[] args) throws IOException {
    final App app = new App();
    app.run();
  }

  public void run() throws IOException {
    try (BufferedReader br = readResource("input.txt")) {
      final Stream<Integer> randomNumbers = parseNumberLine(br.readLine(), ",");
      final Set<BingoBoard> boards = readBingoBoards(br);
      partOne(randomNumbers, boards);
    }
    try (BufferedReader br = readResource("input.txt")) {
      final Stream<Integer> randomNumbers = parseNumberLine(br.readLine(), ",");
      final Set<BingoBoard> boards = readBingoBoards(br);
      partTwo(randomNumbers, boards);
    }
  }

  public BufferedReader readResource(String filename) {
    final InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename);
    return new BufferedReader(new InputStreamReader(is));
  }

  private Stream<Integer> parseNumberLine(String line, String separator) {
    String[] splitLine = line.trim().split(separator);
    return Arrays.stream(splitLine).map(Integer::parseInt);
  }

  private void partOne(Stream<Integer> randomNumbers, Set<BingoBoard> boards) throws IOException {
    for (int randomNumber : randomNumbers.collect(Collectors.toList())) {
      Optional<BingoBoard> winner =
          boards.stream()
              .peek(board -> board.mark(randomNumber))
              .filter(BingoBoard::isComplete)
              .findFirst();
      if (winner.isPresent()) {
        final int score = winner.get().sumRemainingNumbers() * randomNumber;
        System.out.println("Part one - winner scored " + score);
        break;
      }
    }
  }

  private void partTwo(Stream<Integer> randomNumbers, Set<BingoBoard> boards) throws IOException {
    for (int randomNumber : randomNumbers.collect(Collectors.toList())) {
      List<BingoBoard> finishedBoards =
          boards.stream()
              .peek(board -> board.mark(randomNumber))
              .filter(BingoBoard::isComplete)
              .collect(Collectors.toList());
      if (!finishedBoards.isEmpty() && boards.size() == 1) {
        final int score = finishedBoards.get(0).sumRemainingNumbers() * randomNumber;
        System.out.println("Part two - last board scored " + score);
        break;
      }
      if (!finishedBoards.isEmpty() && boards.size() > finishedBoards.size()) {
        boards.removeAll(finishedBoards);
      }
    }
  }

  private Set<BingoBoard> readBingoBoards(BufferedReader br) throws IOException {
    final Set<BingoBoard> boards = new HashSet<>();
    String line;
    while ((line = br.readLine()) != null) {
      final BingoBoard.Builder builder = new BingoBoard.Builder(5);
      for (int i = 0; i < 5; i++) {
        line = br.readLine();
        List<Integer> row = parseNumberLine(line, " +").collect(Collectors.toList());
        builder.addRow(row);
      }
      boards.add(builder.build());
    }
    return boards;
  }
}
