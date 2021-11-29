package com.sysirohub.academicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sysirohub.academicapp.AllStudents;
import com.sysirohub.academicapp.CustomViews.CustomMainHeading;
import com.sysirohub.academicapp.CustomViews.CustomSubHeading;
import com.sysirohub.academicapp.Model.Student;
import com.sysirohub.academicapp.R;

import java.util.List;

public class AllStudentsAdapter extends RecyclerView.Adapter<AllStudentsAdapter.MyViewHolder> {

    Context context;
    List<Student> studentsList;

    public AllStudentsAdapter(Context context, List<Student> studentsList) {
        this.context = context;
        this.studentsList = studentsList;
    }

    @NonNull
    @Override
    public AllStudentsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.class_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllStudentsAdapter.MyViewHolder holder, int position) {

        Student students = studentsList.get(position);

        holder.tvClassName.setText(students.getName());
        holder.tvDivName.setVisibility(View.GONE);
        holder.tvDivName.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_supervised_user_circle_24));

    }


    @Override
    public int getItemCount() {
        return studentsList.size();
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
