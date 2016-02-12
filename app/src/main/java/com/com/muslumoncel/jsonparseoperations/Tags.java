package com.com.muslumoncel.jsonparseoperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muslumoncel on 11/02/16.
 */
public class Tags {
    public static final List<String> vaccines = new ArrayList<>();

    static {
        vaccines.add("BCG");
        vaccines.add("DaBT_IPA");
        vaccines.add("VARICELLA");
        vaccines.add("KMA4");
        vaccines.add("HPA");
        vaccines.add("INFLUENZA");
        vaccines.add("FIRST_RVA_STATUS");
        vaccines.add("SECOND_RVA_STATUS");
        vaccines.add("THIRD_RVA_STATUS");
        vaccines.add("FIRST_HEPATIT_A_STATUS");
        vaccines.add("SECOND_HEPATIT_A_STATUS");
        vaccines.add("FIRST_HEPATIT_B_STATUS");
        vaccines.add("SECOND_HEPATIT_B_STATUS");
        vaccines.add("THIRDD_HEPATIT_B_STATUS");
        vaccines.add("FIRST_KKK_STATUS");
        vaccines.add("SECOND_KKK_STATUS");
        vaccines.add("FIRST_KPA_STATUS");
        vaccines.add("SECOND_KPA_STATUS");
        vaccines.add("THIRD_KPA_STATUS");
        vaccines.add("FOURTH_KPA_STATUS");
        vaccines.add("FIRST_DaBT_IPA_HIB_STATUS");
        vaccines.add("SECOND_DaBT_IPA_HIB_STATUS");
        vaccines.add("THIRD_DaBT_IPA_HIB_STATUS");
        vaccines.add("FOURTH_DaBT_IPA_HIB_STATUS");
        vaccines.add("FIFTH_DaBT_IPA_HIB_STATUS");
        vaccines.add("SIXTH_DaBT_IPA_HIB_STATUS");
    }

    public static final String BABIES_TAG = "BABIES";
    public static final String BABY_NAME_TAG = "BABY_NAME";
    public static final String BABY_ID_TAG = "BABY_ID";
}
