package com.example.selenium.common;

public class StringHandler {

    public static String getValueFromKeyInString(String source, String value){
        int index = source.indexOf(value);
        StringBuilder result = new StringBuilder();
        char temp ;
        while((temp = source.charAt(index+(value.length()+1))) != ',' && temp != '}' ){
             result.append(temp);
             index++;
        }
        return result.toString();
    }

    public static String getValueFromCookie(String source, String value){
        int index = source.indexOf(value);
        StringBuilder result = new StringBuilder();
        char temp ;
        while(true){
            temp = source.charAt(index + value.length()+1);
            if(temp == ',' && source.charAt(index -1 + value.length() + 1) == ']'){
                break;
            }
            result.append(temp);
            index++;
        }
        return result.toString();
    }



}
