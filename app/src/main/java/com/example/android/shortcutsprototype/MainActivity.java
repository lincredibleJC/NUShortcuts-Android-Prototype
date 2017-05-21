package com.example.android.shortcutsprototype;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import static com.example.android.shortcutsprototype.Dijkstra.shortestPathQuery;


public class MainActivity extends AppCompatActivity {

    //stores an adjacency list
    public static Vertex[] adjList;
    public static int numVertex;
    public static List<Vertex> shortestPath;

    private static final String LOG_TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        final TextView textView1 = (TextView) findViewById(R.id.textView1);
        final TextView textView2 = (TextView) findViewById(R.id.textView2);

        final EditText fromInput = (EditText) findViewById(R.id.editText1);
        final EditText toInput = (EditText) findViewById(R.id.editText2);

        //on editor action listener for send button in keyboard, displays shortest path results
        fromInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    //call queries and display
                    String input1 = fromInput.getText().toString();
                    String input2 = toInput.getText().toString();
                    String timeTaken = "";
                    String pathTaken = "";

                    if (input1 != null && input2 != null) {
                        String query = shortestPathQuery(input1, input2);
                        //String query = hardQuery(fromInput.getText().toString(), toInput.getText().toString());

                        String[] res = query.split("_");
                        //display results
                        timeTaken = "Shortest time taken: " + res[0];
                        pathTaken = "Path taken: " + res[1];

                    } else {
                        timeTaken = "";
                        pathTaken = "";
                    }
                    //print out
                    textView1.setText(timeTaken);
                    textView2.setText(pathTaken);

                    handled = true;
                }
                return handled;
            }
        });

        //onClickListener for button to display shortest path results
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call queries and display
                String input1 = fromInput.getText().toString();
                String input2 = toInput.getText().toString();
                String timeTaken = "";
                String pathTaken = "";

                if (input1 != null && input2 != null) {
                    String query = shortestPathQuery(input1, input2);
                    //String query = hardQuery(fromInput.getText().toString(), toInput.getText().toString());

                    String[] res = query.split("_");
                    //display results
                    timeTaken = "Shortest time taken: " + res[0];
                    pathTaken = "Path taken: " + res[1];

                } else {
                    timeTaken = "";
                    pathTaken = "";
                }
                //print out
                textView1.setText(timeTaken);
                textView2.setText(pathTaken);
            }
        });
    }
}


