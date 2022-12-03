package org.arlevin.adventofcode;

import java.lang.reflect.Method;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arlevin.adventofcode.challenges.DailyChallenge;
import org.reflections.Reflections;

public class ChallengeExecutor {

  private final Logger log = LogManager.getLogger(this.getClass().getName());

  public void executeChallenges() {
    log.info("*** Starting Advent of Code challenge execution ***");

    ArrayList<Class<? extends DailyChallenge>> classes = loadClasses();
    try {
      executeClasses(classes);
    } catch (ReflectiveOperationException e) {
      log.error("Caught ReflectiveOperationException, won't execute anymore challenges", e);
    }

    log.info("*** Completed Advent of Code challenge execution ***");
  }

  private ArrayList<Class<? extends DailyChallenge>> loadClasses() {
    System.setProperty("org.slf4j.simpleLogger.log.org.reflections", "warn");
    Reflections reflections = new Reflections("org.arlevin.adventofcode.challenges");
    return new ArrayList<>(reflections.getSubTypesOf(DailyChallenge.class));
  }

  private void executeClasses(ArrayList<Class<? extends DailyChallenge>> challengeClasses)
      throws ReflectiveOperationException {

    for (Class<? extends DailyChallenge> challengeClass : challengeClasses) {
      Method challenge = challengeClass.getMethod("executeChallengeSuccessfully");
      boolean result = (boolean) challenge
          .invoke(challengeClass.getDeclaredConstructor().newInstance());
      if (!result) {
        log.warn("CHALLENGE FAILED: {}", challengeClass.getName());
      }
    }
  }
}
