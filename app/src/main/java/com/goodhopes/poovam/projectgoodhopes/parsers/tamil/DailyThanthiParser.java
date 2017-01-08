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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by poovam on 7/12/16.
 * A parser that parses daily thanthi
 * No image provided
 */

public class DailyThanthiParser {


    public static ArrayList<Entry> parseResponse(String response, Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.saved_data),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(Subscription.DAILYTHANTHI.stringID), response);
        editor.apply();
        ArrayList<Entry> dailyThanthiEntries = new ArrayList<>();
        final XMLParser parser = new XMLParser();

        try {
            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response = response.replace("\n","");
        response = response.replace("\r","");
        response = response.replace("<p>","");
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
                //Thu, 8 Dece 2016 09:30:00 GMT
                String p = "[^.*,\\s\\d+\\s+]+[\\s+]";
                Pattern pattern = Pattern.compile(p);
                Matcher m = pattern.matcher(time);
                while (m.find()) {
                    String month = m.group().substring(0, 3);
                    time = time.replaceFirst(p, month + " ");
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
                Date parsedDate = dateFormat.parse(time);
                timestamp = new java.sql.Timestamp(parsedDate.getTime());
                //Log.d("daily thanthi",timestamp.toString());
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            dailyThanthiEntries.add(new Entry(title,Subscription.DAILYTHANTHI.name,content,thumbNailURL,timestamp,contentURL,
                    Subscription.DAILYTHANTHI.iconID));
        }

        return dailyThanthiEntries;
    }
}
