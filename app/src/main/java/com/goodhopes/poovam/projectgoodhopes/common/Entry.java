package com.goodhopes.poovam.projectgoodhopes.common;

import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by poovam on 4/12/16.
 */

public class Entry {

    @Nullable public String title;
    @Nullable public String author;
    @Nullable public String content;
    @Nullable public String thumbNailUrl;
    @Nullable public String time;
    @Nullable public String contentUrl;

    public Entry(@Nullable String title,@Nullable String author,@Nullable String content,
                 @Nullable String thumbNailUrl,@Nullable String time,@Nullable String contentUrl){
        this.title = title;
        this.author = author;
        this.content = content;
        this.thumbNailUrl = thumbNailUrl;
        this.time = time;
        this.contentUrl = contentUrl;
    }

}
