package com.example.selenium.service.account_facebook;

import com.example.selenium.controller.TableFacebookController;
import com.example.selenium.model.Facebook;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class AccountFacebookService implements IAccountFacebookService{
    @Override
    public List<HBox> loadAccountFacebook() {
        List<Facebook> accounts = new ArrayList<>();
        accounts.add(new Facebook("ID1239192","Tran Minh Nhut",true,"PROFILE"));
        accounts.add(new Facebook("ID0001","Tran Thanh Nha",true,"PAGE"));
        List<HBox> listAccounts = new ArrayList<>();
        try {
            ListIterator<Facebook> iterator = accounts.listIterator();
            while(iterator.hasNext()){
                Facebook facebook = iterator.next();
                int index = iterator.previousIndex();

                FXMLLoader loaderItem = new FXMLLoader(getClass().getResource("../../table-facebook-view.fxml"));
                HBox item = loaderItem.load();

                TableFacebookController tableFacebookController =  loaderItem.getController();
                if(tableFacebookController != null){
                    tableFacebookController.setInformation(index,facebook.getFacebookID(),facebook.getName(), facebook.isStatus(), facebook.getType());
                }
                listAccounts.add(item);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return listAccounts;
    }
}
