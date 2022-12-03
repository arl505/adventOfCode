package org.arlevin.adventofcode.challenges.y21;

import static org.arlevin.adventofcode.utils.ChallengeUtils.getResourceFileLines;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arlevin.adventofcode.challenges.DailyChallenge;

public class Day5 implements DailyChallenge {

  private final Logger log = LogManager.getLogger(this.getClass().getName());

  public boolean executeChallengeSuccessfully() {
    return num_overlaps(false) && num_overlaps(true);
  }

  private boolean num_overlaps(boolean considerDiagonal) {
    List<String> lines = getResourceFileLines("y21/Day5");

    Map<Map.Entry<Integer, Integer>, Integer> map = new HashMap<>();

    for (String line : lines) {
      int x1 = Integer.parseInt(line.substring(0, line.indexOf(',')));
      int y1 = Integer.parseInt(line.substring(line.indexOf(',') + 1, line.indexOf(' ')));

      line = line.substring(line.indexOf("-> ") + 3);

      int x2 = Integer.parseInt(line.substring(0, line.indexOf(',')));
      int y2 = Integer.parseInt(line.substring(line.indexOf(',') + 1));

      if (considerDiagonal) {
        while (x1 != x2 || y1 != y2) {
          map.put(Map.entry(x1, y1), map.getOrDefault(Map.entry(x1, y1), 0) + 1);

          if (x1 < x2) {
            x1++;
          } else if (x1 != x2) {
            x1--;
          }

          if (y1 < y2) {
            y1++;
          } else if (y1 != y2) {
            y1--;
          }
        }
        map.put(Map.entry(x1, y1), map.getOrDefault(Map.entry(x1, y1), 0) + 1);
      } else {
        if (y1 == y2) {
          if (x1 > x2) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
          }

          for (; x1 <= x2; x1++) {
            map.put(Map.entry(x1, y1), map.getOrDefault(Map.entry(x1, y1), 0) + 1);
          }
        } else if (x1 == x2) {
          if (y1 > y2) {
            int temp = y1;
            y1 = y2;
            y2 = temp;
          }

          for (; y1 <= y2; y1++) {
            map.put(Map.entry(x1, y1), map.getOrDefault(Map.entry(x1, y1), 0) + 1);
          }
        }
      }
    }

    long result = map.entrySet().stream()
        .filter(mapEntry -> mapEntry.getValue() >= 2).count();

    log.debug("num overlaps: {}", result);
    return true;
  }
}
