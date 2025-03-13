package cs3500.solored.controller;

import java.util.List;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;

/**
 * The controller interface for the Red Seven Game.
 */
public interface RedGameController {

  /**
   * Plays the game of Red Seven using the provided model, deck, shuffle, number
   * of palettes and size of the hand. Handles the users input and output until the game is over.
   * @param model the model of the game.
   * @param deck the deck of cards to use for the game.
   * @param shuffle whether to shuffle the deck before game start or not.
   * @param handSize the number of cards in for a full hand.
   * @throws IllegalArgumentException if the provided model is null.
   * @throws IllegalStateException if the controller is unable to
   *         successfully receive input or transmit output.
   */
  <C extends Card> void playGame(RedGameModel<C> model, List<C> deck,
                                 boolean shuffle, int numPalettes, int handSize);
}
