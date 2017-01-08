package com.goodhopes.poovam.projectgoodhopes.parsers.english;

import android.content.Context;
import android.content.SharedPreferences;
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

/**
 * Created by poovam on 8/12/16.
 * TOI doesnt have images
 * GMT have to be converted to IST
 */

public class TOIParser {

    public static ArrayList<Entry> parseResponse(String response,Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.saved_data),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(Subscription.TOI.stringID), response);
        editor.apply();
        ArrayList<Entry> toiEntries = new ArrayList<>();
        final XMLParser parser = new XMLParser();

        Document doc = parser.getDomElement(response);
        NodeList nl = doc.getElementsByTagName("item");
        for (int i = 0; i < nl.getLength(); i++) {
            Element e = (Element) nl.item(i);

            String title = (parser.getValue(e,"title"));
            String content = (parser.getValue(e,"description"));
            String thumbNailURL = (parser.getValue(e,"image"));
            String contentURL = (parser.getValue(e,"link"));
            String time = (parser.getValue(e,"pubDate"));
            Timestamp timestamp = new Timestamp(new Date().getDate());
            try {
                //Thu, 08 Dec 2016 18:59:54 GMT
                SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
                Date parsedDate = dateFormat.parse(time);
                timestamp = new java.sql.Timestamp(parsedDate.getTime());
                //Log.d("TOI",timestamp.toString());
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            toiEntries.add(new Entry(title,Subscription.TOI.name,content,thumbNailURL,timestamp,contentURL,
                    Subscription.TOI.iconID));
        }

        return toiEntries;
    }
}
