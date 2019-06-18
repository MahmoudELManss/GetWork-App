package com.example.android.test1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Register extends AppCompatActivity {

    private Spinner address_sp;
    private EditText name_et, email_et, password_et, phone_et;
    private Button register;


    private FirebaseAuth mAuth;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        address_sp = findViewById(R.id.Raddress_sp);
        name_et = findViewById(R.id.Rname_et);
        password_et = findViewById(R.id.Rpassword_et);
        email_et = findViewById(R.id.Remail_et);
        phone_et = findViewById(R.id.Rphone_et);
        bar = findViewById(R.id.barSignUp);

        ArrayAdapter<String> states = new ArrayAdapter<String>(Register.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.states));
        states.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        address_sp.setAdapter(states);


        // to set changes to register
        bar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();

        //******************* so we initialize our variables to be ready**************************



        //to add person on data base after registering
        register = findViewById(R.id.register_btn);

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final String email = email_et.getText().toString().trim();
                final String pass = password_et.getText().toString().trim();
                final String name = name_et.getText().toString();
                final String city = address_sp.getSelectedItem().toString();
                final String phone = phone_et.getText().toString();

                //******************1- first thing is register ****************

                if (email.isEmpty()) {
                    email_et.setError("يجب ادخال الايميل");
                    email_et.requestFocus();
                    return;

                }


                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    email_et.setError("من فضلك قم بادخال ايميل صالح");
                    email_et.requestFocus();
                    return;

                }
                if (pass.isEmpty()) {
                    password_et.setError("يجب ادخال كلمة السر");
                    password_et.requestFocus();
                    return;

                }
                if(city.isEmpty()||phone.isEmpty()||name.isEmpty()){
                    Toast.makeText(Register.this,"يجب ملئ جميع البيانات ",Toast.LENGTH_LONG).show();
                    return ;
                }

                //********** show the progress bar *******************
                bar.setVisibility(View.VISIBLE);

                //********** go to register user ****************
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            bar.setVisibility(View.GONE);

                            //***********************So he register to Firebase and its time to add data :)************************

                            //********************************* 2- the start of adding to add user to data base************************************
                            ConnectionOpenHelper OpenHelper = new ConnectionOpenHelper(Register.this);

                            long id = OpenHelper.insertInRegister(email, pass, name, city, phone);

                            if (id != -1) {

                                Intent intent = new Intent(Register.this, HomeActivity.class);
                               ChooseActivity.email=email;
                                if (intent.resolveActivity(getPackageManager()) != null) {

                                    finish();
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    HomeActivity.isLogged=true;
                                    startActivity(intent);

                                    Toast.makeText(Register.this, "تم حفظ البيانات", Toast.LENGTH_LONG).show();
                                }


                            } else {

                                Toast.makeText(Register.this, "يوجد خطا ما حدث قم بالتسجيل مرة اخرى !", Toast.LENGTH_LONG).show();
                            }
                            //********************************* the end of adding to add user to data base *************************


                        } else {
                            bar.setVisibility(View.GONE);

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "هذا الايميل موجود من قبل :)", Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(getApplicationContext(), "error occurred: Message" + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }

                        }

                    }
                });


            }
        });


    }


}
