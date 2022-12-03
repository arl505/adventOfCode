package org.arlevin.adventofcode.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChallengeUtils {

  public static List<String> getResourceFileLines(String name) {
    try {
      return Arrays.asList(new String(
          Thread.currentThread().getContextClassLoader().getResourceAsStream(name).readAllBytes())
          .split("\n"));
    } catch (IOException e) {
      return Collections.emptyList();
    }
  }
}
