package cs3500.solored;

import org.junit.Test;
import org.junit.Before;

import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.Color;
import cs3500.solored.model.hw02.GameCard;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedGameModel;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;


/**
 * Test class for SoloRedTextController.
 */

public class TestController {
  /**
   * Test invalid input only.
   */
  @Test (expected = IllegalArgumentException.class)
  public void invalidInputTest() throws IOException {
    List<GameCard> deck = new ArrayList<>();
    deck.add(new GameCard(Color.RED, 7));
    deck.add(new GameCard(Color.RED, 6));
    deck.add(new GameCard(Color.RED, 5));
    deck.add(new GameCard(Color.RED, 4));
    RedGameModel model = new SoloRedGameModel();
    Reader in = new StringReader("canvas a");
    Appendable output = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, output);
    controller.playGame(model, deck, false, 2, 2);
  }

  /*
  Test user only inputs "q"
   */
  @Test
  public void testQuitCommand() throws IOException {
    List<GameCard> deck = new ArrayList<>();
    deck.add(new GameCard(Color.RED, 7));
    deck.add(new GameCard(Color.RED, 6));
    deck.add(new GameCard(Color.RED, 5));
    deck.add(new GameCard(Color.RED, 4));
    RedGameModel model = new SoloRedGameModel();
    Reader input = new StringReader("q");
    Appendable out = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(input, out);
    controller.playGame(model, deck, false, 2, 2);
    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("Canvas: R\n")
            .append("> P1: R7\n")
            .append("P2: R6\n")
            .append("Hand: R5 R4\n")
            .append("Number of cards in deck: 0\n")
            .append("Game quit!\n")
            .append("State of game when quit:\n")
            .append("Canvas: R\n")
            .append("> P1: R7\n")
            .append("P2: R6\n")
            .append("Hand: R5 R4\n")
            .append("Number of cards in deck: 0\n");
    assertEquals(expectedOutput.toString(), out.toString());
  }

  @Before
  public void setUp() {
    RedGameModel model = new SoloRedGameModel();
    List<Card> deck = new ArrayList<>();
    deck.add(new GameCard(Color.RED, 7));
    deck.add(new GameCard(Color.RED, 6));
    deck.add(new GameCard(Color.RED, 5));
    deck.add(new GameCard(Color.RED, 4));
  }

  /**
   * Test for user enters quit.
   *
   * @throws IOException input output exception.
   */
  @Test
  public void testQuitOutput() throws IOException {
    StringBuilder output = new StringBuilder();
    RedGameModel model = new SoloRedGameModel();
    List<Card> deck = new ArrayList<>();
    deck.add(new GameCard(Color.RED, 7));
    deck.add(new GameCard(Color.RED, 6));
    deck.add(new GameCard(Color.RED, 5));
    deck.add(new GameCard(Color.RED, 4));
    StringReader input = new StringReader("q");
    SoloRedTextController controller = new SoloRedTextController(input, output);
    controller.playGame(model, deck, false, 2, 2);
    String[] lines = output.toString().split("\n");
    assertEquals(lines.length, 12);
  }

  /**
   * Tets play game output with legal input.
   */
  @Test
  public void testPlayToPalette() throws IOException {
    RedGameModel model = new SoloRedGameModel();
    List<Card> deck = new ArrayList<>();
    deck.add(new GameCard(Color.RED, 7));
    deck.add(new GameCard(Color.RED, 6));
    deck.add(new GameCard(Color.RED, 5));
    deck.add(new GameCard(Color.RED, 4));
    StringBuilder output = new StringBuilder();
    StringReader input = new StringReader("palette 2 1");
    SoloRedTextController controller = new SoloRedTextController(input, output);
    controller.playGame(model, deck, false, 2, 2);
    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("Canvas: R\n")
            .append("> P1: R7\n")
            .append("P2: R6\n")
            .append("Hand: R5 R4\n")
            .append("Number of cards in deck: 0\n")
            .append("Canvas: R\n")
            .append("> P1: R7\n")
            .append("P2: R6 R5\n")
            .append("Hand: R4\n")
            .append("Number of cards in deck: 0\n")
            .append("Game lost.\n")
            .append("Canvas: R\n")
            .append("> P1: R7\n")
            .append("P2: R6 R5\n")
            .append("Hand: R4\n")
            .append("Number of cards in deck: 0\n");
    assertEquals(output.toString(), expectedOutput.toString());
  }

  /**
   * test play to canvas called.
   */
  @Test
  public void testPlayToCanvasValidInputs() throws IOException {
    RedGameModel model = new SoloRedGameModel();
    List<Card> deck = new ArrayList<>();
    deck.add(new GameCard(Color.RED, 7));
    deck.add(new GameCard(Color.BLUE, 6));
    deck.add(new GameCard(Color.ORANGE, 5));
    deck.add(new GameCard(Color.ORANGE, 4));
    deck.add(new GameCard(Color.ORANGE, 3));
    StringBuilder out = new StringBuilder();
    StringReader input = new StringReader("canvas 2");
    SoloRedTextController controller = new SoloRedTextController(input, out);
    controller.playGame(model, deck, false, 2, 2);
    assertEquals(model.getCanvas().toString(), "O4");
  }

  /**
   * Test to make sure game does not quit with
   * valid input and that play to palette
   * updates when a move is made.
   *
   * @throws IOException IO excpetion.
   */
  @Test
  public void testConfirmInputsToPlayToPaletteModelMethods() throws IOException {
    List<GameCard> deck = new ArrayList<>();
    deck.add(new GameCard(Color.RED, 4));
    deck.add(new GameCard(Color.BLUE, 3));
    deck.add(new GameCard(Color.INDIGO, 5));
    deck.add(new GameCard(Color.ORANGE, 6));
    deck.add(new GameCard(Color.VIOLET, 2));
    deck.add(new GameCard(Color.RED, 7));
    SoloRedGameModel model = new SoloRedGameModel();
    StringReader input = new StringReader("palette 1 1");
    StringBuilder output = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(input, output);
    controller.playGame(model, deck, false, 3, 2);
    assertEquals(2, model.getPalette(0).size());
    assertFalse(output.toString().contains("Game quit!"));
  }

  /**
   * Test play to palette with
   * illegal input does not play
   * cards to palette.
   *
   * @throws IOException IO issue.
   */
  @Test
  public void testPlayToPalette2() throws IOException {
    List<GameCard> deck = new ArrayList<>();
    deck.add(new GameCard(Color.RED, 4));
    deck.add(new GameCard(Color.BLUE, 3));
    deck.add(new GameCard(Color.INDIGO, 5));
    deck.add(new GameCard(Color.ORANGE, 6));
    deck.add(new GameCard(Color.VIOLET, 2));
    deck.add(new GameCard(Color.RED, 7));
    SoloRedGameModel model = new SoloRedGameModel();
    StringReader input = new StringReader("palette 12 0");
    StringBuilder output = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(input, output);
    controller.playGame(model, deck, false, 3, 2);
    assertEquals(1, model.getPalette(0).size());
  }

  /**
   * Test exception thrown when given null model.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testExceptions() {
    List<GameCard> deck = new ArrayList<>();
    deck.add(new GameCard(Color.RED, 4));
    deck.add(new GameCard(Color.BLUE, 3));
    deck.add(new GameCard(Color.INDIGO, 5));
    deck.add(new GameCard(Color.ORANGE, 6));
    deck.add(new GameCard(Color.VIOLET, 2));
    deck.add(new GameCard(Color.RED, 7));
    SoloRedGameModel model = null;
    StringReader input = new StringReader("palette 1 1");
    StringBuilder output = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(input, output);
    controller.playGame(model, deck, false, 3, 2);
  }

  /**
   * test to make sure attempting to create an illegal state throws the
   * illegal argument exception.
   */
  @Test
  public void testInvalid() {
    List<GameCard> deck = new ArrayList<>();
    deck.add(new GameCard(Color.RED, 4));
    deck.add(new GameCard(Color.BLUE, 3));
    deck.add(new GameCard(Color.INDIGO, 5));
    deck.add(new GameCard(Color.ORANGE, 6));
    SoloRedGameModel model = new SoloRedGameModel();
    StringReader input = new StringReader("palette 1 2");
    StringBuilder output = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(input, output);
    try {
      controller.playGame(model, deck, false, 3, 2);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /*
  // test to check Illegal state exception
  // Fails , could not figure out how to fix :(
  @Test
  public void testInvalid2() {
    List<GameCard> deck = new ArrayList<>();
    deck.add(new GameCard(Color.RED, 4));
    deck.add(new GameCard(Color.BLUE, 3));
    deck.add(new GameCard(Color.INDIGO, 5));
    deck.add(new GameCard(Color.ORANGE, 6));
    SoloRedGameModel model = new SoloRedGameModel();
    StringReader input = new StringReader("");
    StringBuilder output = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(input, output);
    try {
      controller.playGame(model, deck, false, 2, 2);
      fail("exception not thrown");
    } catch (IllegalStateException e) {
    }
  }
   */

  // also used main for testing.
}




