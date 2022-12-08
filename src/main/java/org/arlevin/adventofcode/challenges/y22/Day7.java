package org.arlevin.adventofcode.challenges.y22;

import static org.arlevin.adventofcode.utils.ChallengeUtils.getResourceFileLines;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arlevin.adventofcode.challenges.DailyChallenge;

public class Day7 implements DailyChallenge {

  private final Logger log = LogManager.getLogger(this.getClass().getName());

  public boolean executeChallengeSuccessfully() {
    return sumDirectoriesSizedUnder100000() && getDirSizeToBeDeleted();
  }

  private boolean getDirSizeToBeDeleted() {
    Map<Integer, Map<String, Integer>> dirSizesByDepth = loadDirSizesByDepth();
    PriorityQueue<Integer> dirSizes = new PriorityQueue<>();
    dirSizesByDepth.values().forEach(e -> dirSizes.addAll(e.values()));
    int target = 30000000 - (70000000 - dirSizesByDepth.get(1).get("/"));
    int closest = 0;
    while (!dirSizes.isEmpty()) {
      closest = dirSizes.remove();
      if (closest >= target) {
        break;
      }
    }

    log.debug("Dir size to be deleted: {}", closest);
    return true;
  }

  private boolean sumDirectoriesSizedUnder100000() {
    int sum = loadDirSizesByDepth().values().stream()
        .mapToInt(dir -> dir.values().stream()
            .filter(size -> size <= 100000)
            .mapToInt(i -> i)
            .sum())
        .sum();
    log.debug("Sum of directory sizes under 100000: {}", sum);
    return true;
  }

  private Map<Integer, Map<String, Integer>> loadDirSizesByDepth() {
    // calculates dir sizes based on ls's without factoring in children, mapped by depth
    List<String> lines = getResourceFileLines("y22/Day7");
    int maxDepth = 1;
    String currDirectory = "";
    Map<Integer, Map<String, Integer>> dirSizesByDepth = new HashMap<>();
    for (String line : lines) {
      if (line.charAt(0) == '$' && line.charAt(2) == 'c') {
        String nextDir = line.substring(line.indexOf("cd ") + 3);
        if (nextDir.equals("..")) {
          currDirectory = currDirectory.substring(0, currDirectory.lastIndexOf("/"));
        } else if (nextDir.equals("/")) {
          currDirectory = "/";
        } else {
          if (!currDirectory.endsWith("/")) {
            currDirectory += "/";
          }
          currDirectory += nextDir;
        }
      } else if (Character.isDigit(line.charAt(0))) {
        int size = Integer.parseInt(line.split(" ")[0]);
        int depth = Math.max(1, currDirectory.split("/").length);
        maxDepth = Math.max(maxDepth, depth);
        Map<String, Integer> dirsMapForDepthN = dirSizesByDepth
            .getOrDefault(depth, new HashMap<>());
        dirsMapForDepthN.put(currDirectory, dirsMapForDepthN.getOrDefault(currDirectory, 0) + size);
        dirSizesByDepth.put(depth, dirsMapForDepthN);
      }
    }

    // run summations over parent/child dirs
    for (int i = maxDepth; i > 0; i--) {
      Map<String, Integer> dirs = dirSizesByDepth.get(i);
      for (Map.Entry<String, Integer> dir : dirs.entrySet()) {
        String dirName = dir.getKey();
        int dirSize = dir.getValue();
        String parentDirectory = dirName.substring(0, Math.max(1, dirName.lastIndexOf("/")));
        Map<String, Integer> parentDirs = dirSizesByDepth.getOrDefault(i - 1, new HashMap<>());
        parentDirs.put(parentDirectory, parentDirs.getOrDefault(parentDirectory, 0) + dirSize);
        dirSizesByDepth.put(i - 1, parentDirs);
      }
    }

    return dirSizesByDepth;
  }
}
