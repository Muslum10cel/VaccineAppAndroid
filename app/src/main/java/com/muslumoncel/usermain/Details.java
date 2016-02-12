package com.muslumoncel.usermain;

/**
 * Created by muslumoncel on 12/02/16.
 */
public class Details {

    private String vaccine_date, vaccine_status;

    public Details(String vaccine_date) {
        this.vaccine_date = vaccine_date;
    }


    public void setVaccine_status(String vaccine_status) {
        this.vaccine_status = vaccine_status;
    }

    @Override
    public String toString() {
        return vaccine_date + " " + vaccine_status;
    }
}
