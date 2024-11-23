package com.example.selenium.common;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FacebookCookieHandler {

    private static List<Map<String, String>> formatCookieFacebook(String cookies) {
        List<Map<String, String>> cookiesResult = new ArrayList<>();
        try {
            cookies = cookies.substring(1, cookies.length() - 1);
            String[] cookieInfors = cookies.split(";;");
            for (int i = 0; i < cookieInfors.length - 1; i++) {
                Map<String, String> cookie = new HashMap<>();
                String[] cookiePair = cookieInfors[i].split(";");

                for (int j = 0; j < cookiePair.length; j++) {
                    if (j == cookiePair.length - 1) {
                        cookie.put(cookiePair[j].trim(), "true");
                    } else {
                        if (cookiePair[j].contains("sameSite")) {
                            String[] nameKey = cookiePair[j].split(",");
                            cookie.put(nameKey[1].split("=")[0].trim(),
                                    nameKey[1].split("=")[1].trim());
                        } else {
                            String[] nameKey = cookiePair[j].split("=");
                            cookie.put(nameKey[0].trim(), nameKey[1].trim());
                        }
                    }
                }
                cookiesResult.add(cookie);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return cookiesResult;
    }
    public static void addCookieFacebookToWeb(WebDriver driver, String cookieStr) {
        try {
            List<Map<String, String>> cookies = formatCookieFacebook(cookieStr);
            SimpleDateFormat gmtFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
            gmtFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            cookies.forEach((cookie) -> {
                String key = null;
                String value = null;
                if (cookie.containsKey("datr")) {
                    key = "datr";
                    value = cookie.get("datr");
                } else if (cookie.containsKey("c_user")) {
                    key = "c_user";
                    value = cookie.get("c_user");
                } else if (cookie.containsKey("xs")) {
                    key = "xs";
                    value = cookie.get("xs");
                } else if (cookie.containsKey("fr")) {
                    key = "fr";
                    value = cookie.get("fr");
                } else if (cookie.containsKey("wd")) {
                    key = "wd";
                    value = cookie.get("wd");
                } else if (cookie.containsKey("sb")) {
                    key = "sb";
                    value = cookie.get("sb");
                }else if(cookie.containsKey("i_user")){
                    key="i_user";
                    value =cookie.get("i_user");
                }
                if (key != null && value != null) {
                    Cookie cookieFace = null;
                    try {
                        cookieFace = new Cookie(
                                key,
                                value,
                                cookie.get("domain"),
                                cookie.get("path"),
                                gmtFormatter.parse(cookie.get("expires")),
                                true);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    if(cookieFace !=null) driver.manage().addCookie(cookieFace);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
