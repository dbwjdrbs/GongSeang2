package com.example.helloboard;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FreeBoard_Edit_Activity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private EditText mTitleEdit, mContentsEdit;
    private String nickname, id, uid;
    private ImageView mPhoto;

    /** FireBaseStorage 사용하기 위한 코드들 **/
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private final int GALLERY_CODE = 10;
    //ex) profile1.jpg 로그인하는 사람에 따라 그에 식별값에 맞는 프로필 사진 가져오기
    String filename = (mAuth.getCurrentUser().getUid()) + formatter.format(date) + ".jpg";
    private FirebaseStorage storage = FirebaseStorage.getInstance(); // 스토리지 인스턴스
    private StorageReference storageReference = storage.getReference(); // 스토리지 참조

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_board_edit);

        mTitleEdit = findViewById(R.id.edit_title);
        mContentsEdit = findViewById(R.id.edit_contents);
        mPhoto = (ImageView) findViewById(R.id.freeboard_edit_photo);
        findViewById(R.id.post_save_button).setOnClickListener(this);
        findViewById(R.id.edit_image).setOnClickListener(this);

        Intent getIntent = getIntent();
        id = getIntent.getStringExtra(FirebaseID.documentId);
        Log.e("ITEM DOCUMENT ID: ", id);

        if (mAuth.getCurrentUser() != null) {  //닉넴
            mStore.collection(FirebaseID.user).document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.getResult() != null) {
                                nickname = (String) task.getResult().getData().get(FirebaseID.nickname);
                                uid = (String) task.getResult().getData().get(FirebaseID.uid);
                            }
                        }
                    });
        }

        /** Firestore에서 문서 불러오기 **/
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
                                    String photo = String.valueOf(snap.get(FirebaseID.photo));

                                    mTitleEdit.setText(title);
                                    mContentsEdit.setText(contents);

                                    String imageStr = photo;
                                    Glide.with(getApplicationContext()).load(imageStr).into(mPhoto);
                                }
                            } else {
                                Toast.makeText(FreeBoard_Edit_Activity.this, "접근할 수 없는 문서입니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        /** FireBaseStorage에서 사진 불러오기 **/
        StorageReference pathReference = storageReference.child("freeboard");
        if (pathReference == null) {
            Toast.makeText(FreeBoard_Edit_Activity.this, "저장소에 사진이 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            StorageReference submitProfile = storageReference.child("freeboard/" + filename);
            submitProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(FreeBoard_Edit_Activity.this).load(uri).into(mPhoto);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    private void loadAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE) {
            Uri file = data.getData();
            StorageReference storageRef = storage.getReference();
            StorageReference riversRef = storageRef.child("freeboard/" + filename);
            UploadTask uploadTask = riversRef.putFile(file);

            try {
                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                mPhoto.setImageBitmap(img);
            } catch (Exception e) {
                e.printStackTrace();
            }

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(FreeBoard_Edit_Activity.this, "사진이 정상적으로 업로드 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(FreeBoard_Edit_Activity.this, "사진이 정상적으로 업로드 되었습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        String Title = mTitleEdit.getText().toString();
        String Contents = mContentsEdit.getText().toString();

        switch (view.getId()) {
            case R.id.post_save_button:
                if (Title.getBytes().length <= 0) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(FreeBoard_Edit_Activity.this);
                    ad.setMessage("제목을 작성해주세요");
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    ad.show();

                } else if (Contents.getBytes().length <= 0) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(FreeBoard_Edit_Activity.this);
                    ad.setMessage("내용을 작성해주세요.");
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    ad.show();

                } else {
                    Map<String, Object> data = new HashMap<>();
                    data.put(FirebaseID.documentId, (id));
                    data.put(FirebaseID.title, mTitleEdit.getText().toString());
                    data.put(FirebaseID.contents, mContentsEdit.getText().toString());
//                    data.put(FirebaseID.timestamp, FieldValue.serverTimestamp());
                    data.put(FirebaseID.nickname, nickname);
                    data.put(FirebaseID.uid, uid);
                    data.put(FirebaseID.photo, "https://firebasestorage.googleapis.com/v0/b/post-72cce.appspot.com/o/freeboard%2F" + filename + "?alt=media");

                    mStore.collection(FirebaseID.freeboard).document(id)
                            .set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(FreeBoard_Edit_Activity.this, FreeBoard_View_Activity.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(FreeBoard_Edit_Activity.this, "실패", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            case R.id.edit_image:
                loadAlbum();
                break;
        }
    }
}