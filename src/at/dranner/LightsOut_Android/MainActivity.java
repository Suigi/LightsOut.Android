package at.dranner.LightsOut_Android;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {

    public static final int ROW_COUNT = 5;
    public static final int COLUMN_COUNT = 5;
    public static final int ON_COLOR = Color.YELLOW;
    public static final int OFF_COLOR = Color.LTGRAY;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        LayoutInflater layoutInflater = getLayoutInflater();

        TableLayout lightsTableLayout = (TableLayout) findViewById(R.id.LightsLayout);

        for (int rowIndex = 0; rowIndex < ROW_COUNT; rowIndex++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setWeightSum(1.0f);

            for (int columnIndex = 0; columnIndex < COLUMN_COUNT; columnIndex++) {
                View buttonView = layoutInflater.inflate(R.layout.light_bulb_button, tableRow);
                ImageButton imageButton = (ImageButton) buttonView.findViewById(R.id.LightBulbButton);
                imageButton.setContentDescription(String.format("[%d,%d]", rowIndex, columnIndex));
                imageButton.setId(rowIndex * ROW_COUNT + columnIndex);
                imageButton.getBackground().setColorFilter(OFF_COLOR, PorterDuff.Mode.MULTIPLY);

                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String message = String.format("%s", v.getContentDescription());
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
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

//                        b.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
//                        b.setBackgroundTintMode(PorterDuff.Mode.LIGHTEN);

                    }
                });
            }

            lightsTableLayout.addView(tableRow);
        }

    }
}
