package cs3500.solored;

import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw02.GameCard;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw04.RedGameCreator;

import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

import static cs3500.solored.model.hw04.RedGameCreator.GameType.ADVANCED;
import static cs3500.solored.model.hw04.RedGameCreator.GameType.BASIC;

/**
 * Class to run a game of Solo Red, either an advanced game or
 * a basic game with a chosen number of palettes and max hand size.
 */
public class SoloRed {
  /**
   * main class to run a type of Solo Red game.
   * @param args the game type.
   * @throws IllegalArgumentException for an invalid game type.
   */
  public static void main(String[] args) {
    if ((!args[0].equalsIgnoreCase("BASIC"))
            && (!args[0].equalsIgnoreCase("ADVANCED"))) {
      throw new IllegalArgumentException("Invalid game type: " + args[0]);
    }
    String typeOfGame = args[0];
    int p = 4;
    int h = 7;
    if (args.length >= 2 && args.length <= 3) {
      try {
        p = Integer.parseInt(args[1]);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid input for the number of palettes: " + args[1]);
      }
    }
    if (args.length == 3) {
      try {
        h = Integer.parseInt(args[2]);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid input for max hand size: " + args[2]);
      }
    }
    if (p < 2 || h <= 0) {
      System.out.println("Invalid number of palettes: " + p);
      System.out.println("Invalid max hand size: " + h);
      return;
    }
    RedGameModel<GameCard> model;
    switch (typeOfGame.toUpperCase()) {
      case "BASIC":
        model = new RedGameCreator().createGame(BASIC);
        break;
      case "ADVANCED":
        model = new RedGameCreator().createGame(ADVANCED);
        break;
      default:
        throw new IllegalArgumentException("Invalid game type: " + typeOfGame);
    }
    List<GameCard> deck = model.getAllCards();
    Collections.shuffle(deck);
    InputStreamReader input = new InputStreamReader(System.in);
    PrintStream output = new PrintStream(System.out);
    SoloRedTextController controller = new SoloRedTextController(input, output);
    controller.playGame(model, deck, true, p, h);
  }
}
