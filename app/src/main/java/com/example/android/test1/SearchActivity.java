package com.example.android.test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    private Spinner category, address;
    ImageView back;
    String city = "", job = "";

    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });


        //********************  that's for spinner to get item in it*************************************************
        //********  for category and address  *******************
        category = findViewById(R.id.Scatg_sp);
        address = findViewById(R.id.Saddress_sp);
        final ArrayAdapter<String> cats = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categories));
        //**************** why ? *****************
        cats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(cats);

        //**********************************************************************************************************************************************

        ArrayAdapter<String> states = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.states));
        states.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        address.setAdapter(states);


        //****************************************************************************************************************************************

        search = findViewById(R.id.search_btn);

        //*************** so we want to get selected items from user***************


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                city = address.getSelectedItem().toString();
                job = category.getSelectedItem().toString();


                Intent intent = new Intent(SearchActivity.this, SearchReasultActivity.class);
                // to take what the user choose
                intent.putExtra("city", city);
                intent.putExtra("job", job);

                //to clear the stack
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }


            }
        });
    }
}
