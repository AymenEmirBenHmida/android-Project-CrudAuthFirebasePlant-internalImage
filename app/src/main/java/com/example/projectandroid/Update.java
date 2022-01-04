package com.example.projectandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {
    EditText name, description;
    Button send;
    DAOplant dao;
    ImageView image;
    Uri imageUri;
    String key;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        i = getIntent();
        name = findViewById(R.id.editName);
        description = findViewById(R.id.editDescription);
        send = findViewById(R.id.buttonSend);
        dao = new DAOplant();
        image = findViewById(R.id.image);
        name.setText(i.getStringExtra("name"));
        key = i.getStringExtra("key");
        description.setText(i.getStringExtra("desc"));
        Glide.with(image.getContext()).load(i.getStringExtra("url")).into(image);
        if(image!= null){
            imageUri= Uri.parse(i.getStringExtra("url"));
        }

    }

    public void addPlant(View view) {
        //get the document name and description
        String plantName = name.getText().toString().trim();
        String plantDesc = description.getText().toString().trim();

        // upload image selected to firebase storage if the image variable is not null
        Map<String,Object> map = new HashMap<>();
        map.put("name",plantName);
        map.put("description",plantDesc);



        //create the object to be uploaded
        if (imageUri != null){
            if(imageUri.toString() != i.getStringExtra("url") )
            dao.update(imageUri,this,image,key,map,name,description);
            else
                dao.update1(key,map,this,name,description);
        }else{
            Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
        }

    }


    public void getImage(View view) {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent , 2);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            image.setImageURI(imageUri);

        }
    }
}