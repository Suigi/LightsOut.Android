package at.dranner.LightsOut_Android;

import java.util.ArrayList;

/**
 * Created by Daniel on 02.06.2015.
 * Contains logic for the state of the light bulb board.
 */
public class Board implements IBoard {

    public static final int ROW_COUNT = 5;
    public static final int COLUMN_COUNT = 5;

    private boolean[] lightStates;
    private ArrayList<LightToggledListener> mLightToggledListeners;

    private int maximumIndex() {
        return ROW_COUNT * COLUMN_COUNT - 1;
    }

    public Board() {
        this(new int[0]);
    }

    public Board(int[] switchedOnIndices) {
        lightStates = new boolean[ROW_COUNT * COLUMN_COUNT];
        for (Integer index : switchedOnIndices) {
            lightStates[index] = true;
        }
    }

    @Override
    public int getNumberOfSwitchedOnLights() {
        int result = 0;
        for (Boolean state : lightStates) {
            if (state) result++;
        }
        return result;
    }

    @Override
    public boolean getLightState(int index) {
        return lightStates[index];
    }

    @Override
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

        for (LightToggledListener listener : mLightToggledListeners){
            listener.onLightToggled(index, newState);
        }
    }

    @Override
    public void addLightToggledListener(LightToggledListener listener){
        if (mLightToggledListeners == null) {
            mLightToggledListeners = new ArrayList<>();
        }
        mLightToggledListeners.add(listener);
    }

}
