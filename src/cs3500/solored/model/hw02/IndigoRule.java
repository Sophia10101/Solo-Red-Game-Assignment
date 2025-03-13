package cs3500.solored.model.hw02;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that dictates if a palette is winning by the Indigo rule.
 * (The palette with the cards that form the longest run wins.
 * A run is an unbroken increasing sequence of consecutive numbers after sorting).
 */

public class IndigoRule implements Rule {
  @Override
  public int findWinningPalette(List<List<GameCard>> palettes) {
    int winningIndex = -1;
    int maxRun = 0;
    List<List<GameCard>> tiedPalettes = new ArrayList<>();
    List<Integer> tiedPaletteIndexes = new ArrayList<>();
    for (int i = 0; i < palettes.size(); i++) {
      int countOfRun = getCountOfRun(palettes.get(i));
      if (countOfRun > maxRun) {
        maxRun = countOfRun;
        tiedPalettes.clear();
        tiedPaletteIndexes.clear();
        tiedPalettes.add(palettes.get(i));
        tiedPaletteIndexes.add(i);
        winningIndex = i;
      } else if (countOfRun == maxRun && countOfRun > 0) {
        tiedPalettes.add(palettes.get(i));
        tiedPaletteIndexes.add(i);
      }
    }
    if (maxRun == 0) {
      return -1;
    }
    if (tiedPalettes.size() > 1) {
      int winningTieIndex = breakTie(tiedPalettes);
      return tiedPaletteIndexes.get(winningTieIndex);
    }
    return winningIndex;
  }

  @Override
  public int breakTie(List<List<GameCard>> tiedPalettes) {
    RedRule redRule = new RedRule();
    return redRule.findWinningPalette(tiedPalettes);
  }

  // purpose: rerturns the count of the longest run.
  private int getCountOfRun(List<GameCard> palette) {
    if (palette.isEmpty()) {
      return 0;
    }
    List<GameCard> longestRun = getLongestRun(palette);
    return longestRun.size();
  }

  // purpose: finds the longest run.
  private List<GameCard> getLongestRun(List<GameCard> palette) {
    if (palette.isEmpty()) {
      return new ArrayList<>();
    }
    sortPaletteByNumberAscending(palette);
    List<List<GameCard>> allNumberRuns = new ArrayList<>();
    List<GameCard> currentRun = new ArrayList<>();
    currentRun.add(palette.get(0));
    createRunLists(palette, currentRun, allNumberRuns);
    if (currentRun.size() > 1) {
      allNumberRuns.add(currentRun);
    }
    if (allNumberRuns.isEmpty()) {
      return new ArrayList<>();
    }
    int maxRunLength = 0;
    List<List<GameCard>> longestNumRuns = new ArrayList<>();
    for (List<GameCard> run : allNumberRuns) {
      if (run.size() > maxRunLength) {
        maxRunLength = run.size();
        longestNumRuns.clear();
        longestNumRuns.add(run);
      } else if (run.size() == maxRunLength) {
        longestNumRuns.add(run);
      }
    }
    if (longestNumRuns.size() == 1) {
      return longestNumRuns.get(0);
    }
    return breakRunTie(longestNumRuns);
  }

  // purpose: creates list for the runs.
  private void createRunLists(List<GameCard> palette, List<GameCard> currentRun,
                              List<List<GameCard>> allNumberRuns) {
    for (int i = 1; i < palette.size(); i++) {
      if (palette.get(i).observeNum() == palette.get(i - 1).observeNum() + 1) {
        currentRun.add(palette.get(i));
      } else {
        if (currentRun.size() > 1) {
          allNumberRuns.add(new ArrayList<>(currentRun));
        }
        currentRun.clear();
        currentRun.add(palette.get(i));
      }
    }
  }

  private List<GameCard> breakRunTie(List<List<GameCard>> tiedRuns) {
    RedRule redRule = new RedRule();
    int winningRunIndex = redRule.findWinningPalette(tiedRuns);
    return tiedRuns.get(winningRunIndex);
  }

  // Sorts the palette in ascending order by number
  private void sortPaletteByNumberAscending(List<GameCard> palette) {
    for (int i = 0; i < palette.size() - 1; i++) {
      for (int j = i + 1; j < palette.size(); j++) {
        if (palette.get(i).observeNum() > palette.get(j).observeNum()) {
          GameCard temp = palette.get(i);
          palette.set(i, palette.get(j));
          palette.set(j, temp);
        }
      }
    }
  }
}
