package com.example.helloboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FreeBoard_Posting_Activity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private EditText mTitle, mContents;
    private String nickname, uid;
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
        setContentView(R.layout.activity_freeboard_posting);

        mTitle = findViewById(R.id.post_title_edit);
        mContents = findViewById(R.id.post_contents_writing);
        mPhoto = (ImageView) findViewById(R.id.freeboard_posting_photo);

        findViewById(R.id.post_save_button).setOnClickListener(this);
        findViewById(R.id.post_image).setOnClickListener(this);

        if (mAuth.getCurrentUser() != null) {
            mStore.collection(FirebaseID.user).document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.getResult() != null) {
                                nickname = (String) task.getResult().getData().get(FirebaseID.nickname);
                                uid = (String) task.getResult().getData().get(FirebaseID.documentId);
                            }
                        }
                    });
        }

        /** FireBaseStorage에서 사진 불러오기 **/
        StorageReference pathReference = storageReference.child("freeboard");
        if (pathReference == null) {
            Toast.makeText(FreeBoard_Posting_Activity.this, "저장소에 사진이 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            StorageReference submitProfile = storageReference.child("photo/.png");
            submitProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(FreeBoard_Posting_Activity.this).load(uri).into(mPhoto);

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
                    Toast.makeText(FreeBoard_Posting_Activity.this, "사진이 정상적으로 업로드 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(FreeBoard_Posting_Activity.this, "사진이 정상적으로 업로드 되었습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onClick(View view) { //온클릭(저장), 제약조건
        String Title = mTitle.getText().toString();
        String Contents = mContents.getText().toString();

        switch (view.getId()) {
            case R.id.post_save_button:
                if (Title.getBytes().length <= 0) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(FreeBoard_Posting_Activity.this);
                    ad.setMessage("제목을 작성해주세요");
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    ad.show();

                } else if (Contents.getBytes().length <= 0) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(FreeBoard_Posting_Activity.this);
                    ad.setMessage("내용을 작성해주세요.");
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    ad.show();

                } else {
                    if (mAuth.getCurrentUser() != null) {
                        String postId = mStore.collection(FirebaseID.freeboard).document().getId();
                        Map<String, Object> data = new HashMap<>();
                        data.put(FirebaseID.documentId, postId);
                        data.put(FirebaseID.title, mTitle.getText().toString());
                        data.put(FirebaseID.contents, mContents.getText().toString());
                        data.put(FirebaseID.timestamp, FieldValue.serverTimestamp());
                        data.put(FirebaseID.nickname, nickname);
                        data.put(FirebaseID.uid, uid);
                        data.put(FirebaseID.photo, "https://firebasestorage.googleapis.com/v0/b/post-72cce.appspot.com/o/freeboard%2F" + filename + "?alt=media");
                        mStore.collection(FirebaseID.freeboard).document(postId).set(data, SetOptions.merge());
                        mStore.collection(FirebaseID.freeboard).document(postId).update(FirebaseID.like, FieldValue.increment(0));
                        mStore.collection(FirebaseID.freeboard).document(postId).update(FirebaseID.bestpost, FieldValue.increment(0));
                        finish();
                    }
                }
                break;
            case R.id.post_image:
                loadAlbum();
                break;
        }
    }
}