package com.chrisdahms.mycameraexample3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

///////////////////////////////////////////////////////////////////////////////////////////////////
public class MainActivity extends AppCompatActivity {

    // member variables ///////////////////////////////////////////////////////////////////////////
    private static final String TAG = "MyApp";

    Button btnGetImage;
    ImageView ivImage;

    final private int REQUEST_MULTIPLE_PERMISSIONS = 123;
    private static final int CAMERA_REQUEST = 1888;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivImage = (ImageView)findViewById(R.id.ivImage);

        btnGetImage = (Button)findViewById(R.id.btnGetImage);
        btnGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                String imageFileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/temp.png";
                File imageFile = new File(imageFileName);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));

                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        // if device is running Android API 22 or earlier . . .
        if(Build.VERSION.SDK_INT <= 22) {
            // the manifest will already have taken care of external storage write permission for us, so go ahead and load the image

            // if device is running android API 23 or later . . .
        } else if(Build.VERSION.SDK_INT >= 23) {
            final List<String> permissionsList = new ArrayList<String>();

            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{ Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, REQUEST_MULTIPLE_PERMISSIONS);
            } else {
                //loadImageFromGallery();
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            String imageFileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/temp.png";
            File imageFile = new File(imageFileName);

            if(imageFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                ivImage.setImageBitmap(bitmap);
            } else {
                Log.d(TAG, "file not found");
            }

        }
    }

}









