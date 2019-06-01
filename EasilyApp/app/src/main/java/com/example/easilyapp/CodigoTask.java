package com.example.easilyapp;


import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import javax.annotation.Nullable;

public class CodigoTask extends AsyncTask<Void, Void, Void> {

    private AlertDialogCode alertDialog;
    private String code;


    public CodigoTask(AlertDialogCode alertDialog) {
        this.alertDialog = alertDialog;
    }

    @Override
    protected Void doInBackground(Void... voids) {


            try {

                while(alertDialog.isAdded()) {
                    Thread.sleep(100);

                }

                FirebaseFirestore.getInstance().collection("/codigos")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.i("Erro", e.getMessage(), e);
                            return;
                        }

                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot doc: docs) {
                            Codigo codigo = doc.toObject(Codigo.class);
                            if (doc != null && codigo.getCodigo() != null && codigo.getCodigo().equalsIgnoreCase(alertDialog.getCode())) {
                                Log.i("Funcionou", alertDialog.getCode());
                                break;
                            }

                        }
                    }
                });

            }

            catch (InterruptedException e) {
                e.printStackTrace();
            }


        return null;
    }


    public String getCode() {
        return code;
    }

    private void setCode(String code) {
        this.code = code;
    }
}
