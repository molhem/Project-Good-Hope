package com.goodhopes.poovam.projectgoodhopes.common;


import android.content.Context;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.parsers.english.IndianExpressParser;
import com.goodhopes.poovam.projectgoodhopes.parsers.english.TOIParser;
import com.goodhopes.poovam.projectgoodhopes.parsers.tamil.DailyThanthiParser;
import com.goodhopes.poovam.projectgoodhopes.parsers.tamil.DinakaranParser;
import com.goodhopes.poovam.projectgoodhopes.parsers.tamil.DinamalarParser;
import com.goodhopes.poovam.projectgoodhopes.parsers.tamil.DinamaniParser;
import com.goodhopes.poovam.projectgoodhopes.parsers.tamil.MaalaiMalarParser;
import com.goodhopes.poovam.projectgoodhopes.parsers.english.TheHinduParser;
import com.goodhopes.poovam.projectgoodhopes.parsers.tamil.TamilHinduParser;
import com.goodhopes.poovam.projectgoodhopes.parsers.tamil.VikatanParser;

import java.util.ArrayList;

/**
 * Created by poovam on 7/12/16.
 * Enum to handle all the subscriptions provided
 */

public enum Subscription {
    DAILYTHANTHI("Daily Thanthi","http://www.dailythanthi.com/RSS/News/TopNews", R.drawable.daily_thanthi,
            R.string.dailythanthi),
    DINAKARAN("Dinakaran","http://www.dinakaran.com/rss_Latest.asp",R.drawable.dinakaran_logo,
            R.string.dinakaran),
    DINAMALAR("Dinamalar","http://feeds.feedburner.com/dinamalar/Front_page_news?format=xml",
            R.drawable.dinamalar,R.string.dinamalar),
    VIKATAN("VIKATAN","http://rss.vikatan.com/?cat=news_imprt",R.drawable.vikatan,R.string.vikatan),
    DINAMANI("Dinamani","http://www.dinamani.com/%E0%AE%A4%E0%AE%B1%E0%AF%8D%E0%AE%AA%E0%AF%8B%E0%" +
            "AE%A4%E0%AF%88%E0%AE%AF-%E0%AE%9A%E0%AF%86%E0%AE%AF%E0%AF%8D%E0%AE%A4%E0%AE%BF%E0%AE%9" +
            "5%E0%AE%B3%E0%AF%8D/%E0%AE%A4%E0%AE%B1%E0%AF%8D%E0%AE%AA%E0%AF%8B%E0%AE%A4%E0%AF%88%E0" +
            "%AE%AF-%E0%AE%9A%E0%AF%86%E0%AE%AF%E0%AF%8D%E0%AE%A4%E0%AE%BF%E0%AE%95%E0%AE%B3%E0%AF%8" +
            "D/rssfeed/?id=687&getXmlFeed=true",R.drawable.dinamani,R.string.dinamani),
    TAMIL_HINDU("Tamil Hindu","http://tamil.thehindu.com/?service=rss",R.drawable.tamil_hindu,
            R.string.tamil_hindu),
    MAALAIMALAR("மாலை மலர்","http://www.maalaimalar.com/SectionRSS/News/TopNews",R.drawable.maalai_malar,
            R.string.maalaimalar),
    TOI("Times Of India","http://timesofindia.indiatimes.com/rssfeeds/1221656.cms",R.drawable.toi,
            R.string.toi),
    THE_HINDU("The Hindu","http://www.thehindu.com/?service=rss",R.drawable.the_hindu,R.string.the_hindu),
    INDIAN_EXPRESS("Indian Express","http://syndication.indianexpress.com/rss/latest-news.xml",
            R.drawable.indian_express,R.string.indian_express);


    public final String name;
    public final String URL;
    public final int iconID;
    public final int stringID;

    Subscription(String name, String URL, int iconID,int stringID){
        this.name=name;
        this.URL = URL;
        this.iconID = iconID;
        this.stringID = stringID;
    }

    public ArrayList<Entry> getParser(String response, Context context){
        switch (this){
            case DAILYTHANTHI:
                return DailyThanthiParser.parseResponse(response,context);
            case DINAKARAN:
                return DinakaranParser.parseResponse(response,context);
            case DINAMALAR:
                return DinamalarParser.parseResponse(response,context);
            case VIKATAN:
                return VikatanParser.parseResponse(response,context);
            case DINAMANI:
                return DinamaniParser.parseResponse(response,context);
            case TAMIL_HINDU:
                return TamilHinduParser.parseResponse(response,context);
            case THE_HINDU:
                return TheHinduParser.parseResponse(response,context);
            case MAALAIMALAR:
                return MaalaiMalarParser.parseResponse(response,context);
            case TOI:
                return TOIParser.parseResponse(response,context);
            case INDIAN_EXPRESS:
                return IndianExpressParser.parseResponse(response,context);

        }
        return new ArrayList<>();
    }
}
