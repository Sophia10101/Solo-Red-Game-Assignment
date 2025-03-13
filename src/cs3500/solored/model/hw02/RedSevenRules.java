package cs3500.solored.model.hw02;

import java.util.ArrayList;
import java.util.List;

/**
 * Class defines the rules for determing a winning palette in the Red Seven game based on
 * a set of color rules.
 */
public class RedSevenRules {

  /**
   * Determines the winning palette in a list of a list of gamecards by checking its canvas
   * and applying the appropriate color rule to the palettes list.
   * @param palettes the list of palettes in the game.
   * @param canvas the canvas card that determines the winning rule.
   * @return list of booleans false for losing ones and true for winning ones.
   */
  public List<Boolean> whichIsWinningPalette(List<List<GameCard>> palettes, GameCard canvas) {
    Rule currentRule = getCanvasRule(canvas);
    List<Boolean> result = createResultsList(palettes.size());
    int winningIndx = currentRule.findWinningPalette(palettes);
    if (winningIndx >= 0 && winningIndx < palettes.size()) {
      result.set(winningIndx, true);
    }
    return result;
  }

  // return the rule of the canvas based on the color of the card.
  private Rule getCanvasRule(GameCard canvas) {
    if (canvas.observeColor().equals("R")) {
      return new RedRule();
    }
    if (canvas.observeColor().equals("O")) {
      return new OrangeRule();
    }
    if (canvas.observeColor().equals("B")) {
      return new BlueRule();
    }
    if (canvas.observeColor().equals("I")) {
      return new IndigoRule();
    }
    if (canvas.observeColor().equals("V")) {
      return new VioletRule();
    }
    else {
      throw new RuntimeException(canvas.observeColor() + " is not a valid color");
    }
  }

  // create a list of all false for use to check winning palette.
  private List<Boolean> createResultsList(int size) {
    List<Boolean> result = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      result.add(false);
    }
    return result;
  }

}
