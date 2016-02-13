package com.com.muslumoncel.jsonparseoperations;

/**
 * Created by muslumoncel on 12/02/16.
 */
public class DateDetails {

    private String vaccine_date, vaccineName;

    public DateDetails(String vaccineName, String vaccine_date) {
        this.vaccineName = vaccineName;
        this.vaccine_date = vaccine_date;
    }

    @Override
    public String toString() {
        return vaccineName + " " + vaccine_date;
    }
}
