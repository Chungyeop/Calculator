package com.example.calculato;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Arithmetics_Graph extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "Test";
    private final int REQUEST_CODE = 1000;
    private TextView equation1, equation2, equation3, empty1, empty2, empty3;           // 함수 입력을 위한 equation, 입력이 되어 있는 가를 확인할 empty
    private Button numBtn0, numBtn1, numBtn2, numBtn3, numBtn4, numBtn5, numBtn6, numBtn7, numBtn8, numBtn9,
            dotBtn, equalBtn, divBtn, mulBtn, subBtn, addBtn, sqrBtn, rootBtn, bracketBtn, sinBtn, cosBtn, tanBtn,
            xBtn, yBtn, backBtn, clearBtn, graphBtn, homeBtn;
    private Toolbar mainToolBar;
    private ActionBarDrawerToggle drawerToggle;
    private String checkFunction1, checkFunction2, checkFunction3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetics_graph);


        // toolbar
        mainToolBar = (Toolbar) findViewById(R.id.main_tool_bar);
        setSupportActionBar(mainToolBar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mainToolBar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        MenuBarEvent menuBarEvent = new MenuBarEvent(this);
        navigationView.setNavigationItemSelectedListener(menuBarEvent);

        empty1 = findViewById(R.id.empty1);
        empty2 = findViewById(R.id.empty2);
        empty3 = findViewById(R.id.empty3);
        equation1 = findViewById(R.id.equation1);
        equation2 = findViewById(R.id.equation2);
        equation3 = findViewById(R.id.equation3);
        numBtn0 = findViewById(R.id.numBtn0);
        numBtn1 = findViewById(R.id.numBtn1);
        numBtn2 = findViewById(R.id.numBtn2);
        numBtn3 = findViewById(R.id.numBtn3);
        numBtn4 = findViewById(R.id.numBtn4);
        numBtn5 = findViewById(R.id.numBtn5);
        numBtn6 = findViewById(R.id.numBtn6);
        numBtn7 = findViewById(R.id.numBtn7);
        numBtn8 = findViewById(R.id.numBtn8);
        numBtn9 = findViewById(R.id.numBtn9);
        equalBtn = findViewById(R.id.equalBtn);
        divBtn = findViewById(R.id.divBtn);
        mulBtn = findViewById(R.id.mulBtn);
        subBtn = findViewById(R.id.subBtn);
        addBtn = findViewById(R.id.addBtn);
        sqrBtn = findViewById(R.id.sqrBtn);
        rootBtn = findViewById(R.id.rootBtn);
        sinBtn = findViewById(R.id.sinBtn);
        cosBtn = findViewById(R.id.cosBtn);
        tanBtn = findViewById(R.id.tanBtn);
        xBtn = findViewById(R.id.xBtn);
        yBtn = findViewById(R.id.yBtn);
        backBtn = findViewById(R.id.backBtn);
        clearBtn = findViewById(R.id.clearBtn);
        graphBtn = findViewById(R.id.graphBtn);
        homeBtn = findViewById(R.id.homeBtn);

        numBtn0.setOnClickListener(this);
        numBtn1.setOnClickListener(this);
        numBtn2.setOnClickListener(this);
        numBtn3.setOnClickListener(this);
        numBtn4.setOnClickListener(this);
        numBtn5.setOnClickListener(this);
        numBtn6.setOnClickListener(this);
        numBtn7.setOnClickListener(this);
        numBtn8.setOnClickListener(this);
        numBtn9.setOnClickListener(this);
        equalBtn.setOnClickListener(this);
        divBtn.setOnClickListener(this);
        mulBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        sqrBtn.setOnClickListener(this);
        rootBtn.setOnClickListener(this);
        sinBtn.setOnClickListener(this);
        cosBtn.setOnClickListener(this);
        tanBtn.setOnClickListener(this);
        xBtn.setOnClickListener(this);
        yBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        graphBtn.setOnClickListener(this);
        homeBtn.setOnClickListener(this);
        equation1.setOnClickListener(this);
        equation2.setOnClickListener(this);
        equation3.setOnClickListener(this);
    }

    // Activity 종료 시 효과 제거
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View view) {
        if(empty3.getText().toString().equals("1")){
            if(view.getId() == R.id.graphBtn ||view.getId() == R.id.backBtn || view.getId() == R.id.homeBtn ||view.getId() == R.id.clearBtn){
                empty3.setText("");
            }else{
                return;
            }
        }
        switch (view.getId()) {
            case R.id.numBtn0:
                if (empty1.getText().toString().equals("")) {
                    equation1.append("0");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.append("0");
                    break;
                } else {
                    equation3.append("0");
                    break;
                }
            case R.id.numBtn1:
                if (empty1.getText().toString().equals("")) {
                    equation1.append("1");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.append("1");
                    break;
                } else {
                    equation3.append("1");
                    break;
                }
            case R.id.numBtn2:
                if (empty1.getText().toString().equals("")) {
                    equation1.append("2");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.append("2");
                    break;
                } else {
                    equation3.append("2");
                    break;
                }
            case R.id.numBtn3:
                if (empty1.getText().toString().equals("")) {
                    equation1.append("3");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.append("3");
                    break;
                } else {
                    equation3.append("3");
                    break;
                }
            case R.id.numBtn4:
                if (empty1.getText().toString().equals("")) {
                    equation1.append("4");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.append("4");
                    break;
                } else {
                    equation3.append("4");
                    break;
                }
            case R.id.numBtn5:
                if (empty1.getText().toString().equals("")) {
                    equation1.append("5");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.append("5");
                    break;
                } else {
                    equation3.append("5");
                    break;
                }
            case R.id.numBtn6:
                if (empty1.getText().toString().equals("")) {
                    equation1.append("6");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.append("6");
                    break;
                } else {
                    equation3.append("6");
                    break;
                }
            case R.id.numBtn7:
                if (empty1.getText().toString().equals("")) {
                    equation1.append("7");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.append("7");
                    break;
                } else {
                    equation3.append("7");
                    break;
                }
            case R.id.numBtn8:
                if (empty1.getText().toString().equals("")) {
                    equation1.append("8");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.append("8");
                    break;
                } else {
                    equation3.append("8");
                    break;
                }
            case R.id.numBtn9:
                if (empty1.getText().toString().equals("")) {
                    equation1.append("9");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.append("9");
                    break;
                } else {
                    equation3.append("9");
                    break;
                }
            case R.id.divBtn:
                if (empty1.getText().toString().equals("")) {
                    equation1.append("/");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.append("/");
                    break;
                } else {
                    equation3.append("/");
                    break;
                }
            case R.id.mulBtn:
                if (empty1.getText().toString().equals("")) {
                    equation1.append("*");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.append("*");
                    break;
                } else {
                    equation3.append("*");
                    break;
                }
            case R.id.subBtn:
                if (empty1.getText().toString().equals("")) {
                    equation1.append("-");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.append("-");
                    break;
                } else {
                    equation3.append("-");
                    break;
                }
            case R.id.addBtn:
                if (empty1.getText().toString().equals("")) {
                    equation1.append("+");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.append("+");
                    break;
                } else {
                    equation3.append("+");
                    break;
                }
            case R.id.sqrBtn:
                if (empty1.getText().toString().equals("")) {
                    equation1.append("x²");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.append("x²");
                    break;
                } else {
                    equation3.append("x²");
                    break;
                }
            case R.id.rootBtn:
                if (empty1.getText().toString().equals("")) {
                    equation1.append("√x");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.append("√x");
                    break;
                } else {
                    equation3.append("√x");
                    break;
                }
            case R.id.sinBtn:
                if (empty1.getText().toString().equals("")) {
                    equation1.setText("y=sinx");
                    empty1.setText("1");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.setText("y=sinx");
                    empty2.setText("1");
                    break;
                } else {
                    equation3.setText("y=sinx");
                    empty3.setText("1");
                    break;
                }
            case R.id.cosBtn:
                if (empty1.getText().toString().equals("")) {
                    equation1.setText("y=cosx");
                    empty1.setText("1");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.setText("y=cosx");
                    empty2.setText("1");
                    break;
                } else {
                    equation3.setText("y=cosx");
                    empty3.setText("1");
                    break;
                }
            case R.id.tanBtn:
                if (empty1.getText().toString().equals("")) {
                    equation1.setText("y=tanx");
                    empty1.setText("1");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.setText("y=tanx");
                    empty2.setText("1");
                    break;
                } else {
                    equation3.setText("y=tanx");
                    empty3.setText("1");
                    break;
                }
            case R.id.xBtn:
                if (empty1.getText().toString().equals("")) {
                    equation1.append("x");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.append("x");
                    break;
                } else {
                    equation3.append("x");
                    break;
                }
            case R.id.yBtn:
                if (empty1.getText().toString().equals("")) {
                    equation1.append("y=x");
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    equation2.append("y=x");
                    break;
                } else {
                    equation3.append("y=x");
                    break;
                }
            case R.id.backBtn:
                if(equation1.getText().toString().equals("")){
                    return;
                }
                if(!equation1.getText().toString().equals("") && !equation2.getText().toString().equals("") && !equation3.getText().toString().equals("")){
                    empty3.setText("");
                }else if(!equation1.getText().toString().equals("") && !equation2.getText().toString().equals("") && equation3.getText().toString().equals("")){
                    empty2.setText("");
                }else if(!equation1.getText().toString().equals("") && equation2.getText().toString().equals("")){
                    empty1.setText("");
                }
                int point = 0;
                if (empty1.getText().toString().equals("")) {
                    point = equation1.getText().toString().length() - 1;
                    if (equation1.getText().toString().substring(point).equals("x")) {
                        equation1.setText("");
                        empty1.setText("");
                        empty2.setText("");
                        empty3.setText("");
                        if(!equation3.getText().toString().equals("")){
                            equation2.setText(equation3.getText().toString());
                            equation3.setText("");
                            empty1.setText("1");
                            empty2.setText("1");
                            empty3.setText("");
                        }
                        if(!equation2.getText().toString().equals("")){
                            equation1.setText(equation2.getText().toString());
                            equation2.setText("");
                            empty1.setText("1");
                            empty2.setText("");
                            empty3.setText("");
                        }
                        break;
                    }
                    int size = equation1.getText().length();
                    if (size >= 1) {
                        equation1.setText(equation1.getText().toString().substring(0, size - 1));
                    }
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    point = equation2.getText().toString().length() - 1;
                    if (equation2.getText().toString().substring(point).equals("x")) {
                        equation2.setText("");
                        empty2.setText("");
                        empty3.setText("");
                        if(!equation3.getText().toString().equals("")){
                            equation2.setText(equation3.getText().toString());
                            equation3.setText("");
                            empty2.setText("");
                            empty3.setText("");
                        }
                        break;
                    }
                    int size = equation2.getText().length();
                    if (size >= 1) {
                        equation2.setText(equation2.getText().toString().substring(0, size - 1));
                    }
                    break;
                } else {
                    point = equation3.getText().toString().length() - 1;
                    if (equation3.getText().toString().substring(point).equals("x")){
                        equation3.setText("");
                        empty2.setText("1");
                        empty3.setText("");
                        break;
                    }
                    int size = equation3.getText().length();
                    if (size >= 1) {
                        equation3.setText(equation3.getText().toString().substring(0, size - 1));
                    }
                    break;
                }
            case R.id.clearBtn:
                equation1.setText("");
                empty1.setText("");
                equation2.setText("");
                empty2.setText("");
                equation3.setText("");
                empty3.setText("");
                break;
            case R.id.equalBtn:
                if (empty1.getText().toString().equals("")) {
                    if (equation1.getText().toString().contains("=")) {
                        break;
                    } else {
                        equation1.append("=");
                    }
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    if (equation2.getText().toString().contains("=")) {
                        break;
                    } else {
                        equation2.append("=");
                    }
                    break;
                } else {
                    equation3.append("=");
                    break;
                }
            case R.id.graphBtn:
                if(!equation1.getText().toString().equals("") && equation2.getText().toString().equals("") && equation3.getText().toString().equals("")){
                    empty1.setText("");
                    empty2.setText("");
                    empty3.setText("");
                }else if(!equation1.getText().toString().equals("") && !equation2.getText().toString().equals("") && equation3.getText().toString().equals("")){
                    empty2.setText("");
                    empty3.setText("");
                }else{
                    empty3.setText("");
                }
                // 각 함수의 마지막 부분에 부호가 들어가는 것을 방지
                String equationCheck1 = equation1.getText().toString();
                String equationCheck2 = equation2.getText().toString();
                String equationCheck3 = equation3.getText().toString();

                Log.v("Check equationCheck", "equationCheck1 : " + equationCheck1);
                Log.v("Check equationCheck", "equationCheck2 : " + equationCheck2);
                Log.v("Check equationCheck", "equationCheck3 : " + equationCheck3);

                if (equationCheck1.contains("=")) {
                    checkFunction1 = equationCheck1.substring(equationCheck1.length() - 1);
                }
                if (equationCheck2.contains("=")) {
                    checkFunction2 = equationCheck2.substring(equationCheck2.length() - 1);
                }
                if (equationCheck3.contains("=")) {
                    checkFunction3 = equationCheck3.substring(equationCheck3.length() - 1);
                }
                Log.v("Check Function", "checkFunction1 : " + checkFunction1);
                Log.v("Check Function", "checkFunction2 : " + checkFunction2);
                Log.v("Check Function", "checkFunction3 : " + checkFunction3);

                if (!equationCheck1.equals("")) {
                    if (checkFunction1.equals("+") || checkFunction1.equals("-") || checkFunction1.equals("*") || checkFunction1.equals("/")) {
                        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Graph.this);
                        myAlertBuilder.setTitle("Alert");
                        myAlertBuilder.setMessage("Do Not Put The Operator. \nAt The End Of Equation.");
                        myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // OK 버튼을 눌렸을 경우
                                equation1.setText("");
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                        myAlertBuilder.show();
                        Log.v("Check Process", "Check Process1");
                        break;
                    } else {
                        Log.v("Check Complete", "Check Complete1");
                    }
                } else {
                    Log.v("Check Equation", "Equation1 Null");
                }
                if (!equationCheck2.equals("")) {
                    if (checkFunction2.equals("+") || checkFunction2.equals("-") || checkFunction2.equals("*") || checkFunction2.equals("/")) {
                        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Graph.this);
                        myAlertBuilder.setTitle("Alert");
                        myAlertBuilder.setMessage("Do Not Put The Operator. \nAt The End Of Equation.");
                        myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // OK 버튼을 눌렸을 경우
                                equation2.setText("");
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                        myAlertBuilder.show();
                        Log.v("Check Process", "Check Process2");
                        break;
                    } else {
                        Log.v("Check Complete", "Check Complete2");
                    }
                } else {
                    Log.v("Check Equation", "Equation2 Null");
                }
                if (!equationCheck3.equals("")) {
                    if (checkFunction3.equals("+") || checkFunction3.equals("-") || checkFunction3.equals("*") || checkFunction3.equals("/")) {
                        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Graph.this);
                        myAlertBuilder.setTitle("Alert");
                        myAlertBuilder.setMessage("Do Not Put The Operator. \nAt The End Of Equation.");
                        myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // OK 버튼을 눌렸을 경우
                                equation3.setText("");
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                        myAlertBuilder.show();
                        Log.v("Check Process", "Check Process3");
                        break;
                    } else {
                        Log.v("Check Complete", "Check Complete3");
                    }
                } else {
                    Log.v("Check Equation", "Equation3 Null");
                }
                if (empty1.getText().toString().equals("")) {
                    empty1.setText("1");
                    Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);     // Activity 전환 시 효과 제거
                    intent.putExtra("empty1", empty1.getText().toString());
                    intent.putExtra("function1", equation1.getText().toString());
                    intent.putExtra("empty2", empty2.getText().toString());
                    intent.putExtra("function2", equation2.getText().toString());
                    intent.putExtra("empty3", empty3.getText().toString());
                    intent.putExtra("function3", equation3.getText().toString());
                    startActivityForResult(intent, REQUEST_CODE);
                    break;
                } else if (empty2.getText().toString().equals("")) {
                    empty2.setText("1");
                    Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);     // Activity 전환 시 효과 제거
                    intent.putExtra("empty1", empty1.getText().toString());
                    intent.putExtra("function1", equation1.getText().toString());
                    intent.putExtra("empty2", empty2.getText().toString());
                    intent.putExtra("function2", equation2.getText().toString());
                    intent.putExtra("empty3", empty3.getText().toString());
                    intent.putExtra("function3", equation3.getText().toString());
                    startActivityForResult(intent, REQUEST_CODE);
                    break;
                } else {
                    empty3.setText("1");
                    Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);     // Activity 전환 시 효과 제거
                    intent.putExtra("empty1", empty1.getText().toString());
                    intent.putExtra("function1", equation1.getText().toString());
                    intent.putExtra("empty2", empty2.getText().toString());
                    intent.putExtra("function2", equation2.getText().toString());
                    intent.putExtra("empty3", empty3.getText().toString());
                    intent.putExtra("function3", equation3.getText().toString());
                    startActivityForResult(intent, REQUEST_CODE);
                    break;
                }

            case R.id.homeBtn:
                Intent homeIntent = new Intent(getApplicationContext(), Arithmetics.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);     // Activity 전환 시 효과 제거
                startActivity(homeIntent);
                break;

            case R.id.equation1:
                if(equation1.getText().toString().equals("")){
                    equation1.setText("y=x");
                }
                empty1.setText("");
                break;
            case R.id.equation2:
                if(!equation1.getText().toString().equals("")){
                    empty1.setText("1");
                    empty2.setText("");
                    if(equation2.getText().toString().equals("")){
                        equation2.setText("y=x");
                    }
                }else{
                    equation1.setText("y=x");
                    empty1.setText("");
                    empty2.setText("");
                }
                break;
            case R.id.equation3:
                if(!equation1.getText().toString().equals("")) {
                    if (!equation2.getText().toString().equals("")) {
                        empty2.setText("1");
                        if (equation3.getText().toString().equals("")) {
                            equation3.setText("y=x");
                        }
                    }
                    if (!equation1.getText().toString().equals("")) {
                        empty1.setText("1");
                        if (equation2.getText().toString().equals("")) {
                            equation2.setText("y=x");
                        }
                    }
                }else{
                    equation1.setText("y=x");
                    empty1.setText("");
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            Log.d(TAG, "Test : " + resultCode);
        }
    }
}
