package com.sysirohub.academicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sysirohub.academicapp.CustomViews.CustomMainHeading;
import com.sysirohub.academicapp.CustomViews.CustomSubHeading;
import com.sysirohub.academicapp.Model.AllSubject;
import com.sysirohub.academicapp.Model.AllSubyclas;
import com.sysirohub.academicapp.R;
import com.sysirohub.academicapp.RecyclerViewClickInterface;

import java.util.List;

public class AllSubjectTeacherAdapter extends RecyclerView.Adapter<AllSubjectTeacherAdapter.MyViewHolder> {

    Context context;
    List<AllSubyclas> subjects;
    RecyclerViewClickInterface recyclerViewClickInterface;

    public AllSubjectTeacherAdapter(Context context, List<AllSubyclas> subjects, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.subjects = subjects;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public AllSubjectTeacherAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.class_card,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AllSubjectTeacherAdapter.MyViewHolder holder, int position) {

        AllSubyclas sub = subjects.get(position);

        holder.tvClassName.setText(sub.getSubjectName());
        holder.tvDivName.setVisibility(View.GONE);
        holder.tvDivName.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_menu_book_24));


    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomMainHeading tvClassName;
        CustomSubHeading tvDivName;
        ImageView ivIcon;
        LinearLayout llClassCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvDivName = itemView.findViewById(R.id.tvDivName);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            llClassCard = itemView.findViewById(R.id.llClassCard);

            llClassCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    recyclerViewClickInterface.onItemClick(getAdapterPosition(),"GOTOATT");


                }
            });


        }
    }
}
