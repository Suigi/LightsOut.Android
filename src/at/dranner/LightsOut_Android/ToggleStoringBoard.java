package at.dranner.LightsOut_Android;

/**
 * Created by Daniel on 03.06.2015.
 * A board that remembers which lights have been toggled. Toggling all of these lights again will clear the board.
 */
public class ToggleStoringBoard implements IBoard {

    private IBoard mInnerBoard;
    private boolean[] mToggleStates;

    public ToggleStoringBoard(int[] switchedOnIndices) {
        mInnerBoard = new Board();
        mToggleStates = new boolean[Board.COLUMN_COUNT*Board.ROW_COUNT];
        for (int index : switchedOnIndices) {
            toggleLight(index);
        }
    }

    @Override
    public int getNumberOfSwitchedOnLights() {
        return mInnerBoard.getNumberOfSwitchedOnLights();
    }

    @Override
    public boolean getLightState(int index) {
        return mInnerBoard.getLightState(index);
    }

    @Override
    public void toggleLight(int index) {
        mToggleStates[index] = !mToggleStates[index];
        mInnerBoard.toggleLight(index);
    }

    @Override
    public void addLightToggledListener(Board.LightToggledListener listener) {
        mInnerBoard.addLightToggledListener(listener);
    }

    public int getNumberOfMovesNecessary(){
        int result = 0;
        for (boolean state : mToggleStates){
            if (state) result++;
        }
        return result;
    }
}
