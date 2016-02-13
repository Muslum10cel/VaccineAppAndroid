package com.com.muslumoncel.jsonparseoperations;

/**
 * Created by muslumoncel on 11/02/16.
 */
public class Baby {

    private String baby_name;
    private int baby_id;

    public Baby(String baby_name, int baby_id) {
        this.baby_name = baby_name;
        this.baby_id = baby_id;
    }

    public int getBaby_id() {
        return baby_id;
    }

    public String getBaby_name() {
        return baby_name;
    }

    @Override
    public String toString() {
        return baby_name;
    }
}
