package org.arlevin.adventofcode.challenges.y21;

import static org.arlevin.adventofcode.utils.ChallengeUtils.getResourceFileLines;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arlevin.adventofcode.challenges.DailyChallenge;

public class Day3 implements DailyChallenge {

  private final Logger log = LogManager.getLogger(this.getClass().getName());

  public boolean executeChallengeSuccessfully() {
    return decodeDiagnostic_pt1() && decodeDiagnostic_pt2();
  }

  private boolean decodeDiagnostic_pt1() {
    List<String> lines = getResourceFileLines("y21/Day3");

    int length = lines.get(0).length();
    int[] activeBitCounts = new int[length];

    for (String line : lines) {
      for (int i = 0; i < line.length(); i++) {
        if (line.charAt(i) == '1') {
          activeBitCounts[i]++;
        }
      }
    }

    StringBuilder gammaString = new StringBuilder();
    StringBuilder epsilonString = new StringBuilder();

    for (int activeBitCount : activeBitCounts) {
      if (activeBitCount > lines.size() / 2) {
        gammaString.append("1");
        epsilonString.append("0");
      } else {
        gammaString.append("0");
        epsilonString.append("1");
      }
    }

    int gamma = Integer.parseInt(gammaString.toString(), 2);
    int epsilon = Integer.parseInt(epsilonString.toString(), 2);
    log.debug("gamma: ({}) epsilon: ({}) multiplied: ({})", gamma, epsilon, gamma * epsilon);
    return true;
  }

  private boolean decodeDiagnostic_pt2() {
    List<String> o2lines = getResourceFileLines("y21/Day3");
    List<String> co2Lines = List.copyOf(o2lines);

    int index = 0;
    while (o2lines.size() > 1 || co2Lines.size() > 1) {
      if (o2lines.size() > 1) {
        List<String> remainingO2Lines = new ArrayList<>();
        boolean o2Keep1 = getActiveCountForIndex(o2lines, index) >= o2lines.size() / 2f;
        for (String line : o2lines) {
          if ((o2Keep1 && line.charAt(index) == '1') || (!o2Keep1 && line.charAt(index) == '0')) {
            remainingO2Lines.add(line);
          }
        }
        o2lines = remainingO2Lines;
      }


      if (co2Lines.size() > 1) {
        List<String> remainingCO2Lines = new ArrayList<>();
        boolean co2Keep1 = getActiveCountForIndex(co2Lines, index) < co2Lines.size() / 2f;
        for (String line : co2Lines) {
          if ((co2Keep1 && line.charAt(index) == '1') || (!co2Keep1 && line.charAt(index) == '0')) {
            remainingCO2Lines.add(line);
          }
        }
        co2Lines = remainingCO2Lines;
      }

      index++;
    }

    log.debug("binary o2: ({}) binary co2: ({}) o2 int: ({}) co2 int: ({}) multiplied: ({})",
        o2lines.get(0), co2Lines.get(0), Integer.parseInt(o2lines.get(0), 2),
        Integer.parseInt(co2Lines.get(0), 2),
        Integer.parseInt(o2lines.get(0), 2) * Integer.parseInt(co2Lines.get(0), 2));
    return true;
  }

  private int getActiveCountForIndex(List<String> lines, int index) {
    int result = 0;
    for (String line : lines) {
      if (line.charAt(index) == '1') {
        result++;
      }
    }
    return result;
  }
}
