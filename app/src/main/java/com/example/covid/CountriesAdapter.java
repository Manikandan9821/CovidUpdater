package com.example.covid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> implements Filterable
{
    private LayoutInflater inflater;
    private ArrayList<CountriesModelRecycler> dataModelArrayList;
    private List<CountriesModelRecycler> movieListFiltered;

    CountriesActivity countriesActivity;
    Context context;

    public CountriesAdapter(Context context, ArrayList<CountriesModelRecycler> dataModelArrayList) {
        this.context=context;
        this.dataModelArrayList = dataModelArrayList;
        movieListFiltered = new ArrayList<>(dataModelArrayList);
    }


    @NonNull
    @Override
    public CountriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_countries, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CountriesAdapter.ViewHolder holder, int position)
    {

       Picasso.get().load(dataModelArrayList.get(position).getImgURL()).into(holder.imageViews);
        /*Picasso.with(context)
            .load(holder.getImageurl())
            .into(holder.img);*/
        holder.Countryname.setText(dataModelArrayList.get(position).getCountry());
        holder.Deaths.setText(dataModelArrayList.get(position).getDeaths());
        holder.Infected.setText(dataModelArrayList.get(position).getInfectedcases());
        holder.Recovered.setText(dataModelArrayList.get(position).getRecovered());

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView Countryname, Deaths, Infected, Recovered;
        ImageView imageViews;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            Countryname = (TextView) itemView.findViewById(R.id.countries_name);
            Infected = (TextView) itemView.findViewById(R.id.countries_infected);
            Recovered = (TextView) itemView.findViewById(R.id.country_recovered);
            Deaths = (TextView) itemView.findViewById(R.id.countries_deaths);
            imageViews = (ImageView) itemView.findViewById(R.id.imagesC);

        }

    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<CountriesModelRecycler> filteredList = new ArrayList<>();
                String charString = charSequence.toString().toLowerCase().trim();
                if (charSequence == null || charSequence.length() == 0) {
                    filteredList.addAll(movieListFiltered);
                }
                else {
                    for (CountriesModelRecycler movie : movieListFiltered) {
                        if (movie.getCountry().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(movie);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataModelArrayList.clear();
                dataModelArrayList.addAll((List) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }
}
