package cs3500.solored.model.hw02;

import java.util.ArrayList;
import java.util.List;

/**
 * The blue rule class which checks if a palette is winning based on which
 * palette contains highest count of unique colors.
 */
public class BlueRule implements Rule {


  @Override
  // Loops through each palette and finds its count of unique colors and then compares
  // this count to the maximum count of unique colors and if it is larger then this palette
  // is winning.
  public int findWinningPalette(List<List<GameCard>> palettes) {
    int winningIndex = -1;
    int maxUniqueColorsInAllPalettes = 0;
    List<List<GameCard>> tiedPalettes = new ArrayList<>();
    List<Integer> tiedPaletteIndex = new ArrayList<>();
    for (int i = 0; i < palettes.size(); i++) {
      int uniqueColorCountInPalette = uniqueColorCount(palettes.get(i));
      if (uniqueColorCountInPalette > maxUniqueColorsInAllPalettes) {
        maxUniqueColorsInAllPalettes = uniqueColorCountInPalette;
        tiedPalettes.clear();
        tiedPaletteIndex.clear();
        tiedPalettes.add(palettes.get(i));
        tiedPaletteIndex.add(i);
        winningIndex = i;
        // testing code 
      }
      else if (uniqueColorCountInPalette == maxUniqueColorsInAllPalettes) {
        tiedPalettes.add(palettes.get(i));
        tiedPaletteIndex.add(i);
      }
      // testing code 

    }
    if (tiedPalettes.size() > 1) {
      int indx = breakTie(tiedPalettes);
      return tiedPaletteIndex.get(indx);
    }

    return winningIndex;
  }

  @Override
  public int breakTie(List<List<GameCard>> tiedpalettes) {
    RedRule redRule = new RedRule();
    return redRule.findWinningPalette(tiedpalettes);
  }

  // helper method
  // determines the count of unique colors for a palette by incrementing a count by 1
  // each time a different color occurs.
  private int uniqueColorCount(List<GameCard> palette) {
    int[] colorCounts = new int[5];
    for (GameCard card : palette) {
      String color = card.observeColor();
      if (color.equals("R")) {
        colorCounts[0]++;
      } else if (color.equals("O")) {
        colorCounts[1]++;
      } else if (color.equals("B")) {
        colorCounts[2]++;
      } else if (color.equals("I")) {
        colorCounts[3]++;
      } else if (color.equals("V")) {
        colorCounts[4]++;
      } else {
        throw new IllegalArgumentException("Invalid card color: " + color);
      }
    }
    int uniqueColorCount = 0;
    for (int count : colorCounts) {
      if (count >= 1) {
        uniqueColorCount++;
      }
    }
    return uniqueColorCount;
  }
}

