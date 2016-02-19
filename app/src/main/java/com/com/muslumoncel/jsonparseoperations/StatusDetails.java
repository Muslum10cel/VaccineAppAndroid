package com.com.muslumoncel.jsonparseoperations;

/**
 * Created by muslumoncel on 13/02/16.
 */
public class StatusDetails {
    private String vaccine_status, vaccineName;


    public StatusDetails(String vaccineName, String vaccine_status) {
        this.vaccineName = vaccineName;
        this.vaccine_status = vaccine_status;
    }

    @Override
    public String toString() {
        return vaccineName + " " + vaccine_status;
    }
}
