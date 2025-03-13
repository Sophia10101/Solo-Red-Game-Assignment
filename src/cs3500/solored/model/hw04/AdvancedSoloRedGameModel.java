package cs3500.solored.model.hw04;

import java.util.List;
import java.util.Random;

import cs3500.solored.model.hw02.GameCard;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * Class to create an advanced game of Solo Red with differing
 * drawing rules from the basic game.
 */
public class AdvancedSoloRedGameModel extends SoloRedGameModel implements RedGameModel<GameCard> {
  protected boolean playedToCanvas;
  protected int numOfCardPlayed = 0; // starting at 0, no card < 0 in  game
  protected boolean drawRule = false;

  /**
   * Constrcutor for an advanced game of
   * solo red that takes in no arguments.
   */
  public AdvancedSoloRedGameModel() {
    super();
  }

  /**
   * Constructor for an advanced game of Solo Red that takes in a
   * random argument for shuffling.
   * @param rand random object.
   */
  public AdvancedSoloRedGameModel(Random rand) {
    super(rand);
  }

  /**
   * Plays the given card from the hand to the palette chosen and
   * The card is removed from the hand and placed to
   * a palette, and the rule for how many cards to draw is set.
   *
   * @param paletteIdx    a 0-index number representing which palette to play to
   * @param cardIdxInHand a 0-index number representing the card to play from the hand
   * @throws IllegalStateException    if the game has not started or the game is over
   * @throws IllegalArgumentException if paletteIdx < 0 or more than the number of palettes
   * @throws IllegalArgumentException if cardIdxInHand < 0
   *                                  or greater/equal to the number of cards in hand
   * @throws IllegalStateException    if the palette referred to by paletteIdx is winning
   */
  @Override
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    super.playToPalette(paletteIdx, cardIdxInHand);
    if (playedToCanvas && numOfCardPlayed > palettes.get(winningPaletteIndex()).size()) {
      drawRule = true;
    }
    else {
      drawRule = false;
    }
    System.out.print(numOfCardPlayed);
  }

  /**
   * Plays the given card from the hand to the canvas.
   * Changes the rules of the game and
   * The method can only be called once per turn.
   *
   * @param cardIdxInHand a 0-index number representing the card to play from the hand
   * @throws IllegalStateException    if the game has not started or the game is over
   * @throws IllegalArgumentException if cardIdxInHand < 0
   *                                  or greater/equal to the number of cards in hand
   * @throws IllegalStateException    if this method was already called once in a given turn
   * @throws IllegalStateException    if there is exactly one card in hand
   */
  @Override
  public void playToCanvas(int cardIdxInHand) {
    super.playToCanvas(cardIdxInHand);
    playedToCanvas = true;
    numOfCardPlayed = canvas.observeNum();
  }

  /**
   * Draws cards from the deck until the hand is full
   * OR until the deck is empty, whichever occurs first. Newly drawn cards
   * are added to the end of the hand (far-right conventionally).
   * SIDE-EFFECT: Allows the player to play to the canvas again.
   *
   * @throws IllegalStateException if the game has not started or the game is over
   */
  @Override
  public void drawForHand() {
    if (!isGameStarted) {
      throw new IllegalStateException("Game is not ongoing");
    }
    advancedDraw(drawRule);
    reset();
  }

  /**
   * Helper method for draw for hand that draws for the hand
   * according to the new advanced rules in the game. If
   * drawRule is true, 2 cards are drawn. If draw rule is
   * false, 1 is drawn.
   * @param rule the boolean value determining what the rule is
   */
  protected void advancedDraw(boolean rule) {
    int drawingNum = 0;
    if (rule) {
      drawingNum = 2;
    }
    if (!rule) {
      drawingNum = 1;
    }
    for (int i = 0; i < drawingNum && hand.size() < handSize && !deck.isEmpty(); i++) {
      hand.add(deck.remove(0));
    }
  }

  /**
   * Starts the game with the given options. The deck given is used
   * to set up the palettes and hand. Modifying the deck given to this method
   * will not modify the game state in any way.
   *
   * @param deck        the cards used to set up and play the game
   * @param shuffle     whether the deck should be shuffled prior to setting up the game
   * @param numPalettes number of palettes in the game
   * @param handSize    the maximum number of cards allowed in the hand
   * @throws IllegalStateException    if the game has started or the game is over
   * @throws IllegalArgumentException if numPalettes < 2 or handSize <= 0
   * @throws IllegalArgumentException if deck's size is not large enough to setup the game
   * @throws IllegalArgumentException if deck has non-unique cards or null cards
   */
  @Override
  public void startGame(List<GameCard> deck, boolean shuffle, int numPalettes, int handSize) {
    super.startGame(deck, shuffle, numPalettes, handSize);
  }

  /**
   * Determines whether a deck has only unique cards (no duplicates).
   * @param deck the deck of cards.
   * @return true if deck contains non-unique cards and false if it does not
   */
  protected boolean hasNonUniqueCard(List<GameCard> deck) {
    return super.hasNonUniqueCard(deck);
  }

  /**
   * Returns the number of cards remaining in the deck used in the game.
   * @return the number of cards in the deck
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int numOfCardsInDeck() {
    return super.numOfCardsInDeck();
  }

  /**
   * Returns the number of palettes in the running game.
   * @return the number of palettes in the game
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int numPalettes() {
    return super.numPalettes();
  }

  /**
   * Returns the index of the winning palette in the game.
   * @return the 0-based index of the winning palette
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int winningPaletteIndex() {
    return super.winningPaletteIndex();
  }

  /**
   * Returns if the game is over as specified by the implementation.
   * @return true if the game has ended and false otherwise
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public boolean isGameOver() {
    return super.isGameOver();
  }

  /**
   * Returns if the game is won by the player as specified by the implementation.
   * @return true if the game has been won or false if the game has not
   * @throws IllegalStateException if the game has not started or the game is not over
   */
  @Override
  public boolean isGameWon() {
    return super.isGameWon();
  }

  /**
   * Returns a copy of the hand in the game. This means modifying the returned list
   * or the cards in the list has no effect on the game.
   * @return a new list containing the cards in the player's hand in the same order
   *         as in the current state of the game.
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public List<GameCard> getHand() {
    return super.getHand();
  }

  /**
   * Returns a copy of the specified palette. This means modifying the returned list
   * or the cards in the list has no effect on the game.
   * @param paletteNum 0-based index of a particular palette
   * @return a new list containing the cards in specified palette in the same order
   *         as in the current state of the game.
   * @throws IllegalStateException    if the game has not started
   * @throws IllegalArgumentException if paletteIdx < 0 or more than the number of palettes
   */
  @Override
  public List<GameCard> getPalette(int paletteNum) {
    return super.getPalette(paletteNum);
  }

  /**
   * Return the top card of the canvas.
   * Modifying this card has no effect on the game.
   * @return the top card of the canvas
   * @throws IllegalStateException if the game has not started or the game is over
   */
  @Override
  public GameCard getCanvas() {
    return super.getCanvas();
  }

  // create all 35 cards possible.
  protected void initializeAllCards() {
    super.initializeAllCards();
  }

  /**
   * Get a NEW list of all cards that can be used to play the game.
   * Editing this list should have no effect on the game itself.
   * Repeated calls to this method should produce a list of cards in the same order.
   * Modifying the cards in this list should have no effect on any returned list
   * or the game itself.
   *
   * @return a new list of all possible cards that can be used for the game
   */
  @Override
  public List<GameCard> getAllCards() {
    return super.getAllCards();
  }

  /**
   * Resets the state of the game so that the drawing rule
   * resets.
   */
  protected void reset() {
    playedToCanvas = false;
    numOfCardPlayed = 0;
    drawRule = false;
  }
}
