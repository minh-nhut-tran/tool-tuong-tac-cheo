package com.example.selenium.service.login;


import com.example.selenium.common.EncryptRSA;
import com.example.selenium.common.FileHandler;
import com.example.selenium.common.PairKeyRSA;
import com.example.selenium.constants.CurrentDirectory;
import com.example.selenium.model.User;
import com.example.selenium.pojo.Account;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import java.io.File;
import java.util.List;


public class LoginService implements ILoginService{

        private static final String URL_LOGIN = "https://tuongtaccheo.com/logintoken.php";

        public Account loginByAccessToken(String accessToken)  {
            Account account = new Account();
            account.setActive(false);
            try {
                //Call API login by access token
                HttpResponse<String> response = Unirest.post(URL_LOGIN)
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .field("access_token",accessToken)
                        .asString();

                //Get body response
                String body = response.getBody();
                Gson gson = new Gson();
                User user = gson.fromJson(body,User.class);
                if(user.getStatus().equals("success")){
                    //Get cookie on headers response
                    List<String> cookies  = response.getHeaders().get("Set-Cookie");
                    account.setSession(cookies.get(0).split(";")[0].trim());
                    account.setAccessToken(accessToken);
                    account.setBalance(user.getData().getSodu());
                    account.setUser(user.getData().getUser());
                    account.setActive(true);

                    if(!FileHandler.checkFileExist("publicKey.rsa", CurrentDirectory.currentDirectoryFacebook)
                            && !FileHandler.checkFileExist("privateKey.rsa",CurrentDirectory.currentDirectoryFacebook)){
                        PairKeyRSA.securityKeyPairGenerate();
                    }
                    if(!FileHandler.checkFileExist("accessToken.dat",CurrentDirectory.currentDirectoryAccessToken)){
                        EncryptRSA.encryption(accessToken,
                                new File(CurrentDirectory.currentDirectoryAccessToken+ "accessToken.dat")
                        );
                    }
                    // save access token
                    return account;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return account;
        }

    @Override
    public String getAccessTokenAvailable() {
        if(!FileHandler.checkFileExist("accessToken.dat",CurrentDirectory.currentDirectoryAccessToken)) return null;
        return EncryptRSA.decryption(
                FileHandler.
                        readFile(
                                new File(CurrentDirectory.currentDirectoryAccessToken+"accessToken.dat")
                        )
        );
    }


}
