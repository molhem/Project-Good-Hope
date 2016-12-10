package com.goodhopes.poovam.projectgoodhopes.parsers;

import android.util.Log;

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
 * Created by poovam on 8/12/16.
 * this is the parser for both hindu and tamil hindu
 * english hindu has a different date format hasn't been parsed
 */

public class HinduParser {

    public static ArrayList<Entry> parseResponse(String response){
        XMLParser parser = new XMLParser();
        ArrayList<Entry> tamilHinduEntries = new ArrayList<>();

        Document doc = parser.getDomElement(response);
        NodeList nl = doc.getElementsByTagName("item");
        for (int i = 0; i < nl.getLength(); i++) {
            Element e = (Element) nl.item(i);
            Element titleElement = (Element) e.getElementsByTagName("title").item(0);
            Element contentElement = (Element) e.getElementsByTagName("description").item(0);
            Element authorElement = (Element) e.getElementsByTagName("author").item(0);
            Element thumbNailURLElement = (Element) e.getElementsByTagName("description").item(0);
            Element contentURLElement = (Element) e.getElementsByTagName("link").item(0);
            Element timeElement = (Element) e.getElementsByTagName("pubDate").item(0);


            String title = parser.getCharacterDataFromElement(titleElement);
            String author = parser.getCharacterDataFromElement(authorElement);
            String thumbNailURL = parser.getCharacterDataFromElement(thumbNailURLElement);
            String content = parser.getCharacterDataFromElement(contentElement);
            String time = parser.getCharacterDataFromElement(timeElement);
            String contentURL = parser.getCharacterDataFromElement(contentURLElement);
            Timestamp timestamp = new Timestamp(new Date().getDate());
            try {
                // hindu Thu, 8 Dec 2016 20:43:09 +0530
                SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
                Date parsedDate = dateFormat.parse(time);
                timestamp = new java.sql.Timestamp(parsedDate.getTime());
                //Log.d("hindu",timestamp.toString());
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            tamilHinduEntries.add(new Entry(title,Subscription.THE_HINDU.name,content,thumbNailURL,timestamp,contentURL,
                    Subscription.THE_HINDU.iconID));
        }
        return tamilHinduEntries;
    }
}
