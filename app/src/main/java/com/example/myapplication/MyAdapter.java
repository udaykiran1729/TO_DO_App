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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public Context context;
    private ArrayList<String> titles,date,day,month,desc,status,urg,cat;
    DBHelper db;



//    public MyAdapter(Context context, ArrayList<String> task, ArrayList<String> date) {
//        this.context = context;
//        this.task = task;
//        this.date = date;
//    }

    public MyAdapter(Context context, ArrayList<String> title, ArrayList<String> date, ArrayList<String> day, ArrayList<String> month, ArrayList<String> desc, ArrayList<String> status, ArrayList<String> urg,ArrayList<String> cat) {
        this.context = context;
        this.titles = title;
        this.date = date;
        this.day = day;
        this.month = month;
        this.desc = desc;
        this.status = status;
        this.urg = urg;
        this.cat=cat;
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
//        holder.time.setText(time.get(position));
        holder.month.setText(month.get(position));
        holder.urg.setText(urg.get(position));
        holder.status.setText(status.get(position));
        holder.cat.setText(cat.get(position));

        switch (urg.get(position)) {
            case "LOW":
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.lowPriorityColor));
                break;
            case "MEDIUM":
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.mediumPriorityColor));
                break;
            case "HIGH":
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.highPriorityColor));
                break;
            default:
                // Set default color or handle other cases
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.defaultColor));
                break;
        }

        switch (status.get(position)) {
            case "Not Completed":
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.statusNewColor));
                break;
            case "Partially Completed":
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.statusInProgressColor));
                break;
            case "Completed":
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.statusCompletedColor));
                break;
            default:
                // Set default color or handle other cases
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.defaultColor));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,desc,month,date,day,time,status,urg,cat;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            desc=itemView.findViewById(R.id.description);
            month=itemView.findViewById(R.id.month);
            date=itemView.findViewById(R.id.date);
            day=itemView.findViewById(R.id.day);
//            time=itemView.findViewById(R.id.time);
            status=itemView.findViewById(R.id.status);
            urg=itemView.findViewById(R.id.urgency);
            cat=itemView.findViewById(R.id.category);
            itemView.findViewById(R.id.delete).setOnClickListener(view -> {
                db=new DBHelper(context);
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Are You sure?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (dialog, which)->{
                    int ind=getAdapterPosition();
                    Boolean m=db.deleteuserdata(titles.get(ind));
                    if (m){
                        titles.remove(ind);MyAdapter.this.date.remove(ind);MyAdapter.this.day.remove(ind);MyAdapter.this.month.remove(ind);
                        MyAdapter.this.desc.remove(ind);MyAdapter.this.status.remove(ind);MyAdapter.this.urg.remove(ind);MyAdapter.this.cat.remove(ind);
                        Toast.makeText(context, "Items Removed", Toast.LENGTH_SHORT).show();

                        notifyItemRemoved(ind);
                        notifyItemRangeChanged(ind, titles.size());
                    }
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            });

            itemView.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db=new DBHelper(context);
                    int ind=getAdapterPosition();

                    Intent intent = new Intent(context,Update.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("title",titles.get(ind));
                    context.startActivity(intent);
                    notifyItemChanged(ind);
                    notifyItemRangeChanged(ind,titles.size());
                }
            });
        }


    }
}