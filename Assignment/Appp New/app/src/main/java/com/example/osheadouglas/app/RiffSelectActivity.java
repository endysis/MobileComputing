package com.example.osheadouglas.app;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RiffSelectActivity extends AppCompatActivity {

    private DatabaseHelper myDb;
    private ListView lV;
    ArrayList<String> riffItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;



    private String riffID;
    private String riffName;
    private String riffInspMood;
    private String riffDescription;
    private String riffPath;
    private String riffPhoPath;
    private String riffLoc;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riff_select);
        myDb = new DatabaseHelper(this);
        lV = (ListView) findViewById(R.id.riffList);

        addList();

        lV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                Cursor result = myDb.getAllData();

                String friend = (String) adapterView.getItemAtPosition(i);
               //Toast.makeText(view.getContext(), "The List title is " + friend,Toast.LENGTH_LONG).show();

                while(result.moveToNext()){
                    if(result.getString(1).equals(friend)){
                        riffID = result.getString(0);
                        riffName = result.getString(1);
                        riffInspMood = result.getString(2);
                        riffDescription = result.getString(3);
                        riffPath = result.getString(4);
                        riffPhoPath = result.getString(5);
                        riffLoc = result.getString(6);
                        break;
                    }
                }
               launchActivity();
            }
        });
    }



    private void addList(){
        Cursor result = myDb.getAllData();

        while(result.moveToNext()){
            riffItems.add(result.getString(1));
        }
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,riffItems);
        lV.setAdapter(adapter);
    }



    public void showClick(View view){
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



    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


    private void launchActivity(){
        Log.i("TAG1",riffName);
        Intent i = new Intent(this, RiffViewActivity.class);
        i.putExtra("rId",riffID);
        i.putExtra("rName",riffName);
        i.putExtra("rMI",riffInspMood);
        i.putExtra("rD",riffDescription);
        i.putExtra("rP",riffPath);
        i.putExtra("rPP",riffPhoPath);
        i.putExtra("rL",riffLoc);
        startActivity(i);
    }


}



















