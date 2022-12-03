package org.arlevin.adventofcode.challenges.y21;

import static org.arlevin.adventofcode.utils.ChallengeUtils.getResourceFileLines;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arlevin.adventofcode.challenges.DailyChallenge;

public class Day2 implements DailyChallenge {

  private final Logger log = LogManager.getLogger(this.getClass().getName());

  public boolean executeChallengeSuccessfully() {
    return diveDeep() && diveDeepWithAim();
  }

  private boolean diveDeep() {
    List<String> lines = getResourceFileLines("y21/Day2");

    int horizontalPlace = 0;
    int depth = 0;

    for (String line : lines) {
      String[] parts = line.split(" ");
      String direction = parts[0];
      int amount = Integer.parseInt(parts[1]);

      if (direction.equals("forward")) {
        horizontalPlace += amount;
      }
      if (direction.equals("up")) {
        depth -= amount;
      }
      if (direction.equals("down")) {
        depth += amount;
      }
    }

    log.debug("Horizontal place: ({}) depth: ({}) multiplied ({})", horizontalPlace, depth,
        horizontalPlace * depth);
    return true;
  }

  private boolean diveDeepWithAim() {
    List<String> lines = getResourceFileLines("y21/Day2");

    int horizontalPlace = 0;
    int depth = 0;
    int aim = 0;

    for (String line : lines) {
      String[] parts = line.split(" ");
      String direction = parts[0];
      int amount = Integer.parseInt(parts[1]);

      if (direction.equals("forward")) {
        horizontalPlace += amount;
        depth += aim * amount;
      }
      if (direction.equals("up")) {
        aim -= amount;
      }
      if (direction.equals("down")) {
        aim += amount;
      }
    }

    log.debug("Horizontal place: ({}) depth: ({}) multiplied ({})", horizontalPlace, depth,
        horizontalPlace * depth);
    return true;
  }
}
