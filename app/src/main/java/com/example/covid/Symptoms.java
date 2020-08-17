package com.example.covid;

public class Symptoms
{
    private int imgId;
    private String symptoms_name,Symptoms_details;


  public Symptoms(String symptoms_name,String symptoms_details,int imgId)
  {
      this.symptoms_name = symptoms_name;
      this.Symptoms_details=symptoms_details;
      this.imgId = imgId;
  }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getSymptoms_name() {
        return symptoms_name;
    }

    public void setSymptoms_name(String symptoms_name) {
        this.symptoms_name = symptoms_name;
    }

    public String getSymptoms_details() {
        return Symptoms_details;
    }

    public void setSymptoms_details(String symptoms_details) {
        Symptoms_details = symptoms_details;
    }
}
