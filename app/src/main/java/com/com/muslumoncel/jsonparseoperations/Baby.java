package com.com.muslumoncel.jsonparseoperations;

/**
 * Created by muslumoncel on 11/02/16.
 */
public class Baby extends Vaccines {

    private String baby_name;
    private int baby_id;

    public Baby(String baby_name, int baby_id) {
        super();
        this.baby_name = baby_name;
        this.baby_id = baby_id;
    }

    public int getBaby_id() {
        return baby_id;
    }

    @Override
    public String toString() {
        return baby_name;
    }
}
