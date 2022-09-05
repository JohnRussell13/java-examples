package com.app.example.sqlite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList actor_id, actor_name, actor_bd;

    CustomAdapter(Activity activity, Context context, ArrayList actor_id, ArrayList actor_name, ArrayList actor_bd){
        this.activity = activity;
        this.context = context;
        this.actor_id = actor_id;
        this.actor_name = actor_name;
        this.actor_bd = actor_bd;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.actor_id_txt.setText(String.valueOf(actor_id.get(position)));
        holder.actor_name_txt.setText(String.valueOf(actor_name.get(position)));
        holder.actor_bd_txt.setText(String.valueOf(actor_bd.get(position)));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(actor_id.get(position)));
                intent.putExtra("title", String.valueOf(actor_name.get(position)));
                intent.putExtra("author", String.valueOf(actor_bd.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return actor_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView actor_id_txt, actor_name_txt, actor_bd_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            actor_id_txt = itemView.findViewById(R.id.actor_id_txt);
            actor_name_txt = itemView.findViewById(R.id.actor_name_txt);
            actor_bd_txt = itemView.findViewById(R.id.actor_bd_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }

}
