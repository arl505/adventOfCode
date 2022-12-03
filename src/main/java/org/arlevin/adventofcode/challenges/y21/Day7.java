package org.arlevin.adventofcode.challenges.y21;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arlevin.adventofcode.challenges.DailyChallenge;
import org.arlevin.adventofcode.utils.ChallengeUtils;

public class Day7 implements DailyChallenge {

  private final Logger log = LogManager.getLogger(this.getClass().getName());

  public boolean executeChallengeSuccessfully() {
    return leastCrabFuel(false) && leastCrabFuel(true);
  }

  private boolean leastCrabFuel(boolean fuelUsageSummation) {
    List<Integer> starts = Arrays
        .stream(ChallengeUtils.getResourceFileLines("y21/Day7").get(0).split(","))
        .map(Integer::parseInt).collect(Collectors.toList());

    // target the median to start
    int target = (int) starts.stream().mapToInt(i -> i).average().orElseThrow();
    int min = starts.stream().mapToInt(i -> i).min().orElseThrow();
    int max = starts.stream().mapToInt(i -> i).max().orElseThrow();
    int expense = howExpensiveToAlignTo(fuelUsageSummation, starts, target);

    while (true) {
      int oneUpExpense = howExpensiveToAlignTo(fuelUsageSummation, starts, Math.min(max, target + 1));
      int oneBelowExpense = howExpensiveToAlignTo(fuelUsageSummation, starts, Math.max(target - 1, min));
      if (oneBelowExpense < expense) {
        expense = oneBelowExpense;
        target = target - 1;
      } else if (oneUpExpense < expense) {
        expense = oneUpExpense;
        target = target + 1;
      } else {
        break;
      }
    }

    log.debug("min fuel expense: {} targeting: {}", expense, target);
    return true;
  }

  private int howExpensiveToAlignTo(boolean fuelUsageSummation, List<Integer> starts, int target) {
    int sum = 0;

    for (int start : starts) {
      if (fuelUsageSummation) {
        for (int i = 1; i <= Math.abs(start - target); i++) {
          sum += i;
        }
      } else {
        sum += Math.abs(start - target);
      }
    }

    return sum;
  }
}
