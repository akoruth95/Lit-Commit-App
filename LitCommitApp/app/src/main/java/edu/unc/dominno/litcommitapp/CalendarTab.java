package edu.unc.dominno.litcommitapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class CalendarTab extends AppCompatActivity {
    CalendarView calendar;
    TextView mm;
    TextView dd;
    TextView yyyy;
    LinearLayout l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_tab);

        mm = (TextView)findViewById(R.id.monthText);
        dd = (TextView)findViewById(R.id.dayText);
        yyyy = (TextView)findViewById(R.id.yearText);
        l = (LinearLayout)findViewById(R.id.bottom);
        initializeCalendar();
    }

    public void initializeCalendar() {
        calendar = (CalendarView)findViewById(R.id.calendarView);



        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayofMonth) {
                //showPop("Date: "+ month + "/" + dayofMonth + "/" + year);
                showPop("" + month);
                switch (month) {
                    case 0:
                        mm.setText("January");
                        break;
                    case 1:
                        mm.setText("February");
                        break;
                    case 2:
                        mm.setText("March");
                        break;
                    case 3:
                        mm.setText("April");
                        break;
                    case 4:
                        mm.setText("May");
                        break;
                    case 5:
                        mm.setText("June");
                        break;
                    case 6:
                        mm.setText("July");
                        break;
                    case 7:
                        mm.setText("August");
                        break;
                    case 8:
                        mm.setText("September");
                        break;
                    case 9:
                        mm.setText("October");
                        break;
                    case 10:
                        mm.setText("November");
                        break;
                    case 11:
                        mm.setText("December");
                        break;
                    default:
                        mm.setText("" + (month+1));
                        break;
                }
                dd.setText(""+dayofMonth);
                yyyy.setText(""+year);

                /*for (int i = 0; i < month; i++) {
                    TextView t = new TextView(getApplicationContext());
                    t.setText("Test "+ month);
                    l.addView(t);
                }*/
                TextView t = new TextView(getApplicationContext());
                t.setText("Test "+ month + "/" + dayofMonth + "/" + year);
                t.setTextColor(Color.BLACK);
                l.addView(t);


            }
        });
    }

    public void showPop(String s) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, s, duration);
        toast.show();
    }
}
