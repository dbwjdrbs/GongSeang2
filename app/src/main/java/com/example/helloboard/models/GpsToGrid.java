package com.example.helloboard.models;

import android.graphics.Point;

public class GpsToGrid {
    private double RE = 6371.00877; // 지구 반경(km)
    private double GRID = 5.0; // 격자 간격(km)
    private double SLAT1 = 30.0; // 투영 위도1(degree)
    private double SLAT2 = 60.0; // 투영 위도2(degree)
    private double OLON = 126.0; // 기준점 경도(degree)
    private double OLAT = 38.0; // 기준점 위도(degree)
    private double XO = 43; // 기준점 X좌표(GRID)
    private double YO = 136; // 기1준점 Y좌표(GRID)

    private double DEGRAD = Math.PI / 180.0;
    private double RADDEG = 180.0 / Math.PI;

    private double re = RE / GRID;
    private double slat1 = SLAT1 * DEGRAD;
    private double slat2 = SLAT2 * DEGRAD;
    private double olon = OLON * DEGRAD;
    private double olat = OLAT * DEGRAD;

    private double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
    private double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
    private double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);

    private double lat, lng, x, y;

    private Point point;

    public GpsToGrid(double mlat, double mlng){
        lat = mlat;
        lng = mlng;
        double ra = Math.tan(Math.PI * 0.25 + lat * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra,sn);
        double theta = lng * DEGRAD - olon;
        if(theta > Math.PI) theta -= 2.0 * Math.PI;
        if(theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;
        point.x = (int)Math.floor(ra*Math.sin(theta) + XO +0.5);
        point.y = (int)Math.floor(ro - ra * Math.cos(theta) + YO +0.5);
    }

    public Point getGrid(){
        return point;
    }

}
