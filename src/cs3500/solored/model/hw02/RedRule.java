package cs3500.solored.model.hw02;

import java.util.List;

/**
 * Class for the red rule in the game. A palette is winning by the red rule
 * if it contains the highest number out of all the cards in the palettes.
 */
public class RedRule implements Rule {


  /**
   * Determines the winning palette based on the highest card.
   * If multiple cards have the same number, their color is used to break the tie.
   *
   * @param palettes A list of palettes containing cards.
   * @return The index of the winning palette.
   */
  @Override
  public int findWinningPalette(List<List<GameCard>> palettes) {
    GameCard overallHighestCard = null;
    int winningIndex = -1;
    for (int i = 0; i < palettes.size(); i++) {
      List<GameCard> palette = palettes.get(i);
      GameCard highestCardInPalette = getHighestCard(palette);
      if (overallHighestCard == null
              ||
              compareCardsByRedRule(highestCardInPalette, overallHighestCard) > 0) {
        overallHighestCard = highestCardInPalette;
        winningIndex = i;
      }
    }

    return winningIndex;
  }

  @Override
  public int breakTie(List<List<GameCard>> palettes) {
    throw new IllegalStateException("RedRule never has ties");
  }

  /**
   * Finds the highest card in a palette. If multiple cards have the same number, it breaks
   * the tie based on color, favoring the card closer to red.
   *
   * @param palette The list of GameCards in the palette.
   * @return The highest GameCard based on number and color.
   */
  private GameCard getHighestCard(List<GameCard> palette) {
    GameCard highestCard = palette.get(0);
    for (GameCard card : palette) {
      if (compareCardsByRedRule(card, highestCard) > 0) {
        highestCard = card;
      }
    }
    return highestCard;
  }

  // Compares two GameCards first by number, and if they have the same number, by color
  // using the rainbow order (Red > Orange > Blue > Indigo > Violet).
  private int compareCardsByRedRule(GameCard c1, GameCard c2) {
    int num1 = c1.observeNum();
    int num2 = c2.observeNum();
    if (num1 > num2) {
      return 1;
    } else if (num1 < num2) {
      return -1;
    }
    return compareByColor(c1, c2);
  }
  // compares cards by their color to break a tie.

  private int compareByColor(GameCard c1, GameCard c2) {
    List<String> rainbowOrder = List.of("R", "O", "B", "I", "V");
    String color1 = c1.observeColor();
    String color2 = c2.observeColor();
    int indx1 = rainbowOrder.indexOf(color1);
    int indx2 = rainbowOrder.indexOf(color2);
    if (indx1 < indx2) {
      return 1;
    } else {
      return -1;
    }
  }
}
