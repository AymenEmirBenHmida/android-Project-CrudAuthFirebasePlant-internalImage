package com.example.projectandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends FirebaseRecyclerAdapter<Plant, myadapter.myviewholder> {
    public myadapter(@NonNull FirebaseRecyclerOptions<Plant> options){super(options);}

    @Override
    protected void onBindViewHolder(@NonNull myviewholder myviewholder, int i, @NonNull Plant plant) {
        myviewholder.name.setText("Name: "+plant.getName());
        myviewholder.desc.setText("Description: "+plant.getDescription());
        System.out.println(plant.name);
        if(plant.getUrl() != null)
        Glide.with(myviewholder.image.getContext()).load(plant.getUrl()).into(myviewholder.image);
        myviewholder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(myviewholder.image.getContext(),Update.class);
                i.putExtra("name",plant.getName());
                i.putExtra("desc",plant.getDescription());
                i.putExtra("url",plant.getUrl());
                i.putExtra("key",getRef(myviewholder.getAdapterPosition()).getKey());
                myviewholder.image.getContext().startActivity(i);

            }
        });
        myviewholder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(myviewholder.image.getContext());
                builder.setTitle("Cheking in");
                builder.setMessage("Delete...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       DAOplant dao = new DAOplant();
                       dao.remove(getRef(myviewholder.getAdapterPosition()).getKey());
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
      /*  myviewholder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(myviewholder.image.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog)).setExpanded(true,1400)
                        .create();
                View myview = dialogPlus.getHolderView();
                EditText name, description;
                Uri imageUri;
                Button send;
                name = myview.findViewById(R.id.editName);
                description = myview.findViewById(R.id.editDescription);
                send = myview.findViewById(R.id.buttonSend);



                name.setText(plant.getName());

                description.setText(plant.getDescription());
                dialogPlus.show();

            }
        });*/

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {
        CircleImageView image;
        ImageButton edit, delete;
        TextView name,desc;
        public myviewholder(@NonNull View itemView){
            super(itemView);
            image = (CircleImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
            desc = (TextView) itemView.findViewById(R.id.desc);
            edit = (ImageButton) itemView.findViewById(R.id.edit);
            delete = (ImageButton) itemView.findViewById(R.id.delete);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
