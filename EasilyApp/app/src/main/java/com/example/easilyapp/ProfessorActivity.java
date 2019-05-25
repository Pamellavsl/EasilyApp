package com.example.easilyapp;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProfessorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        ConstraintLayout constraintLayout = findViewById(R.id.layout_disciplina1);

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfessorActivity.this, DisciplinaProfessorActivity.class); // a intençao da troca de tela
                startActivity(intent); //inicia uma nova acitivity, ou seja, troca de tela
            }
        });
    }


}
