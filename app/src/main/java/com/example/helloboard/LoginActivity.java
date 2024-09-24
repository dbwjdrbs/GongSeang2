package com.example.helloboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private CheckBox au_login;
    private EditText mId, mPassword;
    SharedPreferences setting;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mId = findViewById(R.id.login_id);
        mPassword = findViewById(R.id.login_password);
        au_login = findViewById(R.id.au_login);

        findViewById(R.id.login_register).setOnClickListener(this);
        findViewById(R.id.login_login).setOnClickListener(this);
        findViewById(R.id.login_id_pass_search).setOnClickListener(this);

        setting = getSharedPreferences("setting", 0);
        editor = setting.edit();

        au_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String ID = mId.getText().toString();
                    String PW = mPassword.getText().toString();
                    editor.putString("ID", ID);
                    editor.putString("PW", PW);
                    editor.putBoolean("au_login_enabled", true);
                    editor.commit();
                } else {
                    editor.remove("ID");
                    editor.remove("PW");
                    editor.remove("au_login_enabled");
                    editor.clear();
                    editor.commit();
                }
            }

        });
        if (setting.getBoolean("au_login_enabled", false)) {
            mId.setText(setting.getString("ID", ""));
            mPassword.setText(setting.getString("PW", ""));
            au_login.setChecked(true);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        mId.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern ps = Pattern.compile("^[0-9a-zA-Z@\\.\\_\\-]+$");
                if (source.equals("") || ps.matcher(source).matches()) {
                    return source;
                }
                return "";
            }
        }, new InputFilter.LengthFilter(30)});
    }

    @Override
    public void onClick(View v) {
        //EditText에 현재 입력되어 있는 값을 get해온다
        String userID = mId.getText().toString();
        String userPass = mPassword.getText().toString();

        switch (v.getId()) {
            case R.id.login_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.login_id_pass_search:
                startActivity(new Intent(this, ID_PASS_SearchActivity.class));
                break;

            case R.id.login_login:
                if (userID.getBytes().length <= 0) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(LoginActivity.this);
                    ad.setMessage("아이디를 입력해주세요");
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ad.show();
                } else if (userPass.getBytes().length <= 0) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(LoginActivity.this);
                    ad.setMessage("비밀번호를 입력해주세요");
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ad.show();
                } else {
                    mAuth.signInWithEmailAndPassword(mId.getText().toString(), mPassword.getText().toString()) // ?뚯씠?대쿋?댁뒪 濡쒓렇??湲곕뒫
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user != null) {
                                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        }
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    break;
                }
        }
    }
}