package com.example.projectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.content.SharedPreferences;

public class signup extends AppCompatActivity {
    TextInputLayout t1,t2;
    ProgressBar bar;
    private FirebaseAuth mAuth;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        SharedPreferences sharedPref = getSharedPreferences(
                MyPREFERENCES, Context.MODE_PRIVATE);
        int state = sharedPref.getInt("user", 0);
        if(state == 1 ){
            Intent i = new Intent(getApplicationContext(),List_plants_recycle.class);
            startActivity(i);
        }

        editor = sharedPref.edit();


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        t1=(TextInputLayout)findViewById(R.id.email);
        t2=(TextInputLayout)findViewById(R.id.pwd);
        bar=(ProgressBar)findViewById(R.id.progressBar);
    }

    public void signuphere(View view)
    {
        bar.setVisibility(View.VISIBLE);
        String email=t1.getEditText().getText().toString();
        String password=t2.getEditText().getText().toString();

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            bar.setVisibility(View.INVISIBLE);
                            t1.getEditText().setText("");
                            t2.getEditText().setText("");
                            Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_LONG).show();
                            editor.putInt("user", 1);
                            editor.apply();
                            Intent i = new Intent(getApplicationContext(),List_plants_recycle.class);
                            startActivity(i);

                        }
                        else
                        {
                            bar.setVisibility(View.INVISIBLE);
                            //t1.getEditText().setText("");
                            //t2.getEditText().setText("");
                           // Toast.makeText(getApplicationContext(),"Process Error",Toast.LENGTH_LONG).show();
                        }
                        task.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Error= "+e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });


    }



    public void gotosignin(View view)
    {
        startActivity(new Intent(this, login.class));
    }

}