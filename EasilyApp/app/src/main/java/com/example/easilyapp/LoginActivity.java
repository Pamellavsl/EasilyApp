package com.example.easilyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import javax.annotation.Nullable;

public class LoginActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button mButtonView;
    private EditText mEditEmail;
    private EditText mEditPassword;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        mTextView = findViewById(R.id.textCreateAccount);
        mButtonView = findViewById(R.id.buttonEntrada);
        mEditEmail = findViewById(R.id.edit_email_login);
        mEditPassword = findViewById(R.id.edit_password_login);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
            }
        });

        mButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email = mEditEmail.getText().toString();
                String password = mEditPassword.getText().toString();

                Log.i("Teste", email);
                Log.i("Teste", password);

                if (email == null || email.isEmpty() || password == null || password.isEmpty()) {

                    Toast.makeText(LoginActivity.this, "E-mail e senha devem ser colocados", Toast.LENGTH_SHORT).show();
                    return;

                }

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.i("TesteEntrada", task.getResult().getUser().getUid());
                                fetchUsers(email);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("TesteFalhou", e.getMessage());
                            }
                        });

            }
        });



    }

    private void fetchUsers(String email) {
        FirebaseFirestore.getInstance().collection("/users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e("Erro", e.getMessage(), e);
                            return;
                        }

                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot doc : docs) {
                            User user = doc.toObject(User.class);
                            if(doc != null && user.geteMail() != null && user.geteMail().equals(email)) {
                                if(user.getTipoUser().equalsIgnoreCase("ALUNO")) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("name", user.getUsername());
                                    bundle.putString("registration", user.getMatricula());
                                    startActivity(new Intent(getBaseContext(), DisciplinaAlunoActivity.class).putExtra("bundle", bundle));
                                    Log.d("entrou aluno", user.getTipoUser());
                                    finish();
                                    break;
                                }
                                else if(doc != null && user.geteMail() != null && user.getTipoUser().equalsIgnoreCase("PROFESSOR")){
                                    startActivity(new Intent(getBaseContext(), ProfessorActivity.class));
                                    Log.d("entrou professor", user.getTipoUser());
                                    finish();
                                    break;
                                }
                            }
                            Log.d("Testee", user.getUsername());
                        }
                    }
                });

    }
}
