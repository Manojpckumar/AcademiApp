package com.sysirohub.academicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.sysirohub.academicapp.CustomViews.CustomMainHeading;
import com.sysirohub.academicapp.CustomViews.CustomSubHeading;
import com.sysirohub.academicapp.CustomViews.CustomTextView;
import com.sysirohub.academicapp.Model.AllSubject;
import com.sysirohub.academicapp.Model.Teacher;
import com.sysirohub.academicapp.R;
import com.sysirohub.academicapp.RecyclerViewClickInterface;

import java.util.List;

public class AllTeachersAdapter extends RecyclerView.Adapter<AllTeachersAdapter.MyViewHolder> {

    List<Teacher> teacherList;
    Context context;
    RecyclerViewClickInterface recyclerViewClickInterface;

    public AllTeachersAdapter(List<Teacher> teacherList, Context context,RecyclerViewClickInterface recyclerViewClickInterface) {

        this.teacherList = teacherList;
        this.context = context;
        this.recyclerViewClickInterface = recyclerViewClickInterface;

    }

    @NonNull
    @Override
    public AllTeachersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.class_card,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AllTeachersAdapter.MyViewHolder holder, int position) {

        Teacher teacher = teacherList.get(position);

        holder.tvClassName.setText(teacher.getName());
        holder.tvDivName.setText(teacher.getMobile());

        if(teacher.getStatus().equalsIgnoreCase("0"))
        {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.btnAssign.setVisibility(View.GONE);
        }
        else if(teacher.getStatus().equalsIgnoreCase("1"))
        {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText("block");
            holder.btnAssign.setVisibility(View.VISIBLE);


            holder.tvStatus.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.absent_round_shape));
        }

        //holder.tvDivName.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_person_24));


    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomMainHeading tvClassName;
        CustomSubHeading tvDivName;
        ImageView ivIcon;
        AppCompatButton tvStatus;
        AppCompatButton btnAssign;
        LinearLayout llButtons;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvDivName = itemView.findViewById(R.id.tvDivName);
            ivIcon = itemView.findViewById(R.id.ivIcon);

            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnAssign = itemView.findViewById(R.id.btnAssign);

            llButtons = itemView.findViewById(R.id.llButtons);

            tvStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String text = tvStatus.getText().toString();

                    if (text.equalsIgnoreCase("approve"))
                    {
                        recyclerViewClickInterface.onItemClick(getAdapterPosition(),"APPROVE");
                    }
                    else if(text.equalsIgnoreCase("block"))
                    {
                        recyclerViewClickInterface.onItemClick(getAdapterPosition(),"BLOCK");
                    }
                    else
                    {

                    }



                }
            });

            btnAssign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    recyclerViewClickInterface.onItemClick(getAdapterPosition(),"ASSIGN");

                }
            });
        }
    }
}
