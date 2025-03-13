package cs3500.solored.model.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class represents the model for the RedSeven card game. It implements the
 * {@link RedGameModel} interface and controls the state and operations of the game.
 * Red Seven requires a deck of n cards, n palettes, a player's hand of n cards, and a canvas
 * card that determines the current rule for how to win.
 */
public class SoloRedGameModel implements RedGameModel<GameCard> {

  // fields
  protected List<GameCard> deck;
  protected List<GameCard> hand;
  protected List<List<GameCard>> palettes;
  protected GameCard canvas;
  protected int handSize;
  protected boolean isGameStarted;
  protected boolean gameOver;
  protected boolean gameWon;
  protected List<GameCard> originalCards = new ArrayList<>();
  protected int canvasCalls = 0;
  protected Random rand = new Random();


  /**
   * Constructor that takes in no arguments to create a SoloRedGameModel
   * and initialize the cards and canvas.
   */
  public SoloRedGameModel() {
    initializeAllCards();
  }

  /**
   * Constructor that takes in a random argument (for shuffling) and creates a SoloRedGameModel
   * and initialize the cards and canvas.
   */
  public SoloRedGameModel(Random rand) {
    if (rand == null) {
      throw new IllegalArgumentException("Random object cannot be null");
    }
    this.rand = rand;
    initializeAllCards();
  }

  // Constructor for testing specific canvas color.

  // for testing only
  public SoloRedGameModel(GameCard canvas) {
    this.canvas = canvas;
    initializeAllCards();
  }

  /**
   * Play the given card from the hand to the losing palette chosen.
   * The card is removed from the hand and placed at the far right
   * end of the palette.
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
    if (!isGameStarted || gameOver) {
      throw new IllegalStateException("Game is not ongoing");
    }
    if (paletteIdx < 0 || paletteIdx >= palettes.size()) {
      throw new IllegalArgumentException("Invalid palette index");
    }
    if (cardIdxInHand < 0 || cardIdxInHand >= hand.size()) {
      throw new IllegalArgumentException("Invalid card index");
    }
    if (palettes.get(paletteIdx).equals(palettes.get(winningPaletteIndex()))) {
      throw new IllegalStateException("This palette is already the winning palette");
    }
    GameCard chosenCard = new GameCard(this.hand.get(cardIdxInHand));
    palettes.get(paletteIdx).add(chosenCard);
    hand.remove(cardIdxInHand);
    int playedTo = palettes.indexOf(palettes.get(paletteIdx));
    if (playedTo == winningPaletteIndex()) {
      gameWon = true;
      isGameOver();
    }
    else {
      gameWon = false;
      gameOver = true;
    }
  }

  /**
   * Play the given card from the hand to the canvas.
   * This changes the rules of the game for all palettes.
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
    if (!isGameStarted || gameOver) {
      throw new IllegalStateException("Game is not ongoing");
    }
    if (cardIdxInHand < 0 || cardIdxInHand >= hand.size()) {
      throw new IllegalArgumentException("Invalid card index");
    }
    if (canvasCalls >= 1) {
      throw new IllegalStateException("Canvas has already been played to");
    }
    if (hand.size() == 1) {
      throw new IllegalStateException("There is only one card in the hand");
    }
    canvas = new GameCard(this.hand.get(cardIdxInHand));
    hand.remove(cardIdxInHand);
    canvasCalls++;
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
    while (hand.size() < handSize && !deck.isEmpty()) {
      GameCard drawnCard = deck.remove(0);
      hand.add(drawnCard);
    }
    canvasCalls = 0;
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
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null");
    }
    if (isGameStarted || gameOver) {
      throw new IllegalStateException("Game has already started");
    }
    if (numPalettes < 2 || handSize <= 0) {
      throw new IllegalArgumentException("Invalid number of palettes or hand size");
    }
    if (deck.size() < handSize + numPalettes) {
      throw new IllegalArgumentException("Deck size is not large enough to set up the game");
    }
    if (deck.contains(null) || hasNonUniqueCard(deck)) {
      throw new IllegalArgumentException("Deck contains non-unique cards is null or empty");
    }
    this.deck = new ArrayList<>(deck);

    if (shuffle) {
      Collections.shuffle(this.deck, this.rand);
    }
    this.handSize = handSize;
    isGameStarted = true;
    this.canvasCalls = 0;
    // create the canvas
    canvas = new GameCard(Color.RED, 7);
    // Create the Palettes
    palettes = new ArrayList<>();
    for (int i = 0; i < numPalettes; i++) {
      List<GameCard> paletteCards = new ArrayList<>();
      paletteCards.add(this.deck.remove(0));
      palettes.add(paletteCards);
    }
    hand = new ArrayList<>();
    for (int i = 0; i < handSize; i++) {
      hand.add(this.deck.remove(0));
    }
  }


  /**
   * Determines whether a deck has only unique cards (no duplicates).
   * @param deck the deck of cards.
   * @return true if deck contains non-unique cards and false if it does not
   */
  protected boolean hasNonUniqueCard(List<GameCard> deck) {
    for (int i = 0; i < deck.size(); i++) {
      GameCard currentCard = deck.get(i);
      for (int j = i + 1; j < deck.size(); j++) {
        if (currentCard.equals(deck.get(j))) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Returns the number of cards remaining in the deck used in the game.
   * @return the number of cards in the deck
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int numOfCardsInDeck() {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not yet started");
    }
    return deck.size();
  }

  /**
   * Returns the number of palettes in the running game.
   * @return the number of palettes in the game
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int numPalettes() {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    return palettes.size();
  }


  /**
   * Returns the index of the winning palette in the game.
   * @return the 0-based index of the winning palette
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int winningPaletteIndex() {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    int winningIndx = -1;
    RedSevenRules rules = new RedSevenRules();
    List<Boolean> winners = rules.whichIsWinningPalette(palettes, canvas);
    for (int i = 0; i < winners.size(); i++) {
      if (winners.get(i)) {
        winningIndx = i;
      }
    }
    return winningIndx;
  }

  /**
   * Returns if the game is over as specified by the implementation.
   * @return true if the game has ended and false otherwise
   * @throws IllegalStateException if the game has not started
   */

  @Override
  public boolean isGameOver() {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has yet to start");
    }
    if (deck.isEmpty() && hand.isEmpty() || winningPaletteIndex() == -1) {
      gameOver = true;
    }
    return gameOver;
  }

  /**
   * Returns if the game is won by the player as specified by the implementation.
   * @return true if the game has been won or false if the game has not
   * @throws IllegalStateException if the game has not started or the game is not over
   */
  @Override
  public boolean isGameWon() {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (!gameOver) {
      throw new IllegalStateException("Game is not over yet");
    }
    return gameWon;
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
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    List<GameCard> handCopy = new ArrayList<>(hand);
    return handCopy;
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
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (paletteNum < 0 || paletteNum > palettes.size()) {
      throw new IllegalArgumentException("Invalid palette index");
    }
    return new ArrayList<>(palettes.get(paletteNum));
  }

  /**
   * Return the top card of the canvas.
   * Modifying this card has no effect on the game.
   * @return the top card of the canvas
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public GameCard getCanvas() {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not started at this time or is over");
    }
    return canvas;
  }

  // create all 35 cards possible.
  protected void initializeAllCards() {
    if (originalCards.isEmpty()) {
      for (int i = 1; i <= 7; i++) {
        originalCards.add(new GameCard(Color.RED, i));
        originalCards.add(new GameCard(Color.ORANGE, i));
        originalCards.add(new GameCard(Color.BLUE, i));
        originalCards.add(new GameCard(Color.INDIGO, i));
        originalCards.add(new GameCard(Color.VIOLET, i));
      }
    }
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
    return new ArrayList<>(originalCards);
  }
}
