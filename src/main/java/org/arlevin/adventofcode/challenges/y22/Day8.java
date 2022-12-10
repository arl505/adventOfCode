package org.arlevin.adventofcode.challenges.y22;

import static org.arlevin.adventofcode.utils.ChallengeUtils.getResourceFileLines;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arlevin.adventofcode.challenges.DailyChallenge;

public class Day8 implements DailyChallenge {

  private final Logger log = LogManager.getLogger(this.getClass().getName());

  public boolean executeChallengeSuccessfully() {
    return countVisibleTrees() && getHighestScenicScore();
  }

  private boolean getHighestScenicScore() {
    List<String> lines = getResourceFileLines("y22/Day8");

    int highScore = 0;
    for (int y = 0; y < lines.size(); y++) {
      if (y == 0 || y == lines.size() - 1) {
        continue;
      }

      for (int x = 0; x < lines.get(y).length(); x++) {
        if (x == 0 || x == lines.get(y).length() - 1) {
          continue;
        }
        highScore = Math.max(getScenicScore(lines, x, y), highScore);
      }
    }

    log.debug("Highest scenic score: {}", highScore);
    return true;
  }

  private boolean countVisibleTrees() {
    List<String> lines = getResourceFileLines("y22/Day8");

    int visibleCount = 0;
    for (int y = 0; y < lines.size(); y++) {
      if (y == 0 || y == lines.size() - 1) {
        visibleCount += lines.get(y).length();
        continue;
      }

      for (int x = 0; x < lines.get(y).length(); x++) {
        if (x == 0 || x == lines.get(y).length() - 1) {
          visibleCount += 1;
          continue;
        }

        visibleCount += isTreeVisible(lines, x, y);
      }
    }

    log.debug("Count of visible trees: {}", visibleCount);
    return true;
  }

  private int isTreeVisible(List<String> lines, int x, int y) {
    int maxHeight = Character.getNumericValue(lines.get(y).charAt(x));

    // iterate horizontally thru current row
    char[] row = lines.get(y).toCharArray();
    boolean allShorterBefore = true;
    boolean allShortAfter = true;
    for (int i = 0; i < row.length; i++) {
      int curr = Character.getNumericValue(row[i]);
      if (curr >= maxHeight) {
        if (i < x) {
          allShorterBefore = false;
        } else if (i > x) {
          allShortAfter = false;
        }
      }
    }

    // iterate vertically over rows
    if (allShorterBefore || allShortAfter) {
      return 1;
    } else {
      allShorterBefore = true;
      allShortAfter = true;
      for (int i = 0; i < lines.size(); i++) {
        int curr = Character.getNumericValue(lines.get(i).charAt(x));
        if (curr >= maxHeight) {
          if (i < y) {
            allShorterBefore = false;
          } else if (i > y) {
            allShortAfter = false;
          }
        }
      }
    }
    return allShorterBefore || allShortAfter ? 1 : 0;
  }

  private int getScenicScore(List<String> lines, int x, int y) {
    int target = Character.getNumericValue(lines.get(y).charAt(x));

    int leftCount = 0;
    int rightCount = 0;
    int aboveCount = 0;
    int belowCount = 0;

    for (int i = x - 1; i >= 0; i--) {
      leftCount++;
      if (Character.getNumericValue(lines.get(y).charAt(i)) >= target) {
        break;
      }
    }

    for (int i = x + 1; i < lines.get(y).length(); i++) {
      rightCount++;
      if (Character.getNumericValue(lines.get(y).charAt(i)) >= target) {
        break;
      }
    }

    for (int i = y - 1; i >= 0; i--) {
      aboveCount++;
      if (Character.getNumericValue(lines.get(i).charAt(x)) >= target) {
        break;
      }
    }

    for (int i = y + 1; i < lines.size(); i++) {
      belowCount++;
      if (Character.getNumericValue(lines.get(i).charAt(x)) >= target) {
        break;
      }
    }

    return leftCount * rightCount * aboveCount * belowCount;
  }
}
