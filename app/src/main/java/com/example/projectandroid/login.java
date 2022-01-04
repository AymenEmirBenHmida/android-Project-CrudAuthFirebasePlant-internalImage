package com.example.projectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class login extends AppCompatActivity {

    TextInputLayout t1,t2;
    ProgressBar bar;
    FirebaseAuth mAuth;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences sharedPref = getSharedPreferences(
                MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        t1=(TextInputLayout)findViewById(R.id.email_login);
        t2=(TextInputLayout)findViewById(R.id.pwd_login);
        bar=(ProgressBar)findViewById(R.id.progressBar3_login);
        mAuth = FirebaseAuth.getInstance();
    }

    public void signinhere(View view)
    {
        bar.setVisibility(View.VISIBLE);
        String email=t1.getEditText().getText().toString();
        String password=t2.getEditText().getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            bar.setVisibility(View.INVISIBLE);
                            Intent intent=new Intent(login.this,List_plants_recycle.class);
                            editor.putInt("user", 1);
                            editor.apply();
                            Toast.makeText(getApplicationContext(),"Logged in Beautifully",Toast.LENGTH_LONG).show();
                            intent.putExtra("email",mAuth.getCurrentUser().getEmail());
                            intent.putExtra("uid",mAuth.getCurrentUser().getUid());
                            startActivity(intent);
                        } else
                        {
                            bar.setVisibility(View.INVISIBLE);

                        }
                        task.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Error= "+e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });


                        // ...
                    }
                });



    }
}
