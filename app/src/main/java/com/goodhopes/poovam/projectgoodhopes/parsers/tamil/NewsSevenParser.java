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

/**
 * Created by poova-pt1297 on 2/4/2017.
 */

public class NewsSevenParser {
    public static ArrayList<Entry> parseResponse(String response, Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.saved_data),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(Subscription.TAMIL_HINDU.stringID), response);
        editor.apply();
        XMLParser parser = new XMLParser();
        ArrayList<Entry> entries = new ArrayList<>();
        Document doc = parser.getDomElement(response);
        NodeList nl = doc.getElementsByTagName("item");
        for (int i = 0; i < nl.getLength(); i++) {
            Element e = (Element) nl.item(i);

            String title = (parser.getValue(e,"title"));
            String content = (parser.getValue(e,"description"));
            String temp = content.substring(0,content.indexOf("<p>"));
            content = content.substring(content.indexOf("<p>"),content.length());
            content =  content.replaceAll("<p>","");
            content =  content.replaceAll("</p>","");
            content =  content.replaceAll("<strong>","");
            content =  content.replaceAll("</strong>","");
            String thumbNailURL = temp.substring(temp.indexOf("http"),temp.indexOf(".png"));
            thumbNailURL = thumbNailURL+".png";
            String contentURL = (parser.getValue(e,"link"));
            String time = (parser.getValue(e,"pubDate"));
            Timestamp timestamp = new Timestamp(new Date().getDate());
            try {
                //Saturday, February 4, 2017 - 00:06
                SimpleDateFormat dateFormat = new SimpleDateFormat("E, MMM dd, yyyy - HH:mm");
                Date parsedDate = dateFormat.parse(time);
                timestamp = new java.sql.Timestamp(parsedDate.getTime());
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            entries.add(new Entry(title, Subscription.NEWS_SEVEN_TAMIL.name,content,thumbNailURL,timestamp,contentURL,
                    Subscription.NEWS_SEVEN_TAMIL.iconID));
        }
        return entries;
    }
}
