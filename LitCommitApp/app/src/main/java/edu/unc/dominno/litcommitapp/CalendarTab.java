package edu.unc.dominno.litcommitapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

public class CalendarTab extends AppCompatActivity {
    CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_tab);

        calendar = (CalendarView)findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

            }
        });
    }
}
