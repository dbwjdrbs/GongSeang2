package com.example.helloboard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Test_Info_Local_Area_Gyeongsang extends AppCompatActivity {
    private static String TAG = "MyApplication_Test_Info_Local_Area_Gyeongsang";

    private static final String TAG_JSON = "tset_one";
    private static final String TAG_AREA = "area"; //지역명
    private static final String TAG_PASSLINE = "PassLine"; //합격선
    private static final String TAG_COMPETITION = "competition"; //경쟁률

    private TextView mTextViewResult;
    TextView mTextView;
    String mJsonString; //php에서 json 결과 값을 저장할 String 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_info_local_area_gyeongsang);

        mTextViewResult = (TextView) findViewById(R.id.textView_main_result);
        mTextView = (TextView) findViewById(R.id.textView_main_text);
        mTextView.setMovementMethod(new ScrollingMovementMethod());

        //스피너 정의
        final String[] local_category_list = {"경상도 9급 지방직(일행직) 경쟁률/합격선",
                "경상도 9급 지방직(사복직) 경쟁률/합격선",
                "경상도 9급 지방직(세무직) 경쟁률/합격선"};

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, local_category_list);
        spinner.setAdapter(adapter);
        //선택된 스피너 결과에 따라 DB에서 가져온 php 웹사이트 질의 결과를 밑에서 정의한 GetData 클래스를 통해 json형식으로 결과 값을 가져와 출력
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (position == 0) {
                    mTextView.setText(null);
                    GetData_Gyeongsang task = new GetData_Gyeongsang();
                    task.execute("http://ad66025.dothome.co.kr/getjson_gyeongsang_general_ad.php");
                }
                if (position == 1) {
                    mTextView.setText(null);
                    GetData_Gyeongsang task = new GetData_Gyeongsang();
                    task.execute("http://ad66025.dothome.co.kr/getjson_gyeongsang_social_welfare.php");
                }
                if (position == 2) {
                    mTextView.setText(null);
                    GetData_Gyeongsang task = new GetData_Gyeongsang();
                    task.execute("http://ad66025.dothome.co.kr/getjson_gyeongsang_tax.php");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    //php 결과 값을 json형식으로 가져오고 showResult() 메소드를 호출하여 출력하는 AsyncTask 상속 받은 GetData 클래스
    private class GetData_Gyeongsang extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Test_Info_Local_Area_Gyeongsang.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null) {
                mTextViewResult.setText(errorString);
            } else {
                mJsonString = result;
                System.out.println(mJsonString);
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }
        }
    }

    //가져온 결과를 textview에 출력
    private void showResult() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String area = item.getString(TAG_AREA);
                String pass_line = item.getString(TAG_PASSLINE);
                String competition = item.getString(TAG_COMPETITION);

                mTextView.append(area + "           " + pass_line + "           " + competition + "\n");

            }
        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }
}