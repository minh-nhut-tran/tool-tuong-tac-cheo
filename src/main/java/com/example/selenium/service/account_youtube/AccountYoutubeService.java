package com.example.selenium.service.account_youtube;

import com.example.selenium.controller.TableYoutubeController;
import com.example.selenium.model.Youtube;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class AccountYoutubeService implements IAccountYoutubeService{
    @Override
    public List<HBox> loadAccountYoutube() {
        List<Youtube> accounts = new ArrayList<>();
        accounts.add(new Youtube("CHANEL_ID0001","Chanel Minh Nhut",true));
        accounts.add(new Youtube("CHANEL_ID0002","Chanel Thanh Nha",true));

        List<HBox> listAccounts = new ArrayList<>();
        try {

            ListIterator<Youtube> iterator = accounts.listIterator();
            while(iterator.hasNext()){
                Youtube youtube = iterator.next();
                int index = iterator.previousIndex();

                FXMLLoader loaderItem = new FXMLLoader(getClass().getResource("../../table-youtube-view.fxml"));
                HBox item = loaderItem.load();

                TableYoutubeController tableYoutubeController = loaderItem.getController();
                if(tableYoutubeController != null){
                    tableYoutubeController.setInformation(index, youtube.getChanelID(), youtube.getName(), youtube.isStatus());
                }

                listAccounts.add(item);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return listAccounts;
    }
}
