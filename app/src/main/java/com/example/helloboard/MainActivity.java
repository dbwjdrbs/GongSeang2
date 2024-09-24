package com.example.helloboard;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private Button btn_testinfo;
    private Button btn_map;
    private Button btn_community;
    private Button btn_mypage;
    private Button btn_calendar_past;
    private Button btn_calendar_future;

    private TextView tv_calendar_date;
    private RecyclerView rv_calendar;
    private GregorianCalendar today = new GregorianCalendar();
    private int year = today.get(Calendar.YEAR), month = today.get(Calendar.MONTH);
    private static final int CALENDAR_EMPTY = 0, CALENDAR_DAY = 1;
    private String mdate;
    private static final int CODE_RESULT = 100;

    private SQLiteDatabase sqliteDB;

    private CalendarRecyclerAdapter adapter_calendar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        init_intent();
        sqliteDB = init_DB();
        init_tables();
        init_view();
        btn_clicked();
        setCalender(year,month);

        btn_testinfo = findViewById(R.id.btn_testinfo);
        btn_testinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , Test_InformationActivity.class);
                startActivity(intent);
            }
        });// 시험정보로 이동

        btn_map = findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , MapActivity.class);
                startActivity(intent);
            }
        }); //지도로 이동

        btn_community = findViewById(R.id.btn_community);
        btn_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , CommunityActivity.class);
                startActivity(intent);
            }
        }); //커뮤니티로 이동

        btn_mypage = findViewById(R.id.btn_mypage);
        btn_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , MyPageActivity.class);
                startActivity(intent);
            }
        }); //마이페이지로 이동


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == CODE_RESULT){
            sqliteDB = init_DB();
            init_tables();
            init_view();
            setCalender(year,month);
        }
    }

    private void init_intent(){
        Intent intent = getIntent();
    }

    private void init_view(){
        btn_calendar_past = (Button)findViewById(R.id.btn_calendar_past);
        btn_calendar_future = (Button)findViewById(R.id.btn_calendar_future);
        tv_calendar_date = (TextView)findViewById(R.id.tv_calendar_date);
        rv_calendar = (RecyclerView)findViewById(R.id.rv_calendar);
    }

    private SQLiteDatabase init_DB(){
        SQLiteDatabase db = null;

        File file = new File(getFilesDir(), "helloboard.db");
        try{
            db = SQLiteDatabase.openOrCreateDatabase(file,null);
        }catch (Exception e){
            e.printStackTrace();
        }

        return db;
    }

    private void init_tables(){
        if(sqliteDB != null) {
            String sqlCreateTbl = "CREATE TABLE IF NOT EXISTS SCHEDULE (" +
                    "DATE " + "TEXT," +
                    "CONTENT " + "TEXT," +
                    "LAT " + "DOUBLE," +
                    "LNG " + "DOUBLE" +
                    ")";
            sqliteDB.execSQL(sqlCreateTbl);
        }
    }

    private void btn_clicked(){
        btn_calendar_past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month--;
                rv_calendar.setAdapter(adapter_calendar);
                setCalender(year,month);
            }
        });

        btn_calendar_future.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month++;
                rv_calendar.setAdapter(adapter_calendar);
                setCalender(year,month);
            }
        });
    }

    private void setCalender(int year, int month){

        adapter_calendar = new CalendarRecyclerAdapter(year, month);
        rv_calendar.setLayoutManager(new StaggeredGridLayoutManager(7,StaggeredGridLayoutManager.VERTICAL));
        rv_calendar.setAdapter(adapter_calendar);
        GregorianCalendar calendar = new GregorianCalendar(year, month, 1);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) -1 ;
        int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        String str = "";

        tv_calendar_date.setText(Integer.toString(calendar.get(Calendar.YEAR)) + "." + Integer.toString(calendar.get(Calendar.MONTH)+1));
        for(int i = 0; i < dayOfWeek; i++){
            adapter_calendar.addItem(CALENDAR_EMPTY,0, false, 0);
        }
        for(int i = 1; i <= max; i++){
            mdate = Integer.toString((year * 10000) + ((month+1) * 100) + i);

            Cursor cursor_calendar_schedule = null;
            cursor_calendar_schedule = sqliteDB.rawQuery("SELECT * FROM SCHEDULE WHERE DATE = '" + mdate + "'", null);

            str += Integer.toString(cursor_calendar_schedule.getCount()) + " ";

            if(cursor_calendar_schedule.getCount() != 0) {
                adapter_calendar.addItem(CALENDAR_DAY, i, true, cursor_calendar_schedule.getCount());
            }
            else {
                adapter_calendar.addItem(CALENDAR_DAY, i, false, 0);
            }

            //날짜에 따른 일정 표시
        }
    }
}