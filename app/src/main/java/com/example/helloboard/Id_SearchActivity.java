package com.example.helloboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Id_SearchActivity extends AppCompatActivity {

    private EditText et_name, et_email;
    private Button btn_OK_idsearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_search);

        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);

        btn_OK_idsearch = findViewById(R.id.btn_OK_idsearch);

        et_name.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern ps = Pattern.compile("^[가-힣ㄱ-ㅎㅏ-ㅣ\u318D\u119E\u11A2\u2022\u00B7\uFE55]+$");
                if (source.equals("") || ps.matcher(source).matches()) {
                    return source;
                }
                return "";
            }
        },new InputFilter.LengthFilter(6)});
        btn_OK_idsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = et_name.getText().toString();
                String userEmail = et_email.getText().toString();
                Pattern p_email = Pattern.compile("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z]){2,20}@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}");
                Matcher m_mail = p_email.matcher(userEmail);
                if (userName.getBytes().length <= 0) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(Id_SearchActivity.this);
                    ad.setMessage("이름을 입력해주세요");
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    ad.show();
                } else if (userEmail.getBytes().length <= 0) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(Id_SearchActivity.this);
                    ad.setMessage("이메일을 입력해주세요");
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ad.show();
                } else if (!m_mail.matches()) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(Id_SearchActivity.this);
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
                                    String userName = jsonObject.getString("userName");
                                    String userEmail = jsonObject.getString("userEmail");
                                    String userId = jsonObject.getString("userID");
                                    AlertDialog.Builder ad = new AlertDialog.Builder(Id_SearchActivity.this);
                                    ad.setMessage("아이디는 " + userId + "입니다.");
                                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(Id_SearchActivity.this, LoginActivity.class);
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


                    Id_SearchRequest id_SearchRequest = new Id_SearchRequest(userName, userEmail, responseListner);
                    RequestQueue queue = Volley.newRequestQueue(Id_SearchActivity.this);
                    queue.add(id_SearchRequest);
                }
            }//Intent intent = new Intent(Id_SearchActivity.this , LoginActivity.class);
            //startActivity(intent);
            // }
        });
        //로그인 화면으로
    }

}