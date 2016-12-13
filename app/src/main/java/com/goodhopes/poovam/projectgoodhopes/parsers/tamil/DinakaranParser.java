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

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by poovam on 7/12/16.
 * A parser that parses for Dinakaran
 * parsed timestamp
 * need to get the image link from CDATA
 * date needs no conversion
 * Image is parsed
 */

public class DinakaranParser {


    public static ArrayList<Entry> parseResponse(String response, Context context){

        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.saved_data),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(Subscription.DINAKARAN.stringID), response);
        editor.apply();


        XMLParser parser = new XMLParser();
        ArrayList<Entry> dinakaranEntries = new ArrayList<>();
        try {
            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response = response.replace("<p>","");
        Document doc = parser.getDomElement(response);
        NodeList nl = doc.getElementsByTagName("item");
        for (int i = 0; i < nl.getLength(); i++) {
            Element e = (Element) nl.item(i);
            Element line = (Element) e.getElementsByTagName("description").item(0);

            String title = (parser.getValue(e, "title"));
            String content = parser.getCharacterDataFromElement(line);
            String thumbNailURL = content.split(".jpg")[0];
            thumbNailURL = thumbNailURL.substring(thumbNailURL.lastIndexOf("'")+1);
            thumbNailURL = thumbNailURL+".jpg";
            content = content.replaceAll(".*</a>", "");
            content = content.replaceAll("<p>","");
            content = content.replaceAll("</p>","");

            String contentURL = (parser.getValue(e, "link"));
            String time = (parser.getValue(e, "pubDate"));
            Timestamp timestamp = new Timestamp(new Date().getDate());
            try {
                //8-12-2016 20:34
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                Date parsedDate = dateFormat.parse(time);
                timestamp = new java.sql.Timestamp(parsedDate.getTime());
                //Log.d("dinakaran",timestamp.toString());
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            dinakaranEntries.add(new Entry(title,Subscription.DINAKARAN.name,content,thumbNailURL,
                    timestamp,contentURL, Subscription.DINAKARAN.iconID));
        }
        return dinakaranEntries;
    }
}
