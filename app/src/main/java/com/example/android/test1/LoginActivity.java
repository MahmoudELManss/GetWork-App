package com.example.android.test1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class LoginActivity extends AppCompatActivity {

    EditText user;
    EditText pass;
    ProgressBar bar;
    private FirebaseAuth mAuth;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.SignInemail);
        pass = findViewById(R.id.SignInpassword);
        bar = findViewById(R.id.barLogin);
        login = findViewById(R.id.register_btn);


        bar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();

        //************************************ so its time to login *****************
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = user.getText().toString().trim();
                String password = pass.getText().toString().trim();


                if (email.isEmpty()) {
                    user.setError("يجب ادخال الايميل");
                    user.requestFocus();
                    return;

                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    user.setError("من فضلك ادخل ايميل صالح");
                    user.requestFocus();
                    return;

                }
                if (password.isEmpty()) {
                    pass.setError("يجب ادخال الرقم السرى");
                    pass.requestFocus();
                    return;

                }

                bar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            bar.setVisibility(View.GONE);

                            finish();
                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            ChooseActivity.email=email;
                            HomeActivity.isLogged=true;
                            startActivity(i);


                        } else {


                            bar.setVisibility(View.GONE);

                            if (!(task.getException() instanceof FirebaseAuthUserCollisionException)) {
                                Toast.makeText(getApplicationContext(), "تاكد من الرقم السرى هناك شيئا خطا ! او قم بانشاء حساب جديد", Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(getApplicationContext(), "Some error occurred: Message " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }

                        }

                    }
                });


            }
        });


    }


}
