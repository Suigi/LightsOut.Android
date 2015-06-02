package at.dranner.LightsOut_Android;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 02.06.2015.
 * Contains logic for the state of the light bulb board.
 */
public class Board {

    private static final int ROW_COUNT = 5;
    private static final int COLUMN_COUNT = 5;

    private boolean[] lightStates;
    private Map<Integer, LightToggledListener> mLightToggledListeners;

    private int maximumIndex() {
        return ROW_COUNT * COLUMN_COUNT - 1;
    }

    public Board(int[] switchedOnIndices) {
        lightStates = new boolean[ROW_COUNT * COLUMN_COUNT];
        for (Integer index : switchedOnIndices) {
            lightStates[index] = true;
        }
    }

    public int getNumberOfSwitchedOnLights() {
        int result = 0;
        for (Boolean state : lightStates) {
            if (state) result++;
        }
        return result;
    }

    public boolean getLightState(int index) {
        return lightStates[index];
    }

    public void toggleLight(int index){
        toggleLightWithoutSideEffect(index);
        int column = index % COLUMN_COUNT;
        if (column > 0)
            toggleLightWithoutSideEffect(index - 1);
        if (column < COLUMN_COUNT - 1)
            toggleLightWithoutSideEffect(index + 1);
        toggleLightWithoutSideEffect(index - COLUMN_COUNT);
        toggleLightWithoutSideEffect(index + COLUMN_COUNT);
    }

    private void toggleLightWithoutSideEffect(int index){
        if (index < 0 || index > maximumIndex())
            return;
        boolean newState = !getLightState(index);
        lightStates[index] = newState;
        invokeLightListener(index, newState);
    }

    private void invokeLightListener(int index, boolean newState){
        if (mLightToggledListeners == null) return;
        if (!mLightToggledListeners.containsKey(index)) return;

        mLightToggledListeners.get(index).onLightToggled(index, newState);
    }

    public void addLightToggledListener(int index, LightToggledListener listener){
        if (mLightToggledListeners == null) {
            mLightToggledListeners = new HashMap<>();
        }
        mLightToggledListeners.put(index, listener);
    }

    public interface LightToggledListener {

        /**
         * Notifies that the bulb with the given index was toggled.
         * @param index The index of the toggled bulb.
         * @param newState The new state of the bulb.
         */
        void onLightToggled(int index, boolean newState);
    }
}
