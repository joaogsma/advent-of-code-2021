package jgsma.adventofcode;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Simulation {
  private int currentDay = 0;
  private long populationCount;
  private Map<Integer, Long> spawnSchedule = new HashMap<>();

  public Simulation(Iterable<SpawningEvent> initialEvents) {
    createInitialQueue(initialEvents);
    populationCount = spawnSchedule.values().stream().collect(Collectors.summingLong(e -> e));
  }

  public void advanceDays(int days) {
    final int targetDay = currentDay + days;

    while (currentDay < targetDay) {
      currentDay++;
      if (spawnSchedule.containsKey(currentDay)) {
        final long spawnCount = spawnSchedule.remove(currentDay);
        populationCount += spawnCount;
        spawnSchedule.merge(currentDay + 7, spawnCount, Long::sum);
        spawnSchedule.merge(currentDay + 9, spawnCount, Long::sum);
      }
    }
  }

  private void createInitialQueue(Iterable<SpawningEvent> initialEvents) {
    spawnSchedule =
        StreamSupport.stream(initialEvents.spliterator(), false)
            .collect(
                Collectors.groupingBy(
                    SpawningEvent::dayNumber, Collectors.summingLong(SpawningEvent::spawnCount)));
  }

  public int getCurrentDay() {
    return currentDay;
  }

  public long getPopulationCount() {
    return populationCount;
  }
}
