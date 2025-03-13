package cs3500.solored.model.hw02;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the violet rule in a red seven game. A palette is winning by the
 * violet rule if it has the most GameCards with a number below 4.
 */
public class VioletRule implements Rule {

  // Gets the count of the cards in a palette whos number is below 4 and compares it
  // to a max count holding the largest number of cards in a palette who have numbers below 4
  // and returns the winning palette based on this rule

  @Override
  public int findWinningPalette(List<List<GameCard>> palettes) {
    int winningIndex = -1;
    int maxCountBelowFour = 0;
    List<List<GameCard>> tiedPalettes = new ArrayList<>();
    List<Integer> tiedPaletteIndex = new ArrayList<>();
    for (int i = 0; i < palettes.size(); i++) {
      int countBelowFour = getCountBelowFour(palettes.get(i));
      if (countBelowFour > maxCountBelowFour) {
        maxCountBelowFour = countBelowFour;
        tiedPalettes.clear();
        tiedPaletteIndex.clear();
        tiedPalettes.add(palettes.get(i));
        tiedPaletteIndex.add(i);
        winningIndex = i;
      }
      else if (countBelowFour == maxCountBelowFour) {
        tiedPalettes.add(palettes.get(i));
        tiedPaletteIndex.add(i);
      }
    }
    if (maxCountBelowFour == 0) {
      return -1;
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

  // Checks if the number of each card in a palette is below four
  // and if this is true increments a count by 1
  private int getCountBelowFour(List<GameCard> palette) {
    int count = 0;
    for (GameCard card : palette) {
      if (card.observeNum() < 4) {
        count++;
      }
    }
    return count;
  }
}
