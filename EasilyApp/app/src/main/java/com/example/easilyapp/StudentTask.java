package com.example.easilyapp;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.mail.Transport;

public class StudentTask extends AsyncTask<Void, Void, Void> {

    private CustomAlertDialog alertDialog;
    private String code;
    private String nameStudent;
    private String registryStudent;

    public StudentTask(CustomAlertDialog alertDialog, String nameStudent, String registryStudent) {
        this.alertDialog = alertDialog;
        this.nameStudent = nameStudent;
        this.registryStudent = registryStudent;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            while (alertDialog.getAlertDialog().isShowing()) {
                Thread.sleep(100);

            }
            code = alertDialog.getCode();
            Log.i("FINISH_DIALOG", "finish");

            firestore.collection("codigos")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                Log.i("Erro", e.getMessage(), e);
                                return;
                            }

                            List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot doc : docs) {
                                Codigo codigo = doc.toObject(Codigo.class);
                                if (doc != null && codigo.getCodigo() != null && codigo.getCodigo().equals(getCode())) {
                                    Log.i("Funcionou", getCode());
                                    Map<String, Object> mapStudent = new HashMap<>();
                                    mapStudent.put(registryStudent, nameStudent);
                                    firestore.collection("presence_students")
                                            .add(mapStudent)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.i("WRITE_PRESENCE", documentReference.getId());
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.e("EXCEPTION_PRESENCE", e.getMessage(), e);
                                                }
                                            });
                                    break;
                                }

                            }
                        }
                    });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return null;
    }

    private String getCode() {
        return code;
    }

}
