package cs3500.solored.model.hw02;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Class to test various methods of the Red Seven Game.
 */
public class TestMethods {
  // Test Colors

  // Test for Red rule
  @Test
  public void testRedRule() {
    List<GameCard> p1 = new ArrayList<>();
    p1.add(new GameCard(Color.RED, 1));
    List<GameCard> p2 = new ArrayList<>();
    p2.add(new GameCard(Color.RED, 2));
    List<List<GameCard>> palettes = new ArrayList<>(Arrays.asList(p1, p2));
    RedRule rule = new RedRule();
    int winningIndex = rule.findWinningPalette(palettes);
    assertEquals(1, winningIndex);
  }

  @Test (expected = IllegalStateException.class)
  public void testBreakTieRedRule() {
    List<GameCard> p1 = new ArrayList<>();
    p1.add(new GameCard(Color.RED, 1));
    List<GameCard> p2 = new ArrayList<>();
    p2.add(new GameCard(Color.RED, 2));
    List<List<GameCard>> palettes = new ArrayList<>(Arrays.asList(p1, p2));
    RedRule rule = new RedRule();
    rule.breakTie(palettes);
  }

  // Test Orange Rule

  @Test
  public void testOrangeRuleNoTie() {
    List<GameCard> palette1 = new ArrayList<>();
    palette1.add(new GameCard(Color.RED, 1));
    palette1.add(new GameCard(Color.BLUE, 1));
    List<GameCard> palette2 = new ArrayList<>();
    palette2.add(new GameCard(Color.RED, 7));
    palette2.add(new GameCard(Color.ORANGE, 6));
    palette2.add(new GameCard(Color.BLUE, 5));
    List<List<GameCard>> palettes = Arrays.asList(palette1, palette2);
    OrangeRule orange = new OrangeRule();
    int winningIndex = orange.findWinningPalette(palettes);
    assertEquals(0, winningIndex);
  }

  // Test orange rule with a tie

  @Test
  public void testOrangeRuleTie() {
    List<GameCard> palette1 = new ArrayList<>();
    palette1.add(new GameCard(Color.RED, 1));
    palette1.add(new GameCard(Color.BLUE, 1));
    List<GameCard> palette2 = new ArrayList<>();
    palette2.add(new GameCard(Color.RED, 7));
    palette2.add(new GameCard(Color.ORANGE, 7));
    List<List<GameCard>> palettes = Arrays.asList(palette1, palette2);
    OrangeRule orange = new OrangeRule();
    int winningIndex = orange.findWinningPalette(palettes);
    assertEquals(1, winningIndex);
  }

  // Tests for blue rule
  @Test
  public void testBlueRuleNoTie() {
    List<GameCard> palette1 = new ArrayList<>();
    palette1.add(new GameCard(Color.RED, 1));
    palette1.add(new GameCard(Color.BLUE, 2));
    List<GameCard> palette2 = new ArrayList<>();
    palette2.add(new GameCard(Color.RED, 7));
    palette2.add(new GameCard(Color.ORANGE, 6));
    palette2.add(new GameCard(Color.BLUE, 5));
    List<List<GameCard>> palettes = Arrays.asList(palette1, palette2);
    BlueRule blueRule = new BlueRule();
    int winningIndex = blueRule.findWinningPalette(palettes);
    assertEquals(1, winningIndex);
  }

  @Test
  public void blueRuleTie() {
    List<GameCard> palette1 = new ArrayList<>();
    palette1.add(new GameCard(Color.RED, 1));
    palette1.add(new GameCard(Color.BLUE, 2));
    List<GameCard> palette2 = new ArrayList<>();
    palette2.add(new GameCard(Color.RED, 7));
    palette2.add(new GameCard(Color.ORANGE, 6));
    List<List<GameCard>> palettes = Arrays.asList(palette1, palette2);
    BlueRule blueRule = new BlueRule();
    int winningIndex = blueRule.findWinningPalette(palettes);
    assertEquals(1, winningIndex);
  }

  // Test Indigo Rule
  @Test
  public void testBasicIndigoRuleWin() {
    List<GameCard> palette1 = new ArrayList<>();
    palette1.add(new GameCard(Color.RED, 3));
    palette1.add(new GameCard(Color.BLUE, 2));
    palette1.add(new GameCard(Color.VIOLET, 4));
    List<GameCard> palette2 = new ArrayList<>();
    palette2.add(new GameCard(Color.RED, 5));
    palette2.add(new GameCard(Color.BLUE, 4));
    List<List<GameCard>> palettes = Arrays.asList(palette1, palette2);
    IndigoRule indigoRule = new IndigoRule();
    int winningIndex = indigoRule.findWinningPalette(palettes);
    assertEquals(0, winningIndex);
  }

  @Test
  public void testIndigoRuleRunTiebreak() {
    List<GameCard> palette1 = new ArrayList<>();
    palette1.add(new GameCard(Color.INDIGO, 1));
    palette1.add(new GameCard(Color.VIOLET, 2));
    palette1.add(new GameCard(Color.INDIGO, 5));
    palette1.add(new GameCard(Color.ORANGE, 6));
    List<GameCard> palette2 = new ArrayList<>();
    palette2.add(new GameCard(Color.RED, 5));
    palette2.add(new GameCard(Color.BLUE, 6));
    List<List<GameCard>> palettes = new ArrayList<>();
    palettes.add(palette1);
    palettes.add(palette2);
    IndigoRule indigoRule = new IndigoRule();
    int winningIndex = indigoRule.findWinningPalette(palettes);
    assertEquals(0, winningIndex);
  }

  @Test
  public void testIndigoRuleNoWinner() {
    List<GameCard> palette1 = new ArrayList<>();
    palette1.add(new GameCard(Color.INDIGO, 1));
    palette1.add(new GameCard(Color.VIOLET, 3));
    List<GameCard> palette2 = new ArrayList<>();
    palette2.add(new GameCard(Color.RED, 5));
    palette2.add(new GameCard(Color.BLUE, 7));
    List<List<GameCard>> palettes = Arrays.asList(palette1, palette2);
    IndigoRule indigoRule = new IndigoRule();
    int winningIndex = indigoRule.findWinningPalette(palettes);
    assertEquals(-1, winningIndex);
  }


  // Test violet rule
  @Test
  public void testVioletRule() {
    List<GameCard> palette1 = new ArrayList<>();
    palette1.add(new GameCard(Color.INDIGO, 1));
    palette1.add(new GameCard(Color.VIOLET, 2));
    palette1.add(new GameCard(Color.INDIGO, 5));
    palette1.add(new GameCard(Color.ORANGE, 6));
    List<GameCard> palette2 = new ArrayList<>();
    palette2.add(new GameCard(Color.RED, 5));
    palette2.add(new GameCard(Color.BLUE, 6));
    List<List<GameCard>> palettes = new ArrayList<>();
    palettes.add(palette1);
    palettes.add(palette2);
    VioletRule violetRule = new VioletRule();
    int winningIndex = violetRule.findWinningPalette(palettes);
    assertEquals(0, winningIndex);
  }

  @Test
  public void testVioletRuleNoWinner() {
    List<GameCard> palette1 = new ArrayList<>();
    palette1.add(new GameCard(Color.INDIGO, 6));
    List<GameCard> palette2 = new ArrayList<>();
    palette2.add(new GameCard(Color.RED, 5));
    List<List<GameCard>> palettes = new ArrayList<>(Arrays.asList(palette1, palette2));
    VioletRule violetRule = new VioletRule();
    int winningIndex = violetRule.findWinningPalette(palettes);
    assertEquals(-1, winningIndex);
  }

  @Test
  public void testVioletRuleTie() {
    List<GameCard> palette1 = new ArrayList<>();
    palette1.add(new GameCard(Color.INDIGO, 1));
    palette1.add(new GameCard(Color.VIOLET, 3));
    List<GameCard> palette2 = new ArrayList<>();
    palette2.add(new GameCard(Color.RED, 2));
    palette2.add(new GameCard(Color.BLUE, 3));
    palette2.add(new GameCard(Color.VIOLET, 4));
    List<List<GameCard>> palettes = Arrays.asList(palette1, palette2);
    VioletRule violetRule = new VioletRule();
    int winningIndex = violetRule.findWinningPalette(palettes);
    assertEquals(1, winningIndex);
  }

  // test GameCard methods behave as intended.
  @Test
  public void testGameCard() {
    GameCard canvasCard = new GameCard(Color.BLUE, 2);
    assertEquals(canvasCard.observeColor(),"B" );
    assertEquals(canvasCard.observeNum(),2 );
    assertTrue(canvasCard.equals(canvasCard));
    assertFalse(canvasCard.equals(new GameCard(Color.BLUE, 5)));
  }

  // test RedSevenRules

  @Test
  public void testWhichIsWinningPaletteReturnsCorrectBooleanList() {
    RedSevenRules rules = new RedSevenRules();
    List<GameCard> palette1 = Arrays.asList(
            new GameCard(Color.RED, 6),
            new GameCard(Color.BLUE, 5));
    List<GameCard> palette2 = Arrays.asList(
            new GameCard(Color.VIOLET, 7),
            new GameCard(Color.ORANGE, 3));
    List<List<GameCard>> palettes = Arrays.asList(palette1, palette2);
    GameCard redCanvas = new GameCard(Color.RED, 1);
    List<Boolean> result = rules.whichIsWinningPalette(palettes, redCanvas);
    assertEquals(result, Arrays.asList(false, true));
  }
  // test when two palettes are tied

  @Test
  public void testTiedWhichIsWinningPalette() {
    RedSevenRules rules = new RedSevenRules();
    List<GameCard> palette1 = Arrays.asList(
            new GameCard(Color.RED, 6),
            new GameCard(Color.BLUE, 5));
    List<GameCard> palette2 = Arrays.asList(
            new GameCard(Color.VIOLET, 4),
            new GameCard(Color.ORANGE, 5));
    List<List<GameCard>> palettes = Arrays.asList(palette1, palette2);
    GameCard canvas = new GameCard(Color.INDIGO, 1);
    List<Boolean> result = rules.whichIsWinningPalette(palettes, canvas);
    assertEquals(result, Arrays.asList(true, false));
  }

}

