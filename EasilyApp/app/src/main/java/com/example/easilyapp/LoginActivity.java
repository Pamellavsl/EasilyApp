package com.example.easilyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    String opcao;
    private TextView mTextView;
    private Button mButtonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        opcao = null;
        
       mTextView = findViewById(R.id.textCreateAccount);
       mButtonView = findViewById(R.id.buttonEntrada); //OLHAR/ ATENÇAO
        /*
        final Intent intent = new Intent(this, CreateAccountActivity.class);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        }

        */
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
            }
        });

        mButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               switch (opcao){
                   case "professor":
                       startActivity(new Intent(LoginActivity.this, ProfessorActivity.class));
                       break;

                   case "aluno":
                       startActivity(new Intent(LoginActivity.this, AlunoActivity.class));
                       break;
               }
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        opcao = parent.getItemAtPosition(position).toString().toLowerCase();
        //Toast.makeText(parent.getContext(), opcao, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
