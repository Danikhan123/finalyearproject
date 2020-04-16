package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
/*import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;*/

import java.util.List;

public class ImageToText extends AppCompatActivity {
       private Button cp1,dt1;
       private ImageView imv;
       private TextView t1;
    private android.graphics.Bitmap imageBitmap ;
    private  static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_to_text);

        cp1=(Button)findViewById(R.id.button3);
        dt1=(Button)findViewById(R.id.button4);
        t1=(TextView)findViewById(R.id.textView2);
        imv=(ImageView) findViewById(R.id.imageView);


        cp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   dispatchTakePictureIntent();
            }
        });
        dt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // detectTextFromImage();
                getTextFromImage();
            }
        });
    }



    private void dispatchTakePictureIntent() {
           Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
          startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK )
        {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imv.setImageBitmap(imageBitmap);
        }

        }

        public void getTextFromImage()
        {
          // Bitmap bitmap= BitmapFactory.decodeResource(getApplicationContext().getResources(),R.id.imageView);
            BitmapDrawable bitmapDrawable=(BitmapDrawable)imv.getDrawable();
            Bitmap bitmap=bitmapDrawable.getBitmap();

            TextRecognizer textRecognizer=new TextRecognizer.Builder(getApplicationContext()).build();
            if(!textRecognizer.isOperational())
            {
                Toast.makeText(getApplicationContext(), "Could No detect image" , Toast.LENGTH_SHORT).show();
            }
            else
            {
                Frame frame=new Frame.Builder().setBitmap(bitmap).build();
                SparseArray<TextBlock> items=textRecognizer.detect(frame);
                StringBuilder sb=new StringBuilder();
                for(int i=0;i<items.size();++i)
                {
                    TextBlock myItem=items.valueAt(i);
                    sb.append(myItem.getValue());
                    sb.append("\n");

                }
                t1.setText(sb.toString());
            }
        }



//detection
   /* private void detectTextFromImage() { FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextDetector firebaseVisionTextDetector = FirebaseVision.getInstance().getVisionTextDetector();
        firebaseVisionTextDetector.detectInImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                displayTextFromImage(firebaseVisionText);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error Message" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void displayTextFromImage(FirebaseVisionText firebaseVisionText) {
        List<FirebaseVisionText.Block> blockList = firebaseVisionText.getBlocks();
        if(blockList.size()==0)
        {
            Toast.makeText(getApplicationContext(),"No Text found in image",Toast.LENGTH_SHORT).show();
        }
        else
        {
            for(FirebaseVisionText.Block block : firebaseVisionText.getBlocks())
            {
                String text=block.getText();
                t1.setText(text);
            }
        }*/


}
