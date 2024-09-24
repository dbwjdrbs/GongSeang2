package com.example.helloboard;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Id_SearchRequest extends StringRequest {

    //서버 URL 설정 (php파일연동)
    final static private  String URL = "http://ad66025.dothome.co.kr/Id_search.php";
    private Map<String,String> map;




    public Id_SearchRequest(String userName, String userEmail, Response.Listener<String> listener){
        super(Method.POST,URL,listener, null);

        map = new HashMap<>();
        map.put("userName",userName);
        map.put("userEmail",userEmail);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}