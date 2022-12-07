package org.arlevin.adventofcode.challenges.y22;

import static org.arlevin.adventofcode.utils.ChallengeUtils.getResourceFileLines;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arlevin.adventofcode.challenges.DailyChallenge;

public class Day4 implements DailyChallenge {

  private final Logger log = LogManager.getLogger(this.getClass().getName());

  public boolean executeChallengeSuccessfully() {
    return howManyFullyContainedPairs() && howManyAtAllOverlappedPairs();
  }

  private boolean howManyFullyContainedPairs() {
    List<List<Integer>> assignments = loadAllAssignments(getResourceFileLines("y22/Day4"));

    int total = 0;
    for (int i = 0; i < assignments.size() - 1; i = i + 2) {
      List<Integer> assignment1 = assignments.get(i);
      List<Integer> assignment2 = assignments.get(i + 1);

      boolean twoWhollyContainsOne = true;
      for (int task : assignment1) {
        if (!assignment2.contains(task)) {
          twoWhollyContainsOne = false;
          break;
        }
      }

      boolean oneWhollyContainsTwo = true;
      if (!twoWhollyContainsOne) {
        for (int task : assignment2) {
          if (!assignment1.contains(task)) {
            oneWhollyContainsTwo = false;
            break;
          }
        }
      }

      total += oneWhollyContainsTwo || twoWhollyContainsOne ? 1 : 0;
    }
    log.debug("Found {} contained pairs", total);
    return true;
  }

  private boolean howManyAtAllOverlappedPairs() {
    List<List<Integer>> assignments = loadAllAssignments(getResourceFileLines("y22/Day4"));

    int total = 0;
    for (int i = 0; i < assignments.size() - 1; i = i + 2) {
      List<Integer> assignment1 = assignments.get(i);
      List<Integer> assignment2 = assignments.get(i + 1);

      boolean anyOverlap1 = false;
      for (int task : assignment1) {
        if (assignment2.contains(task)) {
          anyOverlap1 = true;
          break;
        }
      }

      boolean anyOverlap2 = false;
      if (!anyOverlap1) {
        for (int task : assignment2) {
          if (assignment1.contains(task)) {
            anyOverlap2 = true;
            break;
          }
        }
      }

      total += anyOverlap1 || anyOverlap2 ? 1 : 0;
    }
    log.debug("Found {} contained pairs", total);
    return true;
  }

  private List<List<Integer>> loadAllAssignments(List<String> lines) {
    List<List<Integer>> assignments = new ArrayList<>();
    for (String line : lines) {
      Integer start1 = Integer.parseInt(line.substring(0, line.indexOf("-")));
      Integer end1 = Integer.parseInt(line.substring(line.indexOf("-") + 1, line.indexOf(",")));
      line = line.substring(line.indexOf(",") + 1);
      Integer start2 = Integer.parseInt(line.substring(0, line.indexOf("-")));
      Integer end2 = Integer.parseInt(line.substring(line.indexOf("-") + 1));

      List<Integer> assignment1 = new ArrayList<>();
      for (; start1 <= end1; start1++) {
        assignment1.add(start1);
      }
      List<Integer> assignment2 = new ArrayList<>();
      for (; start2 <= end2; start2++) {
        assignment2.add(start2);
      }
      assignments.add(assignment1);
      assignments.add(assignment2);
    }
    return assignments;
  }
}
