package com.goodhopes.poovam.projectgoodhopes.parsers.tamil;

import com.goodhopes.poovam.projectgoodhopes.common.Entry;
import com.goodhopes.poovam.projectgoodhopes.parsers.XMLParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by poovam on 8/12/16.
 */

public class TamilHinduParser {

    public static ArrayList<Entry> parseResponse(String response){
        XMLParser parser = new XMLParser();
        ArrayList<Entry> tamilHinduEntries = new ArrayList<>();

        Document doc = parser.getDomElement(response);
        NodeList nl = doc.getElementsByTagName("item");
        for (int i = 0; i < nl.getLength(); i++) {
            Element e = (Element) nl.item(i);
            String title = (parser.getValue(e, "title"));
            String content = (parser.getValue(e,"description"));
            String author = (parser.getValue(e, "author"));
            String thumbNailURL = (parser.getValue(e, "image"));
            String contentURL = (parser.getValue(e, "link"));
            String time = (parser.getValue(e, "pubDate"));
            tamilHinduEntries.add(new Entry(title,author,content,thumbNailURL,time,contentURL));
        }
        return tamilHinduEntries;
    }
}
