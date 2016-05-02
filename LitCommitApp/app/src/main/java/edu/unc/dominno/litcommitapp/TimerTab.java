package edu.unc.dominno.litcommitapp;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.text.Format;
import java.util.concurrent.TimeUnit;

public class TimerTab extends AppCompatActivity {

    private CountDownTimer pomodoroTimer;
    private CountDownTimer breakTimer;
    private boolean timerStarted;
    private Button studyTimerBtn;
    private Button breakBtn;
    private Button pauseBtn;
    private Button startBtn;
    private TextView currModeTxt;
    private TextView longerBreak;
    private ProgressBar mProgress;
    //holds count down timer's paused status
    private boolean isPaused = false;
    private boolean onBreak = false;
    private int numPomodoros;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_tab);
        studyTimerBtn = (Button) findViewById(R.id.studyTimer);
        pauseBtn = (Button) findViewById(R.id.pause);
        breakBtn = (Button) findViewById(R.id.breakTimer);
        startBtn = (Button) findViewById(R.id.start);
        currModeTxt = (TextView) findViewById(R.id.currentMode);
        longerBreak = (TextView) findViewById(R.id.longerBreak);
        mProgress = (ProgressBar) findViewById(R.id.progressBar);

        //User should start out studying
        breakBtn.setEnabled(false);

    }

    public void start(View v) {
        isPaused = false;
        //user has already clicked start button, so disable it
        studyTimerBtn.setEnabled(false);
        //enable the Pause button
        pauseBtn.setEnabled(true);
        if (!onBreak) {
            pomodoroTimer = new CountDownTimer(5000, 1000) {

                public void onTick(long millisUntilFinished) {
                    //do something on every tick
                    if (isPaused) {
                        showPop("Canceled");
                    }
                    int secs = (int) (millisUntilFinished / 1000);
                    int mins = secs / 60;
                    secs = secs % 60;
                    studyTimerBtn.setText("" + mins +": " + String.format("%02d", secs));
                }

                public void onFinish() {
                    currModeTxt.setText("Break Time!");
                    //Disable Study timer
                    studyTimerBtn.setText("25:00");
                    studyTimerBtn.setEnabled(false);
                    //Disable pause button
                    pauseBtn.setEnabled(false);
                    try {
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                        r.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Get instance of vibrator from current Context
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    //Vibrate 3 times for  500 milliseconds
                    long[] pattern = {0, 500, 300, 500, 300, 500};
                    v.vibrate(pattern, -1);
                    startBtn.setText(("Start break"));
                    //User is now on his break
                    onBreak = true;
                    //Enable break timer
                    breakBtn.setEnabled(true);
                    //update # of Pomodoro Sessions
                    numPomodoros++;
                    //update progress bar
                    int currProgress = mProgress.getProgress();
                    if (currProgress == 100) {
                        //Restart Pomodoro intervals
                        mProgress.setProgress(0);

                    } else {
                        mProgress.incrementProgressBy(25);
                        if (mProgress.getProgress() == mProgress.getMax()) {
                            //user deserves longer break (has been 4 Pomodoro Cycles)
                            longerBreak.setText(R.string.longerBreak);
                            breakBtn.setText("10:00");

                        }



                    }


                }

            }.start();
        } else {
            //User should be on break
            if (numPomodoros == 4) {
                numPomodoros = 0;
                mProgress.setProgress(0);
                //On regular (shorter) break
                longerBreak.setText(R.string.regularBreak);
                //cancel the current break timer
                if (breakTimer != null)
                    breakTimer = null;
                //On Longer break
                breakTimer = new CountDownTimer(10000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        //do something on every tick
                        int secs = (int) (millisUntilFinished / 1000);
                        int mins = secs / 60;
                        secs = secs % 60;
                        breakBtn.setText("" + mins + ": " + String.format("%02d", secs));

                    }

                    @Override
                    public void onFinish() {
                        currModeTxt.setText("Back to Studying!");
                        breakBtn.setText("5:00");
                        //Disable Break timer
                        breakBtn.setEnabled(false);
                        //Disable pause button
                        pauseBtn.setEnabled(false);
                        try {
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //Get instance of vibrator from current Context
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        //Vibrate 2 times for  700 milliseconds
                        long[] pattern = {0, 700, 300, 700};
                        v.vibrate(pattern, -1);
                        startBtn.setText("Start Study");
                        //User should now be studying
                        studyTimerBtn.setEnabled(true);
                        onBreak = false;

                    }
                }.start();

            } else {
                breakTimer = new CountDownTimer(5000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        //do something on every tick
                        int secs = (int) (millisUntilFinished / 1000);
                        int mins = secs / 60;
                        secs = secs % 60;
                        breakBtn.setText("" + mins + ": " + String.format("%02d", secs));

                    }

                    @Override
                    public void onFinish() {
                        currModeTxt.setText("Back to Studying!");
                        breakBtn.setText("5:00");
                        //Disable Break timer
                        breakBtn.setEnabled(false);
                        //Disable pause button
                        pauseBtn.setEnabled(false);
                        try {
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //Get instance of vibrator from current Context
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        //Vibrate 2 times for  700 milliseconds
                        long[] pattern = {0, 700, 300, 700};
                        v.vibrate(pattern, -1);
                        startBtn.setText("Start Study");
                        //User should now be studying
                        studyTimerBtn.setEnabled(true);
                        onBreak = false;

                    }
                }.start();

            }

        }
        timerStarted = true;

    }

    public void pause(View v) {
        //user requested to pause the count down timer
        isPaused = true;
        pomodoroTimer.cancel();
        //enable start button
        studyTimerBtn.setEnabled(false);

    }

    public void reset(View v) {
        studyTimerBtn.setEnabled(true);
        pomodoroTimer.cancel();
        pomodoroTimer = null;
        breakTimer = null;
        studyTimerBtn.setText(("25:00"));
        breakBtn.setText("5:00");
        startBtn.setText("START");

    }

    public void showPop(String s) {
        Context cxt = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(cxt, s, duration);
        toast.show();
    }


}
