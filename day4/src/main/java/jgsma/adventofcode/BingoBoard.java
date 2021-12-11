package jgsma.adventofcode;

import java.util.Optional;
import java.util.List;
import java.util.stream.IntStream;

public class BingoBoard {
  private int size;
  private int[] numbers;

  private BingoBoard(int[] numbers) {
    this.size = (int) Math.sqrt(numbers.length);
    this.numbers = numbers;
  }

  public Optional<Integer> get(int row, int col) {
    final int value = numbers[toIndex(row, col)];
    if (value == -1) {
      return Optional.empty();
    }
    return Optional.of(value);
  }

  public void mark(int value) {
    for (int i = 0; i < size * size; i++) {
      if (numbers[i] == value) {
        numbers[i] = -1;
      }
    }
  }

  public boolean isComplete() {
    return hasCompleteRow() || hasCompleteColumn();
  }

  public int sumRemainingNumbers() {
    int sum = 0;
    for (int i = 0; i < size * size; i++) {
      sum += numbers[i] == -1 ? 0 : numbers[i];
    }
    return sum;
  }

  private boolean hasCompleteRow() {
    return IntStream.range(0, size)
        .anyMatch(
            row ->
                IntStream.range(0, size)
                    .mapToObj(col -> get(row, col))
                    .noneMatch(Optional::isPresent));
  }

  private boolean hasCompleteColumn() {
    return IntStream.range(0, size)
        .anyMatch(
            col ->
                IntStream.range(0, size)
                    .mapToObj(row -> get(row, col))
                    .noneMatch(Optional::isPresent));
  }

  private int toIndex(int row, int col) {
    return row * size + col;
  }

  static class Builder {
    private int size;
    private int position = 0;
    private int[] numbers;

    public Builder(int size) {
      this.size = size;
      this.numbers = new int[size * size];
    }

    public Builder addRow(List<Integer> row) {
      if (row.size() != size) throw new IllegalArgumentException("Wrong sized row!");
      row.forEach(n -> numbers[position++] = n);
      return this;
    }

    public BingoBoard build() {
      if (position != size * size) throw new RuntimeException("Bingo board missing numbers");
      return new BingoBoard(numbers);
    }
  }
}
