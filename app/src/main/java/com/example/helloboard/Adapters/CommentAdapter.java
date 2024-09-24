package com.example.helloboard.Adapters;

import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloboard.FirebaseID;
import com.example.helloboard.R;
import com.example.helloboard.models.Comment;
import com.example.helloboard.models.Post;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    class CommentViewHolder extends RecyclerView.ViewHolder { // 아이템 뷰를 저장하는 뷰홀더 클래스
        private TextView nickname;
        private TextView timestamp;
        private TextView contents;
        private Button addcomment;
        private Button delete;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            nickname = itemView.findViewById(R.id.comment_item_nickname);
            contents = itemView.findViewById(R.id.comment_item_contents);
            timestamp = itemView.findViewById(R.id.comment_item_time);
//            addcomment = itemView.findViewById(R.id.comment_add_comment);
//            delete = itemView.findViewById(R.id.comment_del);
        }
    }

    private List<Comment> datas;

    public CommentAdapter(List<Comment> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  //onCreateViewHolder: 뷰 홀더와 레이아웃 연결
        return new CommentAdapter.CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) { //onBindViewHolder: 뷰홀더에 데이터를 바인딩
        Comment data = datas.get(position);
        holder.nickname.setText(data.getNickname());
        holder.contents.setText(data.getContents());
        holder.timestamp.setText((CharSequence) data.getDate());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}