package org.arlevin.adventofcode.challenges.y21;

import static org.arlevin.adventofcode.utils.ChallengeUtils.getResourceFileLines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arlevin.adventofcode.challenges.DailyChallenge;

public class Day9 implements DailyChallenge {

  private final Logger log = LogManager.getLogger(this.getClass().getName());

  public boolean executeChallengeSuccessfully() {
    return calculateLowPointsScoreSum() && calculateLargestBasinsScore();
  }

  private boolean calculateLargestBasinsScore() {
    List<String> lines = getResourceFileLines("y21/Day9");
    PriorityQueue<Integer> basins = new PriorityQueue<>(Collections.reverseOrder());
    List<Map.Entry<Integer, Integer>> lowPoints = getLowPoints(lines);

    for (Map.Entry<Integer, Integer> entry : lowPoints) {
      basins.add(explorePoint(lines, entry));
    }

    log.debug("product of 3 biggest basins {}", basins.remove() * basins.remove() * basins.remove());
    return true;
  }

  private int explorePoint(List<String> lines, Entry<Integer, Integer> start) {
    HashSet<Entry<Integer, Integer>> visited = new HashSet<>();
    int count = 0;

    // Pair <pair,int>: <<x,y>,prevVal>
    Queue<Entry<Entry<Integer, Integer>, Integer>> toVisit = new LinkedList<>();
    toVisit.add(Map.entry(start, -1));

    while (!toVisit.isEmpty()) {
      Entry<Entry<Integer, Integer>, Integer> curr = toVisit.remove();
      int x = curr.getKey().getKey();
      int y = curr.getKey().getValue();
      int prevVal = curr.getValue();
      int currVal = Character.getNumericValue(lines.get(y).charAt(x));
      if (currVal != 9 && currVal > prevVal && !visited.contains(curr.getKey())) {
        visited.add(curr.getKey());
        count++;
        if (x > 0) {
          toVisit.add(Map.entry(Map.entry(x - 1, y), currVal));
        }
        if (x < lines.get(y).length() - 1) {
          toVisit.add(Map.entry(Map.entry(x + 1, y), currVal));
        }
        if (y > 0) {
          toVisit.add(Map.entry(Map.entry(x, y - 1), currVal));
        }
        if (y < lines.size() - 1) {
          toVisit.add(Map.entry(Map.entry(x, y + 1), currVal));
        }
      }
    }

    return count;
  }

  private boolean calculateLowPointsScoreSum() {
    List<String> lines = getResourceFileLines("y21/Day9");

    int sum = getLowPoints(lines).stream()
        .mapToInt(entry -> 1 + Character
            .getNumericValue(lines.get(entry.getValue()).charAt(entry.getKey())))
        .sum();
    log.debug("Sum of low point scores: {}", sum);
    return true;
  }

  private List<Map.Entry<Integer, Integer>> getLowPoints(List<String> lines) {
    List<Map.Entry<Integer, Integer>> result = new ArrayList<>();

    for (int y = 0; y < lines.size(); y++) {
      for (int x = 0; x < lines.get(y).length(); x++) {
        boolean lowPoint = true;
        int curr = Character.getNumericValue(lines.get(y).charAt(x));
        try {
          if (Character.getNumericValue(lines.get(y).charAt(x - 1)) <= curr) {
            lowPoint = false;
          }
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
          if (Character.getNumericValue(lines.get(y).charAt(x + 1)) <= curr) {
            lowPoint = false;
          }
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
          if (Character.getNumericValue(lines.get(y + 1).charAt(x)) <= curr) {
            lowPoint = false;
          }
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
          if (Character.getNumericValue(lines.get(y - 1).charAt(x)) <= curr) {
            lowPoint = false;
          }
        } catch (IndexOutOfBoundsException ignored) {
        }

        if (lowPoint) {
          result.add(Map.entry(x, y));
        }
      }
    }

    return result;
  }
}
