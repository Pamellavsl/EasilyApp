package com.example.easilyapp;

import android.app.Activity;
import android.widget.TextView;

public class CounterRunnable implements Runnable{

    private int seconds;
    private int maxSeconds;
    private Thread insideThread;
    private Activity activity;
    private String time;
    private TextView textView;

    public CounterRunnable(int seconds, int maxSeconds, Activity activity) {
        this.seconds = seconds;
        this.maxSeconds = maxSeconds;
        this.activity = activity;
        insideThread = new Thread(this);
        time = null;
        textView = activity.findViewById(R.id.text_show_timer);

    }


    public Thread getInsideThread() {
        return insideThread;
    }

    @Override
    public void run() {
        while (seconds < maxSeconds){
            try {
                Thread.sleep(1000);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time = "Tempo: " + seconds + "s";
                        textView.setText(time);
                        seconds++;
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
