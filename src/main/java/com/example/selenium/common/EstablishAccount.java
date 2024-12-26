package com.example.selenium.common;

import com.example.selenium.constants.URL;
import com.example.selenium.pojo.*;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class EstablishAccount {

    public static void EstablishAccountFacebook(AccountSocial accountSocial, Account account){
        try {
                Unirest.post(URL.URL_ESTABLISH_ACCOUNT)
                    .header("Cookie",account.getSession())
                    .field("iddat[]",((Facebook)accountSocial).getFacebookID())
                    .field("loai", "fb")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    public static void EstablishAccountTiktok(AccountSocial accountSocial, Account account){
        try{
            Unirest.post(URL.URL_ESTABLISH_ACCOUNT)
                    .header("Cookie",account.getSession())
                    .field("iddat[]",((Tiktok)accountSocial).getTiktokID())
                    .field("loai", "tt")
                    .asString();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void EstablishAccountYoutube(AccountSocial accountSocial, Account account){
        try{
            Unirest.post(URL.URL_ESTABLISH_ACCOUNT)
                    .header("Cookie",account.getSession())
                    .field("iddat[]",((Youtube)accountSocial).getChanelID())
                    .field("loai", "youtube")
                    .asString();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
