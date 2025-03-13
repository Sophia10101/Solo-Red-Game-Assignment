package cs3500.solored.model.hw02;

import java.util.List;

/**
 * Interface that defines the structure for a rule in a red seven
 * game implementation.
 */
public interface Rule {
  /**
   * Finds the index in the list of palettes that is the winner in
   * the current game play.
   * @param palettes the list of palettes containing gamecards in the current game.
   * @return the index of the winning palette.
   */

  int findWinningPalette(List<List<GameCard>> palettes);

  // method to break a tie between two palettes

  /**
   * Finds the winner via the red rule between two palettes who have a tie under
   * the current rule.
   * @param palettes the list of palettes containing gamecards in the current game.
   * @return the index of the winning palette.
   */
  int breakTie(List<List<GameCard>> palettes);
}
