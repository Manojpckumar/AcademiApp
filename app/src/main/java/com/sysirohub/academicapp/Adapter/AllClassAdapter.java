package com.sysirohub.academicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sysirohub.academicapp.CustomViews.CustomMainHeading;
import com.sysirohub.academicapp.CustomViews.CustomSubHeading;
import com.sysirohub.academicapp.Model.AllClass;
import com.sysirohub.academicapp.R;
import com.sysirohub.academicapp.databinding.ClassCardBinding;
import com.sysirohub.academicapp.databinding.FragmentAdminBinding;

import java.util.List;

public class AllClassAdapter extends RecyclerView.Adapter<AllClassAdapter.MyViewHolder> {

    Context context;
    List<AllClass> list;
    ClassCardBinding binding;

    public AllClassAdapter(Context context, List<AllClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AllClassAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.class_card,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllClassAdapter.MyViewHolder holder, int position) {

        AllClass allClass = list.get(position);

        holder.tvClassName.setText(allClass.getClassName());
        holder.tvDivName.setText(allClass.getDivision());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomMainHeading tvClassName;
        CustomSubHeading tvDivName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvDivName = itemView.findViewById(R.id.tvDivName);
        }
    }
}
