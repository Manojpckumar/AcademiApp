package com.sysirohub.academicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sysirohub.academicapp.AllStudents;
import com.sysirohub.academicapp.AttendanceView;
import com.sysirohub.academicapp.CustomViews.CustomMainHeading;
import com.sysirohub.academicapp.CustomViews.CustomSubHeading;
import com.sysirohub.academicapp.Model.Student;
import com.sysirohub.academicapp.R;

import java.util.List;

public class AllStudentsAdapter extends RecyclerView.Adapter<AllStudentsAdapter.MyViewHolder> {

    Context context;
    List<Student> studentsList;
    String viewTypess;
    View view;

    public AllStudentsAdapter(Context context, List<Student> studentsList,String now) {
        this.context = context;
        this.studentsList = studentsList;
        this.viewTypess = now;
    }

//    public AllStudentsAdapter(Context context, List<Student> studentsList, String viewType) {
//        this.context = context;
//        this.studentsList = studentsList;
//        this.viewType = viewType;
//    }

    @NonNull
    @Override
    public AllStudentsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


          View view = LayoutInflater.from(context).inflate(R.layout.attendance_card,parent,false);
            return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AllStudentsAdapter.MyViewHolder holder, int position) {


            if (viewTypess.equalsIgnoreCase("Students"))
            {
                Student students = studentsList.get(position);

                holder.tvName.setText(students.getName());
                holder.tvStudDes.setVisibility(View.GONE);
                holder.tvAttendance.setVisibility(View.GONE);
//                holder.tvDivName.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_supervised_user_circle_24));
            }
            else if(viewTypess.equalsIgnoreCase("Attendance"))
            {
                Student students = studentsList.get(position);

                holder.tvName.setText(students.getName());
                holder.tvStudDes.setVisibility(View.GONE);

                holder.tvAttendance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if(isChecked)
                        {
                            holder.tvAttendance.setText("Present");
                        }
                        else
                        {
                            holder.tvAttendance.setText("Absent");
                        }

                    }
                });
            }

    }


    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

//        CustomMainHeading tvClassName;
//        CustomSubHeading tvDivName;
//        ImageView ivIcon;

        CustomMainHeading tvName;
        CustomSubHeading tvStudDes;
        Switch tvAttendance;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

//            tvClassName = itemView.findViewById(R.id.tvClassName);
//            tvDivName = itemView.findViewById(R.id.tvDivName);
//            ivIcon = itemView.findViewById(R.id.ivIcon);

            tvName = itemView.findViewById(R.id.tvStudName);
            tvAttendance = itemView.findViewById(R.id.tvAttendance);
            tvStudDes = itemView.findViewById(R.id.tvStudDes);
        }
    }
}
