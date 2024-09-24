package com.example.helloboard;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pass_SearchActivity extends AppCompatActivity {

    private EditText et_id, et_name, et_email;
    private Button btn_OK_pass_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_search);

        et_id = findViewById(R.id.et_id);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        btn_OK_pass_search = findViewById(R.id.btn_OK_pass_search);

        et_name.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern ps = Pattern.compile("^[가-힣ㄱ-ㅎㅏ-ㅣ\u318D\u119E\u11A2\u2022\u00B7\uFE55]+$");
                if (source.equals("") || ps.matcher(source).matches()) {
                    return source;
                }
                return "";
            }
        }, new InputFilter.LengthFilter(6)});
        et_id.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern ps = Pattern.compile("^[a-zA-Z0-9]");
                if (source.equals("") || ps.matcher(source).matches()) {
                    return source;
                }
                return "";
            }
        }, new InputFilter.LengthFilter(15)});

        btn_OK_pass_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText에 현재 입력되어 있는 값을 get해온다
                String userID = et_id.getText().toString();
                String userName = et_name.getText().toString();
                String userEmail = et_email.getText().toString();
                Pattern p_email = Pattern.compile("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z]){2,20}@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}");
                Matcher m_mail = p_email.matcher(userEmail);

                if (userID.getBytes().length <= 0) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(Pass_SearchActivity.this);
                    ad.setMessage("아이디를 입력해주세요");
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ad.show();
                } else if (userID.getBytes().length <= 2) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(Pass_SearchActivity.this);
                    ad.setMessage("아이디가 3글자 이상이어야합니다.");
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ad.show();
                } else if (userName.getBytes().length <= 0) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(Pass_SearchActivity.this);
                    ad.setMessage("이름을 입력해주세요");
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ad.show();
                } else if (userEmail.getBytes().length <= 0) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(Pass_SearchActivity.this);
                    ad.setMessage("이메일을 입력해주세요");
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ad.show();
                } else if (!m_mail.matches()) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(Pass_SearchActivity.this);
                    ad.setMessage("이메일양식에 알맞지 않습니다");
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
                                    String userID = jsonObject.getString("userID");
                                    String userPass = jsonObject.getString("userPassword");
                                    String userName = jsonObject.getString("userName");
                                    String userEmail = jsonObject.getString("userEmail");
                                    AlertDialog.Builder ad = new AlertDialog.Builder(Pass_SearchActivity.this);
                                    ad.setMessage("비밀번호는 " + userPass + "입니다.");
                                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(Pass_SearchActivity.this, LoginActivity.class);
                                            intent.putExtra("userID", userID);
                                            intent.putExtra("userName", userName);
                                            intent.putExtra("userEmail", userEmail);
                                            startActivity(intent);
                                        }
                                    });
                                    ad.show();

                                } else {//실패할경우
                                    Toast.makeText(getApplicationContext(), "해당 이용자는 없습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    Pass_SearchRequest pass_searchRequest = new Pass_SearchRequest(userID, userName, userEmail, responseListner);
                    RequestQueue queue = Volley.newRequestQueue(Pass_SearchActivity.this);
                    queue.add(pass_searchRequest);
                }
            }
        });

    }
}



