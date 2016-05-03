package edu.unc.dominno.litcommitapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateChooser extends AppCompatActivity {
    Spinner spinner;
    EditText inputDate;
    Button submitDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_chooser);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.9), (int)(height*0.25));

        inputDate = (EditText)findViewById(R.id.inputDueDate);
        submitDate = (Button)findViewById(R.id.setDueDate);
        submitDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidDate(inputDate.getText().toString())) {
                    showPop("Please input a date that follows this format: yyyy-MM-dd");
                } else {
                    Intent data = new Intent();
                    String text = inputDate.getText().toString();
                    //---set the data to pass back---
                    data.setData(Uri.parse(text));
                    setResult(RESULT_OK, data);
                    //---close the activity---
                    finish();
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

    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
}
