package com.example.covid;

public class CountriesModelRecycler
{
    private String deaths, country, recovered,infectedcases, imgURL;

    public CountriesModelRecycler()
    {

    }


    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getInfectedcases() {
        return infectedcases;
    }

    public void setInfectedcases(String infectedcases) {
        this.infectedcases = infectedcases;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
