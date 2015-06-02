package at.dranner.LightsOut_Android;

import java.util.Collections;
import java.util.Enumeration;

/**
 * Created by Daniel on 02.06.2015.
 * Contains logic for the state of the light bulb board.
 */
public class Board {

    private static final int ROW_COUNT = 5;
    private static final int COLUMN_COUNT = 5;

    private boolean[] lightStates;

    public Board(int[] switchedOnIndices) {
        lightStates = new boolean[ROW_COUNT * COLUMN_COUNT];
        for (Integer index : switchedOnIndices){
            lightStates[index] = true;
        }
    }

    public int getNumberOfSwitchedOnLights(){
        int result = 0;
        for (Boolean state : lightStates){
            if (state) result++;
        }
        return result;
    }

    public boolean getLightState(int index){
        return lightStates[index];
    }
}
