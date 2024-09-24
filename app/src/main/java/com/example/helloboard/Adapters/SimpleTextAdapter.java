package com.example.helloboard.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloboard.R;
import com.example.helloboard.models.SingleItem;

import java.util.ArrayList;
import java.util.List;

public class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.ViewHolder> {
    private ArrayList<SingleItem> items = null;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView text_name;
        TextView text_description;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_name = itemView.findViewById(R.id.text_name);
            text_description = itemView.findViewById(R.id.text_description);
//            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    // 생성자
    public SimpleTextAdapter(ArrayList<SingleItem> list){
        items = list;
    }

    // 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_search, parent, false);
        SimpleTextAdapter.ViewHolder vh = new SimpleTextAdapter.ViewHolder(view);

        return vh;
    }

    // position 에 해당하는 데이터를 뷰홀더의 아이템 뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull SimpleTextAdapter.ViewHolder holder, int position) {
        String name = items.get(position).name;
        String description = items.get(position).description;
        int resId = items.get(position).resId;

        holder.text_name.setText(name);
        holder.text_description.setText(description);
        holder.imageView.setImageResource(resId);
    }

    // 전체 데이터 개수 리턴
    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<SingleItem> list){
        items = list;
        notifyDataSetChanged();
    }
}