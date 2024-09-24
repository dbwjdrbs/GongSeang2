package com.example.helloboard;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WithdrawalRequest extends StringRequest {

    //서버 URL 설정 (php파일연동)
    final static private  String URL = "http://ad66025.dothome.co.kr/Withdrawal.php";
    private Map<String,String> map;




    public WithdrawalRequest(String userPassword, Response.Listener<String> listener){
        super(Method.POST,URL,listener, null);

        map = new HashMap<>();
        map.put("userPassword",userPassword);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}