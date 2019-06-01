package com.example.easilyapp;

import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class DisciplinaAlunoActivity extends AppCompatActivity {

   private ConstraintLayout constraintLayout;
   private EditText editTextCode;
   private String code;
   private CodigoTask codigoTask;
   private AlertDialogCode alertDialogCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina_aluno);


        //codigoTask = new CodigoTask(alertDialog);

        constraintLayout = findViewById(R.id.layout_button_aluno);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
                codigoTask.execute();
            }
        });

    }

    public void createDialog() {
        alertDialogCode = new AlertDialogCode();
        alertDialogCode.show(getSupportFragmentManager(), "Diolog");
        codigoTask = new CodigoTask(alertDialogCode);


    }
}
