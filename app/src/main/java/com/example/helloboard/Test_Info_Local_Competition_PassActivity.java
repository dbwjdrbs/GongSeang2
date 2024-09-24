package com.example.helloboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Test_Info_Local_Competition_PassActivity extends AppCompatActivity {
    private Button btn_Gyeonggi;
    private Button btn_Gangwon;
    private Button btn_Chungcheong;
    private Button btn_Jeolla;
    private Button btn_Gyeongsang;
    private Button btn_Jeju;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_info_local_competition_pass);

        btn_Gyeonggi = findViewById(R.id.btn_Gyeonggi);
        btn_Gyeonggi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Test_Info_Local_Competition_PassActivity.this, Test_Info_Local_Area_Gyeonggi.class);
                startActivity(intent);
            }
        }); //경기도 지역 경쟁률&합격선 창으로 이동

        btn_Gangwon = findViewById(R.id.btn_Gangwon);
        btn_Gangwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Test_Info_Local_Competition_PassActivity.this, Test_Info_Local_Area_Gangwon.class);
                startActivity(intent);
            }
        }); //강원도 지역 경쟁률&합격선 창으로 이동

        btn_Chungcheong = findViewById(R.id.btn_Chungcheong);
        btn_Chungcheong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Test_Info_Local_Competition_PassActivity.this, Test_Info_Local_Area_Chungcheong.class);
                startActivity(intent);
            }
        }); //충청도 지역 경쟁률&합격선 창으로 이동

        btn_Jeolla = findViewById(R.id.btn_Jeolla);
        btn_Jeolla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Test_Info_Local_Competition_PassActivity.this, Test_Info_Local_Area_Jeolla.class);
                startActivity(intent);
            }
        }); //전라도 지역 경쟁률&합격선 창으로 이동

        btn_Gyeongsang = findViewById(R.id.btn_Gyeongsang);
        btn_Gyeongsang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Test_Info_Local_Competition_PassActivity.this, Test_Info_Local_Area_Gyeongsang.class);
                startActivity(intent);
            }
        }); //경상도 지역 경쟁률&합격선 창으로 이동
    }
}