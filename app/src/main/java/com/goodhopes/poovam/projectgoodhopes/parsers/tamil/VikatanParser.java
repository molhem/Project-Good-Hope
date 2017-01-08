package com.goodhopes.poovam.projectgoodhopes.parsers.tamil;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.common.Entry;
import com.goodhopes.poovam.projectgoodhopes.common.Subscription;
import com.goodhopes.poovam.projectgoodhopes.parsers.XMLParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by poovam on 7/12/16.
 * A parser for Vikatan Tamil Daily RSS
 * Image parsed content cleaned
 */

public class VikatanParser {

    public static ArrayList<Entry> parseResponse(String response,Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.saved_data),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(Subscription.VIKATAN.stringID), response);
        editor.apply();
        XMLParser parser = new XMLParser();
        ArrayList<Entry> vikatanEntries = new ArrayList<>();
        try {
            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response = response.replace("<p>","");
        Document doc = parser.getDomElement(response);
        if(doc!=null){
        NodeList nl = doc.getElementsByTagName("item");
            for (int i = 0; i < nl.getLength(); i++) {
                Element e = (Element) nl.item(i);
                Element line = (Element) e.getElementsByTagName("description").item(0);

                String title = (parser.getValue(e, "title"));
                String content = parser.getCharacterDataFromElement(line);
                String thumbNailURL = content.split(".jpg")[0];
                thumbNailURL = thumbNailURL.substring(thumbNailURL.lastIndexOf("'") + 1);

                content = content.substring(content.lastIndexOf(">") + 1);

                String contentURL = (parser.getValue(e, "link"));
                String time = (parser.getValue(e, "pubDate"));
                Timestamp timestamp = new Timestamp(new Date().getDate());
                try {
                    //Fri, 09 Dec 2016 00:10:02 IST
                    time = time.replace("IST", "");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
                    Date parsedDate = dateFormat.parse(time);
                    timestamp = new java.sql.Timestamp(parsedDate.getTime());
                    //Log.d("vikatan",timestamp.toString());
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                vikatanEntries.add(new Entry(title, Subscription.VIKATAN.name, content, thumbNailURL, timestamp, contentURL,
                        Subscription.VIKATAN.iconID));
            }
        }
        return vikatanEntries;
    }
}
