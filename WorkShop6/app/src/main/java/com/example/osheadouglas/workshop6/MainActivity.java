package com.example.osheadouglas.workshop6;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;

    // IN the XML
    EditText editRiffName;
    EditText editRiffKey;
    EditText editUserMood;
    EditText editRiffLocation;
    EditText editTextId;

    Button btnAddData;
    Button btnViewAll;

    Button btnUpdate;

    TextView testView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this); // This is context in the database helper class

        // Linking with views in XML
        editRiffName = (EditText)findViewById(R.id.nameText);
        editRiffKey = (EditText)findViewById(R.id.keyText);
        editUserMood = (EditText)findViewById(R.id.moodText);
        editRiffLocation = (EditText)findViewById(R.id.locText);
        btnViewAll = (Button)findViewById(R.id.viewBtn); // View button
        testView = (TextView) findViewById(R.id.textTest);
        btnUpdate = (Button) findViewById(R.id.updateBtn); // Update Button
    }

    public void updateData(View view){
        boolean isUpdate = myDb.updateData(editTextId.getText().toString(),editRiffName.getText().toString(),editUserMood.getText().toString(),editRiffLocation.getText().toString());
        if(isUpdate == true){
            Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(MainActivity.this,"Data not updated ",Toast.LENGTH_LONG).show();
    }



    //DB button
    public void addData(View view){
      boolean isInserted = myDb.insertData(editRiffName.getText().toString(),editUserMood.getText().toString(),editRiffLocation.getText().toString()); // Inserts the data using the method in the database helper class
      if(isInserted == true){
          Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
      } else {
          Toast.makeText(MainActivity.this,"Data Not Inserted",Toast.LENGTH_LONG).show();
      }
    }





    public void viewData(View v){
        Cursor result = myDb.getAllData();


        if(result.getCount() == 0){
            // show message
            showMessage("Error","Nothing Found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while(result.moveToNext()){
            buffer.append("ID :" + result.getString(0) + "\n");
            buffer.append("NAME :" + result.getString(1) + "\n");
            buffer.append("MOOD :" + result.getString(2) + "\n");
            buffer.append("LOCATION :" + result.getString(3) + "\n\n");
        }

        // Show all data
        showMessage("Data",buffer.toString());
    }




    // Show message Box builder
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }






    public void getMusicData(View view){

        Intent intent = new Intent(this,Music.class);
        startActivity(intent);
    }
}























