package at.dranner.LightsOut_Android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends Activity {

    private ToggleStoringBoard mBoard;
    private LightPropertyProvider mLightPropertyProvider;
    private GridView mGameGrid;
    private Random mRandom = new Random();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mBoard = new ToggleStoringBoard(new int[]{0, 6, 12, 18, 24});
        mLightPropertyProvider = new LightPropertyProvider(this);

        mGameGrid = (GridView) findViewById(R.id.GameGrid);
        mGameGrid.setAdapter(new GameGridAdapter(mBoard, getLayoutInflater(), mLightPropertyProvider));
        mGameGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Bulb", String.format("Clicked bulb with index: %d", position));
                mBoard.toggleLight(position);
                checkForLevelCompletion();
            }
        });

        mBoard.addLightToggledListener(new IBoard.LightToggledListener() {
            @Override
            public void onLightToggled(int index, boolean newState) {
                Log.i("Bulb", String.format("Transitioning bulb with index: %d to: %b", index, newState));
                transitionLight(index, newState);
            }
        });
    }

    private void transitionLight(int index, boolean newState) {
        View view = mGameGrid.getChildAt(index);
        final ImageView imageView = (ImageView) view.findViewById(R.id.LightBulbButton);
        imageView.setImageResource(mLightPropertyProvider.getLightImageResource(newState));

        TransitionDrawable background = (TransitionDrawable) imageView.getBackground();
        if (newState)
            background.startTransition(200);
        else
            background.reverseTransition(200);
    }

    private void checkForLevelCompletion() {
        if (mBoard.getNumberOfSwitchedOnLights() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You did it!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    shuffleBoard(5);
                }
            }).setCancelable(false).create().show();
        }
    }

    private void shuffleBoard(int numberOfMoves) {
        ArrayList<Integer> list = new ArrayList<>();
        int max = Board.COLUMN_COUNT * Board.ROW_COUNT;
        for (int i = 0; i < max; i++) {
            list.add(i);
        }
        for (int i = max - 1; i > 0; i--) {
            int j = mRandom.nextInt(i+1);
            Integer tmp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, tmp);
        }
        for (int i = 0; i < numberOfMoves; i++) {
            mBoard.toggleLight(list.get(i));
        }
    }

    private void updateMovesLeft() {
    }
}
