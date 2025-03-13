package cs3500.solored.model.hw04;

import cs3500.solored.model.hw02.GameCard;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * Factory Class to determine the type of
 * game to play (advanced or basic).
 */
public class RedGameCreator {

  /**
   * Enum for a Game Type
   * BASIC or ADVANCED game.
   */

  public enum GameType {
    BASIC, ADVANCED
  }

  /**
   * returns a new game model based on the given type,
   * BASIC for the original SoloRedGameModel and
   * ADVANCED for the new AdvancedSoloRedGameModel.
   * @param type the type of game BASIC or ADVANCED
   * @return the game model corresponding with the type given.
   */
  public static RedGameModel<GameCard> createGame(GameType type) {
    switch (type) {
      case BASIC:
        return new SoloRedGameModel();
      case ADVANCED:
        return new AdvancedSoloRedGameModel();
      default:
        throw new IllegalArgumentException("Unrecognized gameType: " + type);
    }
  }
}
