package com.example.easilyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateAccountActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner sistemas;
    private Button mButtonView;
    private EditText mEditEmail;
    private EditText mEditPassword;
    private EditText mNomeCompleto;
    private EditText mMatricula;
    private Spinner mSpinner;
    private String opcao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mEditEmail = findViewById(R.id.edit_email_cadastro);
        mEditPassword = findViewById(R.id.edit_senha_cadastro);
        mNomeCompleto = findViewById(R.id.edit_nome_cadastro);
        mMatricula = findViewById(R.id.edit_matricula_cadastro);


        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Opcao, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        mButtonView = findViewById(R.id.buttonCreateAccount);
        mButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
                finish();
            }
        });
    }

    private void createUser() {
        String email = mEditEmail.getText().toString();
        String senha = mEditPassword.getText().toString();
        String nomeCompleto = mNomeCompleto.getText().toString();
        String matricula = mMatricula.getText().toString();

        if (email == null || email.isEmpty() || senha == null || senha.isEmpty() || nomeCompleto == null || nomeCompleto.isEmpty() || matricula == null || matricula.isEmpty()) {
            Toast.makeText(this, "Nome, Matricula, E-mail e Senha devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Log.i("Teste", task.getResult().getUser().getUid());
                            saveUserInFirebase();

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste", e.getMessage());

                    }
                });
    }

    private void saveUserInFirebase() {
        //String uid = FirebaseAuth.getInstance().getUid();
        String username = mNomeCompleto.getText().toString();
        String matricula = mMatricula.getText().toString();
        String email = mEditEmail.getText().toString();
        String senha = mEditPassword.getText().toString();

        User usuario = new User(username, matricula, email, senha);
        usuario.tipoUser(opcao); // pega o tipo do usuario

        FirebaseFirestore.getInstance().collection("users")
                .add(usuario) // escrevendo dados no database
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("Sucesso", documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Falha", e.getMessage());

                    }
                });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String opcao = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), opcao, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
