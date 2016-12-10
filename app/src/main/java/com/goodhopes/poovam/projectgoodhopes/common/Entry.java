package com.goodhopes.poovam.projectgoodhopes.common;

import android.support.annotation.Nullable;
import android.util.Log;

import java.sql.Timestamp;

/**
 * Created by poovam on 4/12/16.
 */

public class Entry {

    @Nullable public String title;
    @Nullable public String author;
    @Nullable public String content;
    @Nullable public String thumbNailUrl;
    @Nullable public Timestamp time;
    @Nullable public String contentUrl;
    public int companyLogoId ;

    public Entry(@Nullable String title,@Nullable String author,@Nullable String content,
                 @Nullable String thumbNailUrl,@Nullable Timestamp time,@Nullable String contentUrl,
                 int companyLogoId){
        this.title = title;
        this.author = author;
        this.content = content;
        this.thumbNailUrl = thumbNailUrl;
        this.time = time;
        this.contentUrl = contentUrl;
        this.companyLogoId = companyLogoId;
    }

}
