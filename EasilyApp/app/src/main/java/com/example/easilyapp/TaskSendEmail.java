package com.example.easilyapp;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.Properties;

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


public class TaskSendEmail extends AsyncTask<Void, Void, Void> {

    private ProfessorTask task;


    public TaskSendEmail(ProfessorTask task) {
        this.task = task;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Void doInBackground(Void... voids) {

        Status status;
        try {
            while (task.getStatus().equals(Status.RUNNING) && !task.getStatus().equals(Status.PENDING)) {
                Thread.sleep(100);
                Log.i("ENTROU NO LOOP", task.getStatus().toString());
            }
            Log.i("ISENT: ", String.valueOf(task.getListMissingStudents().size()));
            sendEmailWithStudentsChecked(task.getListMissingStudents(), "philipelunacc@gmail.com");

        }
        catch (InterruptedException e) {
            Log.i(e.getMessage(), "Erro");
        }

        return null;
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

