package cs3500.solored.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.view.hw02.RedGameView;
import cs3500.solored.view.hw02.SoloRedGameTextView;

/**
 * Class to represent a controller in the Red Seven game.
 * Used so game can be played with input and game state is outputted
 * to the user.
 */
public class SoloRedTextController implements RedGameController {
  final Scanner scan; // java style complained about private
  final Appendable ap; // java style complained about private
  private RedGameModel<?> model;
  private SoloRedGameTextView view;
  private boolean gameQuit;

  /**
   * constructor for Solo Red Controller that takes in readable
   * and appendable.
   *
   * @param rd input
   * @param ap output
   */
  public SoloRedTextController(Readable rd, Appendable ap) {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.ap = ap;
    this.scan = new Scanner(rd);
  }

  /**
   * Method to play the game with an input and run the Red Seven game
   * using the model.
   *
   * @param model       the model of the game.
   * @param deck        the deck of cards to use for the game.
   * @param shuffle     whether to shuffle the deck before game start or not.
   * @param numPalettes number of palettes in the game
   * @param handSize    the number of cards in for a full hand.
   * @param <C>         Type.
   */
  @Override
  public <C extends Card> void playGame(RedGameModel<C> model, List<C> deck, boolean shuffle,
                                        int numPalettes, int handSize) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    try {
      try {
        model.startGame(deck, shuffle, numPalettes, handSize);
        printGameState();
        while (!model.isGameOver() && scan.hasNext()) {
          handleUserInput(model);
          if (gameQuit) {
            break;
          }
        }
        if (!gameQuit) {
          transmitGameOverMessage(model);
        }
      } catch (IllegalStateException e) {
        throw new IllegalArgumentException("Invalid state occurred");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Input or output failed.");
    }
  }

  // purpose: Handles user input and conducts specific functions based
  // on the command given.
  private <C extends Card> void handleUserInput(RedGameModel<C> model) throws IOException {
    if (!scan.hasNext()) {
      return;
    }
    String cmd = scan.next();
    switch (cmd.toLowerCase()) {
      case "palette":
        paletteCase(model);
        break;
      case "canvas":
        canvasCase(model);
        break;
      case "q":
        transmitQuit();
        break;
      default:
        transmit("Invalid command. Try again.");
        break;
    }
  }

  // purpose: gets the value of the number inputted for a canvas card and
  // checks if the game needs to quit in the middle and calls the function
  // to use the card.
  private <C extends Card> void canvasCase(RedGameModel<C> model) throws IOException {
    int canvasCard = getNextInt();
    if (canvasCard == -1) {
      transmitQuit();
      return;
    }
    canvasBehavior(model, canvasCard);
  }

  // purpose: gets the value of the numbers inputted for a palette and card index form the hand
  // to play with to the palette and
  // checks if the game needs to quit in the middle and calls the function
  // to use those cards.
  private <C extends Card> void paletteCase(RedGameModel<C> model) throws IOException {
    int paletteIndx = getNextInt();
    if (paletteIndx == -1) {
      transmitQuit();
      return;
    }
    int cardIndx = getNextInt();
    if (cardIndx == -1) {
      transmitQuit();
      return;
    }
    paletteBehavior(model, paletteIndx, cardIndx);
  }

  // Calls the play to canvas method from the model and outputs an invalid
  // statement with a helpful message if the input given was invalid
  // by the exceptions from the model.
  private <C extends Card> void canvasBehavior(RedGameModel<C> model, int canvasCard)
          throws IOException {
    try {
      model.playToCanvas(canvasCard - 1);
      printGameState();
    } catch (IllegalArgumentException | IllegalStateException e) {
      transmit("Invalid move. Try again. " + e.getMessage());
      printGameState();
    }
  }

  // Calls the play to palette method from the model and outputs an invalid
  // statement with a helpful message if any of the input given was invalid
  // by the exceptions from the method in the model.
  private <C extends Card> void paletteBehavior(RedGameModel<C> model, int paletteIndx,
                                                int cardIndx) throws IOException {
    try {
      model.playToPalette(paletteIndx - 1, cardIndx - 1);
      if (!model.isGameOver()) {
        model.drawForHand();
      }
      printGameState();
    } catch (IllegalArgumentException | IllegalStateException e) {
      transmit("Invalid move. Try again. " + e.getMessage());
      printGameState();
    }
  }

  // purpose: prints the state of the game
  private void printGameState() throws IOException {
    RedGameView view = new SoloRedGameTextView(model, ap);
    view.render();
    transmit("\nNumber of cards in deck: " + model.numOfCardsInDeck());
  }

  // purpose: gets the value of the input , if the value
  // is invalid ex, a letter other than q, keeps re-reading
  // until a valid input is read or there are no inputs left
  // and an illegal argument is thrown.
  private int getNextInt() throws IOException {
    while (scan.hasNext()) {
      if (scan.hasNextInt()) {
        int input = scan.nextInt();
        if (input >= 0) {
          return input;
        }
      } else {
        String input = scan.next();
        if (input.equalsIgnoreCase("q")) {
          return -1;
        }
      }
    }
    throw new IllegalStateException("No input left");
  }

  // purpose: prints the game quit message including the state.
  private void transmitQuit() throws IOException {
    transmit("Game quit!");
    transmit("State of game when quit:");
    printGameState();
    gameQuit = true;
  }

  // purpose: append strings to the appendable with "\n"
  private void transmit(String message) throws IOException {
    this.ap.append(message).append("\n");
  }

  // purpose: prints the game over message with the result of the game.
  private <C extends Card> void transmitGameOverMessage(RedGameModel<C> model) throws IOException {
    if (model.isGameOver()) {
      if (model.isGameWon()) {
        transmit("Game won.");
      } else {
        transmit("Game lost.");
      }
      printGameState();
    }
  }
}
