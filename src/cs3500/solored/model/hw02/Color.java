package cs3500.solored.model.hw02;

/**
 * Color enum that creates a fixed set of Colors for GameCards that can be used in
 * the red seven gameplay.
 */
public enum Color {
  RED("R"), ORANGE("O"), BLUE("B"), INDIGO("I"), VIOLET("V");
  private final String display;

  Color(String display) {
    this.display = display;
  }

  /**
   * Abbreviates the Word of a Color to the first letter.
   * @return the first letter of the color
   */
  public String toString() {
    return display;
  }

}
