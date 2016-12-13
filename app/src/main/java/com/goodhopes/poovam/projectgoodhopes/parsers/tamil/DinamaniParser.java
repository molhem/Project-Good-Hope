package com.goodhopes.poovam.projectgoodhopes.parsers.tamil;

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

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by poovam on 8/12/16.
 * Dinamani parser...
 * date cleaned
 * no image src sent by server
 */

public class DinamaniParser {
    public static ArrayList<Entry> parseResponse(String response,Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.saved_data),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(Subscription.DINAMANI.stringID), response);
        editor.apply();
        XMLParser parser = new XMLParser();
        ArrayList<Entry> dinamaniEntries = new ArrayList<>();

        Document doc = parser.getDomElement(response);
        NodeList nl = doc.getElementsByTagName("item");
        for (int i = 0; i < nl.getLength(); i++) {
            Element e = (Element) nl.item(i);
            Element line = (Element) e.getElementsByTagName("description").item(0);

            String title = (parser.getValue(e, "title"));
            String content = parser.getCharacterDataFromElement(line);
            String author = (parser.getValue(e, "author"));
            String thumbNailURL = (parser.getValue(e, "image"));
            String contentURL = (parser.getValue(e, "link"));
            String time = (parser.getValue(e, "pubDate"));
            Timestamp timestamp = new Timestamp(new Date().getDate());
            try {
                //Monday, December 5, 2016 08:08 AM +0530
                SimpleDateFormat dateFormat = new SimpleDateFormat("E, MMM dd, yyyy HH:mm a Z", Locale.US);
                Date parsedDate = dateFormat.parse(time);
                timestamp = new java.sql.Timestamp(parsedDate.getTime());
                //Log.d("dinamani",timestamp.toString());
            } catch (ParseException e1) {
                e1.printStackTrace();
            }

            dinamaniEntries.add(new Entry(title,Subscription.DINAMANI.name,content,thumbNailURL,
                    timestamp,contentURL,Subscription.DINAMANI.iconID));
        }
        return dinamaniEntries;
    }
}
