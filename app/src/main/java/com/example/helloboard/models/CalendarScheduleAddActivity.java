package com.example.helloboard.models;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helloboard.Data.Address;
import com.example.helloboard.models.GpsGetter;
import com.example.helloboard.R;
import com.example.helloboard.models.ReverseGeocoderClient;
import com.example.helloboard.models.ReverseGeocoderInterface;
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

public class CalendarScheduleAddActivity extends AppCompatActivity implements OnMapReadyCallback, NaverMap.OnMapClickListener {
    private Button btn_calendar_schedule_add_ok;
    private MapView mv_calendar_schedule_add;
    private EditText et_calendar_schedule_add;
    private TextView tv_calendar_schedule_add_date;
    private TextView tv_calendar_schedule_add_address;

    private ReverseGeocoderClient reverseGeocoderClient;
    private ReverseGeocoderInterface reverseGeocoderInterface;

    private int year, month, day;
    private String key_id = "7q0lgka690";
    private String key = "SnCg0qPzDElfRnn9EHAWEWrIN4nyxnEKhwnIVmIQ";
    private String maddress, mdate = "hi";

    private SQLiteDatabase sqliteDB;

    private Marker marker = new Marker();
    private LatLng mlatlng = null;

    private static NaverMap naverMap;

    private GpsGetter gpsGetter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_schedule_add);

        init_intent();

        init_view();
        sqliteDB = init_DB();
        init_tables();

        save_values();
        mv_calendar_schedule_add.onCreate(savedInstanceState);
        mv_calendar_schedule_add.getMapAsync(this);

        mdate = Integer.toString((year * 10000) + ((month+1) * 100) + day);

        try {
            tv_calendar_schedule_add_date.setText(mdate);
        }catch (Exception e){
            tv_calendar_schedule_add_date.setText(e.getMessage());
        }

    }

    private void init_view(){
        btn_calendar_schedule_add_ok = (Button)findViewById(R.id.btn_calendar_schedule_add_ok);
        mv_calendar_schedule_add = (MapView)findViewById(R.id.mv_calendar_schedule_add);
        et_calendar_schedule_add = (EditText)findViewById(R.id.et_calendar_schedule_add);
        tv_calendar_schedule_add_date = (TextView)findViewById(R.id.tv_calendar_schedule_add_date);
        tv_calendar_schedule_add_address = (TextView)findViewById(R.id.tv_calendar_schedule_add_address);
    }

    private void init_intent(){
        Intent intent = getIntent();
        year = intent.getIntExtra("year",1);
        month = intent.getIntExtra("month",1);
        day = intent.getIntExtra("day",1);
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

    private void save_values(){
        btn_calendar_schedule_add_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String sqlInsert = "INSERT INTO SCHEDULE" +
                            "(DATE, CONTENT, LAT, LNG) VALUES (" +
                            "'" + mdate + "'" + "," +
                            "'" + et_calendar_schedule_add.getText().toString() + "'" + "," +
                            mlatlng.latitude + "," +
                            mlatlng.longitude + ")";
                    sqliteDB.execSQL(sqlInsert);

                    et_calendar_schedule_add.setText("");
                    setResult(100);
                    Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }catch (Exception e){
                    et_calendar_schedule_add.setText(e.getMessage());
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        gpsGetter = new GpsGetter(this);
        mlatlng = new LatLng(gpsGetter.getLatitude(), gpsGetter.getLongitude());

        CameraPosition cameraPosition = new CameraPosition(mlatlng,16);

        naverMap.setCameraPosition(cameraPosition);

        naverMap.setOnMapClickListener(this);

        marker.setPosition(mlatlng);
        marker.setMap(naverMap);
        CallRetrofit(mlatlng);
    }

    @Override
    public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
        marker.setMap(null);
        marker.setPosition(latLng);
        marker.setMap(naverMap);

        //tv_calendar_schedule_add_address.setText( Double.toString(latLng.latitude) + "/" + Double.toString(latLng.longitude));
        CallRetrofit(latLng);
    }

    public void CallRetrofit(LatLng latlng){
        try {
            reverseGeocoderClient = ReverseGeocoderClient.getInstance();
            reverseGeocoderInterface = ReverseGeocoderClient.getReverseGeocoderInterface();

            mlatlng = latlng;
            reverseGeocoderInterface.getAddress(Double.toString(latlng.longitude) + "," + Double.toString(latlng.latitude),
                    "json",
                    "addr,roadaddr",
                    key_id,
                    key).enqueue(new Callback<Address>() {
                @Override
                public void onResponse(Call<Address> call, Response<Address> response) {
                    //need to set out of country
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
                        tv_calendar_schedule_add_address.setText(maddress);
                    }
                    else{
                        tv_calendar_schedule_add_address.setText("위치 정보가 없습니다.");
                    }

                }

                @Override
                public void onFailure(Call<Address> call, Throwable t) {

                }
            });
        }catch (Exception e){
            tv_calendar_schedule_add_address.setText(e.getMessage());
        }
    }
}
