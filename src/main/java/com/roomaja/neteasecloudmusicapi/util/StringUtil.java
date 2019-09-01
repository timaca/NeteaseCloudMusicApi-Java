package com.roomaja.neteasecloudmusicapi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    /**
     * 相当于python 的 findall():在传进来的url中获取doc_id
     * @param regex
     * @param text
     * @return
     */
    public static List<String> findall(String regex, String text){
        Matcher matcher = Pattern.compile(regex).matcher(text);
        List<String> matchStrings = new ArrayList<String>();
        while(matcher.find()){
            matchStrings.add(matcher.group());
        }
        return matchStrings;
    }
}
