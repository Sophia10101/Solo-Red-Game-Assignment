package cs3500.solored.model.hw02;

/**
 * Observations for a Card in the game of Red Seven.
 */
public interface GameCardInfo extends Card {
  /**
   * Finds the number associated with a GameCard.
   * @return the number of a GameCard
   */
  public int observeNum();

  /**
   * Finds the color associated with a GameCard.
   * @return the string color of a GameCard
   */
  public String observeColor();

}
