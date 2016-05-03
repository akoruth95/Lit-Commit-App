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
    private Button pauseBtn, resetBtn;
    private Button startBtn;
    private TextView currModeTxt;
    private TextView longerBreak;
    private ProgressBar mProgress;
    //holds count down timer's paused status
    private boolean isPaused = false;
    private boolean onBreak = false;
    private int numPomodoros;
    private long studyTimeUntilFinished;
    private long breakTimeUntilFinished;
    //the remaining time when user hit paused
    private long studyPausedTime;
    private long breakPausedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_tab);
        studyTimerBtn = (Button) findViewById(R.id.studyTimer);
        pauseBtn = (Button) findViewById(R.id.pause);
        breakBtn = (Button) findViewById(R.id.breakTimer);
        startBtn = (Button) findViewById(R.id.start);
        resetBtn = (Button) findViewById(R.id.reset);
        currModeTxt = (TextView) findViewById(R.id.currentMode);
        longerBreak = (TextView) findViewById(R.id.longerBreak);
        mProgress = (ProgressBar) findViewById(R.id.progressBar);

        //User should start out studying
        breakBtn.setEnabled(false);

    }

    public void start(View v) {

        //user has already clicked start button, so disable it
        startBtn.setEnabled(false);

        //studyTimerBtn.setEnabled(false);
        //enable the Pause button
        pauseBtn.setEnabled(true);
        resetBtn.setEnabled(true);
        if (!onBreak) {
            if (isPaused) {
                pomodoroTimer = new CountDownTimer(studyTimeUntilFinished, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        studyTimeUntilFinished = millisUntilFinished;
                        int secs = (int) (millisUntilFinished / 1000);
                        int mins = secs / 60;
                        secs = secs % 60;
                        studyTimerBtn.setText("" + mins + ": " + String.format("%02d", secs));
                    }

                    @Override
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
                        //enable start button
                        startBtn.setEnabled(true);
                    }

                }.start();

            } else {
                //not Paused
                pomodoroTimer = new CountDownTimer(1500000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        studyTimeUntilFinished = millisUntilFinished;
                        int secs = (int) (millisUntilFinished / 1000);
                        int mins = secs / 60;
                        secs = secs % 60;
                        studyTimerBtn.setText("" + mins + ": " + String.format("%02d", secs));
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
                        //enable start button
                        startBtn.setEnabled(true);


                    }

                }.start();
            }
        }
        else {
            if (isPaused) {
                breakTimer = new CountDownTimer(breakTimeUntilFinished, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        //do something on every tick
                        breakTimeUntilFinished = millisUntilFinished;
                        int secs = (int) (millisUntilFinished / 1000);
                        int mins = secs / 60;
                        secs = secs % 60;
                        breakBtn.setText("" + mins + ": " + String.format("%02d", secs));

                    }

                    @Override
                    public void onFinish() {
                        currModeTxt.setText("Back to Studying!");
                        if (numPomodoros == 4)
                            breakBtn.setText("10:00");
                        else {
                            breakBtn.setText("5:00");
                        }
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
                        //enable start button
                        startBtn.setEnabled(true);
                    }
                }.start();
            }
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
                breakTimer = new CountDownTimer(600000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        //do something on every tick
                        breakTimeUntilFinished = millisUntilFinished;
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
                        //enable start button
                        startBtn.setEnabled(true);

                    }
                }.start();

            } else {
                //On regular (shorter) break
                breakTimer = new CountDownTimer(300000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        //do something on every tick
                        breakTimeUntilFinished = millisUntilFinished;
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
                        //enable start button
                        startBtn.setEnabled(true);

                    }
                }.start();

            }

        }

        isPaused = false;

    }

    public void pause(View v) {
        //user requested to pause the count down timer
        isPaused = true;
        if (pomodoroTimer != null) {
            pomodoroTimer.cancel();
            //studyTimerBtn =
        }

        if (breakTimer != null)
            breakTimer.cancel();
        //enable reset button
        resetBtn.setEnabled(true);
        //enable start button
        startBtn.setEnabled(true);
        //disable pause button
        pauseBtn.setEnabled(false);

    }

    public void reset (View v) {
        //disable reset and pause button
        resetBtn.setEnabled(false);
        pauseBtn.setEnabled(false);
        //enable the start button
        startBtn.setEnabled(true);
        //reset timers
        if (pomodoroTimer != null) {
            pomodoroTimer.cancel();
            pomodoroTimer = null;
        }
        if (breakTimer != null) {
            breakTimer.cancel();
            breakTimer = null;
        }

        studyTimerBtn.setText(("25:00"));
        if (numPomodoros == 4) {
            //longer break
            breakBtn.setText("10:00");
        } else {
            breakBtn.setText("5:00");
        }

        startBtn.setText("START");
        //not paused anymore
        isPaused = false;


    }

    public void showPop(String s) {
        Context cxt = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(cxt, s, duration);
        toast.show();
    }


}
