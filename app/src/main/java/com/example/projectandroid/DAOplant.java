package com.example.projectandroid;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Map;

public class DAOplant {
    private DatabaseReference database;
    private StorageReference reference;

    DAOplant(){
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://androidcrud-ce3e1-default-rtdb.europe-west1.firebasedatabase.app/");
        database = db.getReference(Plant.class.getSimpleName());
        reference = FirebaseStorage.getInstance().getReference();
    }

    public Task<Void> add(Plant p){


        return database.push().setValue(p);
    }

    public void uploadToFirebase(Uri uri, Context applicationContext, Plant p, ImageView imageView) {
        if(p == null){
            System.out.println(" method add the object passed is null");
        }
        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri,applicationContext));
         fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                 fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                     @Override
                     public void onSuccess(Uri uri) {

                         Plant plant = new Plant(p.getName(),p.getDescription(),uri.toString());
                         String modelId = database.push().getKey();
                         database.child(modelId).setValue(plant).addOnSuccessListener(v->
                         {
                             Toast.makeText(applicationContext,"Plant inserted",Toast.LENGTH_LONG).show();
                         }).addOnFailureListener(v->{
                             Toast.makeText(applicationContext,""+v.getMessage(),Toast.LENGTH_LONG).show();

                         });;


                         imageView.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
                     }
                 });
             }
         });
    }



    public void update(Uri uri, Context applicationContext, ImageView imageView, String key, Map<String, Object> map, EditText name, EditText description) {

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri,applicationContext));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        map.put("url",uri.toString());

                         database.child(key).updateChildren(map).addOnSuccessListener(v->
                         {
                             Toast.makeText(applicationContext,"Plant updated",Toast.LENGTH_LONG).show();
                             name.setText("");
                             description.setText("");
                             imageView.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
                             Intent i = new Intent(imageView.getContext(),List_plants_recycle.class);
                             imageView.getContext().startActivity(i);

                         }).addOnFailureListener(v->{
                             Toast.makeText(applicationContext,""+v.getMessage(),Toast.LENGTH_LONG).show();

                         });;
                    }
                });
            }
        });
    }



    public Task<Void> remove(String key){


        return database.child(key).removeValue();
    }

    private String getFileExtension(Uri uri, Context con) {
        ContentResolver cr = con.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    public void update1(String key, Map<String, Object> map, Context applicationContext, EditText name, EditText description) {
        database.child(key).updateChildren(map).addOnSuccessListener(v->
        {
            Toast.makeText(applicationContext,"Plant updated",Toast.LENGTH_LONG).show();
            name.setText("");
            description.setText("");
            Intent i = new Intent(applicationContext,List_plants_recycle.class);
            applicationContext.startActivity(i);

        }).addOnFailureListener(v->{
            Toast.makeText(applicationContext,""+v.getMessage(),Toast.LENGTH_LONG).show();

        });;;
    }
}
