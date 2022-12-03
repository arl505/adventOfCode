package org.arlevin.adventofcode.challenges.y21;

import static org.arlevin.adventofcode.utils.ChallengeUtils.getResourceFileLines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arlevin.adventofcode.challenges.DailyChallenge;

public class Day4 implements DailyChallenge {

  private final Logger log = LogManager.getLogger(this.getClass().getName());

  public boolean executeChallengeSuccessfully() {
    return firstBingoScore() && lastBingoScore();
  }

  private boolean lastBingoScore() {
    List<String> lines = getResourceFileLines("y21/Day4");

    List<Integer> drawnNumbers = Arrays.stream(lines.get(0).split(","))
        .map(Integer::parseInt).collect(Collectors.toList());
    List<List<List<Map.Entry<Integer, AtomicBoolean>>>> boards = loadBoards(lines);

    List<List<Map.Entry<Integer, AtomicBoolean>>> lastBoard = new ArrayList<>();
    int draw = drawnNumbers.get(0);
    List<List<List<Map.Entry<Integer, AtomicBoolean>>>> remainingBoards;
    for (int i = 0; i < drawnNumbers.size() && lastBoard.isEmpty(); i++) {
      draw = drawnNumbers.get(i);
      remainingBoards = new ArrayList<>();

      for (List<List<Entry<Integer, AtomicBoolean>>> board : boards) {
        for (List<Entry<Integer, AtomicBoolean>> row : board) {
          for (Entry<Integer, AtomicBoolean> entry : row) {
            if (entry.getKey() == draw) {
              entry.getValue().set(true);
            }
          }
        }
        if (!checkForBingoByRow(board) && !checkForBingoByColumn(board)) {
          remainingBoards.add(board);
        } else if (boards.size() == 1) {
          lastBoard = board;
        }
      }
      boards = remainingBoards;
    }

    int winningScore = lastBoard.stream().mapToInt(
        row -> row.stream().filter(entry -> !entry.getValue().get()).mapToInt(Entry::getKey).sum())
        .sum() * draw;

    log.debug("winning score {}", winningScore);
    return true;
  }

  private boolean firstBingoScore() {
    List<String> lines = getResourceFileLines("y21/Day4");

    List<Integer> drawnNumbers = Arrays.stream(lines.get(0).split(","))
        .map(Integer::parseInt).collect(Collectors.toList());
    List<List<List<Map.Entry<Integer, AtomicBoolean>>>> boards = loadBoards(lines);

    List<List<Map.Entry<Integer, AtomicBoolean>>> winningBoard = new ArrayList<>();

    int draw = drawnNumbers.get(0);
    for (int i = 0; i < drawnNumbers.size() && winningBoard.isEmpty(); i++) {
      draw = drawnNumbers.get(i);
      for (List<List<Entry<Integer, AtomicBoolean>>> board : boards) {
        for (List<Entry<Integer, AtomicBoolean>> row : board) {
          for (Entry<Integer, AtomicBoolean> entry : row) {
            if (entry.getKey() == draw) {
              entry.getValue().set(true);
            }
          }
        }
        if (checkForBingoByRow(board) || checkForBingoByColumn(board)) {
          winningBoard = board;
          break;
        }
      }
    }

    int winningScore = winningBoard.stream().mapToInt(
        row -> row.stream().filter(entry -> !entry.getValue().get()).mapToInt(Entry::getKey).sum())
        .sum() * draw;

    log.debug("winning score {}", winningScore);
    return true;
  }

  private boolean checkForBingoByColumn(List<List<Map.Entry<Integer, AtomicBoolean>>> board) {
    for (int i = 0; i < board.get(0).size(); i++) {
      boolean bingo = true;
      for (int j = 0; j < board.size() && bingo; j++) {
        if (!board.get(j).get(i).getValue().get()) {
          bingo = false;
        }
      }

      if (bingo) {
        return true;
      }
    }
    return false;
  }

  private boolean checkForBingoByRow(List<List<Map.Entry<Integer, AtomicBoolean>>> board) {
    for (List<Map.Entry<Integer, AtomicBoolean>> row : board) {
      boolean bingo = true;
      for (int i = 0; bingo && i < row.size(); i++) {
        if (!row.get(i).getValue().get()) {
          bingo = false;
        }
      }

      if (bingo) {
        return true;
      }
    }
    return false;
  }

  private List<List<List<Map.Entry<Integer, AtomicBoolean>>>> loadBoards(List<String> lines) {
    List<List<List<Map.Entry<Integer, AtomicBoolean>>>> boards = new ArrayList<>();
    List<List<Map.Entry<Integer, AtomicBoolean>>> board = new ArrayList<>();
    for (int i = 2; i < lines.size(); i++) {
      if (lines.get(i).equals("")) {
        boards.add(board);
        board = new ArrayList<>();
      } else {
        board.add(parseRow(lines.get(i)));
      }
    }

    if (!board.isEmpty()) {
      boards.add(board);
    }
    return boards;
  }

  private List<Map.Entry<Integer, AtomicBoolean>> parseRow(String row) {
    List<Map.Entry<Integer, AtomicBoolean>> result = new ArrayList<>();
    StringBuilder curr = new StringBuilder();

    for (char c : row.toCharArray()) {
      if (c == ' ') {
        if (!curr.toString().equals("")) {
          result.add(Map.entry(Integer.parseInt(curr.toString()), new AtomicBoolean(false)));
          curr = new StringBuilder();
        }
      } else {
        curr.append(c);
      }
    }

    if (!curr.toString().equals("")) {
      result.add(Map.entry(Integer.parseInt(curr.toString()), new AtomicBoolean(false)));
    }

    return result;
  }
}
