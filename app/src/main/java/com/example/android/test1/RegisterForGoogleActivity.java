package com.example.android.test1;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterForGoogleActivity extends AppCompatActivity {


    private Spinner address_sp;
    private EditText name_et, email_et, password_et, phone_et;
    private Button register;

    private FirebaseUser mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_for_google);

        address_sp = findViewById(R.id.Raddress_sp);
        name_et = findViewById(R.id.Rname_et);
        phone_et = findViewById(R.id.Rphone_et);

        ArrayAdapter<String> states = new ArrayAdapter<String>(RegisterForGoogleActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.states));
        states.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        address_sp.setAdapter(states);



        //********************************** we initialize our  variables *************

        //to add person on data base after registering
        register = findViewById(R.id.register_btn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email= getIntent().getExtras().getString("email");
                final String name = name_et.getText().toString();
                final String city = address_sp.getSelectedItem().toString();
                final String phone = phone_et.getText().toString();

                if(city.isEmpty()||phone.isEmpty()||name.isEmpty()){
                    Toast.makeText(RegisterForGoogleActivity.this,"يجب ملئ جميع البيانات ",Toast.LENGTH_LONG).show();
                    return ;
                }

                //************** so add its data ****************************
                ConnectionOpenHelper OpenHelper = new ConnectionOpenHelper(RegisterForGoogleActivity.this);
                long id = OpenHelper.insertInRegister(email, "123", name, city, phone);

                if (id != -1) {
                    Intent intent = new Intent(RegisterForGoogleActivity.this, HomeActivity.class);
                    if (intent.resolveActivity(getPackageManager()) != null) {

                        finish();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("email",email);
                        startActivity(intent);

                        Toast.makeText(RegisterForGoogleActivity.this, "تم حفظ البيانات", Toast.LENGTH_LONG).show();
                    }


                } else {

                    Toast.makeText(RegisterForGoogleActivity.this, "خطا ما حدث قم بالتسجيل مرة اخرى !", Toast.LENGTH_LONG).show();
                }


            }
        });



    }

    @Override
    protected void onStart() {



        ConnectionOpenHelper helper=new ConnectionOpenHelper(RegisterForGoogleActivity.this);
       Cursor c= helper.selectRowForGoogle();
        String getemail= getIntent().getExtras().getString("email");
       while(c.moveToNext()){

           String email=c.getString(c.getColumnIndex(Helper.DbUSEREMAIL));
           if(email.equals(getemail)){
               finish();
               Intent i=new Intent(RegisterForGoogleActivity.this, HomeActivity.class);
               HomeActivity.isLogged=true;
               startActivity(i);

           }

       }

        super.onStart();
    }
}
