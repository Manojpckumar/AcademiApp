package com.sysirohub.academicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sysirohub.academicapp.Adapter.TimeTable.FridayAdapter;
import com.sysirohub.academicapp.CustomViews.CustomMainHeading;
import com.sysirohub.academicapp.HomeActivity;
import com.sysirohub.academicapp.MainActivity;
import com.sysirohub.academicapp.Model.AttendanceView;
import com.sysirohub.academicapp.Model.Common;
import com.sysirohub.academicapp.R;
import com.sysirohub.academicapp.RecyclerViewClickInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AttendanceViewAdapter extends RecyclerView.Adapter<AttendanceViewAdapter.MyViewHolder> {

    Context context;
    List<AttendanceView> attendanceViews;
    RecyclerViewClickInterface recyclerViewClickInterface;

    public AttendanceViewAdapter(Context context, List<AttendanceView> attendanceViews, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.attendanceViews = attendanceViews;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public AttendanceViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.today_attendance_task_card,parent,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewAdapter.MyViewHolder holder, int position) {

        AttendanceView model = attendanceViews.get(position);

        holder.tvSubject.setText(model.getSubjectName());
        holder.tvClass.setText(model.getClassName());
        holder.tvTime.setText(model.getStart()+ " - " +model.getEnd());


        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");



        try {
            Date start = parser.parse(model.getStart());
            Date end = parser.parse(model.getEnd());

            Date userDate = Common.getSystem24Time();
            if (userDate.after(start) && userDate.before(end))
            {
                holder.ll_mainCard.setClickable(true);
//                Toast.makeText(context, "condition is true", Toast.LENGTH_LONG).show();

                holder.ll_mainCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, com.sysirohub.academicapp.AttendanceView.class);
                        intent.putExtra("classId",model.getClassId());
                        context.startActivity(intent);
                    }
                });
            }
            else
            {
                holder.ll_mainCard.setClickable(false);
                holder.ll_mainCard.setBackgroundColor(context.getResources().getColor(R.color.ashlite));

            }
        } catch (ParseException e) {
            // Invalid date was entered
        }




    }

    @Override
    public int getItemCount() {
        return attendanceViews.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomMainHeading tvSubject,tvClass,tvTime;
        LinearLayout ll_mainCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvClass = itemView.findViewById(R.id.tvClass);
            tvTime = itemView.findViewById(R.id.tvTime);

            ll_mainCard = itemView.findViewById(R.id.ll_mainCard);
        }
    }
}
