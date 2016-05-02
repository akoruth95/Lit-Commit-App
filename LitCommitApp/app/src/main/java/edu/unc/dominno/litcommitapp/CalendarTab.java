package edu.unc.dominno.litcommitapp;

import android.content.Context;
import android.database.Cursor;
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
    String mnum;
    String dnum;
    TextView dd;
    TextView yyyy;
    String date;
    String where_clause;
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
                        mnum = "01";
                        break;
                    case 1:
                        mm.setText("February");
                        mnum = "02";
                        break;
                    case 2:
                        mm.setText("March");
                        mnum = "03";
                        break;
                    case 3:
                        mm.setText("April");
                        mnum = "04";
                        break;
                    case 4:
                        mm.setText("May");
                        mnum = "05";
                        break;
                    case 5:
                        mm.setText("June");
                        mnum = "06";
                        break;
                    case 6:
                        mm.setText("July");
                        mnum = "07";
                        break;
                    case 7:
                        mm.setText("August");
                        mnum = "08";
                        break;
                    case 8:
                        mm.setText("September");
                        mnum = "09";
                        break;
                    case 9:
                        mm.setText("October");
                        mnum = "10";
                        break;
                    case 10:
                        mm.setText("November");
                        mnum = "11";
                        break;
                    case 11:
                        mm.setText("December");
                        mnum = "12";
                        break;
                    default:
                        mm.setText("" + (month+1));
                        mnum = ""+ (month+1);
                        break;
                }
                dd.setText(""+dayofMonth);
                yyyy.setText(""+year);

                dnum = Integer.toString(dayofMonth);
                if (dayofMonth < 10) {
                    dnum = "0" + Integer.toString(dayofMonth);
                }

                date = yyyy.getText() + "-" + mnum + "-" + dnum;
                Log.v("DOMINNO", date + " chosen");
                //where_clause = DBOpener.DUE_DATE + "=" + date;
                //Cursor cursor = getContentResolver().query(TodoCP.DB_URI, DBOpener.ALL_COLUMNS, where_clause, null, null);
                Cursor cursor = getContentResolver().query(TodoCP.DB_URI, DBOpener.ALL_COLUMNS, null, null, null);
                if (cursor == null) {
                    Log.v("DOMINNO", "cursor is null");
                }
                cursor.moveToFirst();//move to first row

                Cursor c2;

                l.removeAllViews();

                while (!cursor.isAfterLast()) {
                    String s = "";
                    String checked = cursor.getString(cursor.getColumnIndex(DBOpener.DUE_DATE));
                    cursor.getPosition();
                    if (checked.equals(date)) {
                        where_clause = DBOpener.TODO_ID + "=" + cursor.getInt(cursor.getColumnIndex(DBOpener.TODO_ID));
                        c2 = getContentResolver().query(TodoCP.DB_URI, DBOpener.ALL_COLUMNS, where_clause, null, null);
                        c2.moveToFirst();
                        s = c2.getString(c2.getColumnIndex(DBOpener.TODO_TEXT));
                        Log.v("DOMINNO", s);
                        TextView t = new TextView(getApplicationContext());
                        t.setText(s);
                        t.setTextColor(Color.BLACK);
                        l.addView(t);
                    }
                    //Log.v("DOMINNO", "In while loop");
                    //String s = (cursor.getString(cursor.getColumnIndex(DBOpener.TODO_TEXT)));

                    cursor.moveToNext();
                }

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
