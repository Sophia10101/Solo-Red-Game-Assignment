package cs3500.solored.model.hw02;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the Orange Rule. A palette is winning by the Orange rule :
 * The palette with the most of a single number wins.
 *
 */

public class OrangeRule implements Rule {
  // Finds the count of repeated numbers for each individual palette
  // and compares it to the maximum count of repeated numbers , if it
  // is greater this becomes the new maximum count and the index of the
  // palette with the maximum count is the winner and its index is returned.

  @Override
  public int findWinningPalette(List<List<GameCard>> palettes) {
    int winningIndex = -1;
    int maxRepeatedNumberInAllPalettes = 0;
    List<List<GameCard>> tiedPalettes = new ArrayList<>();
    List<Integer> tiedPaletteIndex = new ArrayList<>();
    for (int i = 0; i < palettes.size(); i++) {
      int sameNumberCountInPalette = getMaxSameNumberCount(palettes.get(i));
      if (sameNumberCountInPalette > maxRepeatedNumberInAllPalettes) {
        maxRepeatedNumberInAllPalettes = sameNumberCountInPalette;
        tiedPalettes.clear();
        tiedPaletteIndex.clear();
        tiedPalettes.add(palettes.get(i));
        tiedPaletteIndex.add(i);
        winningIndex = i;
      }
      else if (sameNumberCountInPalette == maxRepeatedNumberInAllPalettes) {
        tiedPalettes.add(palettes.get(i));
        tiedPaletteIndex.add(i);
      }

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

  // Creates an array of number 0 -7 that represents the possible number
  // values of cards in the game (ignoring 0) and increments the index
  // corresponding to the card number each time a card has that number.
  private int getMaxSameNumberCount(List<GameCard> palette) {
    int[] counts = new int[8];
    for (GameCard card : palette) {
      counts[card.observeNum()]++;
    }
    int maxCount = 0;
    for (int count : counts) {
      if (count > maxCount) {
        maxCount = count;
      }
    }
    return maxCount;
  }
}
