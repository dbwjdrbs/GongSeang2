package com.example.helloboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.ViewHolder> {
    private ArrayList<CalendarRecyclerItem> lists = new ArrayList<>();

    private int key;
    private GregorianCalendar today = new GregorianCalendar();
    private int year, month;

    public CalendarRecyclerAdapter(int year, int month){
        this.year = year;
        this.month = month;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_calendar_day;
        private TextView tv_calendar_schedule;
        private ImageView iv_calendar_day;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_calendar_day = (TextView)itemView.findViewById(R.id.tv_calendar_day);
            tv_calendar_schedule = (TextView)itemView.findViewById(R.id.tv_calendar_schedule);
            iv_calendar_day = (ImageView)itemView.findViewById(R.id.iv_calendar_day);
        }
    }

    @Override
    public int getItemViewType(int position) {
        key = lists.get(position).getKey();
        switch (key){
            case 0 : return 0;
            case 1 : return 1;
        }
        return -1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater;
        View view;
        ViewHolder vh;

        switch(viewType){
            case 0 :
                inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.recycler_calendar_empty, parent, false);
                vh = new ViewHolder(view);
                return vh;

            case 1 :
                inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.recycler_calendar_day, parent, false);
                vh = new ViewHolder(view);
                return vh;

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        CalendarRecyclerItem item = lists.get(position);
        switch(viewType) {
            case 0:
                break;
            case 1:
                GregorianCalendar daycheck = new GregorianCalendar(year, month, item.getDay() );
                if(daycheck.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
                    holder.tv_calendar_day.setTextColor(0xff0000ff);
                    holder.iv_calendar_day.setBackgroundColor(0xff0000ff);
                }
                if(daycheck.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                    holder.tv_calendar_day.setTextColor(0xffff0000);
                    holder.iv_calendar_day.setBackgroundColor(0xffff0000);
                }
                if(today.get(Calendar.YEAR) == year &&
                today.get(Calendar.MONTH) == month &&
                today.get(Calendar.DATE) == item.getDay()){
                    holder.tv_calendar_day.setTextColor(0xff00ff00);
                    holder.iv_calendar_day.setBackgroundColor(0xff00ff00);
                }
                holder.tv_calendar_day.setText(Integer.toString(item.getDay()));
                if(item.isSchedule()){
                    holder.tv_calendar_schedule.setVisibility(View.VISIBLE);
                    holder.tv_calendar_schedule.setText("일정" + Integer.toString(item.getCount()));
                }
                selItem(holder,item.getDay());

                break;
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void addItem(int key, int day, boolean schedule, int count){
        CalendarRecyclerItem item = new CalendarRecyclerItem();

        item.setKey(key);
        item.setDay(day);
        item.setSchedule(schedule);
        item.setCount(count);

        lists.add(item);
    }

    private void selItem(ViewHolder holder,int day){
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(v.getContext(), CalendarScheduleActivity.class);
                    intent.putExtra("year", year);
                    intent.putExtra("month",month);
                    intent.putExtra("day", day);
                    //v.getContext().startActivity(intent);
                    ((Activity)v.getContext()).startActivityForResult(intent, 0);
                }catch (Exception e){
                    holder.tv_calendar_day.setText(e.getMessage());
                }

            }
        });
    }
}
