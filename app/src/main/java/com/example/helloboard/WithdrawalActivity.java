package com.example.helloboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class WithdrawalActivity extends AppCompatActivity {

    private EditText et_password;
    private Button btn_withdrawal;

    SharedPreferences setting;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);

        setting = getSharedPreferences("setting", 0);

        editor = setting.edit();

        et_password = findViewById(R.id.et_password);
        btn_withdrawal = findViewById(R.id.btn_withdrawal);

        btn_withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText에 현재 입력되어 있는 값을 get해온다
                String userPass = et_password.getText().toString();
                String pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,15}$";

                Boolean pw = Pattern.matches(pwPattern, userPass);

                if (userPass.getBytes().length <= 0) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(WithdrawalActivity.this);
                    ad.setMessage("비밀번호를 입력해주세요.");
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ad.show();
                } else if (pw == false) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(WithdrawalActivity.this);
                    ad.setMessage("비밀번호 양식(대소문자,숫자,특수문자를 포함한 8~15자)에 알맞지 않습니다.");
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
                                    Toast.makeText(getApplicationContext(), "회원탈퇴가 정상적으로 되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(WithdrawalActivity.this, LoginActivity.class);
                                    intent.putExtra("userPass", userPass);
                                    editor.remove("ID");
                                    editor.remove("PW");
                                    editor.remove("au_login_enabled");
                                    editor.clear();
                                    editor.commit();
                                    startActivity(intent);

                                } else {//실패할경우
                                    Toast.makeText(getApplicationContext(), "해당 이용자는 없습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    WithdrawalRequest withdrawalRequest = new WithdrawalRequest(userPass, responseListner);
                    RequestQueue queue = Volley.newRequestQueue(WithdrawalActivity.this);
                    queue.add(withdrawalRequest);
                }
            }
        });

    }

}

