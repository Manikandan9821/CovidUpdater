package com.example.covid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SymptomsAdapter extends RecyclerView.Adapter<SymptomsAdapter.ViewHolder>
{

    private Symptoms[] symptoms;

    public SymptomsAdapter(Symptoms[] symptoms) {
        this.symptoms=symptoms;
    }

    @NonNull
    @Override
    public SymptomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_symptoms, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SymptomsAdapter.ViewHolder holder, int position) {
        final Symptoms symptomsdata = symptoms[position];
        holder.SyptomsName.setText(symptoms[position].getSymptoms_name());
        holder.SyptomsDetails.setText(symptoms[position].getSymptoms_details());
        holder.imageView.setImageResource(symptoms[position].getImgId());


    }

    @Override
    public int getItemCount() {
        return symptoms.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView SyptomsName,SyptomsDetails;
        int position;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.Syptoms_image);
            SyptomsName = itemView.findViewById(R.id.textSymptoms);
            SyptomsDetails =itemView.findViewById(R.id.textSymptomsdetails);

        }
    }


}
