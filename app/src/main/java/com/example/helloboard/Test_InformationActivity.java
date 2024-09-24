package com.example.helloboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Test_InformationActivity extends AppCompatActivity {

    private Button local_btn_competition;
    private Button national_btn_competition;
    private Button btn_schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_information);

        local_btn_competition = findViewById(R.id.local_btn_competition);
        local_btn_competition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Test_InformationActivity.this, Test_Info_Local_Competition_PassActivity.class);
                startActivity(intent);
            }
        }); //지방직 경쟁률&합격선 창으로 이동

        national_btn_competition = findViewById(R.id.national_btn_competition);
        national_btn_competition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Test_InformationActivity.this, Test_Info_National_Competition_PassActivity.class);
                startActivity(intent);
            }
        }); //국가직 경쟁률&합격선 창으로 이동

        btn_schedule = findViewById(R.id.btn_schedule);
        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Test_InformationActivity.this, TestScheduleActivity.class);
                startActivity(intent);
            }
        }); // 시험일정 창으로 이동
    }
}