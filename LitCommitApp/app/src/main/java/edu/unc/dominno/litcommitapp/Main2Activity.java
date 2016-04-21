package edu.unc.dominno.litcommitapp;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class Main2Activity extends TabActivity {
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tabHost = (TabHost)findViewById(android.R.id.tabhost);
        //tabHost = getTabHost();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Third Tab");

        tab1.setIndicator("Calendar");
        tab1.setContent(new Intent(this, CalendarTab.class));

        tab2.setIndicator("To-Do");
        tab2.setContent(new Intent(this, TodoTab.class));

        tab3.setIndicator("Timer");
        tab3.setContent(new Intent(this, TimerTab.class));

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
    }
}
