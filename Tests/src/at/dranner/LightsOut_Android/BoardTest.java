package at.dranner.LightsOut_Android;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by Daniel on 02.06.2015.
 * Tests logic of the Board
 *
 * Indices of a 5x5 board:
 * +----------------+
 * |  0  1  2  3  4 |
 * |  5  6  7  8  9 |
 * | 10 11 12 13 14 |
 * | 15 16 17 18 19 |
 * | 20 21 22 23 24 |
 * +----------------+
 *
 */

public class BoardTest extends TestCase {

    private Board board;

    public void test_getNumberOfSwitchedOnLights_WhenBoardIsEmpty_ShouldReturnZero() throws Exception {
        Board underTest = new Board(new int[0]);
        Assert.assertEquals(0, underTest.getNumberOfSwitchedOnLights());
    }

    public void test_getNumberOfSwitchedOnLights_WhenBoardWasInitializedWithLightsOn() throws Exception {
        Board underTest = new Board(new int[]{3, 5, 9});
        Assert.assertEquals(3, underTest.getNumberOfSwitchedOnLights());
    }

    public void test_getLightState_WhenInitializedOn_ShouldReturnTrue() throws Exception {
        Board underTest = new Board(new int[]{0, 6, 12});
        Assert.assertEquals(true, underTest.getLightState(6));
    }

    public void test_getLightState_WhenInitializedOff_ShouldReturnFalse() throws Exception {
        Board underTest = new Board(new int[]{0, 6, 12});
        Assert.assertEquals(false, underTest.getLightState(1));
    }

    public void test_toggleLight_ShouldToggleNeighboursToo() throws Exception {
        Board underTest = new Board(new int[]{6, 11});
        underTest.toggleLight(11);

        Assert.assertEquals(false, underTest.getLightState(6));
        Assert.assertEquals(true, underTest.getLightState(10));
        Assert.assertEquals(false, underTest.getLightState(11));
        Assert.assertEquals(true, underTest.getLightState(12));
        Assert.assertEquals(true, underTest.getLightState(16));
    }

    public void test_toggleLight_OnEdge_ShouldToggleNeighboursToo() throws Exception {
        Board underTest = new Board(new int[]{6, 11});
        underTest.toggleLight(5);

        Assert.assertEquals(true, underTest.getLightState(0));
        Assert.assertEquals(true, underTest.getLightState(5));
        Assert.assertEquals(false, underTest.getLightState(6));
        Assert.assertEquals(true, underTest.getLightState(10));
    }

    public void test_toggleLight_WhenOnRightEdge_ShouldNotToggleLightOnLeftEdgeDueToWrapping() throws Exception {
        Board underTest = new Board(new int[]{6, 11});
        underTest.toggleLight(4);

        Assert.assertEquals(false, underTest.getLightState(5));
     }
}