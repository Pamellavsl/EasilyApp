package com.example.easilyapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class TimeTask extends AsyncTask<Void, Void, Void> {

    private Activity activity;
    private AlertDialog alertDialog;
    private int seconds;
    private final int maxSeconds = 60;
    private final String path = "/codigos";
    private final String referenceDocument = "path_code";


    public TimeTask(Activity activity, AlertDialog alertDialog) {
        this.activity = activity;
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
                    .document(referenceDocument).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i("Deletado", aVoid.toString());
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