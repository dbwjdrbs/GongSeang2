package com.example.helloboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.helloboard.Adapters.PostAdapter;
import com.example.helloboard.models.Post;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FreeBoardActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewItemClickListener.OnItemClickListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private RecyclerView mPostRecyclerView;
    private RecyclerView mBestRecyclerView;

    private PostAdapter mAdapter;
    private List<Post> mDatas;
    private String bestpost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {  //main 같은거
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_board);
        setTitle("자유게시판");

        mPostRecyclerView = findViewById(R.id.freeboard_recycler_view);
//        mBestRecyclerView = findViewById(R.id.best_freeboard_recycler_view);
        findViewById(R.id.btn_writing).setOnClickListener(this); // 플로팅

        // 아이템터치리스너
        mPostRecyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(this, mPostRecyclerView, this));
//        mBestRecyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(this, mBestRecyclerView, this));

        /*** BEST 게시물 ***/
        mDatas = new ArrayList<>();
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
//                                bestpost = String.valueOf(shot.get(FirebaseID.bestpost));
                                Post data = new Post(documentId, nickname, title, contents);
                                mDatas.add(data);
                            }
                            mAdapter = new PostAdapter(mDatas);
                            mPostRecyclerView.setAdapter(mAdapter);
//                            if(bestpost.equals(1))
//                            mBestRecyclerView.setAdapter(mAdapter);
                        }
                    }
                });
    }

    /*** Firestore 에서 문서 불러오기 ***/
    @Override
    protected void onStart() {
        super.onStart();
        mDatas = new ArrayList<>();
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
//                            mBestRecyclerView.setAdapter(mAdapter);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) { //플로팅
        startActivity(new Intent(this, FreeBoard_Posting_Activity.class));
    }

    /* 아이템 클릭 리스너 */
    @Override
    public void onItemClick(View v, int position) {
        Intent intent = new Intent(this, FreeBoard_View_Activity.class);
        intent.putExtra(FirebaseID.documentId, mDatas.get(position).getDocumentId());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View v, int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("삭제 하시겠습니까?");
        dialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mStore.collection(FirebaseID.freeboard).document(mDatas.get(position).getDocumentId()).delete();
            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialog.setTitle("삭제 알림");
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu_freeboard, menu);
        MenuItem item = menu.findItem(R.id.searchId);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.settingsId) {
            Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
        }
//        switch (item.getItemId()) {

//            case R.id.menu1:
//                startActivity(new Intent(FreeBoardActivity.this, FreeBoardSearchActivity.class));
//                break;

//            case R.id.menu2:
//                break;
//        }
        return super.onOptionsItemSelected(item);
    }

    private void filterList(String text) {
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
//                            mAdapter = new PostAdapter(mDatas);
                            List<Post> filteredList = new ArrayList<>();
                            for (Post item : mDatas) {
                                if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                                    filteredList.add(item);
                                }
                            }
                            if (filteredList.isEmpty()) {
//                                Toast.makeText(FreeBoardActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                            } else {
//                                Toast.makeText(FreeBoardActivity.this, "성공", Toast.LENGTH_SHORT).show();
                                mAdapter = new PostAdapter(filteredList);
                                mPostRecyclerView.setAdapter(mAdapter);

                            }
                        }
                    }
                });
    }

    /** (구)검색기능 **/
//    private void searchData(String query) {
//        ProgressDialog progressDialog = new ProgressDialog(FreeBoardActivity.this);
//        progressDialog.setTitle("Searching...");
////        progressDialog.show();
//        mStore.collection(FirebaseID.freeboard).whereEqualTo(FirebaseID.title, query.toLowerCase())
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        mDatas.clear();
//                        progressDialog.dismiss();
//                        for (DocumentSnapshot snap : task.getResult()) {
//                            Map<String, Object> shot = snap.getData();
//                            String documentId = String.valueOf(shot.get(FirebaseID.documentId));
//                            String nickname = String.valueOf(shot.get(FirebaseID.nickname));
//                            String title = String.valueOf(snap.get(FirebaseID.title));
//                            String contents = String.valueOf(shot.get(FirebaseID.contents));
//                            Post data = new Post(documentId, nickname, title, contents);
//                            mDatas.add(data);
//                        }
//                        mAdapter = new PostAdapter(mDatas);
//                        mPostRecyclerView.setAdapter(mAdapter);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        progressDialog.dismiss();
//                        Toast.makeText(FreeBoardActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
}