package at.dranner.LightsOut_Android;

import android.content.Context;

public class LightPropertyProvider {
    private final Context mContext;

    public LightPropertyProvider(Context context) {
        this.mContext = context;
    }

    int getLightImageResource(boolean isOn) {
        return isOn ? R.drawable.ic_light_bulb : R.drawable.ic_light_bulb_off;
    }

    int getLightBackgroundColor(boolean isOn) {
        return mContext.getResources().getColor(isOn ? R.color.bulb_on_background : R.color.bulb_off_background);
    }
}