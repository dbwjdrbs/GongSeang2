package com.example.helloboard;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloboard.Adapters.PostAdapter;

import java.util.List;

public class RecyclerViewItemClickListener implements RecyclerView.OnItemTouchListener {

    // 2. 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener listener;

    // 3. 플러터 이벤트 변수
    private GestureDetector gestureDetector;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // 1. 어댑터 내에서 커스텀 리스너 인터페이스 정의.
    public interface OnItemClickListener {
        void onItemClick(View v, int position);

        void onItemLongClick(View v, int position);
    }

    // 4. 생성자
    public RecyclerViewItemClickListener(Context context, final RecyclerView recyclerView, final OnItemClickListener listener) {
        this.listener = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View v = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (v != null && listener != null) {
                    listener.onItemLongClick(v, recyclerView.getChildAdapterPosition(v));
                }
            }
        });
    }

    // interface 번외
    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        View view = rv.findChildViewUnder(e.getX(), e.getY());
        if (view != null && gestureDetector.onTouchEvent(e)) {
            listener.onItemClick(view, rv.getChildAdapterPosition(view));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
