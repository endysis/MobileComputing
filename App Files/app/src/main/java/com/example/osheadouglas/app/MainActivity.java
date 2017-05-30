package com.example.osheadouglas.app;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    // https://www.bing.com/images/search?view=detailv2&FORM=OIIRPO&q=sailing+dinghies&id=343498995D41C02DDC1E7DF0B7196F7A86E34593&simid=608048528616131155


    String s = "Hello";
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
        setContentView(R.layout.activity_main);

        //AsyncTaskParseJson j = new AsyncTaskParseJson();
       // j.execute();

        //ImageView i = (ImageView) findViewById(R.id.imageView);
        //Picasso.with(getApplicationContext()).load("http://www.bing.com/cr?IG=E3B20A7C005646DF8DE3A5BA007EB72F&CID=28D5250B697F660B32692CFB68536788&rd=1&h=nNFbmvcIqNTh4SwhGXU2uQQ2jF8b7iyC3REetHDT4PI&v=1&r=http%3a%2f%2fnostalgicillusions.files.wordpress.com%2f2010%2f06%2fcoldplay-12109.jpg&p=DevEx,5008.1").into(i);

        //encrypt();
        //decrypt();
    }


    public void recC(View view){
        Intent intent = new Intent(this,RecordActivity.class);
        intent.putExtra("location"," ");
        intent.putExtra("inspire", " ");
        startActivity(intent);
    }


    public void inspireClick(View view){
        Intent intent = new Intent(this,InspireOptions.class);
        startActivity(intent);
    }

    public void viewClick(View view){
        Intent intent = new Intent(this,RiffSelectActivity.class);
        startActivity(intent);
    }






    public void encrypt() {
        try {
            //   This holds the encryted data
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            input = s.getBytes();

            cipher = Cipher.getInstance("DES/CTR/NoPadding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            cipherText = new byte[cipher.getOutputSize(input.length)];
            ctLength = cipher.update(input, 0, input.length, cipherText, 0);
            ctLength += cipher.doFinal(cipherText, ctLength);
            Log.i("CYPHER Encode", cipherText.toString());
        } catch (Exception e) {
            Log.i("CYPHER Encode", "FAILEDD");
        }
    }

    // To encrypt an decrpt will have to send ciphertext and ctLength



    public void decrypt() {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            byte[] plainText = new byte[cipher.getOutputSize(ctLength)];   // Text variable
            int ptLength = cipher.update(cipherText, 0, ctLength, plainText, 0);
            ptLength += cipher.doFinal(plainText, ptLength);
            Log.i("CYPHER Decode", new String(plainText));
        } catch (Exception e) {

        }
    }



















}




// http://www.bing.com/cr?IG=619AAE9F9C1F4CBC9FC7820B4DFF1226&CID=23404499F65868F701544D40F73E699A&rd=1&h=k4UDjLQvESCjqWJWRUV0E1QtWi46JX5rHSsebLNRg7o&v=1&r=http%3a%2f%2fimages5.fanpop.com%2fimage%2fphotos%2f27500000%2fRadiohead-radiohead-27519232-1920-1080.jpg&p=DevEx,5008.1