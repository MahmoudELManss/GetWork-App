package com.example.android.test1;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    public static boolean isLogged = false;

    private ImageView add, search;
    private Spinner address;

    private RecyclerView recyclerView;
    private MyRecyclerAdapter myAdapter;
    private List<RecyclerItem> itemList;

    //for nav bar
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mtoolbar;

    ConnectionOpenHelper helper = new ConnectionOpenHelper(this);


    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView = findViewById(R.id.mainNavigation);
        navigationView.setNavigationItemSelectedListener(this);


        // for nav bar and its buttons
        add = findViewById(R.id.add_btn);
        search = findViewById(R.id.search_iv);
        mDrawer = (DrawerLayout) findViewById(R.id.mainDrawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open, R.string.close);
        mtoolbar = (Toolbar) findViewById(R.id.nav_bar);
        setSupportActionBar(mtoolbar);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // end of it


        address = findViewById(R.id.Saddress_sp);
        ArrayAdapter<String> states = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.states));
        states.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        address.setAdapter(states);




        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();


        address.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                Cursor c = helper.homeSearch(address.getSelectedItem().toString());

                while (c.moveToNext()) {
                    itemList.add(new RecyclerItem(c.getString(c.getColumnIndex(Helper.DbWORKERNAME)), c.getString(c.getColumnIndex(Helper.DbWORKERJOB)), c.getString(c.getColumnIndex(Helper.DbWORKERCITY)), c.getString(c.getColumnIndex(Helper.DbWORKERDESCRIPTION)), R.drawable.user));

                }
                myAdapter = new MyRecyclerAdapter(itemList, HomeActivity.this);
                recyclerView.setAdapter(myAdapter);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //***************************** to set action of  menu *********************************


        //****************************************************************************************

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddActivity.class);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });


    }


    //nav bar too
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.profile) {

            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        }
        if (id == R.id.exit) {
            FirebaseAuth.getInstance().signOut();
            finish();
            isLogged = false;
            startActivity(new Intent(HomeActivity.this, ChooseActivity.class));
        }

        return false;
    }
}
