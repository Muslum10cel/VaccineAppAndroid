package com.example.muslumoncel.vaccineapp;

/**
 * Created by muslumoncel on 04/02/16.
 */
public class Options {

    private String option;

    public Options(String option) {

        this.option = option;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return this.option;
    }
}
