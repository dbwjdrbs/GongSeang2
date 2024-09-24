package com.example.helloboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class CommunityActivity extends AppCompatActivity {
    private Button btn_freeboard;
    private Button btn_studyboard;
    private Button btn_recommendboard;
    private Button btn_testreviewboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        /* 버튼 파인드뷰 */
        btn_freeboard = findViewById(R.id.btn_freeboard);
        btn_studyboard = findViewById(R.id.btn_studyboard);
        btn_recommendboard = findViewById(R.id.btn_recommendboard);
        btn_testreviewboard = findViewById(R.id.btn_testreviewboard);

        /* 자유게시판 온클릭 이벤트 */
        btn_freeboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CommunityActivity.this, FreeBoardActivity.class));
            }
        });

        /* 스터디게시판 온클릭 이벤트 */
        btn_studyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CommunityActivity.this, StudyBoardActivity.class));
            }
        });

        /* 교재, 강의 추천게시판 온클릭 이벤트 */
        btn_recommendboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CommunityActivity.this, RecommendBoardActivity.class));
            }
        });

        /* 합격후기게시판 온클릭 이벤트 */
        btn_testreviewboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CommunityActivity.this, ReviewBoardActivity.class));
            }
        });
    }
}