package com.example.covid;

public class Precautions
{
    private String precautionsNames,precautionsDetails;
    private int preimgId;



    public Precautions(String precautionsNames,int preimgId,String precautionsDetails)
    {
        this.precautionsNames=precautionsNames;
        this.preimgId=preimgId;
        this.precautionsDetails = precautionsDetails;

    }


    public String getPrecautionsNames() {
        return precautionsNames;
    }

    public void setPrecautionsNames(String precautionsNames) {
        this.precautionsNames = precautionsNames;
    }

    public int getPreimgId() {
        return preimgId;
    }

    public String getPrecautionsDetails() {
        return precautionsDetails;
    }

    public void setPrecautionsDetails(String precautionsDetails) {
        this.precautionsDetails = precautionsDetails;
    }

    public void setPreimgId(int preimgId) {
        this.preimgId = preimgId;
    }
}
