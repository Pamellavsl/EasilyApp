package com.example.easilyapp;

import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisciplinaAlunoActivity extends AppCompatActivity {

   private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina_aluno);
//
//        textViewUsername = (TextView) findViewById(R.id.layout_button_aluno);
//
//    }

//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog builder = new AlertDialog.Builder(getActivity())
//    }

        constraintLayout = findViewById(R.id.layout_button_aluno);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlertDialog().show();
            }
        });

    }

    public AlertDialog getAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.alert_dialog_aluno)
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
