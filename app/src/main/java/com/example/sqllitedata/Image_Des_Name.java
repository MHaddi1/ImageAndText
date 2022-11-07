package com.example.sqllitedata;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Image_Des_Name extends AppCompatActivity {
    ImageView img;
    EditText title, description;
    Button submit;
    DatabaseHelper db;
    SQLiteDatabase sqLiteDatabase;
    static final int cameraRequest = 100;
    static final int StorageRequest = 101;
    String CameraPermission[];
    String StoragePermission[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_des_name);



        db = new DatabaseHelper(this);
        finid();
        insertData();
        img.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                int avtar = 0;
                if (avtar==0){
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }else {
                        PickImage();
                    }
                }else if(avtar==1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();

                    }else {
                        PickImage();
                    }
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Title = title.getText().toString();
                String Description = description.getText().toString();
                db.insertDataImage(Title,Description,ImagebyByte(img));

            }
        });

//        img.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View view) {
//                boolean pick = true;
//                if (pick == true) {
//                    if (!checkCameraPermission()) {
//                        requestCameraPermission();
//
//                    } else PickImage();
//                } else {
//                    if (!checkStoragePermission()) {
//                        requestStoragePermission();
//
//                    } else PickImage();
//                }
//            }
//        });

//       databaseHelper.asModelDao().Image(new AsModel("Data","Data"));


    }



        private byte[] ImagebyByte(ImageView img) {
        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArray);
        byte[] bytes = byteArray.toByteArray();
        return bytes;
    }

    private void PickImage() {

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},200);
    }


    private boolean requestCameraPermission() {
       boolean res = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
       return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkStoragePermission() {

//        boolean rest1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkCameraPermission() {
        requestPermissions(CameraPermission,cameraRequest);
//        boolean rest1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
//        boolean rest2 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

    }

    public void onActivityResult(int RequestCode,int resultCode,Intent data) {
        super.onActivityResult(RequestCode, resultCode, data);
        if (RequestCode == 200) {

            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {

            }
        }
    }
    private void insertData() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("Name_image",title.getText().toString());
                contentValues.put("Description_image",description.getText().toString());
                contentValues.put("Image",ImagebyByte(img));
                sqLiteDatabase = db.getWritableDatabase();
                Long res = sqLiteDatabase.insert("TABLE_NAME_1",null,contentValues);
                if (res!=null){
                    Toast.makeText(Image_Des_Name.this, "Data Insert Successfully", Toast.LENGTH_SHORT).show();
                    title.setText("");
                    description.setText("");
                    img.setImageResource(R.mipmap.ic_launcher);
                }else{
                    Toast.makeText(Image_Des_Name.this, "Data is Not Inserted!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void finid(){
        img = findViewById(R.id.imageView);
        title = findViewById(R.id.Name);
        description = findViewById(R.id.Description);
        submit = findViewById(R.id.Insert);
    }

}