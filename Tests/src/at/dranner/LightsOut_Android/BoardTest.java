package at.dranner.LightsOut_Android;

import at.dranner.LightsOut_Android.Board;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by Daniel on 02.06.2015.
 * Tests logic of the Board
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
        Board underTEst = new Board(new int[]{0,6,12});
        Assert.assertEquals(true, underTEst.getLightState(6));
    }

    public void test_getLightState_WhenInitializedOff_ShouldReturnFalse() throws Exception {
        Board underTEst = new Board(new int[]{0,6,12});
        Assert.assertEquals(false, underTEst.getLightState(1));
    }
}