package com.example.easilyapp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import javax.annotation.Nullable;

public class FirestoreRunnable implements Runnable {

    private FirebaseFirestore firestore;
    private List<Student> students;
    private String path;
    private String reference;

    public Thread getInsideThread() {
        return insideThread;
    }

    private boolean flag;
    private Thread insideThread;

    public FirestoreRunnable(FirebaseFirestore firestore, List<Student> students, String path, String reference) {
        this.firestore = firestore;
        this.students = students;
        this.path = path;
        this.reference = reference;
        flag = false;
        insideThread = new Thread(this);
    }

    @Override
    public void run() {

        try {
            while (!flag) {
                firestore.collection(path)
                        .document(reference).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.i("Deletado", path);
                                firestore.collection("/alunos")
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                                List<DocumentSnapshot> documentos = queryDocumentSnapshots.getDocuments();
                                                for (DocumentSnapshot doc : documentos) {
                                                    Student student = doc.toObject(Student.class);
                                                    if (student != null)
                                                        students.add(student);
                                                }
                                                flag = true;
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Nao deletado", e.getMessage());
                            }
                        });

                if (!flag) {
                    Thread.sleep(200);
                }

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
