package com.example.covid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CountriesActivity extends AppCompatActivity {

    private CountriesAdapter countriesAdapter;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    //search view
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        // Toolbar
        toolbar = findViewById(R.id.countires_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Countries Lists");

        recyclerView = findViewById(R.id.recyleviewCountries);

        fetchJSON();

    }

    private void fetchJSON()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CountriesInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        CountriesInterface api = retrofit.create(CountriesInterface.class);

        Call<String> call = api.getString();


        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(CountriesActivity.this,R.style.AppCompatAlertDialogStyle);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Please wait some moment");
        progressDoalog.setTitle("loading....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setCancelable(false);
        progressDoalog.setCanceledOnTouchOutside(false);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", response.body().toString());
                if (response.isSuccessful())
                {
                    progressDoalog.dismiss();
                    if (response.body() != null)
                    {
                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        writeRecycler(jsonresponse);
                    }
                    else
                    {
                        Log.i("onEmptyResponse", "Returned empty response");
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                progressDoalog.dismiss();
                Log.i("Response", t.getLocalizedMessage());
                Toast.makeText(getApplicationContext(),"Something Went Wrong!...",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void writeRecycler(String response) {


        try
        {
            JSONArray jsonArray = new JSONArray(response);

            ArrayList<CountriesModelRecycler> modelRecyclerArrayList = new ArrayList<>();

            for(int i=0;i<jsonArray.length();i++)
            {

                CountriesModelRecycler modelRecycler = new CountriesModelRecycler();
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String value1 = jsonObject1.optString("country");
                String value2 = jsonObject1.optString("updated");
                Log.i("S1",value1.toString());
                Log.i("S2",value2.toString());

                String val = jsonObject1.optString("countryInfo");
                Log.i("S2",val.toString());

                JSONObject jsonObject2 = jsonObject1.getJSONObject("countryInfo");

                for(int j=0;j<jsonObject2.length();j++)
                  modelRecycler.setImgURL(jsonObject2.optString("flag"));

                modelRecycler.setCountry(jsonObject1.optString("country"));
                modelRecycler.setInfectedcases(jsonObject1.optString("cases"));
                modelRecycler.setRecovered(jsonObject1.optString("recovered"));
                modelRecycler.setDeaths(jsonObject1.optString("deaths"));
                modelRecyclerArrayList.add(modelRecycler);
            }

            countriesAdapter = new CountriesAdapter(getApplicationContext(), modelRecyclerArrayList);
            recyclerView.setAdapter(countriesAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Log.i("e",e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                countriesAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //no inspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }


}