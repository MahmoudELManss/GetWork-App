package com.example.android.test1;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchReasultActivity extends AppCompatActivity {

    ImageView back;

    private RecyclerView recyclerView;
    private MyRecyclerAdapter myAdapter;


    //*************************************************************************************
    private List<RecyclerItem> itemList;

    //*************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_reasult);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchReasultActivity.this, SearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        recyclerView = findViewById(R.id.recycler);

        //**************************************************************************
        recyclerView.setHasFixedSize(true);
        //**************************************************************************************

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //*********************************************************** starting from here *********************************


        String city =getIntent().getExtras().getString("city");
        String job=getIntent().getExtras().getString("job");

        ConnectionOpenHelper helper=new ConnectionOpenHelper(this);
        Cursor c=helper.search(city);


        itemList = new ArrayList<>();
        while(c.moveToNext()){
            itemList.add(new RecyclerItem(c.getString(c.getColumnIndex(Helper.DbWORKERNAME)) , c.getString(c.getColumnIndex(Helper.DbWORKERJOB)), c.getString(c.getColumnIndex(Helper.DbWORKERCITY)) ,c.getString(c.getColumnIndex(Helper.DbWORKERDESCRIPTION)) , R.drawable.user));
        }

        //******************************************************************************************************************************
        myAdapter = new MyRecyclerAdapter(itemList, this);
        recyclerView.setAdapter(myAdapter);
    }
}
