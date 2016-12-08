package com.goodhopes.poovam.projectgoodhopes.common;


import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.parsers.tamil.DailyThanthiParser;
import com.goodhopes.poovam.projectgoodhopes.parsers.tamil.DinakaranParser;
import com.goodhopes.poovam.projectgoodhopes.parsers.tamil.DinamalarParser;
import com.goodhopes.poovam.projectgoodhopes.parsers.tamil.DinamaniParser;
import com.goodhopes.poovam.projectgoodhopes.parsers.tamil.TamilHinduParser;
import com.goodhopes.poovam.projectgoodhopes.parsers.tamil.VikatanParser;

import java.util.ArrayList;

/**
 * Created by poovam on 7/12/16.
 * Enum to handle all the subscriptions provided
 */

public enum Subscription {
    DAILYTHANTHI("Daily Thanthi","http://www.dailythanthi.com/RSS/News/TopNews", R.drawable.dinakaran_logo),
    DINAKARAN("Dinakaran","http://www.dinakaran.com/rss_Latest.asp",R.drawable.dinakaran_logo),
    DINAMALAR("Dinamalar","http://feeds.feedburner.com/dinamalar/Front_page_news?format=xml",
            R.drawable.dinakaran_logo),
    VIKATAN("VIKATAN","http://rss.vikatan.com/?cat=news_imprt",R.drawable.dinakaran_logo),
    DINAMANI("Dinamani","http://www.dinamani.com/%E0%AE%A4%E0%AE%B1%E0%AF%8D%E0%AE%AA%E0%AF%8B%E0%" +
            "AE%A4%E0%AF%88%E0%AE%AF-%E0%AE%9A%E0%AF%86%E0%AE%AF%E0%AF%8D%E0%AE%A4%E0%AE%BF%E0%AE%9" +
            "5%E0%AE%B3%E0%AF%8D/%E0%AE%A4%E0%AE%B1%E0%AF%8D%E0%AE%AA%E0%AF%8B%E0%AE%A4%E0%AF%88%E0" +
            "%AE%AF-%E0%AE%9A%E0%AF%86%E0%AE%AF%E0%AF%8D%E0%AE%A4%E0%AE%BF%E0%AE%95%E0%AE%B3%E0%AF%8" +
            "D/rssfeed/?id=687&getXmlFeed=true",R.drawable.dinakaran_logo),
    TAMIL_HINDU("The Hindu","http://tamil.thehindu.com/?service=rss",R.drawable.dinakaran_logo);


    public final String name;
    public final String URL;
    public final int iconID;

    Subscription(String name, String URL, int iconID){
        this.name=name;
        this.URL = URL;
        this.iconID = iconID;
    }

    public ArrayList<Entry> getParser(String response){
        switch (this){
            case DAILYTHANTHI:
                return DailyThanthiParser.parseResponse(response);
            case DINAKARAN:
                return DinakaranParser.parseResponse(response);
            case DINAMALAR:
                return DinamalarParser.parseResponse(response);
            case VIKATAN:
                return VikatanParser.parseResponse(response);
            case DINAMANI:
                return DinamaniParser.parseResponse(response);
            case TAMIL_HINDU:
                return TamilHinduParser.parseResponse(response);

        }
        return new ArrayList<>();
    }
}
