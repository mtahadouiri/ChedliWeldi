package com.esprit.chedliweldi.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.esprit.chedliweldi.Entities.Babysitter;

public class BabySittersJSONParser {

    static List<Babysitter> babysitters;

    public static List<Babysitter> parseData(String content) {

        JSONArray games_arry = null;
        Babysitter babysitter = null;
        try {

            games_arry = new JSONArray(content);
            babysitters = new ArrayList<>();

            for (int i = 0; i < games_arry.length(); i++) {

                JSONObject obj = games_arry.getJSONObject(i);
                babysitter = new Babysitter();

                babysitter.setId(obj.getInt("id"));
                babysitter.setFirstName(obj.getString("firstname"));
                babysitter.setLastName(obj.getString("lastname"));
                babysitter.setLongitude((float) obj.getDouble("longitude"));
                babysitter.setAltitude((float) obj.getDouble("altitude"));
                babysitter.setBirthDate(Date.valueOf(obj.getString("birthdate")));
                babysitter.setEmail(obj.getString("email"));
                babysitter.setImgURL(obj.getString("image"));
                babysitter.setDescr(obj.getString("descr"));
                babysitters.add(babysitter);
            }

            return babysitters;

        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
