package edu.ufl;

import org.json.*;
import java.io.*;

public class JSON {

    public static JSONObject fileAsObject(int id) {
        try {
            String json_raw = Util.rawResourceToString(id);
            return new JSONObject(json_raw);
        } catch (IOException e) {
            GameLog.d("JSON","Error reading file");
            return new JSONObject();
        } catch (JSONException e) {
            GameLog.d("JSON","Error reading file");
            return new JSONObject();
        }
    }

    public static JSONArray fileAsArray(int id) {
        try {
            String json_raw = Util.rawResourceToString(id);
            return new JSONArray(json_raw);
        } catch (IOException e) {
            GameLog.d("JSON","Error reading file");
            return new JSONArray();
        } catch (JSONException e) {
            GameLog.d("JSON","Error reading file");
            return new JSONArray();
        }
    }

}

