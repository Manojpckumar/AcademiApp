package com.sysirohub.academicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sysirohub.academicapp.CustomViews.CustomMainHeading;
import com.sysirohub.academicapp.CustomViews.CustomSubHeading;
import com.sysirohub.academicapp.Model.AllSubject;
import com.sysirohub.academicapp.Model.Student;
import com.sysirohub.academicapp.R;

import java.util.List;

public class AllSubjectsAdapter extends RecyclerView.Adapter<AllSubjectsAdapter.MyViewHolder> {

    Context context;
    List<AllSubject> subjects;

    public AllSubjectsAdapter(Context context, List<AllSubject> subjects) {
        this.context = context;
        this.subjects = subjects;
    }

    @NonNull
    @Override
    public AllSubjectsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.class_card,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AllSubjectsAdapter.MyViewHolder holder, int position) {

        AllSubject sub = subjects.get(position);

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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvDivName = itemView.findViewById(R.id.tvDivName);
            ivIcon = itemView.findViewById(R.id.ivIcon);
        }
    }
}
