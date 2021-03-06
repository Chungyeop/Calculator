package com.example.calculato;

import static com.example.calculato.util.Constants.CHECK_LOG;
import static com.example.calculato.util.Constants.FUNCTION_1;
import static com.example.calculato.util.Constants.FUNCTION_2;
import static com.example.calculato.util.Constants.FUNCTION_3;
import static com.example.calculato.util.Constants.GRAPH_LOG_TAG;
import static com.example.calculato.util.Constants.HANDLER_MESSAGE_GRAPH_ASYNC_DRAW;
import static com.example.calculato.util.Constants.X_LINE;
import static com.example.calculato.util.Constants.Y_LINE;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GraphActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView functionOne, functionTwo, functionThree, emptyOne, emptyTwo, emptyThree;
    private LineChart chart;
    private Button graph;
    private String function1, function2, function3, empty1, empty2, empty3;

    GraphAsyncTask graphAsyncTaskX;
    GraphAsyncTask graphAsyncTaskY;
    GraphAsyncTask graphAsyncTask1;
    GraphAsyncTask graphAsyncTask2;
    GraphAsyncTask graphAsyncTask3;

    private static ArrayList<ILineDataSet> dataSets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        chart = findViewById(R.id.linechart);
        graph = findViewById(R.id.graph);
        functionOne = findViewById(R.id.functionOne);
        functionTwo = findViewById(R.id.functionTwo);
        functionThree = findViewById(R.id.functionThree);
        emptyOne = findViewById(R.id.emptyOne);
        emptyTwo = findViewById(R.id.emptyTwo);
        emptyThree = findViewById(R.id.emptyThree);

        graph.setOnClickListener(this);

        // ?????? ?????? Data ??????
        Bundle extras = getIntent().getExtras();
        if (extras.getString("function1").equals("")) {
            function1 = "";
        } else {
            function1 = extras.getString("function1");
        }
        if (extras.getString("empty1").equals("")) {
            empty1 = "";
        } else {
            empty1 = extras.getString("empty1");
        }
        if (extras.getString("function2").equals("")) {
            function2 = "";
        } else {
            function2 = extras.getString("function2");
        }
        if (extras.getString("empty2").equals("")) {
            empty2 = "";
        } else {
            empty2 = extras.getString("empty2");
        }
        if (extras.getString("function3").equals("")) {
            function3 = "";
        } else {
            function3 = extras.getString("function3");
        }
        if (extras.getString("empty3").equals("")) {
            empty3 = "";
        } else {
            empty3 = extras.getString("empty3");
        }

        // ????????? ????????? Data Setting
        functionOne.setText(function1);
        emptyOne.setText(empty1);
        functionTwo.setText(function2);
        emptyTwo.setText(empty2);
        functionThree.setText(function3);
        emptyThree.setText(empty3);

        // function2,3??? ""??? ???, ?????? ???????????? Data?????? ????????????
        ArrayList<Entry> xValues = new ArrayList<>();
        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<Entry> firstValues = new ArrayList<>();
        ArrayList<Entry> secondValues = new ArrayList<>();
        ArrayList<Entry> thirdValues = new ArrayList<>();

        graphAsyncTaskX = new GraphAsyncTask("x", this, X_LINE);
        graphAsyncTaskY = new GraphAsyncTask("y", this, Y_LINE);

        String firstFunction = null;
        if (!function1.equals("")) {
            firstFunction = functionOne.getText().toString();
            Log.v("firstFunction", "firstFunction : " + firstFunction);
        }
        graphAsyncTask1 = new GraphAsyncTask(firstFunction, this, FUNCTION_1);

        String secondFunction = null;
        if (!function2.equals("")) {
            secondFunction = functionTwo.getText().toString();
            Log.v("secondFunction", "secondFunction : " + secondFunction);
        }
        graphAsyncTask2 = new GraphAsyncTask(secondFunction, this, FUNCTION_2);

        String thirdFunction = null;
        if (!function3.equals("")) {
            /* ?????? ?????? ????????? String */
            thirdFunction = functionThree.getText().toString();
            Log.v("thirdFunction", "thirdFunction : " + thirdFunction);
        }

        graphAsyncTask3 = new GraphAsyncTask(thirdFunction, this, FUNCTION_3);

        Thread handlerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                DRAW_PLAY = true;
                X_Y_LINE_DONE = false;
                if(CHECK_LOG) Log.i(GRAPH_LOG_TAG, "thread.run()");
                if(DRAW_PLAY) {
                    GRAPH_HANDLER_POSITION = 0;
                    Message message = new Message();
                    message.what = HANDLER_MESSAGE_GRAPH_ASYNC_DRAW;
                    graphHandler.sendMessage(message);
                    message = null;
                }
            }
        });

        dataSets = new ArrayList<>();

        // create a data object with the data sets
        LineData data = new LineData(dataSets);
        handlerThread.start();
        if(CHECK_LOG) Log.v(GRAPH_LOG_TAG, "handlerThread.start()");
        // set data
        chart.setData(data);
    }

    public synchronized ArrayList<ILineDataSet> getDataSets() {
        return dataSets;
    }

    // Activity ?????? ??? ?????? ??????
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.graph:
                Intent homeIntent = new Intent(getApplicationContext(), com.example.calculato.Arithmetics_Graph.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);     // Activity ?????? ??? ?????? ??????
                setResult(Activity.RESULT_OK);
                finish();
                break;
        }
    }

    public static String[] deleteEmpty(final String[] array) {              // String[]??? Empty Data ??????
        List<String> list = new ArrayList<>(Arrays.asList(array));
        list.removeAll(Collections.singleton(""));                          // list ?????? Data "" ?????? ??????
        return list.toArray(new String[list.size()]);
    }

    public static synchronized boolean numberCheck(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int GRAPH_HANDLER_POSITION = 0;
    private static boolean DRAW_PLAY = true;
    private static boolean X_Y_LINE_DONE = false;

    public void setDrawPlay(boolean drawPlay) {
        this.DRAW_PLAY = drawPlay;
    }

    public void setXYLineDone(boolean xyLineDone) {
        this.X_Y_LINE_DONE = xyLineDone;
    }

    Handler graphHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            if(CHECK_LOG) Log.i(GRAPH_LOG_TAG, "graphHandler.handleMessage()");
            if(message.what == HANDLER_MESSAGE_GRAPH_ASYNC_DRAW) {
                if(X_Y_LINE_DONE) setChartData();
                if(GRAPH_HANDLER_POSITION == X_LINE) {
                    // AsyncTask.SERIAL_EXECUTOR == ????????? ??????
                    // AsyncTask.THREAD_POOL_EXECUTOR == ?????? ??????
                    graphAsyncTaskX.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                }else if(GRAPH_HANDLER_POSITION == Y_LINE) {
                    graphAsyncTaskY.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                }else if(GRAPH_HANDLER_POSITION == FUNCTION_1) {
                    graphAsyncTask1.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                }else if(GRAPH_HANDLER_POSITION == FUNCTION_2) {
                    graphAsyncTask2.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                }else if(GRAPH_HANDLER_POSITION == FUNCTION_3) {
                    graphAsyncTask3.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                }
                if(GRAPH_HANDLER_POSITION <= FUNCTION_3) {
                    GRAPH_HANDLER_POSITION++;
                    graphHandler.sendEmptyMessage(HANDLER_MESSAGE_GRAPH_ASYNC_DRAW);
                }else if(DRAW_PLAY) {
                    // AsyncTask ????????? ??????????????? ???????????????.
                    graphHandler.sendEmptyMessageDelayed(HANDLER_MESSAGE_GRAPH_ASYNC_DRAW, 200);
                }else {
                    setChartData();
                }
            }
        }
    };
    private void setChartData() {
        LineData lineData = new LineData(dataSets);
        chart.setData(lineData);
        chart.notifyDataSetChanged();
//        chart.moveViewTo(0.0f, 0.0f, YAxis.AxisDependency.LEFT);
        chart.invalidate();
        if(CHECK_LOG) Log.i(GRAPH_LOG_TAG, "dataSets = " + dataSets);
    }
}