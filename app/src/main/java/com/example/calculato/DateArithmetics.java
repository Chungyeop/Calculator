package com.example.calculato;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateArithmetics extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mainToolBar;
    private ActionBarDrawerToggle drawerToggle;
    private TextView startView;
    private TextView lastView;
    private TextView resultView;

    // 안드로이드 os 버전 때문에 발생하는 LocalDate 에러를 해결하는 Annotation
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        // 현재 날짜 세팅
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        // 내일 날짜 세팅
        calendar.add(Calendar.DATE, +1);
        Date tomorrow = calendar.getTime();
        // 날짜 표시형식 변경
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        // 날짜 스트링으로 변환
        String startDay = simpleDateFormat.format(today);
        String lastDay = simpleDateFormat.format(tomorrow);
        
        // 처음화면에 날짜를 세팅
        startView = (TextView)findViewById(R.id.startDay);
        startView.setText(startDay);
        lastView = (TextView)findViewById(R.id.lastDay);
        lastView.setText(lastDay);
        resultView = (TextView)findViewById(R.id.resultView);
        resultView.setText("0Day");

        // toolbar 와 drawerLayout 세팅
        mainToolBar = (Toolbar)findViewById(R.id.main_tool_bar);
        setSupportActionBar(mainToolBar);

        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mainToolBar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        MenuBarEvent menuBarEvent = new MenuBarEvent(this);
        navigationView.setNavigationItemSelectedListener(menuBarEvent);

        // 클릭 이벤트
        Button btnStart = (Button)findViewById(R.id.btn_date_start);
        Button btnLast = (Button)findViewById(R.id.btn_date_last);
        btnStart.setOnClickListener(this);
        btnLast.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        DatePickerDialog datePickerDialog = null;
        int[] intArr = null;
        switch (view.getId()) {
            case R.id.btn_date_start:
                String startDateStr = startView.getText().toString();
                intArr = setDate(startDateStr);
                if(intArr != null) {
                    datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            // 월이 0부터 시작하므로 +1 해준다(ex: 1월달일 경우 값이 0으로 들어온다)
                            month += 1;
                            startView.setText(year + "-" + month + "-" + day);
                            long resultDay = calculatorDate(startView.getText().toString(), lastView.getText().toString());
                            if(resultDay != -1) {
                                resultView.setText(resultDay+"Day");
                            }else {
                                resultView.setText("Error");
                            }
                        }
                    }, intArr[0], intArr[1]-1, intArr[2]); // 월이 +1 되서 들어가서 -1 해줘야한다.
                }
                break;
            case R.id.btn_date_last:
                String lastDateStr = lastView.getText().toString();
                intArr = setDate(lastDateStr);
                if(intArr != null) {
                    datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            // 월이 0부터 시작하므로 +1 해준다(ex: 1월달일 경우 값이 0으로 들어온다)
                            month+=1;
                            lastView.setText(year + "-" + month + "-" + day);
                            long resultDay = calculatorDate(startView.getText().toString(), lastView.getText().toString());
                            if(resultDay != -1) {
                                resultView.setText(resultDay+"Day");
                            }else {
                                resultView.setText("Error");
                            }
                        }
                    }, intArr[0], intArr[1]-1, intArr[2]); // 월이 +1 되서 들어가서 -1 해줘야한다.
                }
                break;
        }
        if(datePickerDialog != null) {
            datePickerDialog.show();
        }
    }
    // 현재 표시된 날짜를 가져와서 int 배열로 만드는 메서드
    private int[] setDate(String dateStr) {
        int[] resultArr = new int[3];
        if(dateStr != null) {
            if(dateStr.length() != 0) {
                int startIndex = 0;
                for(int i=0; i<3; i++) {
                    String tempStr = "";
                    if(dateStr.indexOf("-", startIndex) != -1) {
                        tempStr = dateStr.substring(startIndex, dateStr.indexOf("-", startIndex));
                    }else {
                        tempStr = dateStr.substring(startIndex, dateStr.length());
                    }
                    try {
                        resultArr[i] = Integer.parseInt(tempStr);
                    }catch(NumberFormatException nfe) {
                        return null;
                    }
                    startIndex = dateStr.indexOf("-", startIndex)+1;
                }
                return resultArr;
            }
        }
        return null;
    }

    //
    private long calculatorDate(String startStr, String lastStr) {
        if(startStr != null && lastStr != null) {
            if(startStr.length() != 0 && lastStr.length() != 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                try {
                    Date startDate = sdf.parse(startStr);
                    Date lastDate = sdf.parse(lastStr);
                    long resultDay = (lastDate.getTime() - startDate.getTime()) / (24*60*60*1000);
                    // startDate를 더 크게 설정한 경우에 음수가 나오는걸 막기위한 코드
                    if(resultDay < 0) {
                        resultDay += resultDay*(-2);
                    }
                    return resultDay;
                } catch (ParseException e) {
                }
            }
        }
        return -1L;
    }
}
