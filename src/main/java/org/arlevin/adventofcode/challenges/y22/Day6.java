package org.arlevin.adventofcode.challenges.y22;

import static org.arlevin.adventofcode.utils.ChallengeUtils.getResourceFileLines;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arlevin.adventofcode.challenges.DailyChallenge;

public class Day6 implements DailyChallenge {

  private final Logger log = LogManager.getLogger(this.getClass().getName());

  public boolean executeChallengeSuccessfully() {
    return distanceToStartOfPacket(4) && distanceToStartOfPacket(14) ;
  }

  private boolean distanceToStartOfPacket(int numUnique) {
    String line = getResourceFileLines("y22/Day6").get(0);
    int result = -1;
    String start = line.substring(0, numUnique);
    List<Character> currSet = start.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
    if (new HashSet<>(currSet).size() == currSet.size()) {
      result = numUnique;
    }

    for (int i = numUnique; result == -1 && i < line.length(); i++) {
      currSet.add(line.charAt(i));
      currSet.remove(0);
      if (new HashSet<>(currSet).size() == currSet.size()) {
        result = i + 1;
      }
    }

    log.debug("distance to start: {}", result);
    return true;
  }
}
