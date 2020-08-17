package com.example.covid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PrecautionsAdapter extends RecyclerView.Adapter<PrecautionsAdapter.ViewHolder>
{

    private Precautions[] precautions;

    public PrecautionsAdapter(Precautions[] precautions) {
        this.precautions=precautions;
    }

    @NonNull
    @Override
    public PrecautionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_precautions, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PrecautionsAdapter.ViewHolder holder, int position) {
        final Precautions precautionsdata = precautions[position];
        holder.precautionsName.setText(precautions[position].getPrecautionsNames());
        holder.imageView.setImageResource(precautions[position].getPreimgId());
        holder.PrecationsDetails.setText(precautions[position].getPrecautionsDetails());

    }

    @Override
    public int getItemCount() {
        return precautions.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView precautionsName;
        TextView PrecationsDetails;
        int position;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.precations_image_views);
            precautionsName = itemView.findViewById(R.id.precations_text);
            PrecationsDetails = itemView.findViewById(R.id.textprecautionsdetails);

        }
    }


}
