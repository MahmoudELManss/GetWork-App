package com.example.android.test1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddActivity extends AppCompatActivity {

    private Spinner category, address;
    ImageView back;
    EditText name_et,phone_et,description_et;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        //********************************************************************************************************************
        category = findViewById(R.id.Acatg_sp);
        address = findViewById(R.id.Aaddress_sp);

        ArrayAdapter<String> cats = new ArrayAdapter<String>(AddActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.categories));
        cats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(cats);

        ArrayAdapter<String> states = new ArrayAdapter<String>(AddActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.states));
        states.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        address.setAdapter(states);

        //************************************************************************************************************************

        add=findViewById(R.id.addwork_btn);

        name_et=findViewById(R.id.Aname_et);
        phone_et=findViewById(R.id.Aphone_et);
        description_et=findViewById(R.id.Adescription_et);

        //add info of worker to show it in our socity

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String name=name_et.getText().toString();
                 String phone=phone_et.getText().toString();
                 String job=category.getSelectedItem().toString();
                 String city=address.getSelectedItem().toString();
                 String Description=description_et.getText().toString();

                 if(name.isEmpty()||phone.isEmpty()||Description.isEmpty()){
                     Toast.makeText(AddActivity.this,"يجب ملئ جميع البيانات ",Toast.LENGTH_LONG).show();
                     return ;

                 }

                 ConnectionOpenHelper helper=new ConnectionOpenHelper(AddActivity.this);
                 long id=helper.insertInAddWorker(name,phone,job,city,Description);
                 if(id!=-1){
                     Toast.makeText(AddActivity.this,"تم الاضافة بنجاح",Toast.LENGTH_SHORT).show();

                     name_et.setText("");
                     phone_et.setText("");
                     description_et.setText("");

                 }
                 else{

                     Toast.makeText(AddActivity.this,"هناك خطا ما يرجى المحاولة مرة اخرى",Toast.LENGTH_SHORT).show();
                 }


            }
        });

        //******************************************************************************************************************************



    }

}
