package com.goodhopes.poovam.projectgoodhopes.common;

/**
 * Created by poovam on 12/12/16.
 */

public class Utilities {
    public static String timeConversion(long seconds) {
        long minutes = ((seconds % 3600) / 60);
        long hours = (seconds % 86400) / 3600;
        long days = (seconds % (86400 * 30)) / 86400;
        String timeAgo;

        if (days > 0) {
            timeAgo = days + " days ago";
            return timeAgo;
        } else if (hours > 0) {
            timeAgo = hours + " hours ago";
            return timeAgo;
        } else if (minutes > 0) {
            timeAgo = minutes + " minutes ago";
            return timeAgo;
        }
        return "-";
    }
}
