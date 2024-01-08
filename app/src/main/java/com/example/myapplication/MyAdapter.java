package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public Context context;
    private ArrayList<String> titles,date,day,time,desc,status,urg;
    DBHelper db;



//    public MyAdapter(Context context, ArrayList<String> task, ArrayList<String> date) {
//        this.context = context;
//        this.task = task;
//        this.date = date;
//    }

    public MyAdapter(Context context, ArrayList<String> title, ArrayList<String> date, ArrayList<String> day, ArrayList<String> time, ArrayList<String> desc, ArrayList<String> status, ArrayList<String> urg) {
        this.context = context;
        this.titles = title;
        this.date = date;
        this.day = day;
        this.time = time;
        this.desc = desc;
        this.status = status;
        this.urg = urg;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.card,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        holder.desc.setText(desc.get(position));
        holder.date.setText(date.get(position));
        holder.day.setText(day.get(position));
        holder.time.setText(time.get(position));
        holder.urg.setText(urg.get(position));
        holder.status.setText(status.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,desc,month,date,day,time,status,urg;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            desc=itemView.findViewById(R.id.description);
            month=itemView.findViewById(R.id.month);
            date=itemView.findViewById(R.id.date);
            day=itemView.findViewById(R.id.day);
            time=itemView.findViewById(R.id.time);
            status=itemView.findViewById(R.id.status);
            urg=itemView.findViewById(R.id.urgency);
            itemView.findViewById(R.id.delete).setOnClickListener(view -> {
                db=new DBHelper(context);
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Are You sure?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener)(dialog,which)->{
                    Boolean m=db.deleteuserdata(titles.get(getAdapterPosition()));
                    if (m){
                        Toast.makeText(context, "Items Removed", Toast.LENGTH_SHORT).show();
//                        main_page rel= new main_page();
//                        rel.reload();
                    }
                });
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            });
        }


    }
}