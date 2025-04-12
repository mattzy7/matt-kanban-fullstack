package com.matt.utils;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;


public class JsoupUtil  {
    private final static Safelist safelist = Safelist.basic();
    static {
        safelist.addTags("img");  // Allow <img> tag
        safelist.addAttributes(":all", "href", "src");  // Allow "href" and "src" on any tag
    }
    public static String sanitize(String input) {
        return Jsoup.clean(input, safelist);
    }
}
