package com.example.easilyapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyTask extends AsyncTask<Void, Void, Void> {

    private Activity activity;
    private String code;
    private String path;
    private AlertDialog alertDialog;
    private int seconds;
    private final int maxSeconds = 60;


    public MyTask(Activity activity, String code, String path, AlertDialog alertDialog) {
        this.activity = activity;
        this.code = code;
        this.path = path;
        this.alertDialog = alertDialog;
        seconds = 0;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {

            while (alertDialog.isShowing()) {
                Thread.sleep(100);


            }

            CounterRunnable runnable = new CounterRunnable(seconds, maxSeconds, activity);
            runnable.getInsideThread().start();
            runnable.getInsideThread().join();

            FirebaseFirestore.getInstance().collection(path)
                    .document(code).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Deletado", code);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Nao deletado", e.getMessage());
                        }
                    });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

}