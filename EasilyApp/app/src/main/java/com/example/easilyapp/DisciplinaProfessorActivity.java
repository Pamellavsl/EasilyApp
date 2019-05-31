package com.example.easilyapp;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

public class DisciplinaProfessorActivity extends AppCompatActivity {

    private Button mButtonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina_professor);

        mButtonView = findViewById(R.id.button_pdf);
        mButtonView.setOnClickListener((v)-> {

                });


        ConstraintLayout constraintLayout = findViewById(R.id.layout_button2); // chamar um construtor
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Codigo codigoAleatorio = new Codigo(codigoAleatorio(6));

                FirebaseFirestore.getInstance().collection("codigos")
                        .add(codigoAleatorio)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.i("Sucesso", codigoAleatorio.getCodigo());

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("Falha", codigoAleatorio.getCodigo());

                            }
                        });
                AlertDialog alertDialog = showDialog(codigoAleatorio.getCodigo()); //janela aparecera quando o constraintLayout for clicado
                alertDialog.show(); // faz com que a janela apareça na tela
            }
        });

    }

    public AlertDialog showDialog(String mensagem) { //criaçao da janela
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Codigo").setMessage(mensagem)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    public String codigoAleatorio(int caracteres){

        Random rand = new Random();
        char[] letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789".toCharArray();


        StringBuffer sb = new StringBuffer();
        for (int i=0; i<caracteres; i++) {
            int ch = rand.nextInt(letras.length);
            sb.append(letras[ch]);
        }

        return sb.toString();
    }
}
