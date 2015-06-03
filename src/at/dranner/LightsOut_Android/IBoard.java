package at.dranner.LightsOut_Android;

/**
 * Created by Daniel on 03.06.2015.
 * Contains the game's state and functionality to toggle individual lights as well as a listener to react to lights
 * being turned on or off.
 */
public interface IBoard {

    /**
     * @return Returns the number of light bulbs on the board that are on.
     */
    int getNumberOfSwitchedOnLights();

    /**
     * @param index The index of the light you want to know the state of.
     * @return Returns the state of the light with the given index.
     */
    boolean getLightState(int index);

    /**
     * Toggles a light and all within a 4-neighbourhood (top,down,left,right).
     * @param index The index of the light to toggle.
     */
    void toggleLight(int index);

    /**
     * Adds a listener for when a light switches state.
     * @param listener The listener being added.
     */
    void addLightToggledListener(Board.LightToggledListener listener);

    interface LightToggledListener {

        /**
         * Notifies that the bulb with the given index was toggled.
         * @param index The index of the toggled bulb.
         * @param newState The new state of the bulb.
         */
        void onLightToggled(int index, boolean newState);
    }
}
