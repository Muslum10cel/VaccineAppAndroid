package com.com.muslumoncel.jsonparseoperations;

/**
 * Created by muslumoncel on 11/02/16.
 */
public class Vaccines {
    private String vaccine_name;
    private String vaccine_date;
    private int status;

    public void setVaccine_name(String vaccine_name) {
        this.vaccine_name = vaccine_name;
    }

    public void setVaccine_date(String vaccine_date) {
        this.vaccine_date = vaccine_date;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Vaccines(){}

    public String getVaccine_date() {
        return vaccine_date;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return vaccine_name;
    }
}
