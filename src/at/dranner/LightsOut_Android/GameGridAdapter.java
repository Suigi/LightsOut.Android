package at.dranner.LightsOut_Android;

import android.graphics.drawable.TransitionDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 03.06.2015.
 * Creates a grid of light bulbs and initializes them according to the given board.
 */
public class GameGridAdapter extends BaseAdapter {
    private final IBoard mBoard;
    private final LayoutInflater mLayoutInflater;
    private final Map<Integer, View> mViewMap = new HashMap<>();
    private final LightPropertyProvider mLightPropertyProvider;

    public GameGridAdapter(IBoard mBoard, LayoutInflater mLayoutInflater, LightPropertyProvider lightPropertyProvider) {
        this.mBoard = mBoard;
        this.mLayoutInflater = mLayoutInflater;
        this.mLightPropertyProvider = lightPropertyProvider;
    }

    @Override
    public int getCount() {
        return Board.COLUMN_COUNT * Board.ROW_COUNT;
    }

    @Override
    public Object getItem(int position) {
        return mViewMap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mViewMap.containsKey(position))
            return mViewMap.get(position);

        View view = mLayoutInflater.inflate(R.layout.light_bulb_button, parent, false);
        view.setId(position);
        mViewMap.put(position, view);
        InitializeLight(position, view);
        return view;
    }

    private void InitializeLight(int position, View view) {
        final ImageView imageView = (ImageView) view.findViewById(R.id.LightBulbButton);
        final boolean lightState = mBoard.getLightState(position);
        imageView.setImageResource(mLightPropertyProvider.getLightImageResource(lightState));
        if (lightState)
            ((TransitionDrawable)imageView.getBackground()).startTransition(200);
    }
}
