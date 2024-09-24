package com.example.helloboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarScheduleRecyclerAdapter extends RecyclerView.Adapter<CalendarScheduleRecyclerAdapter.ViewHolder>{
    private ArrayList<CalendarScheduleRecyclerItem> lists = new ArrayList<>();
    private SQLiteDatabase sqliteDB;

    private int year, month, day;

    CalendarScheduleRecyclerAdapter(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_calendar_schedule_content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_calendar_schedule_content = (TextView)itemView.findViewById(R.id.tv_calendar_schedule_content);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recycler_calendar_schedule,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CalendarScheduleRecyclerItem item = lists.get(position);

        holder.tv_calendar_schedule_content.setText(item.getContent());
        selItem(holder, item);

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void addItem(String content, double latitude, double longitude){
        CalendarScheduleRecyclerItem item = new CalendarScheduleRecyclerItem();

        item.setContent(content);
        item.setLatitude(latitude);
        item.setLongitude(longitude);

        lists.add(item);
    }

    private void selItem(ViewHolder holder, CalendarScheduleRecyclerItem item){
        holder.tv_calendar_schedule_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CalendarScheduleShowActivity.class);
                intent.putExtra("content", item.getContent());
                intent.putExtra("lat", item.getLatitude());
                intent.putExtra("lng", item.getLongitude());
                intent.putExtra("year", year);
                intent.putExtra("month", month);
                intent.putExtra("day", day);
                ((Activity)v.getContext()).startActivityForResult(intent, 0);
            }
        });
    }
}
