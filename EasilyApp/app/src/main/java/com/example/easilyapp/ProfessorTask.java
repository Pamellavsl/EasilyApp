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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class ProfessorTask extends AsyncTask<Void, Void, Void> {

    private Activity activity;
    private AlertDialog alertDialog;
    private int seconds;
    private List<String> studentsChecked;


    public ProfessorTask(Activity activity, AlertDialog alertDialog) {
        this.activity = activity;
        this.alertDialog = alertDialog;
        seconds = 0;
        studentsChecked = new LinkedList<>();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            String path = "codigos";
            String referenceDocument = "path_code";
            int maxSeconds = 60;

            while (alertDialog.isShowing()) {
                Thread.sleep(100);
            }

            List<Student> studentList = generateStudentsOfFirestore();

            CounterRunnable runnable = new CounterRunnable(seconds, maxSeconds, activity);
            runnable.getInsideThread().start();
            runnable.getInsideThread().join();

            firestore.collection(path)
                    .document(referenceDocument).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i("Deletado", referenceDocument);

                            firestore.collection("presence_students")
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                            if (e != null) {
                                                Log.i("EXCEPTION_FIRESTORE", e.getMessage());
                                                return;
                                            }

                                            List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                                            for (DocumentSnapshot snapshot : snapshots) {
                                                if (snapshot != null) {
                                                    Map<String, Object> fieldStudent = snapshot.getData();

                                                    if (fieldStudent != null && !fieldStudent.isEmpty()) {
                                                        for (Student student : studentList) {
                                                            if (fieldStudent.containsKey(student.getMatricula())
                                                                    && !studentsChecked.contains(fieldStudent.get(student.getMatricula()).toString())) {
                                                                studentsChecked.add(fieldStudent.get(student.getMatricula()).toString());
                                                                Log.i("STUDENT_CHECKED", student.getNome());
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            Log.i("SIZE_LIST_CHECKED", String.valueOf(studentsChecked.size()));
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


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Student> generateStudentsOfFirestore() {
        List<Student> students = new LinkedList<>();

        FirebaseFirestore.getInstance().collection("alunos")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();

                        for (DocumentSnapshot snapshot : snapshots) {
                            if (snapshot != null) {
                                students.add(snapshot.toObject(Student.class));
                            }
                        }
                    }
                });

        return students;
    }

}