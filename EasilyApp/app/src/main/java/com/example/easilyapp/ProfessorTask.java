package com.example.easilyapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Nullable;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class ProfessorTask extends AsyncTask<Void, Void, Void> {

    private Activity activity;
    private AlertDialog alertDialog;
    private int seconds;
    private List<String> studentsChecked;
    private final String userDefaultEmail = "pamellavsl@gmail.com";
    private final String passwordEmail = "32353998";
    FirebaseFirestore firestore;
    private String path;
    private String referenceDocument;
    private int maxSeconds;


    public ProfessorTask(Activity activity, AlertDialog alertDialog) {
        this.activity = activity;
        this.alertDialog = alertDialog;
        seconds = 0;
        studentsChecked = new LinkedList<>();
    }

    @Override
    protected void onPreExecute() {
        firestore = FirebaseFirestore.getInstance();
        path = "codigos";
        referenceDocument = "path_code";
        maxSeconds = 60;

    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            /*
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            String path = "codigos";
            String referenceDocument = "path_code";
            int maxSeconds = 60;
            */
            while (alertDialog.isShowing()) {
                Thread.sleep(100);
            }

            List<Student> studentList = generateStudentsOfFirestore();
            List<String> listIds = new LinkedList<>();

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
                                                listIds.add(snapshot.getId());
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
                                            sendEmailWithStudentsChecked(studentsChecked, "philipelunacc@gmail.com");
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

        public List<String> missingStudents() {
        List<String> listMissingStudents = new LinkedList<>();
        List<Student> students = new LinkedList<>();

        students = generateStudentsOfFirestore();

        for(Student student: students) {
            for(String matricula: studentsChecked) {
                if(!student.getMatricula().equals(matricula)) {
                    listMissingStudents.add(student.getNome());
                }
            }
        }


        Collections.sort(listMissingStudents);
        return listMissingStudents;
    }



    private void sendEmailWithStudentsChecked(List<String> students, String addressee){
        try {

            String bodyEmail = "";

            for (String nameStudent : students){
                bodyEmail = bodyEmail + nameStudent + "\n";
            }

            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.socketFactory.port", "587");
            properties.put("mail.smpt.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.socketFactory.fallback", "false");

            Session session = Session.getDefaultInstance(properties,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("pamellavsl@gmail.com", "32353998");
                        }
                    });

            session.setDebug(true);



            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("pamellavsl@gmail.com"));

            Address[] addresses = InternetAddress.parse("philipelunacc@gmail.com");
            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setSubject("Lista de Alunos com FALTA");

            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(bodyEmail, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);

            message.setContent(multipart);

            message.setText(bodyEmail);

            Thread  thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        Transport.send(message);
                        Log.i("EMAIL_SEND...", message.getSubject());

                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        } catch (MessagingException e){
            Log.i("MESSAGE_EXCEPTION", e.getMessage());
        }

    }

}