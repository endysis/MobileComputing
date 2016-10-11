package uk.ac.lincoln.students.rob.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;


public class helloworld extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helloworld);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_helloworld, menu);
        return true;
    }
    /** Called when user touches the CLICK ME! button */
    public void textChangeButton(View view){

        // Creates an intent to start the activity called 'Test Activity'
        Intent intent = new Intent(this,TestActivity.class);
        // Find the textview control to change first
        TextView tv1 = (TextView)findViewById(R.id.changeText);
        // set visibility to visible
        tv1.setVisibility(View.VISIBLE);
        // edit textView control value
        tv1.setText("Hello TestActivity");
        // Get the string value of textView
        String message = tv1.getText().toString();
        // pass the string to the new testActivity
        intent.putExtra("testParameter",message);

        //Start the testActivity
        startActivity(intent);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
