package com.sysirohub.academicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sysirohub.academicapp.CustomViews.CustomMainHeading;
import com.sysirohub.academicapp.CustomViews.CustomSubHeading;
import com.sysirohub.academicapp.Model.AllClass;
import com.sysirohub.academicapp.Model.Allassigned;
import com.sysirohub.academicapp.R;
import com.sysirohub.academicapp.RecyclerViewClickInterface;

import java.util.List;

public class AllClassTeacherAdapter extends RecyclerView.Adapter<AllClassTeacherAdapter.MyViewHolder> {

    Context context;
    List<Allassigned> classes;
    RecyclerViewClickInterface recyclerViewClickInterface;

    public AllClassTeacherAdapter(Context context, List<Allassigned> classes, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.classes = classes;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public AllClassTeacherAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.class_card,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllClassTeacherAdapter.MyViewHolder holder, int position) {

        Allassigned allClass = classes.get(position);

        holder.tvClassName.setText(allClass.getClassName());
        holder.tvDivName.setText(allClass.getClassId());

    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomMainHeading tvClassName;
        CustomSubHeading tvDivName;
        LinearLayout llClassCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvDivName = itemView.findViewById(R.id.tvDivName);
            llClassCard = itemView.findViewById(R.id.llClassCard);

            llClassCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    recyclerViewClickInterface.onItemClick(getAdapterPosition(),"GOTOSUB");

                }
            });

        }
    }
}
