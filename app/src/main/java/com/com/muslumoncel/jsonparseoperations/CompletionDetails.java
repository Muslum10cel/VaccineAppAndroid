package com.com.muslumoncel.jsonparseoperations;

/**
 * Created by muslumoncel on 18/02/16.
 */
public class CompletionDetails {
    private String vaccine_status, vaccineName;

    public CompletionDetails(String vaccineName, String vaccine_status) {
        this.vaccineName = vaccineName;
        this.vaccine_status = vaccine_status;
    }

    @Override
    public String toString() {
        return vaccineName + ":" + vaccine_status;
    }
}
