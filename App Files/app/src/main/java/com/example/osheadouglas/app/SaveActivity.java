package com.example.osheadouglas.app;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.security.Security;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static android.R.attr.value;

public class SaveActivity extends AppCompatActivity {

    private DatabaseHelper myDb;
    private String riffFilePath = " ";
    private String riffphotoPath = " ";
    private String riffLoc = " ";
    private String inspireString = "";
    private EditText editRiffName;
    private EditText editRiffMood;
    private EditText editRiffDescription;
    private File imageFile;
    private ImageView sI;

    String toEncrypt = " ";
    String encrypted = " ";
    Cipher cipher;
    byte[] input;
    byte[] cipherText; //
    int ctLength; //
    byte[] keyBytes = "12345678".getBytes(); // Encryption key
    byte[] ivBytes = "input123".getBytes(); // Another key
    SecretKeySpec key = new SecretKeySpec(keyBytes,"DES"); //  Defines the key
    IvParameterSpec ivSpec = new IvParameterSpec(ivBytes); // And the other one

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        editRiffName = (EditText) findViewById(R.id.riffNameEdit);
        editRiffMood = (EditText) findViewById(R.id.riffMoodEdit);
        editRiffDescription = (EditText) findViewById(R.id.riffDesEdit);
        sI = (ImageView) findViewById(R.id.saveImage);
        myDb = new DatabaseHelper(this); // This is context in the database helper class
        Bundle bundle = getIntent().getExtras();
        riffFilePath = bundle.getString("filePath");
        inspireString = bundle.getString("inspire");
        riffLoc = bundle.getString("location");
        editRiffMood.setText(inspireString);
    }


    //DB button
    public void saveBtn(View view){// Encrypt the riff location here
        //toEncrypt = riffLoc;
        //encrypt();
        //encrypted = cipherText.toString();
        boolean isInserted = myDb.insertData(editRiffName.getText().toString(),editRiffMood.getText().toString(),editRiffDescription.getText().toString(),riffFilePath,riffphotoPath,riffLoc); // Inserts the data using the method in the database helper class
        if(isInserted == true){
            Toast.makeText(this,"Data Inserted",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this,"Data Not Inserted",Toast.LENGTH_LONG).show();
        }

        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void photoClick(View view){
        Date now = new Date();
        String time = now.toString().replace(":","_");

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),time + "riffImage.jgp");

        Uri tempUri = Uri.fromFile(imageFile);
        i.putExtra(MediaStore.EXTRA_OUTPUT,tempUri);
        i.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
        startActivityForResult(i,0);
    }

    protected  void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode == 0){
            switch (resultCode){
                case Activity.RESULT_OK:
                        if(imageFile.exists()){
                            Log.i("IMAGE SAVED",imageFile.getAbsolutePath());
                            riffphotoPath = imageFile.getAbsolutePath();
                            Bitmap bity = BitmapFactory.decodeFile(riffphotoPath);
                            sI.setImageBitmap(bity);
                        }
                    break;

                case Activity.RESULT_CANCELED:
                    Log.i("IMAGE DID NOT SAVE","PEAK");
                    break;
                default:
                break;

            }
        }
    }

    // Show message Box builder
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


      public String encrypt(String in) {
        try {
            //   This holds the encryted data
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            input = in.getBytes();

            cipher = Cipher.getInstance("DES/CTR/NoPadding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            cipherText = new byte[cipher.getOutputSize(input.length)];

            ctLength = cipher.update(input, 0, input.length, cipherText, 0);

            ctLength += cipher.doFinal(cipherText, ctLength);

            Log.i("CYPHER Encode", cipherText.toString());
        } catch (Exception e) {

        }
          return cipherText.toString();
    }




}
