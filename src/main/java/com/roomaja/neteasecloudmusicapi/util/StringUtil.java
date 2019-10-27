package com.roomaja.neteasecloudmusicapi.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
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

    /*执行js脚本*/
    public static String evalJS(String js){
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try {
            return String.valueOf(engine.eval(js));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return null;
    }
}
