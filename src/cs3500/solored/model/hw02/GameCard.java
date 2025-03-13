package cs3500.solored.model.hw02;

import java.util.Objects;

/**
 * Represents a Card in the game Red Seven.
 */
public class GameCard implements Card, GameCardInfo {

  private Color color;
  private final int num;

  /**
   * Constructs a GameCard with a color and a number.
   *
   * @param color the color of the card
   * @param num   the number on the card
   * @throws IllegalArgumentException if the color of the card is not red, orange, blue, indigo,
   *                                  or violet
   * @throws IllegalArgumentException if the number on the card is not in the valid range
   *                                  (1-7 inclusive)
   */
  public GameCard(Color color, int num) {
    if (!(color.equals(Color.RED) || color.equals(Color.BLUE) || color.equals(Color.ORANGE)
            || color.equals(Color.INDIGO) || color.equals(Color.VIOLET))) {
      throw new IllegalArgumentException("Color must be Red, Orange, Blue, Indigo, or Violet");
    }
    if (num < 1 || num > 7) {
      throw new IllegalArgumentException("Color must be between 1 and & inclusive");
    }
    this.color = color;
    this.num = num;
  }

  public GameCard(GameCard other) {
    this(other.color, other.num);
  }

  /**
   * Finds the number associated with a GameCard.
   * @return the number of a GameCard
   */
  @Override
  public int observeNum() {
    return this.num;
  }

  /**
   * Finds the color associated with a GameCard.
   * @return the string color of a GameCard
   */
  @Override
  public String observeColor() {
    return new String(this.color.toString());
  }

  /**
   * Create a String representation of a Game Card
   * Blue 7 - > "B7".
   * @return a string representation of a GameCard
   */
  @Override
  public String toString() {
    return this.color.toString() + this.num;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.color, this.num);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    GameCard other = (GameCard) obj;
    return this.color.equals(other.color) && this.num == other.num;
  }


}

