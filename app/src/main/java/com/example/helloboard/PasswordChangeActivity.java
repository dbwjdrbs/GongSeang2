package com.example.helloboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

public class PasswordChangeActivity extends AppCompatActivity {

    private Button btn_OK_password;
    private EditText et_new_password, et_now_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        et_now_password = findViewById(R.id.et_now_password);
        et_new_password = findViewById(R.id.et_new_password);
        btn_OK_password = findViewById(R.id.btn_OK_password);
        btn_OK_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText에 현재 입력되어 있는 값을 get해온다
                String nowPass = et_now_password.getText().toString();
                String newPass = et_new_password.getText().toString();
                String pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,15}$";

                Boolean nowpw = Pattern.matches(pwPattern, nowPass);
                Boolean newpw = Pattern.matches(pwPattern, newPass);

                if (nowPass.getBytes().length <= 0) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(PasswordChangeActivity.this);
                    ad.setMessage("현재 비밀번호를 입력해주세요.");
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ad.show();
                } else if (nowpw == false) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(PasswordChangeActivity.this);
                    ad.setMessage("현재 비밀번호 양식(대소문자,숫자,특수문자를 포함한 8~15자)에 알맞지 않습니다.");
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ad.show();
                } else if (newPass.getBytes().length <= 0) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(PasswordChangeActivity.this);
                    ad.setMessage("새 비밀번호를 입력해주세요.");
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ad.show();
                } else if (newpw == false) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(PasswordChangeActivity.this);
                    ad.setMessage("새 비밀번호 양식(대소문자,숫자,특수문자를 포함한 8~15자)에 알맞지 않습니다.");
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ad.show();
                } else {
                    Response.Listener<String> responseListner = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");

                                if (success) {//성공할경우
                                    Toast.makeText(getApplicationContext(), "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(PasswordChangeActivity.this, MyPageActivity.class);
                                    intent.putExtra("nowPass", nowPass);
                                    intent.putExtra("newPass", newPass);
                                    startActivity(intent);
                                } else {//실패할경우
                                    Toast.makeText(getApplicationContext(), "비밀번호 변경에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest(nowPass, newPass, responseListner);
                    RequestQueue queue = Volley.newRequestQueue(PasswordChangeActivity.this);
                    queue.add(passwordChangeRequest);
                }
            }
        });

    }

}