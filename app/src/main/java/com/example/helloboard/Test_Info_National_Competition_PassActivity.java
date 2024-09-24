package com.example.helloboard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


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

public class Test_Info_National_Competition_PassActivity extends AppCompatActivity {

    private static String TAG = "MyApplication_Test_Info_National_Competition_PassActivity";

    private static final String TAG_JSON = "tset_one";
    private static final String TAG_RECRUITMENT = "Recruitment"; //모집단위
    private static final String TAG_PASSLINE = "PassLine"; //합격선
    private static final String TAG_COMPETITION = "competition"; //경쟁률

    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList; //json 결과값을 HashMap key, value형식으로 저장할 ArrayList
    ListView mlistView;
    String mJsonString; //php에서 json 결과 값을 저장할 String 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_info_national_competition_pass);

        mTextViewResult = (TextView) findViewById(R.id.textView_main_result);
        mlistView = (ListView) findViewById(R.id.listView_main_list);
        mArrayList = new ArrayList<>();

        //DB에서 가져온 php 웹사이트 질의 결과를 밑에서 정의한 GetData 클래스를 통해 json형식으로 결과 값을 가져와 출력
        GetData task = new GetData();
        task.execute("http://ad66025.dothome.co.kr/getjson_National_Competition_PassActivity.php");
    }

    //php 결과 값을 json형식으로 가져오고 showResult() 메소드를 호출하여 출력하는 AsyncTask 상속 받은 GetData 클래스
    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Test_Info_National_Competition_PassActivity.this,
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

    //가져온 결과를 hashMap에 key, value형식으로 저장하고 이 hashMap을 ArrayList에 저장한 후 adapter로 listview에 연결하여 결과 출력
    private void showResult() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String Recruitment = item.getString(TAG_RECRUITMENT);
                String PassLine = item.getString(TAG_PASSLINE);
                String competition = item.getString(TAG_COMPETITION);

                System.out.println(Recruitment + PassLine + competition);

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_RECRUITMENT, Recruitment);
                hashMap.put(TAG_PASSLINE, PassLine);
                hashMap.put(TAG_COMPETITION, competition);

                mArrayList.add(hashMap);
            }
            ListAdapter adapter = new SimpleAdapter(
                    Test_Info_National_Competition_PassActivity.this, mArrayList, R.layout.item_list,
                    new String[]{TAG_RECRUITMENT, TAG_PASSLINE, TAG_COMPETITION},
                    new int[]{R.id.textView_list_id, R.id.textView_list_name, R.id.textView_list_address}
            );
            mlistView.setAdapter(adapter);
        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }
}