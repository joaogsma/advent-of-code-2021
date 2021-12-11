package jgsma.adventofcode;

import org.immutables.value.Value;

@Value.Immutable
public interface SpawningEvent {
  public int dayNumber();

  public int spawnCount();
}
