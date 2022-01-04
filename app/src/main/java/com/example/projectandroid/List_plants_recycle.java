package com.example.projectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class List_plants_recycle extends AppCompatActivity {

    RecyclerView plant;
    myadapter adapter;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_plants_recycle);
        SharedPreferences sharedPref = getSharedPreferences(
                MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        plant = findViewById(R.id.recycle);
        plant.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Plant> options = new FirebaseRecyclerOptions.Builder<Plant>().setQuery(
                FirebaseDatabase.getInstance("https://androidcrud-ce3e1-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Plant"), Plant.class
        ).build();
        adapter = new myadapter(options);
        plant.setAdapter(adapter);
    }



    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void goAddPage(View view) {
        Intent i = new Intent(this, Crud.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    private void processSearch(String s){
        FirebaseRecyclerOptions<Plant> options = new FirebaseRecyclerOptions.Builder<Plant>().setQuery(
                FirebaseDatabase.getInstance("https://androidcrud-ce3e1-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Plant").orderByChild("name").startAt(s).endAt(s+"\uf8ff"), Plant.class
        ).build();
        adapter = new myadapter(options);
        adapter.startListening();
        plant.setAdapter(adapter);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:{
                startActivity(new Intent(this, signup.class));
                editor.putInt("user", 0);
                editor.apply();}
                return true;


            case R.id.Map:
                startActivity(new Intent(this, MainActivity.class));
            return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


}