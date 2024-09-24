package com.example.helloboard;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestScheduleActivity extends AppCompatActivity {
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_schedule);

        btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gosi.kr/receipt/selectScheduleList.do"));
                startActivity(intent);
            }
        }); //5급, 7급, 9급 민간경력자 공채 일정 홈페이지로 이동

        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gosi.assembly.go.kr/board/examList.do"));
                startActivity(intent);
            }
        }); //국회공무원 공채 일정 홈페이지로 이동

        btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nec.go.kr/site/nec/ex/bbs/List.do?cbIdx=1087"));
                startActivity(intent);
            }
        }); //중앙선관위 공채 일정 홈페이지로 이동

        btn4 = (Button) findViewById(R.id.button4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://career.nis.go.kr:4017/info/notice/list.html"));
                startActivity(intent);
            }
        }); //국가정보원 공채 일정 홈페이지로 이동

        btn5 = (Button) findViewById(R.id.button5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://exam.scourt.go.kr/wex/exinfo/info01.jsp"));
                startActivity(intent);
            }
        }); //법정공무원 공채 일정 홈페이지로 이동

        btn6 = (Button) findViewById(R.id.button6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.nfsa.go.kr/nfsa/news/0011/job/"));
                startActivity(intent);
            }
        }); //소방공무원 공채 일정 홈페이지로 이동

        btn7 = (Button) findViewById(R.id.button7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://public.jinhakapply.com/PoliceV2/public/public_1.aspx"));
                startActivity(intent);
            }
        }); //경찰공무원 공채 일정 홈페이지로 이동
    }
}