package at.dranner.LightsOut_Android;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {

    public static final int ROW_COUNT = 5;
    public static final int COLUMN_COUNT = 5;
    public static final int ON_COLOR = Color.YELLOW;
    public static final int OFF_COLOR = Color.LTGRAY;
    private Board mBoard;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mBoard = new Board(new int[]{0, 6, 12, 18, 24});

        final LayoutInflater layoutInflater = getLayoutInflater();
        final TableLayout lightsTableLayout = (TableLayout) findViewById(R.id.LightsLayout);

        for (int rowIndex = 0; rowIndex < ROW_COUNT; rowIndex++) {
            final TableRow tableRow = new TableRow(this);
            tableRow.setWeightSum(1.0f);

            for (int columnIndex = 0; columnIndex < COLUMN_COUNT; columnIndex++) {
                final View buttonView = layoutInflater.inflate(R.layout.light_bulb_button, tableRow);
                final ImageButton imageButton = (ImageButton) buttonView.findViewById(R.id.LightBulbButton);
                final int bulbIndex = rowIndex * ROW_COUNT + columnIndex;
                InitializeLight(imageButton, bulbIndex);

                imageButton.setContentDescription(String.format("[%d,%d]", rowIndex, columnIndex));
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBoard.toggleLight(bulbIndex);
                    }
                });
            }

            lightsTableLayout.addView(tableRow);
        }

    }

    private int getLightImageResource(boolean isOn){
        return isOn ? R.drawable.ic_light_bulb : R.drawable.ic_light_bulb_off;
    }

    private int getLightBackgroundColor(boolean isOn){
        return isOn ? ON_COLOR : OFF_COLOR;
    }

    private void InitializeLight(final ImageButton button, final int index){
        button.setId(index);
        final boolean lightState = mBoard.getLightState(index);
        button.setImageResource(getLightImageResource(lightState));
        button.getBackground().setColorFilter(getLightBackgroundColor(lightState), PorterDuff.Mode.MULTIPLY);
        mBoard.addLightToggledListener(index, new Board.LightToggledListener() {
            @Override
            public void onLightToggled(int index, boolean newState) {
                transitionLight(button, newState);
            }
        });
    }

    private void transitionLight(final ImageButton imageButton, boolean newState) {
        imageButton.setImageResource(getLightImageResource(newState));

        final int fromColor = getLightBackgroundColor(!newState);
        final int toColor = getLightBackgroundColor(newState);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        colorAnimation.setDuration(200);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                imageButton.getBackground().setColorFilter((Integer) animation.getAnimatedValue(), PorterDuff.Mode.MULTIPLY);
            }
        });
        colorAnimation.start();
    }

    /*
                        final ImageButton b = (ImageButton) v;
                        b.setImageResource(R.drawable.ic_light_bulb);

                        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), OFF_COLOR, ON_COLOR);
                        colorAnimation.setDuration(200);
                        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                b.getBackground().setColorFilter((Integer) animation.getAnimatedValue(), PorterDuff.Mode.MULTIPLY);
                            }
                        });
                        colorAnimation.start();


     */
}
