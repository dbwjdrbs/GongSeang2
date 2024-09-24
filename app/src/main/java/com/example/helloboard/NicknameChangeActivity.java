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


public class NicknameChangeActivity extends AppCompatActivity {

    private Button btn_OK_nickname;
    private EditText et_id, et_new_nickname;

    public class ByteLengthFilter implements InputFilter {

        private String mCharset; //인코딩 문자셋

        protected int mMaxByte; // 입력가능한 최대 바이트 길이

        public ByteLengthFilter(int maxbyte, String charset) {
            this.mMaxByte = maxbyte;
            this.mCharset = charset;
        }

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
                                   int dend) {

            // 변경 후 예상되는 문자열
            String expected = new String();
            expected += dest.subSequence(0, dstart);
            expected += source.subSequence(start, end);
            expected += dest.subSequence(dend, dest.length());

            int keep = calculateMaxLength(expected) - (dest.length() - (dend - dstart));

            if (keep <= 0) {
                return ""; // source 입력 불가(원래 문자열 변경 없음)
            } else if (keep >= end - start) {
                return null; // keep original. source 그대로 허용
            } else {
                return source.subSequence(start, start + keep); // source중 일부만 입력 허용
            }
        }

        protected int calculateMaxLength(String expected) {
            return mMaxByte - (getByteLength(expected) - expected.length());
        }

        private int getByteLength(String str) {
            try {
                return str.getBytes(mCharset).length;
            } catch (UnsupportedEncodingException e) {
                //e.printStackTrace();
            }
            return 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname_change);

        et_id = findViewById(R.id.et_id);
        et_new_nickname = findViewById(R.id.et_new_nickname);
        btn_OK_nickname = findViewById(R.id.btn_OK_nickname);

        InputFilter[] filters = new InputFilter[]{new NicknameChangeActivity.ByteLengthFilter(13, "KSC5601")};
        et_new_nickname.setFilters(filters);
        et_id.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern ps = Pattern.compile("^[-_a-zA-Z0-9]+$");
                if (source.equals("") || ps.matcher(source).matches()) {
                    return source;
                }
                return "";
            }
        }, new InputFilter.LengthFilter(15)});

        btn_OK_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText에 현재 입력되어 있는 값을 get해온다
                String userID = et_id.getText().toString();
                String newNick = et_new_nickname.getText().toString();

                Response.Listener<String> responseListner = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (userID.getBytes().length <= 0) {
                                AlertDialog.Builder ad = new AlertDialog.Builder(NicknameChangeActivity.this);
                                ad.setMessage("아이디를 입력해주세요");
                                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                ad.show();
                            } else if (userID.getBytes().length <= 2) {
                                AlertDialog.Builder ad = new AlertDialog.Builder(NicknameChangeActivity.this);
                                ad.setMessage("아이디가 3글자 이상이어야합니다.");
                                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                ad.show();
                            } else if (newNick.getBytes().length <= 0) {
                                AlertDialog.Builder ad = new AlertDialog.Builder(NicknameChangeActivity.this);
                                ad.setMessage("닉네임을 입력해주세요.");
                                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                ad.show();
                            } else if (success) {//성공할경우
                                Toast.makeText(getApplicationContext(), "닉네임 변경에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NicknameChangeActivity.this, MyPageActivity.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("newNick", newNick);
                                startActivity(intent);
                            } else {//실패할경우
                                Toast.makeText(getApplicationContext(), "닉네임 변경에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                NicknameChangeRequest nicknameChangeRequest = new NicknameChangeRequest(newNick, userID, responseListner);
                RequestQueue queue = Volley.newRequestQueue(NicknameChangeActivity.this);
                queue.add(nicknameChangeRequest);
            }
        });
    }
}