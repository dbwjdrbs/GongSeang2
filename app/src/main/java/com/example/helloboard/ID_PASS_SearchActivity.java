package com.example.helloboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ID_PASS_SearchActivity extends AppCompatActivity {

    private Button btn_id_search;
    private Button btn_pass_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_pass_search);

        btn_id_search = findViewById(R.id.btn_id_search);
        btn_id_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ID_PASS_SearchActivity.this , Id_SearchActivity.class );
                startActivity(intent);
            }
        }); // 아이디 찾기 버튼

        btn_pass_search = findViewById(R.id.btn_pass_search);
        btn_pass_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ID_PASS_SearchActivity.this , Pass_SearchActivity.class);
                startActivity(intent);
            }
        }); //비밀번호 찾기 버튼


    }
}