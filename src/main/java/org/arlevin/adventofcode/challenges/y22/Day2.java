package org.arlevin.adventofcode.challenges.y22;

import static org.arlevin.adventofcode.utils.ChallengeUtils.getResourceFileLines;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arlevin.adventofcode.challenges.DailyChallenge;

public class Day2 implements DailyChallenge {

  private final Logger log = LogManager.getLogger(this.getClass().getName());

  public boolean executeChallengeSuccessfully() {
    return calculateScoreFromGuide() && calculateScoreSecond();
  }

  // X means you need to lose, Y means you need to end the round in a draw, and Z means you need to win

  private boolean calculateScoreSecond() {
    List<String> lines = getResourceFileLines("y22/Day2");
    int sum = 0;
    for (String line : lines) {
      String theirMove = line.split(" ")[0];
      String needTo = line.split(" ")[1];

      if (needTo.equals("X")) {
        if (theirMove.equals("A")) {
          sum += 3;
        } else if (theirMove.equals("B")) {
          sum += 1;
        } else {
          sum += 2;
        }
      } else if (needTo.equals("Y")) {
        if (theirMove.equals("A")) {
          sum += 4;
        } else if (theirMove.equals("B")) {
          sum += 5;
        } else {
          sum += 6;
        }
      } else {
        if (theirMove.equals("A")) {
          sum += 8;
        } else if (theirMove.equals("B")) {
          sum += 9;
        } else {
          sum += 7;
        }
      }
    }

    log.debug("score: {}", sum);
    return true;
  }

  private boolean calculateScoreFromGuide() {
    int result2 = getResourceFileLines("y22/Day2")
        .stream()
        .mapToInt(line -> calculateScoreForHand(line.split(" ")[0], line.split(" ")[1]))
        .sum();

    log.debug("score: {}", result2);
    return true;
  }

  private int calculateScoreForHand(String theirMove, String myMove) {
    if (theirMove.equals("A")) {
      switch (myMove) {
        case "X":
          return 4;
        case "Y":
          return 8;
        default:
          return 3;
      }
    } else if (theirMove.equals("B")) {
      switch (myMove) {
        case "X":
          return 1;
        case "Y":
          return 5;
        default:
          return 9;
      }
    } else {
      switch (myMove) {
        case "X":
          return 7;
        case "Y":
          return 2;
        default:
          return 6;
      }
    }
  }
}
