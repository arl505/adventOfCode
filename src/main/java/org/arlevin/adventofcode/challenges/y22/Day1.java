package org.arlevin.adventofcode.challenges.y22;

import static org.arlevin.adventofcode.utils.ChallengeUtils.getResourceFileLines;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arlevin.adventofcode.challenges.DailyChallenge;

public class Day1 implements DailyChallenge {

  private final Logger log = LogManager.getLogger(this.getClass().getName());

  public boolean executeChallengeSuccessfully() {
    return calorieCounting(1) && calorieCounting(3);
  }

  private boolean calorieCounting(int topN) {
    List<String> lines = getResourceFileLines("y22/Day1");

    PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
    int curr = 0;

    for (String line : lines) {
      if (line.length() == 0) {
        pq.add(curr);
        curr = 0;
      } else {
        curr += Integer.parseInt(line);
      }
    }

    int result = 0;
    for (int i = 0; i < topN; i++) {
      result += pq.remove();
    }
    log.debug("max calories: {}", result);

    return true;
  }
}
