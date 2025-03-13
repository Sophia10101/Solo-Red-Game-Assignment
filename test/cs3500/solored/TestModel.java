package cs3500.solored;

import org.junit.Before;

import org.junit.Test;

import java.util.ArrayList;

import java.util.List;

import cs3500.solored.model.hw02.Color;
import cs3500.solored.model.hw02.GameCard;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedGameModel;
import cs3500.solored.view.hw02.SoloRedGameTextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Class to test cases of the SoloRedGame model methods and view, to
 * assert that the game logic is correct and works as intended.
 */
public class TestModel {
  // Test game methods
  ArrayList<GameCard> exampleDeck;
  RedGameModel<GameCard> model;


  protected RedGameModel<GameCard> makeModel() {
    return new SoloRedGameModel();
  }

  @Before

  public void toUse() {
    deck = new ArrayList<>();
    deck.add(new GameCard(Color.RED, 7));
    deck.add(new GameCard(Color.RED, 6));
    deck.add(new GameCard(Color.RED, 5));
    deck.add(new GameCard(Color.RED, 4));
    deck.add(new GameCard(Color.RED, 3));
    model = makeModel();
  }
  // Tests for play to palette method

  /**
   * Tests that an IllegalStateException is thrown if playToPalette is called
   * before the game has started.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayToPaletteGameNotStarted() {
    model.playToPalette(0, 0);
  }

  /**
   * Tests that an Illegal state exception is thrown when trying to play to
   * a palette after the game is already over.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayToPaletteGameOver() {
    List<GameCard> exDeck = new ArrayList<>();
    exDeck.add(new GameCard(Color.RED, 1));
    exDeck.add(new GameCard(Color.BLUE, 1));
    exDeck.add(new GameCard(Color.VIOLET, 5));
    exDeck.add(new GameCard(Color.VIOLET, 3));
    exDeck.add(new GameCard(Color.BLUE, 4));
    GameCard canvas = new GameCard(Color.BLUE, 2);
    SoloRedGameModel model = new SoloRedGameModel(canvas);
    model.startGame(exDeck, false, 3, 2);
    model.playToPalette(1, 1);
    model.playToPalette(2, 0);
  }

  /**
   * Tests an Illegal Argument Exception is thrown if trying to play to an
   * invalid palette.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPaletteIndxBelowZero() {
    model.startGame(deck, false, 3, 2);
    model.playToPalette(-1, 0);
  }

  /**
   * Tests an Illegal Argument Exception is thrown if trying to play to an
   * invalid palette.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPaletteIndxGreaterThanPalettesSize() {
    model.startGame(deck, false, 3, 2);
    model.playToPalette(4, 0);
  }

  /**
   * Tests an Illegal Argument Exception is thrown if trying to access
   * invalid card index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCardIndx() {
    model.startGame(deck, false, 3, 2);
    model.playToPalette(1, -1);
  }

  /**
   * Tests an Illegal Argument Exception is thrown if trying to access
   * invalid card index (greater than hand size).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCardIndx2() {
    model.startGame(deck, false, 3, 2);
    model.playToPalette(1, 2);
  }

  /**
   * Tests that an Exception is thrown if playToPalette is called
   * on a palette that is already the winning palette.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayToPaletteWinningPalette() {
    model.startGame(deck, false, 4, 2);
    model.playToPalette(1, 0);
    model.playToPalette(1, 0);
  }

  /**
   * Test to make sure PlayToPalette method correctly
   * adds a Gamecard to a palette.
   */
  @Test
  public void testPlayToPaletteBehavior() {
    ArrayList<GameCard> exDeck = new ArrayList<>();
    exDeck.add(new GameCard(Color.RED, 1));
    exDeck.add(new GameCard(Color.RED, 2));
    exDeck.add(new GameCard(Color.RED, 3));
    exDeck.add(new GameCard(Color.RED, 4));
    model.startGame(exDeck, false, 2, 2);
    model.playToPalette(0, 1);
    assertEquals(model.getPalette(0).get(1), new GameCard(Color.RED, 4));
  }

  // Tests for playToCanvas Method
  @Before
  public void game() {
    deck = new ArrayList<>();
    deck.add(new GameCard(Color.RED, 7));
    deck.add(new GameCard(Color.RED, 6));
    deck.add(new GameCard(Color.RED, 5));
    deck.add(new GameCard(Color.RED, 4));
    deck.add(new GameCard(Color.RED, 3));
    deck.add(new GameCard(Color.RED, 2));
    model = makeModel();
  }

  /**
   * Tests that an Exception is thrown if playToCanvas is called
   * before the game is started.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayToCanvasGameNotStarted() {
    model.playToCanvas(0);
  }

  /**
   * Tests that an Exception is thrown if playToCanvas is called
   * with a negative card index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToCanvasInvalidCardIndexNegative() {
    model.startGame(deck, false, 4, 1);
    model.playToCanvas(-1);
  }

  /**
   * Tests that an Exception is thrown if playToCanvas is called
   * with a card index >= the hands capacity.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToCanvasInvalidCardIndexTooHigh() {
    model.startGame(deck, false, 4, 1);
    model.playToCanvas(2);
  }

  /**
   * Tests that an Exception is thrown if playToCanvas is called
   * more than once in the same turn.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayToCanvasMultipleCalls() {
    model.startGame(deck, false, 4, 1);
    model.playToCanvas(0);
    model.playToCanvas(0);
  }

  /**
   * Tests that an Exception is thrown if playToCanvas is called
   * when there is only one card in the hand.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayToCanvasOneCardInHand() {
    model.startGame(deck, false, 4, 1);
    model.playToCanvas(0);
  }

  /**
   * Test that playToCanvas properly removes the card played from
   * the hand.
   */
  @Test
  public void testHandAfterPlayToCanvas() {
    model.startGame(deck, false, 4, 2);
    model.playToCanvas(0);
    assertEquals(model.getHand().size(), 1);
  }

  // Tests for draw for hand
  @Before
  public void setupForHand() {
    deck = new ArrayList<>();
    deck.add(new GameCard(Color.RED, 7));
    deck.add(new GameCard(Color.RED, 6));
    deck.add(new GameCard(Color.RED, 5));
    model = makeModel();
  }

  /**
   * Tests that an Exception is thrown when drawForHand is called
   * before the game has started.
   */
  @Test(expected = IllegalStateException.class)
  public void testDrawForHandBeforeGameStart() {
    model.drawForHand();
  }

  /**
   * Tests that an Exception is thrown when drawForHand is called
   * after the game is over.
   */
  @Test(expected = IllegalStateException.class)
  public void testDrawForHandAfterGameOver() {
    model.startGame(deck, false, 2, 2);
    model.playToPalette(0, 0);
    model.playToPalette(1, 0);
    model.drawForHand();
  }

  /**
   * Tests that cards are properly drawn from the deck to fill the hand.
   */
  @Test
  public void testDrawForHandFillsHand() {
    deck.add(new GameCard(Color.ORANGE, 2));
    deck.add(new GameCard(Color.ORANGE, 3));
    deck.add(new GameCard(Color.ORANGE, 4));
    model.startGame(deck, false, 2, 2);
    assertEquals(model.getHand().size(), 2);
    model.playToPalette(1, 0);
    model.drawForHand();
    assertEquals(model.getHand().size(), 2);
  }

  /**
   * Test startGame method with an invalid number of palettes.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidPalettes() {
    model.startGame(deck, false, 1, 7);
  }

  /**
   * Tests that Exception is thrown when the deck is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithNullDeck() {
    model.startGame(null, false, 2, 2);
  }

  /**
   * Tests that Exception is thrown when the game has already started.
   */
  @Test(expected = IllegalStateException.class)
  public void testStartGameWhenAlreadyStarted() {
    model.startGame(deck, false, 2, 2);
    model.startGame(deck, false, 2, 2);
  }

  /**
   * Test startGame method with too little cards in the deck.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameDeckTooSmall() {
    List<GameCard> tooSmallDeck = new ArrayList<>();
    tooSmallDeck.add(new GameCard(Color.RED, 1));
    model.startGame(tooSmallDeck, false, 4, 7);
  }

  /**
   * Tests that an IllegalArgumentException is thrown when # palettes < 2.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithInvalidNumPalettes() {
    model.startGame(deck, false, 1, 2);
  }

  /**
   * Tests that Exception is thrown when the deck contains null cards.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithNullCardInDeck() {
    deck.add(null);
    model.startGame(deck, false, 2, 2);
  }

  /**
   * Tests that an IllegalArgumentException is thrown when the deck contains non-unique cards.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithNonUniqueCards() {
    deck.add(new GameCard(Color.RED, 7));
    model.startGame(deck, false, 2, 2);
  }

  // test num of cards in deck
  @Test
  public void testNumofCardsAndPalettesInDeck() {
    List<GameCard> deck = new ArrayList<>();
    deck.add(new GameCard(Color.RED, 7));
    deck.add(new GameCard(Color.RED, 6));
    deck.add(new GameCard(Color.RED, 5));
    SoloRedGameModel model = new SoloRedGameModel();
    model.startGame(deck, false, 2, 1);
    assertEquals(model.numOfCardsInDeck(), 0);
    assertEquals(model.numPalettes(), 2);
  }

  @Before
  public void setup() {
    exampleDeck = new ArrayList<>();
    exampleDeck.add(new GameCard(Color.RED, 7));
    exampleDeck.add(new GameCard(Color.BLUE, 6));
    exampleDeck.add(new GameCard(Color.BLUE, 5));
    exampleDeck.add(new GameCard(Color.BLUE, 4));
    exampleDeck.add(new GameCard(Color.BLUE, 3));
    exampleDeck.add(new GameCard(Color.BLUE, 2));
    exampleDeck.add(new GameCard(Color.BLUE, 1));
    exampleDeck.add(new GameCard(Color.VIOLET, 2));
    exampleDeck.add(new GameCard(Color.VIOLET, 1));
    exampleDeck.add(new GameCard(Color.VIOLET, 5));
    model = makeModel();
  }

  List<GameCard> deck;

  @Before

  public void setup2() {
    deck = new ArrayList<>();
    for (int i = 1; i <= 7; i++) {
      deck.add(new GameCard(Color.RED, i));
      deck.add(new GameCard(Color.BLUE, i));
    }
  }

  /**
   * Test exception thrown when playing a card to an invalid palette index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToPaletteInvalidIndex() {
    model.startGame(deck, false, 4, 7);
    model.playToPalette(10, 0);
  }

  /**
   * Test gameOver method when the deck and hand are empty.
   */
  @Test
  public void testIsGameOverWhenDeckAndHandEmpty() {
    List<GameCard> exDeck = new ArrayList<>();
    for (int i = 1; i <= 5; i++) {
      exDeck.add(new GameCard(Color.RED, i));
    }
    model.startGame(exDeck, false, 4, 1);
    model.drawForHand();
    model.playToPalette(0, 0);
    assertTrue(model.isGameOver());
  }

  /**
   * Test gameIsOver when the deck and hand are empty.
   */
  @Test
  public void testGameisWon() {
    List<GameCard> exDeck = new ArrayList<>();
    for (int i = 1; i <= 5; i++) {
      exDeck.add(new GameCard(Color.RED, i));
    }
    model.startGame(exDeck, false, 4, 1);
    model.drawForHand();
    model.playToPalette(0, 0);
    assertTrue(model.isGameOver());
  }

  @Test
  public void testGameIsWonWithWinningPalette() {
    List<GameCard> deck = new ArrayList<>();
    deck.add(new GameCard(Color.BLUE, 3));
    deck.add(new GameCard(Color.BLUE, 5));
    deck.add(new GameCard(Color.BLUE, 4));
    deck.add(new GameCard(Color.BLUE, 6));
    model.startGame(deck, false, 3, 1);
    model.drawForHand();
    model.playToPalette(0, 0);
    assertEquals(0, model.winningPaletteIndex());
    assertTrue(model.isGameWon());
  }

  /**
   * Test this palette is already the winning palette exception.
   */
  @Test(expected = IllegalStateException.class)
  public void testGameIsWonWithWinningPalette2() {
    List<GameCard> deck = new ArrayList<>();
    deck.add(new GameCard(Color.BLUE, 6));
    deck.add(new GameCard(Color.BLUE, 5));
    deck.add(new GameCard(Color.BLUE, 4));
    deck.add(new GameCard(Color.BLUE, 3));
    model.startGame(deck, false, 3, 1);
    model.drawForHand();
    model.playToPalette(0, 0);
  }

  // Test isGameOver()

  /**
   * Test behavior of isGameOver method.
   */
  @Test
  public void testIsGameOver() {
    List<GameCard> exDeck = new ArrayList<>();
    exDeck.add(new GameCard(Color.RED, 1));
    exDeck.add(new GameCard(Color.BLUE, 1));
    exDeck.add(new GameCard(Color.VIOLET, 5));
    exDeck.add(new GameCard(Color.VIOLET, 3));
    SoloRedGameModel model = new SoloRedGameModel(new GameCard(Color.BLUE, 7));
    model.startGame(exDeck, false, 3, 1);
    model.playToPalette(1, 0);
    assertEquals(model.isGameOver(), true);
  }
  // Test getCanvas()

  /**
   * Test get canvas method returns the correct GameCard.
   */
  @Test
  public void testGetCanvas() {
    List<GameCard> deck = new ArrayList<>();
    deck.add(new GameCard(Color.BLUE, 5));
    deck.add(new GameCard(Color.BLUE, 6));
    deck.add(new GameCard(Color.BLUE, 4));
    RedGameModel model = makeModel();
    model.startGame(deck, false, 2, 1);
    assertEquals(model.getCanvas(), new GameCard(Color.RED, 7));
  }
  // Test is game won method

  /**
   * Tests that Exception is thrown if the game has not started.
   */
  @Test(expected = IllegalStateException.class)
  public void testIsGameWonBeforeGameStart() {
    model.isGameWon();
  }

  /**
   * Tests that Exception is thrown if the game is not over yet.
   */
  @Test(expected = IllegalStateException.class)
  public void testIsGameWonBeforeGameOver() {
    model.startGame(deck, false, 2, 2);
    model.isGameWon();
  }

  // Test method winningPaletteIndex
  @Test
  public void testWinningPaletteIndex() {
    List<GameCard> deck = new ArrayList<>();
    deck.add(new GameCard(Color.BLUE, 5));
    deck.add(new GameCard(Color.BLUE, 6));
    deck.add(new GameCard(Color.BLUE, 4));
    deck.add(new GameCard(Color.BLUE, 3));
    RedGameModel model = makeModel();
    model.startGame(deck, false, 2, 1);
    assertEquals(model.winningPaletteIndex(), 1);
  }

  /**
   * Test that the isGameWon() method correctly
   * changes the state of the game when it is won
   * and works as intended.
   */
  @Test
  public void testIsGameWon() {
    List<GameCard> deck = new ArrayList<>();
    deck.add(new GameCard(Color.BLUE, 3));
    deck.add(new GameCard(Color.BLUE, 5));
    deck.add(new GameCard(Color.RED, 7));
    model.startGame(deck, false, 2, 1);
    model.playToPalette(0, 0);
    assertEquals(model.isGameWon(), true);
  }
  /**
   * Test playToCanvas and isGameWon methods
   * work together as intended.
   */

  @Test

  public void testGameBehaviorTwoTurn() {
    List<GameCard> deck = new ArrayList<>();
    deck.add(new GameCard(Color.BLUE, 5));
    deck.add(new GameCard(Color.BLUE, 6));
    deck.add(new GameCard(Color.BLUE, 4));
    deck.add(new GameCard(Color.ORANGE, 3));
    RedGameModel model = makeModel();
    model.startGame(deck, false, 2, 2);
    SoloRedGameTextView view = new SoloRedGameTextView(model);
    model.playToCanvas(0);
    model.playToPalette(0, 0);
    assertEquals(model.winningPaletteIndex(), 0);
    assertTrue(model.isGameWon());
  }
  // Tests for the view

  @Test

  public void testViewBehavior() {
    RedGameModel gameModel = makeModel();
    List<GameCard> deck = new ArrayList<>();
    deck.add(new GameCard(Color.RED, 1));
    deck.add(new GameCard(Color.RED, 2));
    deck.add(new GameCard(Color.RED, 3));
    deck.add(new GameCard(Color.RED, 4));
    deck.add(new GameCard(Color.RED, 5));
    deck.add(new GameCard(Color.RED, 6));
    deck.add(new GameCard(Color.RED, 7));
    SoloRedGameTextView view = new SoloRedGameTextView(gameModel);
    gameModel.startGame(deck, false, 2, 3);
    assertEquals("Canvas: R\n"
            +
            "P1: R1\n"
            +
            "> P2: R2\n"
            +
            "Hand: R3 R4 R5",view.toString());

  }
  // Test one turn game error in handins

  @Test
  public void redTest() {
    List<GameCard> deck = new ArrayList<>();
    deck.add(new GameCard(Color.RED, 7));
    deck.add(new GameCard(Color.RED, 6));
    deck.add(new GameCard(Color.RED, 5));
    deck.add(new GameCard(Color.RED, 4));
    deck.add(new GameCard(Color.RED, 3));
    RedGameModel model = makeModel();
    SoloRedGameTextView view = new SoloRedGameTextView(model);
    model.startGame(deck, false, 4, 1);
    System.out.print(view);
    model.playToPalette(1, 0);
    assertFalse(model.isGameWon());
  }
}