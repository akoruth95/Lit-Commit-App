package edu.unc.dominno.rekindleapp;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class Main2Activity extends TabActivity {
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        setContentView(R.layout.activity_main);

        tabHost = (TabHost)findViewById(android.R.id.tabhost);
        //tabHost = getTabHost();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Third Tab");
        TabHost.TabSpec tab4 = tabHost.newTabSpec("Fourth Tab");

        tab1.setIndicator("Home");
        tab1.setContent(new Intent(this, Ratings.class));

        tab2.setIndicator("My Ratings");
        tab2.setContent(new Intent(this, ShowRatings.class));

        tab3.setIndicator("", getResources().getDrawable(R.drawable.micon));
        tab3.setContent(new Intent(this, Messages.class));

        tab4.setIndicator("FAQ");
        tab4.setContent(new Intent(this, FAQ.class));

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        tabHost.addTab(tab4);
    }
}
