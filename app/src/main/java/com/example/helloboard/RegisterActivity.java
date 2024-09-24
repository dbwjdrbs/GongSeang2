package com.example.helloboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance(); // 파이어베이스에서 가입정보를 받아옴
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance(); // mStore라는 이름으로 파이어베이스 DB를 받음

    private EditText mId, mPasswordText, mName, mEmail, mNick; // EditText의 변수명을 지정해준거겠지?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mId = findViewById(R.id.reg_id);
        mPasswordText = findViewById(R.id.reg_password);
        mName = findViewById(R.id.reg_name);
//        mEmail = findViewById(R.id.reg_);
        mNick = findViewById(R.id.reg_nickname);
        findViewById(R.id.btn_register1).setOnClickListener(this);


        InputFilter[] filters = new InputFilter[]{new ByteLengthFilter(13, "KSC5601")};
        mNick.setFilters(filters);

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
        mName.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern ps = Pattern.compile("^[가-힣ㄱ-ㅎㅏ-ㅣ\u318D\u119E\u11A2\u2022\u00B7\uFE55]+$");
                if (source.equals("") || ps.matcher(source).matches()) {
                    return source;
                }
                return "";
            }
        }, new InputFilter.LengthFilter(6)});
    }

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
    public void onClick(View view) { //회원가입
        //EditText에 현재 입력되어 있는 값을 get해온다
        String userID = mId.getText().toString();
        String userPass = mPasswordText.getText().toString();
        String userName = mName.getText().toString();
//        String userEmail = mEmail.getText().toString();
        String userNick = mNick.getText().toString();
        String pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,15}$";
        Pattern p_email = Pattern.compile("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z]){2,20}@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}");
        Matcher m_mail = p_email.matcher(userID);
        Boolean pw = Pattern.matches(pwPattern, userPass);

        if (userID.getBytes().length <= 0) {
            AlertDialog.Builder ad = new AlertDialog.Builder(RegisterActivity.this);
            ad.setMessage("아이디를 입력해주세요");
            ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            ad.show();
        } else if (userID.getBytes().length <= 2) {
            AlertDialog.Builder ad = new AlertDialog.Builder(RegisterActivity.this);
            ad.setMessage("아이디가 3글자 이상이어야합니다.");
            ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            ad.show();
        } else if (!m_mail.matches()) {
            AlertDialog.Builder ad = new AlertDialog.Builder(RegisterActivity.this);
            ad.setMessage("이메일양식에 알맞지 않습니다");
            ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            ad.show();
        } else if (userPass.getBytes().length <= 0) {
            AlertDialog.Builder ad = new AlertDialog.Builder(RegisterActivity.this);
            ad.setMessage("비밀번호를 입력해주세요.");
            ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            ad.show();
        } else if (pw == false) {
            AlertDialog.Builder ad = new AlertDialog.Builder(RegisterActivity.this);
            ad.setMessage("비밀번호 양식(대소문자,숫자,특수문자를 포함한 8~15자)에 알맞지 않습니다.");
            ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            ad.show();
        } else if (userName.getBytes().length <= 0) {
            AlertDialog.Builder ad = new AlertDialog.Builder(RegisterActivity.this);
            ad.setMessage("이름을 입력해주세요.");
            ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            ad.show();
//        } else if (userEmail.getBytes().length <= 0) {
//            AlertDialog.Builder ad = new AlertDialog.Builder(RegisterActivity.this);
//            ad.setMessage("이메일을 입력해주세요.");
//            ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                }
//            });
//            ad.show();
//        } else if (!m_mail.matches()) {
//            AlertDialog.Builder ad = new AlertDialog.Builder(RegisterActivity.this);
//            ad.setMessage("이메일양식에 알맞지 않습니다");
//            ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                }
//            });
//            ad.show();
        } else if (userNick.getBytes().length <= 0) {
            AlertDialog.Builder ad = new AlertDialog.Builder(RegisterActivity.this);
            ad.setMessage("닉네임을 입력해주세요.");
            ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            ad.show();
        }else{
            mAuth.createUserWithEmailAndPassword(mId.getText().toString(), mPasswordText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Map<String, Object> userMap = new HashMap<>();  // Firestore DB 저장
                                userMap.put(FirebaseID.documentId, user.getUid());
                                userMap.put(FirebaseID.email, mId.getText().toString());
                                userMap.put(FirebaseID.password, mPasswordText.getText().toString());
                                userMap.put(FirebaseID.nickname, mNick.getText().toString());
                                userMap.put(FirebaseID.name, mName.getText().toString());
                                mStore.collection(FirebaseID.user).document(user.getUid()).set(userMap, SetOptions.merge()); //merge를 쓰면 DB에 덮어쓰기가 됨. user DB
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegisterActivity.this, "회원가입 에러", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
}