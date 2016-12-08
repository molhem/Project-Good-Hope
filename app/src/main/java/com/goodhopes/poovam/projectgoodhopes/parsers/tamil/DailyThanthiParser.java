package com.goodhopes.poovam.projectgoodhopes.parsers.tamil;

import com.goodhopes.poovam.projectgoodhopes.common.Entry;
import com.goodhopes.poovam.projectgoodhopes.parsers.XMLParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by poovam on 7/12/16.
 * A parser that parses daily thanthi
 */

public class DailyThanthiParser {


    public static ArrayList<Entry> parseResponse(String response){
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
            String author = (parser.getValue(e,"author"));
            String thumbNailURL = (parser.getValue(e,"image"));
            String contentURL = (parser.getValue(e,"link"));
            String time = (parser.getValue(e,"pubDate"));

            dailyThanthiEntries.add(new Entry(title,author,content,thumbNailURL,time,contentURL));
        }

        return dailyThanthiEntries;
    }
}
