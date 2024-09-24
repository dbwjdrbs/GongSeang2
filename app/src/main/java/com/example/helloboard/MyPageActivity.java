package com.example.helloboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MyPageActivity extends AppCompatActivity {

    private Button btn_nickname_change;
    private Button btn_password_change;
    private Button btn_withdrawal;
    private Button btn_logout;
    SharedPreferences setting;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        setting = getSharedPreferences("setting", 0);

        editor = setting.edit();

        btn_nickname_change = findViewById(R.id.btn_nickname_change);
        btn_nickname_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPageActivity.this, NicknameChangeActivity.class);
                startActivity(intent);
            }
        }); //닉네임 변경창으로 이동

        btn_password_change = findViewById(R.id.btn_password_change);
        btn_password_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPageActivity.this, PasswordChangeActivity.class);
                startActivity(intent);
            }
        }); //비밀번호 변경창으로 이동

        btn_withdrawal = findViewById(R.id.btn_withdrawal);
        btn_withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPageActivity.this, WithdrawalActivity.class);
                startActivity(intent);
            }
        }); //회원탈퇴창으로 이동

        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                editor.remove("ID");
                editor.remove("PW");
                editor.remove("au_login_enabled");
                editor.clear();
                editor.commit();
                Intent intent = new Intent(MyPageActivity.this, LoginActivity.class);
                startActivity(intent);
    }
}); //로그인창으로 이동

       }
}