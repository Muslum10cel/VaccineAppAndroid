package com.muslum.vaccineapp.ws;

import com.muslumyusuf.webserviceforvaccineapp.DBOperations;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by muslumoncel on 01/02/16.
 */
public class WebServiceOperations {
    private final DBOperations dbOperations=new DBOperations();

    public int register(String username,String fullName,String password,String passwordConfirm,String e_mail) throws IOException, XmlPullParserException {
        return dbOperations.register(username,fullName,password,passwordConfirm,e_mail);
    }

    public int log_in(String username,String password) throws IOException, XmlPullParserException {
        return dbOperations.log_in(username, password);
    }

    public int addBaby(String username,String baby_name,String  date_of_birth) throws IOException, XmlPullParserException {
        return dbOperations.addBaby(username, baby_name, date_of_birth);
    }

    public int update_DaBT_Ipa_Hib_Vaccine(int baby_id, int flag) throws IOException, XmlPullParserException {
        return dbOperations.update_DaBT_Ipa_Hib_Vaccine(baby_id, flag);
    }

    public int update_Hepatit_A(int baby_id, int flag) throws IOException, XmlPullParserException {
        return dbOperations.update_Hepatit_A(baby_id, flag);
    }

    public int update_Hepatit_B(int baby_id, int flag) throws IOException, XmlPullParserException {
        return dbOperations.update_Hepatit_B(baby_id, flag);
    }

    public int update_KKK(int baby_id, int flag) throws IOException, XmlPullParserException {
        return dbOperations.update_KKK(baby_id, flag);
    }

    public int update_KPA(int baby_id, int flag) throws IOException, XmlPullParserException {
        return dbOperations.update_KPA(baby_id, flag);
    }

    public int update_OPA(int baby_id, int flag) throws IOException, XmlPullParserException {
        return dbOperations.update_OPA(baby_id, flag);
    }

    public int update_RVA(int baby_id, int flag) throws IOException, XmlPullParserException {
        return dbOperations.update_RVA(baby_id, flag);
    }

    public JSONObject getBabies(String username) throws IOException, XmlPullParserException, JSONException {
        return dbOperations.getBabies(username);
    }

    public int update_Vaccines(int baby_id, int flag) throws IOException, XmlPullParserException {
        return dbOperations.update_Vaccines(baby_id, flag);
    }

    public int addComment(String username, String vaccine_name, String comment) throws IOException, XmlPullParserException {
        return dbOperations.addComment(username, vaccine_name, comment);
    }

    public int forgottenpassword(String username, String newPassword) throws IOException, XmlPullParserException {
        return dbOperations.forgottenpassword(username, newPassword);
    }

    public int sendVerificationCode(String e_mail) throws IOException, XmlPullParserException {
        return dbOperations.sendVerificationCode(e_mail);
    }

    public int validateVerificationCode(String e_mail, String code) throws IOException, XmlPullParserException {
        return dbOperations.validateVerificationCode(e_mail,code);
    }

    public JSONObject getBabyVaccineDetails(int baby_id) throws JSONException, XmlPullParserException, IOException {
        return dbOperations.getBabyVaccineDetails(baby_id);
    }

    public JSONObject getComments(String vaccine_name,int begin,int end) throws IOException, XmlPullParserException, JSONException {
        return dbOperations.getComments(vaccine_name, begin, end);
    }

    public JSONObject getVaccineNames() throws JSONException, XmlPullParserException, IOException {
        return dbOperations.getVaccineNames();
    }

    public JSONObject getCompletedVaccines(int baby_id) throws JSONException, XmlPullParserException, IOException {
        return dbOperations.getCompletedVaccines(baby_id);
    }
}
