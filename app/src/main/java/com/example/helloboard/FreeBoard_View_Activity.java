package com.example.helloboard;

import static com.example.helloboard.FirebaseID.like;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.helloboard.Adapters.CommentAdapter;
import com.example.helloboard.Adapters.PostAdapter;
import com.example.helloboard.models.Comment;
import com.example.helloboard.models.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreeBoard_View_Activity extends AppCompatActivity
        implements View.OnClickListener, RecyclerViewItemClickListener.OnItemClickListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private RecyclerView mCommentRecyclerView;

    private CommentAdapter mAdapter;
    private List<Comment> mDatas;

    private TextView mTitleText, mContentsText, mNameText, mLike;
    private EditText mCommentText;
    private String id, nickname, uid;
    private ImageView mPhoto;
    private String userVerification;
    private Button btn_freeboard_like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeboard_view);

        mCommentRecyclerView = findViewById(R.id.comment_recycler_view);

        mTitleText = findViewById(R.id.tv_post_title);
        mContentsText = findViewById(R.id.tv_post_contents);
        mNameText = findViewById(R.id.tv_post_name);
        mCommentText = findViewById(R.id.et_comment);
        mPhoto = (ImageView) findViewById(R.id.freeboard_view_photo);
        mLike = findViewById(R.id.tv_like);

        findViewById(R.id.btn_freeboard_like).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);
        btn_freeboard_like = (Button) findViewById(R.id.btn_freeboard_like);

        // 아이템터치리스너
        mCommentRecyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(this, mCommentRecyclerView, this));

        /*** nickname 따오기 ***/
        if (mAuth.getCurrentUser() != null) {
            mStore.collection(FirebaseID.user).document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.getResult() != null) {
                                nickname = (String) task.getResult().getData().get(FirebaseID.nickname);
                            }
                        }
                    });
        }

        /*** DocumentID 불러오기 ***/
        Intent getIntent = getIntent();
        id = getIntent.getStringExtra(FirebaseID.documentId);
        Log.e("ITEM DOCUMENT ID: ", id);

        /*** Firestore에서 문서 불러오기 ***/
        mStore.collection(FirebaseID.freeboard).document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                if (task.getResult() != null) {
                                    Map<String, Object> snap = task.getResult().getData();
                                    String title = String.valueOf(snap.get(FirebaseID.title));
                                    String contents = String.valueOf(snap.get(FirebaseID.contents));
                                    String name = String.valueOf(snap.get(FirebaseID.nickname));
                                    String photo = String.valueOf(snap.get(FirebaseID.photo));
                                    String like = String.valueOf(snap.get(FirebaseID.like));

                                    mTitleText.setText(title);
                                    mContentsText.setText(contents);
                                    mNameText.setText(name);
                                    mLike.setText(like);

                                    String imageStr = photo;
                                    Glide.with(getApplicationContext()).load(imageStr).into(mPhoto);
                                }
                            } else {
                                Toast.makeText(FreeBoard_View_Activity.this, "접근할 수 없는 문서입니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        /*** 좋아요 버튼 활성화 ***/
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String useruid = user.getUid();
            userVerification = useruid;
            mStore.collection(FirebaseID.freeboard).document(id).collection(like).document(userVerification)  // Firestore 불러오기 :
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    if (task.getResult() != null) {
                                        Map<String, Object> snap = task.getResult().getData();
                                        String uid = String.valueOf(snap.get(FirebaseID.uid));
                                        if (useruid.equals(userVerification)) {
                                            btn_freeboard_like.setText("좋아요 취소");
                                        }
                                    }
                                } else {
                                    btn_freeboard_like.setText("좋아요");
                                }
                            }
                        }
                    });
        }
    }

    /*** FIRESTORE에서 댓글 불러오기 ***/
    @Override
    protected void onStart() {
        super.onStart();
        mDatas = new ArrayList<>();
        mStore.collection(FirebaseID.freeboard).document(id).collection(FirebaseID.comment)
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
                                String contents = String.valueOf(shot.get(FirebaseID.contents));
                                Comment data = new Comment(nickname, contents, documentId);
                                mDatas.add(data);
                            }
                            mAdapter = new CommentAdapter(mDatas);
                            mCommentRecyclerView.setAdapter(mAdapter);
                        }
                    }
                });
    }

    /*** Button 클릭  1.댓글저장 2.좋아요 ***/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                if (mAuth.getCurrentUser() != null) {
                    String postId = mStore.collection(FirebaseID.freeboard).document(id).collection(FirebaseID.comment).document().getId();
                    Map<String, Object> data = new HashMap<>();
                    data.put(FirebaseID.documentId, postId);
                    data.put(FirebaseID.contents, mCommentText.getText().toString());
                    data.put(FirebaseID.timestamp, FieldValue.serverTimestamp());
                    data.put(FirebaseID.nickname, nickname);
                    mStore.collection(FirebaseID.freeboard).document(id).collection(FirebaseID.comment).document(postId).set(data, SetOptions.merge());
                    mCommentText.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mCommentText.getWindowToken(), 0);
                    Toast.makeText(FreeBoard_View_Activity.this, "댓글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    break;
                }

            case R.id.btn_freeboard_like:  /*** 좋아요 기능 ***/
                /*** 1. uid 따오기 ***/
                mAuth = FirebaseAuth.getInstance();
                final FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    String useruid = user.getUid();
                    userVerification = useruid;
                    /*** 2. 좋아요 기능 ***/
                    if (useruid.equals(userVerification)) {  // 좋아요를 이미 눌렀을 경우. (좋아요 취소)
                        btn_freeboard_like.setText("좋아요");
                        mStore.collection(FirebaseID.freeboard).document(id).collection(like).document(userVerification)  // Firestore 불러오기 :
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            if (task.getResult().exists()) {
                                                if (task.getResult() != null) {
                                                    Map<String, Object> snap = task.getResult().getData();
                                                    String uid = String.valueOf(snap.get(FirebaseID.uid));
                                                    mStore.collection(FirebaseID.freeboard).document(id).collection(FirebaseID.like)
                                                            .document(userVerification).delete();
                                                    mStore.collection(FirebaseID.freeboard).document(id).update(FirebaseID.like, FieldValue.increment(-1));
                                                    Toast.makeText(FreeBoard_View_Activity.this, "좋아요 취소", Toast.LENGTH_SHORT).show();
                                                }

                                                /*** 좋아요 업데이트 ***/
                                                mStore.collection(FirebaseID.freeboard).document(id)
                                                        .get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if (task.isSuccessful()) {
                                                                    if (task.getResult().exists()) {
                                                                        if (task.getResult() != null) {
                                                                            Map<String, Object> snap = task.getResult().getData();
                                                                            String like = String.valueOf(snap.get(FirebaseID.like));

                                                                            mLike.setText(like);
                                                                        }
                                                                    } else {
                                                                        Toast.makeText(FreeBoard_View_Activity.this, "접근할 수 없는 문서입니다.", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            }
                                                        });
                                            } else { /*** 4. 좋아요 기능 ***/
                                                if (mAuth.getCurrentUser() != null) {
                                                    btn_freeboard_like.setText("좋아요 취소");
                                                    String postId = mStore.collection(FirebaseID.freeboard).document(id).collection(like).document(userVerification).getId();
                                                    Map<String, Object> data = new HashMap<>();
                                                    data.put(FirebaseID.uid, postId);
                                                    data.put(FirebaseID.nickname, nickname);
                                                    mStore.collection(FirebaseID.freeboard).document(id).collection(FirebaseID.like).document(postId).set(data, SetOptions.merge());
                                                    Toast.makeText(FreeBoard_View_Activity.this, "좋아요", Toast.LENGTH_SHORT).show();
                                                    mStore.collection(FirebaseID.freeboard).document(id).update(FirebaseID.like, FieldValue.increment(+1));
                                                }
                                                /*** 좋아요 업데이트 ***/
                                                mStore.collection(FirebaseID.freeboard).document(id)
                                                        .get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if (task.isSuccessful()) {
                                                                    if (task.getResult().exists()) {
                                                                        if (task.getResult() != null) {
                                                                            Map<String, Object> snap = task.getResult().getData();
                                                                            String like = String.valueOf(snap.get(FirebaseID.like));

                                                                            mLike.setText(like);
                                                                        }
                                                                    } else {
                                                                        Toast.makeText(FreeBoard_View_Activity.this, "접근할 수 없는 문서입니다.", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                });
                    }
                    break;
                }
        }
    }

    @Override
    public void onItemClick(View v, int position) {

    }

    /*** FIRESTORE에서 댓글 삭제 ***/
    @Override
    public void onItemLongClick(View v, int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("삭제 하시겠습니까?");
        dialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mStore.collection(FirebaseID.freeboard).document(id).collection(FirebaseID.comment)
                        .document(mDatas.get(position).getDocumentId()).delete();
            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialog.setTitle("삭제 알림");
        dialog.show();
    }

    /*** 옵션메뉴  1.수정 2.삭제 ***/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu1: /*** 게시물 수정 기능 ***/
                mAuth = FirebaseAuth.getInstance();
                final FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    String uid = user.getUid();
                    userVerification = uid;
                }

                mStore.collection(FirebaseID.freeboard).document(id)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        if (task.getResult() != null) {
                                            Map<String, Object> snap = task.getResult().getData();
                                            String uid = String.valueOf(snap.get(FirebaseID.uid));
                                            if (uid.equals(userVerification)) {
                                                Intent intent = new Intent(FreeBoard_View_Activity.this, FreeBoard_Edit_Activity.class);
                                                intent.putExtra(FirebaseID.documentId, id);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(FreeBoard_View_Activity.this, "접근할 수 없습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            }
                        });
                break;

            case R.id.menu2: /*** 게시물 삭제 기능 ***/
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                mAuth = FirebaseAuth.getInstance();
                final FirebaseUser user1 = mAuth.getCurrentUser();
                if (user1 != null) {
                    String uid = user1.getUid();
                    userVerification = uid;
                }
                mStore.collection(FirebaseID.freeboard).document(id)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        if (task.getResult() != null) {
                                            Map<String, Object> snap = task.getResult().getData();
                                            String uid = String.valueOf(snap.get(FirebaseID.uid));
                                            if (uid.equals(userVerification)) {
                                                dialog.setMessage("삭제 하시겠습니까?");
                                                dialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        mStore.collection(FirebaseID.freeboard).document(id).delete();
                                                        startActivity(new Intent(FreeBoard_View_Activity.this, FreeBoardActivity.class));
                                                    }
                                                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                    }
                                                });
                                                dialog.setTitle("삭제 알림");
                                                dialog.show();
                                            } else {
                                                Toast.makeText(FreeBoard_View_Activity.this, "접근할 수 없습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            }
                        });
                break;
        }
        return super.

                onOptionsItemSelected(item);
    }
}