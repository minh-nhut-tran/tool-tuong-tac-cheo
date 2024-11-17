package com.example.selenium.service.account_facebook;

import com.example.selenium.common.*;
import com.example.selenium.config.ChromeOptionsConfig;
import com.example.selenium.constants.CurrentDirectory;
import com.example.selenium.controller.TableFacebookController;
import com.example.selenium.model.Facebook;
import com.example.selenium.pojo.AccountSocial;
import com.example.selenium.selenium.FacebookSelenium;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class AccountFacebookService implements IAccountFacebookService{

    private final FacebookSelenium facebookSelenium;

    public AccountFacebookService(){
        facebookSelenium = new FacebookSelenium();
    }


    @Override
    public List<HBox> loadAccountFacebook() {
        List<Facebook> accounts = new ArrayList<>();
        getAllAccountFacebook().forEach(accountSocial -> {
            accounts.add( new Facebook(
                    ((com.example.selenium.pojo.Facebook)accountSocial).getFacebookID(),
                    ((com.example.selenium.pojo.Facebook)accountSocial).getName(),
                    accountSocial.getStatus().equals("ACTIVE"),
                    ((com.example.selenium.pojo.Facebook) accountSocial).getType()
            ));
        });
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

    @Override
    public boolean loginAccountFacebook(AccountSocial account) throws InterruptedException {
        String profileName = "Profile " + new Date().getTime();
        ChromeOptions options = new ChromeOptionsConfig().setupOptionsChrome("Facebook",profileName);
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        boolean isLogin = facebookSelenium.loginAutomationFacebookOnGoogle(driver,account);
        if(!isLogin){
            DirectWindows.closeAllTab(driver);
            Thread.sleep(100);
            String path = "D:\\Youtube\\ChromeProfile\\Facebook\\" + profileName;
            if(!Directory.deleteDirectory(path)) System.out.println("Delete profile unsuccessful!");
            return false;
        }
        account.setProfile(profileName.trim());
        account.setStatus("ACTIVE");
        String encryptInformation = Mapper.mapAccountFacebookToString(account);
        EncryptRSA.encryption(encryptInformation,
               new File(CurrentDirectory.currentDirectoryFacebook + ((com.example.selenium.pojo.Facebook)account).getFacebookID() + ".dat")
        );
        DirectWindows.closeAllTab(driver);
        return true;
    }

    public List<AccountSocial> getAllAccountFacebook(){
        List<AccountSocial> accountSocials = new ArrayList<>();
        Objects.requireNonNull(FileHandler.getAllFileOnDirectory(CurrentDirectory.currentDirectoryFacebook)).forEach(
                file -> {
                    if(file.exists() && file.getName().endsWith(".dat")){
                        AccountSocial accountSocial =  Mapper.mapStringToAccountFacebook(EncryptRSA.decryption(FileHandler.readFile(file)));
                        accountSocials.add(accountSocial);
                    }
                }
        );
        return accountSocials;
    }


}
