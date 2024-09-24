package com.example.helloboard.models;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helloboard.Data.Address;
import com.example.helloboard.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarScheduleShowActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mv_calendar_schedule_show;
    private Button btn_calendar_schedule_show_delete;
    private TextView tv_calendar_schedule_show_date;
    private TextView tv_calendar_schedule_show_content;
    private TextView tv_calendar_schedule_show_gps;

    private String content;

    private SQLiteDatabase sqliteDB;

    private Marker marker = new Marker();

    private double lat, lng;
    private NaverMap naverMap;
    private String maddress, mdate;
    private String key_id = "7q0lgka690";
    private String key = "SnCg0qPzDElfRnn9EHAWEWrIN4nyxnEKhwnIVmIQ";
    private int year, month, day;

    private ReverseGeocoderClient reverseGeocoderClient;
    private ReverseGeocoderInterface reverseGeocoderInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_schedule_show);
        //날짜 표시

        init_intent();

        init_view();
        sqliteDB = init_DB();
        init_tables();

        tv_calendar_schedule_show_date.setText(mdate);
        tv_calendar_schedule_show_content.setText(content);
        mv_calendar_schedule_show.onCreate(savedInstanceState);
        mv_calendar_schedule_show.getMapAsync(this);

        delete_values();
    }

    private void init_view(){
        mv_calendar_schedule_show = (MapView)findViewById(R.id.mv_calendar_schedule_show);
        btn_calendar_schedule_show_delete = (Button)findViewById(R.id.btn_calendar_schedule_show_delete);
        tv_calendar_schedule_show_date = (TextView)findViewById(R.id.tv_calendar_schedule_show_date);
        tv_calendar_schedule_show_content = (TextView)findViewById(R.id.tv_calendar_schedule_show_content);
        tv_calendar_schedule_show_gps = (TextView)findViewById(R.id.tv_calendar_schedule_show_gps);
    }

    private void init_intent(){
        Intent intent = getIntent();

        content = intent.getStringExtra("content");
        lat = intent.getDoubleExtra("lat",1);
        lng = intent.getDoubleExtra("lng",1);
        year = intent.getIntExtra("year",1);
        month = intent.getIntExtra("month", 1);
        day = intent.getIntExtra("day",1);

        mdate = Integer.toString((year * 10000) + ((month+1) * 100) + day);
    }

    private SQLiteDatabase init_DB(){
        SQLiteDatabase db = null;

        File file = new File(getFilesDir(), "helloboard.db");
        try{
            db = SQLiteDatabase.openOrCreateDatabase(file,null);
        }catch (Exception e){
            e.printStackTrace();
        }

        return db;
    }

    private void init_tables(){
        if(sqliteDB != null) {
            String sqlCreateTbl = "CREATE TABLE IF NOT EXISTS SCHEDULE (" +
                    "DATE " + "TEXT," +
                    "CONTENT " + "TEXT," +
                    "LAT " + "DOUBLE," +
                    "LNG " + "DOUBLE" +
                    ")";
            sqliteDB.execSQL(sqlCreateTbl);
        }
    }

    private void delete_values(){
        btn_calendar_schedule_show_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqliteDB.execSQL("DELETE FROM SCHEDULE WHERE CONTENT = '" + content + "'");
                setResult(100);
                finish();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        CameraPosition cameraPosition = new CameraPosition(new LatLng(lat, lng),16);
        naverMap.setCameraPosition(cameraPosition);

        marker.setPosition(new LatLng(lat, lng));
        marker.setMap(naverMap);
        CallRetrofit(new LatLng(lat, lng));
    }

    public void CallRetrofit(LatLng latlng){
        try {
            reverseGeocoderClient = ReverseGeocoderClient.getInstance();
            reverseGeocoderInterface = ReverseGeocoderClient.getReverseGeocoderInterface();

            reverseGeocoderInterface.getAddress(Double.toString(latlng.longitude) + "," + Double.toString(latlng.latitude),
                    "json",
                    "addr,roadaddr",
                    key_id,
                    key).enqueue(new Callback<Address>() {
                @Override
                public void onResponse(Call<Address> call, Response<Address> response) {
                    Address address = response.body();
                    if(address.getStatus().getCode()==0) {
                        maddress = address.getResults().get(0).getRegion().getArea1().getName() + " " +
                                address.getResults().get(0).getRegion().getArea2().getName() + " " +
                                address.getResults().get(0).getRegion().getArea3().getName();

                        if(address.getResults().size() == 1) {
                            maddress += " " + address.getResults().get(0).getLand().getNumber1();
                            if(address.getResults().get(0).getLand().getNumber2().length() != 0)
                                maddress += "-" + address.getResults().get(0).getLand().getNumber2();
                        }else{
                            maddress += " " + address.getResults().get(0).getLand().getNumber1();
                            if(address.getResults().get(0).getLand().getNumber2().length() != 0)
                                maddress += "-" + address.getResults().get(0).getLand().getNumber2();
                            maddress += "\n" + address.getResults().get(1).getLand().getAddition0().getValue();
                        }

                        //tv_calendar_schedule_add_address.setText(Integer.toString(address.getResults().size()));
                        tv_calendar_schedule_show_gps.setText(maddress);
                    }
                    else{
                        tv_calendar_schedule_show_gps.setText("위치 정보가 없습니다.");
                    }
                }

                @Override
                public void onFailure(Call<Address> call, Throwable t) {
                    tv_calendar_schedule_show_gps.setText(t.getMessage());
                }
            });
        }catch (Exception e){
            tv_calendar_schedule_show_gps.setText(e.getMessage());
        }
    }
}
