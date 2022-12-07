package org.arlevin.adventofcode.challenges.y21;

import static org.arlevin.adventofcode.utils.ChallengeUtils.getResourceFileLines;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arlevin.adventofcode.challenges.DailyChallenge;

// I know its gross, the question was difficult to fully understand
// my solution below reflects how I arrived at an understanding: slowly piecing it together 1 digit at a time

public class Day8 implements DailyChallenge {

  private final Logger log = LogManager.getLogger(this.getClass().getName());
  private final Set<Character> all = new HashSet<>(
      Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g'));

  public boolean executeChallengeSuccessfully() {
    return howManyOfSomeNums() && getDisplaySum();
  }

  private boolean getDisplaySum() {
    List<String> lines = getResourceFileLines("y21/Day8");
    int sum = 0;
    for (String line : lines) {
      String[] inputSignals = line.substring(0, line.indexOf("|") - 1).split(" ");
      Character f = findFSignal(inputSignals);
      Character c = findCSignal(inputSignals, f);
      Character a = findASignal(inputSignals, f, c);

      Map.Entry<Character, Character> dAndE = findDAndESignals(inputSignals, c);
      Character d = dAndE.getKey();
      Character e = dAndE.getValue();
      Character b = findBSignal(inputSignals, e);
      Character g = findGSignal(a, b, c, d, e, f);

      StringBuilder sb = new StringBuilder();
      String[] inputs = line.substring(line.indexOf("|") + 2).split(" ");
      for (String input : inputs) {
        sb.append(getNumFromChars(input.chars()
            .mapToObj(letter -> (char) letter)
            .collect(Collectors.toSet()), a, b, c, d, e, f, g));
      }
      sum += Integer.parseInt(sb.toString());
    }

    log.debug("Sum: {}", sum);
    return true;
  }

  private char findGSignal(Character a, Character b, Character c, Character d, Character e,
      Character f) {
    Set<Character> allCopy = new HashSet<>(all);
    allCopy.remove(a);
    allCopy.remove(b);
    allCopy.remove(c);
    allCopy.remove(d);
    allCopy.remove(e);
    allCopy.remove(f);
    return allCopy.stream().findFirst().get();
  }

  private Character findBSignal(String[] parts, Character e) {
    Map<Character, Integer> countMap = new HashMap<>();
    for (String part : parts) {
      if (part.length() == 5) {
        for (char c : part.toCharArray()) {
          countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }
      }
    }

    for (Map.Entry<Character, Integer> entry : countMap.entrySet()) {
      if (entry.getValue() == 1 && entry.getKey() != e) {
        return entry.getKey();
      }
    }

    return null;
  }

  private Map.Entry<Character, Character> findDAndESignals(String[] parts, Character c) {
    Map<Character, Integer> dOrE = new HashMap<>();
    for (String part : parts) {
      if (part.length() == 6) {
        Set<Character> copy = new HashSet<>(all);
        for (char curr : part.toCharArray()) {
          copy.remove(curr);
        }
        if (copy.size() == 1) {
          char remaining = copy.stream().findFirst().get();
          if (remaining != c) {
            dOrE.put(remaining, 0);
          }
        }
      }
    }

    for (String part : parts) {
      if (part.length() == 5) {
        for (Character letter : part.toCharArray()) {
          if (dOrE.containsKey(letter)) {
            dOrE.put(letter, dOrE.get(letter) + 1);
          }
        }
      }
    }

    char d = ' ';
    char e = ' ';
    for (Map.Entry<Character, Integer> entry : dOrE.entrySet()) {
      if (entry.getValue() == 1) {
        e = entry.getKey();
      } else {
        d = entry.getKey();
      }
    }

    return Map.entry(d, e);
  }

  private Character findASignal(String[] parts, Character f, Character c) {
    for (String part : parts) {
      if (part.length() == 3) {
        for (char currChar : part.toCharArray()) {
          if (currChar != c && currChar != f) {
            return currChar;
          }
        }
      }
    }
    return null;
  }

  private Character findCSignal(String[] parts, Character f) {
    for (String part : parts) {
      if (part.length() == 2) {
        return part.charAt(0) == f
            ? part.charAt(1)
            : part.charAt(0);
      }
    }
    return null;
  }

  private Character findFSignal(String[] parts) {
    Map<Character, Integer> charCountMap = new HashMap<>();
    for (String part : parts) {
      for (char c : part.toCharArray()) {
        charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
      }
    }

    for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
      if (entry.getValue() == 9) {
        return entry.getKey();
      }
    }
    return null;
  }


  private Character getNumFromChars(Set<Character> chars, Character a, Character b, Character c,
      Character d, Character e,
      Character f, Character g) {
    if (chars.equals(new HashSet<>(Arrays.asList(a, b, c, e, f, g)))) {
      return '0';
    } else if (chars.equals(new HashSet<>(Arrays.asList(c, f)))) {
      return '1';
    } else if (chars.equals(new HashSet<>(Arrays.asList(a, c, d, e, g)))) {
      return '2';
    } else if (chars.equals(new HashSet<>(Arrays.asList(a, c, d, f, g)))) {
      return '3';
    } else if (chars.equals(new HashSet<>(Arrays.asList(b, c, d, f)))) {
      return '4';
    } else if (chars.equals(new HashSet<>(Arrays.asList(a, b, d, f, g)))) {
      return '5';
    } else if (chars.equals(new HashSet<>(Arrays.asList(a, b, d, e, f, g)))) {
      return '6';
    } else if (chars.equals(new HashSet<>(Arrays.asList(a, c, f)))) {
      return '7';
    } else if (chars.equals(new HashSet<>(Arrays.asList(a, b, c, d, e, f, g)))) {
      return '8';
    } else {
      return '9';
    }
  }

  private boolean howManyOfSomeNums() {
    log.debug("matches count {}", getResourceFileLines("y21/Day8").stream().mapToInt(line -> {
      String[] signals = line.substring(line.indexOf("| ") + 2).split(" ");
      return (int) Arrays.stream(signals)
          .filter(signal -> Arrays.asList(2, 3, 4, 7).contains(signal.length())).count();
    }).sum());

    return true;
  }
}
