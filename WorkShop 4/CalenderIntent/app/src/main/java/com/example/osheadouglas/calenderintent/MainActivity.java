package com.example.osheadouglas.calenderintent;

import android.content.Intent;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.provider.CalendarContract;
import android.widget.EditText;

import java.util.Calendar;

import static com.example.osheadouglas.calenderintent.R.styleable.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void eventIntent(View view) { // Action-What i want to do    // Data I want to work with
        Intent calendarIntent = new Intent(Intent.ACTION_INSERT,CalendarContract.Events.CONTENT_URI);
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2015,12,25,00,00);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2015,12,25,00,01);
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY,true);
        calendarIntent.putExtra(CalendarContract.Events.TITLE, "Xmas!");
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "North Pole");
        startActivity(calendarIntent);
    }

    public void startTimer(View view){

        EditText text = (EditText)findViewById(R.id.timerInput);
        String value = text.getText().toString();
        int iValue = Integer.parseInt(value);

        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE,"timer started!");
        intent.putExtra(AlarmClock.EXTRA_LENGTH,iValue);
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI,false);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }
}
