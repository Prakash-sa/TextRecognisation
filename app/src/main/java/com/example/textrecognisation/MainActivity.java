package com.example.textrecognisation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class MainActivity extends AppCompatActivity {

    private Button camerbu;
    private ImageView img;
    private final static int REQUEST_CAMERA_CAPTURE=124;
    private FirebaseVisionTextRecognizer textRecognizer;
    private FirebaseVisionImage image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        camerbu=findViewById(R.id.Clickme);
        img=findViewById(R.id.imageis);
        camerbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(intent,REQUEST_CAMERA_CAPTURE);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CAMERA_CAPTURE&&resultCode==RESULT_OK){
            Bundle extras=data.getExtras();
            Bitmap bitmap=(Bitmap)extras.get("data");
            recogniseText(bitmap);
            img.setImageBitmap(bitmap);
        }

    }

    private void recogniseText(Bitmap bitmap) {

        image=FirebaseVisionImage.fromBitmap(bitmap);
        textRecognizer= FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        textRecognizer.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                String resultText=firebaseVisionText.getText();
                if(resultText.isEmpty()){
                    Toast.makeText(MainActivity.this,"No Text Detected",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent=new Intent(MainActivity.this,ResultActivity.class);
                    intent.putExtra(LCOTextRecognisation.RESULT_TEXT,resultText);
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,"WRong",Toast.LENGTH_LONG).show();
            }
        });

    }
}
