package com.example.android.shortcutsprototype;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.android.shortcutsprototype.Dijkstra.runQuery;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText fromInput = (EditText) findViewById(R.id.from_input);
        final EditText toInput = (EditText) findViewById(R.id.to_input);
        Button button = (Button) findViewById(R.id.go_button);

        final TextView dirFastestView = (TextView) findViewById(R.id.fastest_directions);
        final TextView timeFastestView = (TextView) findViewById(R.id.fastest_time);

        final TextView dirStairsView = (TextView) findViewById(R.id.least_stairs_directions);
        final TextView timeStairsView = (TextView) findViewById(R.id.least_stairs_time);

        final TextView dirShelterView = (TextView) findViewById(R.id.sheltered_directions);
        final TextView timeShelterView = (TextView) findViewById(R.id.sheltered_time);

        //on editor action listener for send button in keyboard, displays shortest path results
        TextView.OnEditorActionListener startSearch = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    //call queries and display
                    String input1 = fromInput.getText().toString();
                    String input2 = toInput.getText().toString();

                    if (input1 != null && input2 != null) { //check whether input is null
                        String finalString = runQuery(input1, input2);
                        if (finalString.equals("The location you entered cannot be found")) {
                            //make everything invisible
                            findViewById(R.id.fast_title).setVisibility(View.INVISIBLE);
                            dirFastestView.setVisibility(View.INVISIBLE);
                            timeFastestView.setVisibility(View.INVISIBLE);
                            findViewById(R.id.stairs_title).setVisibility(View.INVISIBLE);
                            timeStairsView.setVisibility(View.INVISIBLE);
                            findViewById(R.id.shelter_title).setVisibility(View.INVISIBLE);
                            dirShelterView.setVisibility(View.INVISIBLE);
                            timeShelterView.setVisibility(View.INVISIBLE);

                            dirStairsView.setText(finalString);
                        }else {
                            String[] res = finalString.split("_");
                            //print out
                            findViewById(R.id.fast_title).setVisibility(View.VISIBLE);
                            dirFastestView.setVisibility(View.VISIBLE);
                            timeFastestView.setVisibility(View.VISIBLE);
                            findViewById(R.id.stairs_title).setVisibility(View.VISIBLE);
                            dirStairsView.setVisibility(View.VISIBLE);
                            timeStairsView.setVisibility(View.VISIBLE);
                            findViewById(R.id.shelter_title).setVisibility(View.VISIBLE);
                            dirShelterView.setVisibility(View.VISIBLE);
                            timeShelterView.setVisibility(View.VISIBLE);

                            timeFastestView.setText(res[0]);
                            dirFastestView.setText(res[1]);
                            timeStairsView.setText(res[2]);
                            dirStairsView.setText(res[3]);
                            timeShelterView.setText(res[4]);
                            dirShelterView.setText(res[5]);
                        }
                    } else {    //either one has null input
                        dirFastestView.setText("Please enter your location and destination");
                    }
                    handled = true;
                }
                return handled;
            }
        };

        fromInput.setOnEditorActionListener(startSearch);
        toInput.setOnEditorActionListener(startSearch);

        //onClickListener for button to display shortest path results
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call queries and display
                String input1 = fromInput.getText().toString();
                String input2 = toInput.getText().toString();

                if (input1 != null && input2 != null) { //check whether input is null
                    String finalString = runQuery(input1, input2);
                    if (finalString.equals("The location you entered cannot be found")) {
                        //make everything invisible
                        findViewById(R.id.fast_title).setVisibility(View.INVISIBLE);
                        dirFastestView.setVisibility(View.INVISIBLE);
                        timeFastestView.setVisibility(View.INVISIBLE);
                        findViewById(R.id.stairs_title).setVisibility(View.INVISIBLE);
                        timeStairsView.setVisibility(View.INVISIBLE);
                        findViewById(R.id.shelter_title).setVisibility(View.INVISIBLE);
                        dirShelterView.setVisibility(View.INVISIBLE);
                        timeShelterView.setVisibility(View.INVISIBLE);

                        dirStairsView.setText(finalString);
                    }else {
                        String[] res = finalString.split("_");
                        //print out
                        findViewById(R.id.fast_title).setVisibility(View.VISIBLE);
                        dirFastestView.setVisibility(View.VISIBLE);
                        timeFastestView.setVisibility(View.VISIBLE);
                        findViewById(R.id.stairs_title).setVisibility(View.VISIBLE);
                        dirStairsView.setVisibility(View.VISIBLE);
                        timeStairsView.setVisibility(View.VISIBLE);
                        findViewById(R.id.shelter_title).setVisibility(View.VISIBLE);
                        dirShelterView.setVisibility(View.VISIBLE);
                        timeShelterView.setVisibility(View.VISIBLE);

                        timeFastestView.setText(res[0]);
                        dirFastestView.setText(res[1]);
                        timeStairsView.setText(res[2]);
                        dirStairsView.setText(res[3]);
                        timeShelterView.setText(res[4]);
                        dirShelterView.setText(res[5]);
                    }
                } else {    //either one has no input
                    dirFastestView.setText("Please enter your location and destination");
                }

            }
        });
    }
}


