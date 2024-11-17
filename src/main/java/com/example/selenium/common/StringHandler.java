package com.example.selenium.common;

public class StringHandler {

    public static String getValueFromKeyInString(String source, String value){
        int index= source.indexOf(value);
        StringBuilder result = new StringBuilder();
        char temp ;
        while((temp = source.charAt(index+(value.length()+1))) != ',' && temp != '}' ){
             result.append(temp);
             index++;
        }
        return result.toString();
    }

}
