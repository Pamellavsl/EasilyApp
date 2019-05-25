package com.example.easilyapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                AlertDialog alertDialog = showDialog(); //janela aparecera quando o constraintLayout for clicado
                alertDialog.show(); // faz com que a janela apareça na tela
            }
        });

    }

    public AlertDialog showDialog() { //criaçao da janela
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Codigo").setMessage("HF7A66DS")
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
}
