package org.arlevin.adventofcode.challenges.y22;

import static org.arlevin.adventofcode.utils.ChallengeUtils.getResourceFileLines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arlevin.adventofcode.challenges.DailyChallenge;

public class Day5 implements DailyChallenge {

  private final Logger log = LogManager.getLogger(this.getClass().getName());

  public boolean executeChallengeSuccessfully() {
    return getStackTops(false) && getStackTops(true);
  }

  private boolean getStackTops(boolean moveInPlace) {
    List<String> lines = getResourceFileLines("y22/Day5");
    Map.Entry<Integer, Map<Integer, Stack<Character>>> stacksAndStart = getStacks(lines);
    Map<Integer, Stack<Character>> stacks = stacksAndStart.getValue();

    for (int i = stacksAndStart.getKey(); i < lines.size(); i++) {
      String line = lines.get(i);
      String[] parts = line.split(" ");
      int howMany = Integer.parseInt(parts[1]);
      int from = Integer.parseInt(parts[3]);
      int to = Integer.parseInt(parts[5]);
      if (moveInPlace) {
        List<Character> toMove = new ArrayList<>();
        for (int iterations = 0; iterations < howMany; iterations++) {
          toMove.add(stacks.get(from).pop());
        }
        for (int count = toMove.size() - 1; count >= 0; count--) {
          stacks.get(to).add(toMove.get(count));
        }
      } else {
        for (int iterations = 0; iterations < howMany; iterations++) {
          stacks.get(to).add(stacks.get(from).pop());
        }
      }
    }

    StringBuilder sb = new StringBuilder();
    for (Entry<Integer, Stack<Character>> entries : stacks.entrySet()) {
      sb.append(entries.getValue().pop());
    }

    log.debug("Stack tops: {}", sb);
    return true;
  }

  private Map.Entry<Integer, Map<Integer, Stack<Character>>> getStacks(List<String> lines) {
    Map<Integer, List<Character>> mapOfLists = new HashMap<>();
    boolean foundEnd = false;
    int lineCount = 0;
    for (; !foundEnd && lineCount < lines.size(); lineCount++) {
      String line = lines.get(lineCount);
      for (int i = 0; !foundEnd && i < line.length(); i++) {
        char c = line.charAt(i);
        if (Character.isDigit(c)) {
          foundEnd = true;
        } else if (Character.isAlphabetic(c)) {
          int index = ((i - 1) / 4) + 1;
          List<Character> stack = mapOfLists.getOrDefault(index, new Stack<>());
          stack.add(c);
          mapOfLists.put(index, stack);
        }
      }
    }

    Map<Integer, Stack<Character>> result = new HashMap<>();
    for (Map.Entry<Integer, List<Character>> entry : mapOfLists.entrySet()) {
      Stack<Character> stack = new Stack<>();
      List<Character> list = entry.getValue();
      Collections.reverse(list);
      stack.addAll(list);
      result.put(entry.getKey(), stack);
    }
    return Map.entry(lineCount + 1, result);
  }
}
