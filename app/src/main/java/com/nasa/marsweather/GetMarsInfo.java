package com.nasa.marsweather;

    import android.util.Log;

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.net.MalformedURLException;
    import java.net.URL;
    import java.util.ArrayList;
    import java.util.List;

    import org.json.JSONException;
    import org.json.simple.JSONArray;
    import org.json.simple.JSONObject;
    import org.json.simple.parser.JSONParser;
    import org.json.simple.parser.ParseException;

public class GetMarsInfo {
    public static String getParsing() throws IOException {
        try {
            URL url = new URL("https://mars.nasa.gov/rss/api/?feed=weather&category=msl&feedtype=json");
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String result = bf.readLine();
            return result;
        } catch(MalformedURLException e) {
            e.printStackTrace();
            return "fuck";
        }
    }
    public static String[] getTemp(String date) throws IOException, ParseException {
        String min = "", max = "";
        String[][] s = getDate();
        for (int i = 0; i < s.length; i++)
        {
            if (s[i][0].equals(date))
            {
                min = s[i][1];
                max = s[i][2];
                break;
            }
        }
        String temp[] = {min, max};
        return temp;
    }

    public static String[][] getDate() throws IOException, ParseException {
        String result = GetMarsInfo.getParsing();
        Object obj = null;
        JSONObject jsonobj = null;
        JSONParser jsonParser = new JSONParser();
        obj = jsonParser.parse(result);
        jsonobj = (JSONObject) obj;
        Object sol = jsonobj.get("soles");
        org.json.simple.JSONArray temp = (JSONArray) sol;
        String l[][] = new String[temp.size()][3];
        for (int i = 0; i < temp.size(); i++)
        {
            JSONObject jDate = (JSONObject) temp.get(i);
            String date = (String)jDate.get("terrestrial_date");
            String min = (String)jDate.get("min_temp");
            String max = (String)jDate.get("max_temp");
            l[i][0] = date;
            l[i][1] = min;
            l[i][2] = max;
        }
        return l;
    }
}