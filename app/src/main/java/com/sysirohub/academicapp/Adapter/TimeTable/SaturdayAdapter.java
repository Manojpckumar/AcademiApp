package com.sysirohub.academicapp.Adapter.TimeTable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sysirohub.academicapp.Adapter.AllClassAdapter;
import com.sysirohub.academicapp.Model.Monday;
import com.sysirohub.academicapp.Model.Saturday;
import com.sysirohub.academicapp.R;
import com.sysirohub.academicapp.RecyclerViewClickInterface;

import java.util.List;

public class SaturdayAdapter extends RecyclerView.Adapter<SaturdayAdapter.MyViewHolder> {

    Context context;
    List<Saturday> mondayList;
    RecyclerViewClickInterface recyclerViewClickInterface;

    public SaturdayAdapter(Context context, List<Saturday> mondayList,RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.mondayList = mondayList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.timetable_card,parent,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Saturday monday = mondayList.get(position);

        holder.tv1.setText(monday.getSubjectName());

    }


    @Override
    public int getItemCount() {
        return mondayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.tv1);

            tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    recyclerViewClickInterface.onItemClick(getAdapterPosition(),"SATURDAY");

                }
            });
        }
    }
}
