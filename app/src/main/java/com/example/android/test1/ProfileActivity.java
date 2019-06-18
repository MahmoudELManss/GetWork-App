package com.example.android.test1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    private Spinner category, address;
    EditText name_et,phone_et,email_et;
    Button update;
    Spinner address_sp;
    ImageView back;
    String getEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        //************** for Spinners *************************************
        address = findViewById(R.id.address);

        //**********************************************************************************************************************************************

        ArrayAdapter<String> states = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.states));
        states.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        address.setAdapter(states);


        //************************************ to show the data of user using its email ******************
        name_et=findViewById(R.id.name);
        phone_et=findViewById(R.id.phone);
        email_et=findViewById(R.id.email);
        address_sp=findViewById(R.id.address);


      getEmail=ChooseActivity.email;


        ConnectionOpenHelper helper=new ConnectionOpenHelper(this);
        Cursor c=helper.selectUser(getEmail);

        if( c.isAfterLast()==false) {

            c.moveToNext();

            email_et.setText(getEmail);
            phone_et.setText(c.getString(c.getColumnIndex(Helper.DbUSERPHONENUMBER)));
            name_et.setText(c.getString(c.getColumnIndex(Helper.DbUSERNAME)));
            String city=c.getString(c.getColumnIndex(Helper.DbUSERCITY));
            if(city!=null){

                int spinnerPosition = states.getPosition(city);
                address_sp.setSelection(spinnerPosition);
            }

        }else{
            Toast.makeText(ProfileActivity.this,"يوجد خطا ما حدث فى عرض البيانات الخاصة بك ! ",Toast.LENGTH_LONG).show();
        }
        //******************************************************************************************************************************************

        update=findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionOpenHelper helper=new ConnectionOpenHelper(ProfileActivity.this);
               int count= helper.updateProfile(name_et.getText().toString(),phone_et.getText().toString(),address_sp.getSelectedItem().toString(),getEmail);

                    Toast.makeText(ProfileActivity.this,count+"تم تحديث البيانات ",Toast.LENGTH_LONG).show();


            }
        });




    }
}
