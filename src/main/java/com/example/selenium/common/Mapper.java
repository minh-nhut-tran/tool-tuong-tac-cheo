package com.example.selenium.common;

import com.example.selenium.pojo.AccountSocial;
import com.example.selenium.pojo.Facebook;

import java.util.HashMap;
import java.util.Map;

public class Mapper {

        public static String mapAccountFacebookToString(AccountSocial account){
            Map<String,String> accountFacebook = new HashMap<>();
            accountFacebook.put("username",account.getUsername());
            accountFacebook.put("password",account.getPassword());
            accountFacebook.put("cookie",account.getCookie());
            accountFacebook.put("status",account.getStatus());
            accountFacebook.put("profile",account.getProfile());
            accountFacebook.put("facebookID",((Facebook)account).getFacebookID());
            accountFacebook.put("name",((Facebook)account).getName());
            accountFacebook.put("type",((Facebook)account).getType());
            return accountFacebook.toString();
        }

        public static AccountSocial mapStringToAccountFacebook(String accountString){
            AccountSocial account = new Facebook();
            String username = StringHandler.getValueFromKeyInString(accountString,"username");
            String password = StringHandler.getValueFromKeyInString(accountString,"password");
            String cookie = StringHandler.getValueFromKeyInString(accountString,"cookie");
            String status = StringHandler.getValueFromKeyInString(accountString,"status");
            String profile = StringHandler.getValueFromKeyInString(accountString,"profile");
            String facebookID = StringHandler.getValueFromKeyInString(accountString,"facebookID");
            String name = StringHandler.getValueFromKeyInString(accountString,"name");
            String type = StringHandler.getValueFromKeyInString(accountString,"type");

            account.setUsername(username.trim());
            account.setPassword(password.trim());
            account.setCookie(cookie.trim());
            account.setProfile(profile.trim());
            account.setStatus(status);
            ((Facebook)account).setFacebookID(facebookID.trim());
            ((Facebook)account).setName(name.trim());
            ((Facebook)account).setType(type);
            return account;
        }
}
