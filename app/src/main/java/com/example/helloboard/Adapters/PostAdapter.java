package com.example.helloboard.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloboard.R;
import com.example.helloboard.models.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> datas;
    public PostAdapter(List<Post> datas) {
        this.datas = datas;
    }

    class PostViewHolder extends RecyclerView.ViewHolder { // 아이템 뷰를 저장하는 뷰홀더 클래스

        private TextView nickname;
        private TextView title;
        private TextView contents;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            nickname = itemView.findViewById(R.id.item_post_nickname);
            title = itemView.findViewById(R.id.post_item_title);
            contents = itemView.findViewById(R.id.post_item_contents);
        }
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  //onCreateViewHolder: 뷰 홀더와 레이아웃 연결
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) { //onBindViewHolder: 뷰홀더에 데이터를 바인딩
        Post data = datas.get(position);
        holder.nickname.setText("작성자: " + data.getNickname());
        holder.title.setText(data.getTitle());
        holder.contents.setText(data.getContents());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}