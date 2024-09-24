package com.example.helloboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.helloboard.Adapters.PostAdapter;
import com.example.helloboard.models.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LikePostActivity extends AppCompatActivity implements RecyclerViewItemClickListener.OnItemClickListener {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private RecyclerView mPostRecyclerView;

    private PostAdapter mAdapter;
    private List<Post> mDatas;
    private String userVerification, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_post);
        setTitle("좋아요를 표시한 게시물");

        mPostRecyclerView = findViewById(R.id.like_post_recycler_view);

        // 아이템터치리스너
        mPostRecyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(this, mPostRecyclerView, this));

        if (mAuth.getCurrentUser() != null) {
            mStore.collection(FirebaseID.user).document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.getResult() != null) {
                                uid = (String) task.getResult().getData().get(FirebaseID.documentId);
                            }
                        }
                    });
        }

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String useruid = user.getUid();
            userVerification = useruid;
            mDatas = new ArrayList<>();
            if (userVerification.equals(uid)) {
                mStore.collection(FirebaseID.freeboard)
                        .orderBy(FirebaseID.timestamp, Query.Direction.DESCENDING) //내림차순?
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if (queryDocumentSnapshots != null) {
                                    mDatas.clear();
                                    for (DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                        Map<String, Object> shot = snap.getData();
                                        String documentId = String.valueOf(shot.get(FirebaseID.documentId));
                                        String nickname = String.valueOf(shot.get(FirebaseID.nickname));
                                        String title = String.valueOf(snap.get(FirebaseID.title));
                                        String contents = String.valueOf(shot.get(FirebaseID.contents));
                                        Post data = new Post(documentId, nickname, title, contents);
                                        mDatas.add(data);
                                    }
                                    mAdapter = new PostAdapter(mDatas);
                                    mPostRecyclerView.setAdapter(mAdapter);
                                }
                            }
                        });
            }
        }
    }

    @Override
    public void onItemClick(View v, int position) {
        Intent intent = new Intent(this, FreeBoard_View_Activity.class);
        intent.putExtra(FirebaseID.documentId, mDatas.get(position).getDocumentId());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View v, int position) {

    }
}