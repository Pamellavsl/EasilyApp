package com.example.easilyapp;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DisciplinaAlunoActivity extends AppCompatActivity {

   private ConstraintLayout constraintLayout;
   private StudentTask studentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina_aluno);

        constraintLayout = findViewById(R.id.layout_button_aluno);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAlertDialog dialog = new CustomAlertDialog();
                dialog.createDialog(DisciplinaAlunoActivity.this);
                dialog.getAlertDialog().show();

                Bundle bundle = getIntent().getBundleExtra("bundle");

                studentTask = new StudentTask(dialog, bundle.getString("name"),
                        bundle.getString("registration"), DisciplinaAlunoActivity.this);
                studentTask.execute();
            }
        });
    }
}
