package com.example.selenium.service.account_tiktok;

import com.example.selenium.config.ChromeOptionsConfig;
import com.example.selenium.controller.TableTiktokController;
import com.example.selenium.model.Tiktok;
import com.example.selenium.pojo.AccountSocial;
import com.example.selenium.selenium.TiktokSelenium;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

public class AccountTiktokService implements IAccountTiktokService{

    private TiktokSelenium tiktokSelenium;

    public AccountTiktokService() {
        tiktokSelenium = new TiktokSelenium();
    }

    @Override
    public List<HBox>  loadAccountTiktok() {

        List<Tiktok> accounts = new ArrayList<>();
        accounts.add(new Tiktok("tran_minh_nhut","Tran Minh Nhut",true));
        accounts.add(new Tiktok("nguyen_nha","Nguyen Thi Thanh Nha",true));

        List<HBox> listAccounts = new ArrayList<>();
        try {
           // FXMLLoader loader = new FXMLLoader(getClass().getResource("../../account-tiktok-view.fxml"));
            //VBox listItems = loader.load();

            ListIterator<Tiktok> iterator = accounts.listIterator();
            while(iterator.hasNext()){
                Tiktok tiktok = iterator.next();
                int index = iterator.previousIndex();

                FXMLLoader loaderItem = new FXMLLoader(getClass().getResource("../../table-tiktok-view.fxml"));
                HBox item = loaderItem.load();

                TableTiktokController tableTiktokController = loaderItem.getController();
                if(tableTiktokController != null){
                    tableTiktokController.setInformation(index, tiktok.getTiktokID(), tiktok.getName(),tiktok.isStatus());
                }

                listAccounts.add(item);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return listAccounts;
    }

    @Override
    public boolean loginAccountTiktok(AccountSocial account) throws InterruptedException {
        String profileName = "Profile " + new Date().getTime();
        ChromeOptions options = new ChromeOptionsConfig().setupOptionsChrome("Tiktok",profileName);
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        boolean isLogin = tiktokSelenium.loginTiktokAccountOnGoogle(driver,account);
        return false;
    }
}
