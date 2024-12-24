package com.example.selenium.service.account_youtube;

import com.example.selenium.common.Directory;
import com.example.selenium.common.EncryptRSA;
import com.example.selenium.common.Mapper;
import com.example.selenium.config.ChromeOptionsConfig;
import com.example.selenium.constants.CurrentDirectory;
import com.example.selenium.controller.TableYoutubeController;
import com.example.selenium.model.Youtube;
import com.example.selenium.pojo.AccountSocial;
import com.example.selenium.selenium.YoutubeSelenium;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

public class AccountYoutubeService implements IAccountYoutubeService{

    private YoutubeSelenium youtubeSelenium;

    public AccountYoutubeService() {
        youtubeSelenium = new YoutubeSelenium();
    }

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

    @Override
    public boolean loginAccountYoutube(AccountSocial account) throws InterruptedException {
        String profileName = "Profile " + new Date().getTime();
        ChromeOptions options = new ChromeOptionsConfig().setupOptionsChrome("Youtube",profileName);
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        boolean isLogin = youtubeSelenium.loginYoutubeAccount(driver,account);
        if(!isLogin){
            String path = "D:\\Youtube\\ChromeProfile\\Youtube\\" + profileName;
            if(!Directory.deleteDirectory(path)) System.out.println("Delete profile unsuccessful!");
            return false;
        }
        account.setProfile(profileName.trim());
        account.setStatus("ACTIVE");
        String encryptInformation = Mapper.mapAccountYoutubeToString(account);
        EncryptRSA.encryption(encryptInformation,
                new File(CurrentDirectory.currentDirectoryYoutube + ((com.example.selenium.pojo.Youtube)account).getChanelID() + ".dat")
        );
        return true;
    }
}
