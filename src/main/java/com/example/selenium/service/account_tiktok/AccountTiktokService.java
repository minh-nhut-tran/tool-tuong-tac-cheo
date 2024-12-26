package com.example.selenium.service.account_tiktok;

import com.example.selenium.common.Directory;
import com.example.selenium.common.EncryptRSA;
import com.example.selenium.common.FileHandler;
import com.example.selenium.common.Mapper;
import com.example.selenium.config.ChromeOptionsConfig;
import com.example.selenium.constants.CurrentDirectory;
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

import java.io.File;
import java.io.IOException;
import java.util.*;

public class AccountTiktokService implements IAccountTiktokService{

    private final TiktokSelenium tiktokSelenium;

    public AccountTiktokService() {
        tiktokSelenium = new TiktokSelenium();
    }

    @Override
    public List<HBox>  loadAccountTiktok() {

        List<Tiktok> accounts = new ArrayList<>();
        getAllAccountTiktok().forEach( accountSocial -> {
            accounts.add( new Tiktok(
                    ((com.example.selenium.pojo.Tiktok)accountSocial).getTiktokID(),
                    ((com.example.selenium.pojo.Tiktok)accountSocial).getName(),
                    accountSocial.getStatus().equals("ACTIVE")
            ));
        });

        List<HBox> listAccounts = new ArrayList<>();
        try {
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
    public boolean loginAccountTiktok(AccountSocial account, String typeLogin) throws InterruptedException {
        String profileName = "Profile " + new Date().getTime();
        ChromeOptions options = new ChromeOptionsConfig().setupOptionsChrome("Tiktok",profileName);
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        boolean isLogin = tiktokSelenium.loginTiktokAccountOnGoogle(driver,account, typeLogin);
        if(!isLogin){
            String path = "D:\\Youtube\\ChromeProfile\\Tiktok\\" + profileName;
            if(!Directory.deleteDirectory(path)) System.out.println("Delete profile unsuccessful!");
            return false;
        }
        account.setProfile(profileName.trim());
        account.setStatus("ACTIVE");
        String encryptInformation = Mapper.mapAccountTiktokToString(account);
        EncryptRSA.encryption(encryptInformation,
                new File(CurrentDirectory.currentDirectoryTiktok + ((com.example.selenium.pojo.Tiktok)account).getTiktokID() + ".dat")
        );
        return true;
    }

    @Override
    public void deleteAccountTiktok(String tiktokID)  {
        String pathProfile = "D:\\Youtube\\ChromeProfile\\Tiktok\\" + getAccountTiktok(tiktokID).getProfile();
        if(!Directory.deleteDirectory(pathProfile)) System.out.println("Delete profile account unsuccessful!");
        String pathInformation = CurrentDirectory.currentDirectoryTiktok + tiktokID+".dat";
        if(!FileHandler.deleteFile(pathInformation)) System.out.println("Delete account unsuccessful!");
    }

    @Override
    public void setStatus(String tiktokID, String status)  {
        AccountSocial accountTiktok =  Mapper.mapStringToAccountTiktok(
                EncryptRSA.decryption(FileHandler.readFile(
                        new File( CurrentDirectory.currentDirectoryTiktok + tiktokID+".dat"))));
        accountTiktok.setStatus(status);
        String encryptInformation = Mapper.mapAccountTiktokToString(accountTiktok);
        EncryptRSA.encryption(encryptInformation,
                new File(CurrentDirectory.currentDirectoryTiktok + tiktokID+".dat")
        );
    }

    @Override
    public List<AccountSocial> getAllAccountTiktok(){
        List<AccountSocial> accounts = new ArrayList<>();
        Objects.requireNonNull(FileHandler.getAllFileOnDirectory(CurrentDirectory.currentDirectoryTiktok)).forEach(
                file -> {
                    if(file.exists() && file.getName().endsWith(".dat")){
                        AccountSocial account = Mapper.mapStringToAccountTiktok(
                                EncryptRSA.decryption(FileHandler.readFile(file))
                        );
                        accounts.add(account);
                    }
                });
        return accounts;
    }

    public AccountSocial getAccountTiktok(String tiktokID){
        return Mapper.mapStringToAccountTiktok(
                EncryptRSA.decryption(
                        FileHandler.readFile(
                                new File(CurrentDirectory.currentDirectoryTiktok+tiktokID+".dat"))));
    }


}
