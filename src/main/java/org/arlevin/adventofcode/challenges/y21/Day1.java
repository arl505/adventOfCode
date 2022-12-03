package org.arlevin.adventofcode.challenges.y21;

import static org.arlevin.adventofcode.utils.ChallengeUtils.getResourceFileLines;

import java.util.List;
import java.util.stream.IntStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arlevin.adventofcode.challenges.DailyChallenge;

public class Day1 implements DailyChallenge {

  private final Logger log = LogManager.getLogger(this.getClass().getName());

  public boolean executeChallengeSuccessfully() {
    return slidingNumIncreases(1) && slidingNumIncreases(3);
  }

  private boolean slidingNumIncreases(int windowSize) {
    List<String> lines = getResourceFileLines("y21/Day1");

    int[] window = new int[windowSize];
    int prev = 0;
    int result = 0;

    for (int i = 0; i < lines.size(); i++) {
      addToWindow(window, windowSize, Integer.parseInt(lines.get(i)));
      int windowSum = IntStream.of(window).sum();
      if (windowSum > prev && i >= windowSize) {
        result++;
      }
      prev = windowSum;
    }

    log.debug("num increases: {}", result);
    return true;
  }

  private void addToWindow(int[] window, int windowSize, int val) {
    for (int i = windowSize - 1; i >= 0; i--) {
      if (i == 0) {
        window[i] = val;
      } else {
        window[i] = window[i - 1];
      }
    }
  }
}
