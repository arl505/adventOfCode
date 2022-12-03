package org.arlevin.adventofcode.challenges.y21;

import static org.arlevin.adventofcode.utils.ChallengeUtils.getResourceFileLines;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arlevin.adventofcode.challenges.DailyChallenge;

public class Day6 implements DailyChallenge {

  private final Logger log = LogManager.getLogger(this.getClass().getName());

  public boolean executeChallengeSuccessfully() {
    return simulateFish(80) && simulateFish(256);
  }

  private boolean simulateFish(int days) {
    List<Integer> fishList = Arrays.stream(getResourceFileLines("y21/Day6").get(0).split(","))
        .map(Integer::parseInt)
        .collect(Collectors.toList());

    Map<Integer, Long> fishCounts = new HashMap<>();
    for (int fish : fishList) {
      fishCounts.put(fish, fishCounts.getOrDefault(fish, 0L) + 1);
    }

    for (int i = 0; i < days; i++) {
      Map<Integer, Long> newFishCounts = new HashMap<>();
      for (Map.Entry<Integer, Long> entry : fishCounts.entrySet()) {
        if (entry.getKey() != 0) {
          newFishCounts.put(entry.getKey() - 1,
              newFishCounts.getOrDefault(entry.getKey() - 1, 0L) + entry.getValue());
        } else {
          newFishCounts.put(8, entry.getValue());
          newFishCounts.put(6, newFishCounts.getOrDefault(6, 0L) + entry.getValue());
        }
      }
      fishCounts = newFishCounts;
    }

    log.debug("fish count: {}", fishCounts.values().stream().mapToLong(l ->l).sum());
    return true;
  }
}
