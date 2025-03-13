package cs3500.solored.view.hw02;

import java.io.IOException;

import cs3500.solored.model.hw02.RedGameModel;

/**
 * Class that implements the view for a Red Seven Game scene. The view includes
 * the canvas, palettes, hand, and identifies the current winning palette.
 */
public class SoloRedGameTextView implements RedGameView {
  private final RedGameModel<?> model;
  private Appendable output;

  /**
   * Constructor for View.
   * @param model game model.
   */
  public SoloRedGameTextView(RedGameModel<?> model) {

    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
  }

  /**
   * Constructor for view that takes in Appendable as arg.
   * @param model game model.
   * @param output output to user.
   */
  public SoloRedGameTextView(RedGameModel<?> model, Appendable output) {
    if (model == null || output == null) {
      throw new IllegalArgumentException("Model and Appendable cannot be null");
    }
    this.model = model;
    this.output = output;
  }


  @Override
  public String toString() {
    String result = "Canvas: " + model.getCanvas().toString().charAt(0) + "\n";
    int winningPaletteIndex = model.winningPaletteIndex();
    for (int i = 0 ; i < model.numPalettes(); i ++) {
      if (i == winningPaletteIndex) {
        result += "> ";
      }
      result += "P" + (i + 1) + ": ";
      if (!model.getPalette(i).isEmpty()) {
        for (Object c: model.getPalette(i)) {
          result += c.toString() + " ";
        }
        result = result.trim();
      }
      result += "\n";
    }
    result += "Hand: ";
    for (int i = 0; i < model.getHand().size(); i++) {
      result += model.getHand().get(i).toString() + " ";
    }
    if (!(model.getHand().isEmpty())) {
      result = result.trim();
    }
    return result;
  }

  @Override
  public void render() throws IOException {
    if (output != null) {
      output.append(this.toString());
    }
  }
}


