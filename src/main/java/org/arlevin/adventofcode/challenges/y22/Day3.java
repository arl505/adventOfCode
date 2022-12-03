package org.arlevin.adventofcode.challenges.y22;

import static org.arlevin.adventofcode.utils.ChallengeUtils.getResourceFileLines;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arlevin.adventofcode.challenges.DailyChallenge;

public class Day3 implements DailyChallenge {

  private final Logger log = LogManager.getLogger(this.getClass().getName());

  public boolean executeChallengeSuccessfully() {
    return prioritySum() && priorityOfAppearsThriceItems();
  }

  private boolean priorityOfAppearsThriceItems() {
    List<String> lines = getResourceFileLines("y22/Day3");

    int sum = 0;
    for (int i = 0; i < lines.size(); i = i + 3) {
      String line = lines.get(i);
      Set<Character> s1 = new HashSet<>();
      for (char c : line.toCharArray()) {
        s1.add(c);
      }

      line = lines.get(i + 1);
      Set<Character> sameSet = new HashSet<>();
      for (char c : line.toCharArray()) {
        if (s1.contains(c)) {
          sameSet.add(c);
        }
      }

      line = lines.get(i + 2);
      for (char c : line.toCharArray()) {
        if (sameSet.contains(c)) {
          sum += getPriorityForItem(c);
          break;
        }
      }
    }

    log.debug("rucksack priority sum: {}", sum);
    return true;
  }

  private boolean prioritySum() {
    List<String> lines = getResourceFileLines("y22/Day3");

    int sum = 0;
    for (String line : lines) {
      String compartment1 = line.substring(0, line.length() / 2);
      String compartment2 = line.substring(line.length() / 2);

      Set<Character> compartment1Set = new HashSet<>();
      Set<Character> sameSet = new HashSet<>();
      for (char character : compartment1.toCharArray()) {
        compartment1Set.add(character);
      }
      for (char character : compartment2.toCharArray()) {
        if (compartment1Set.contains(character) && !sameSet.contains(character)) {
          sameSet.add(character);
          sum += getPriorityForItem(character);
        }
      }
    }

    log.debug("rucksack priority sum: {}", sum);
    return true;
  }

  private int getPriorityForItem(char item) {
    return Character.isLowerCase(item)
        ? item - 96
        : item - 38;
  }
}
