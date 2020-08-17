package com.example.covid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiModel
{




    @SerializedName("updated")
    @Expose
    private int updated;
    @SerializedName("cases")
    @Expose
    private int cases;
    @SerializedName("recovered")
    @Expose
    private int recovered;
    @SerializedName("active")
    @Expose
    private int active;

    @SerializedName("critical")
     private  int critical;

    @SerializedName("deaths")
      private int deaths;

    ApiModel(){

    }

    public int getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getCritical() {
        return critical;
    }

    public void setCritical(int critical) {
        this.critical = critical;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }
}

