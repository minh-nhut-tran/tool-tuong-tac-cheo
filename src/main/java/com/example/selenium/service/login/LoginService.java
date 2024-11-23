package com.example.selenium.service.login;


import com.example.selenium.common.EncryptRSA;
import com.example.selenium.common.FileHandler;
import com.example.selenium.common.PairKeyRSA;
import com.example.selenium.constants.CurrentDirectory;
import com.example.selenium.model.User;
import com.example.selenium.pojo.Account;
import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;



public class LoginService implements ILoginService {
    public String readResponseFromRequest(String accessToken ){
        StringBuilder response = new StringBuilder();
        String cookie = null;
        try {
            HttpURLConnection connection = getHttpURLConnection(accessToken);
            cookie = connection.getHeaderField("Set-cookie");
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            BufferedReader in = new BufferedReader(reader);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                 response.append(inputLine);
            }
            in.close();
            connection.disconnect();
        } catch (Exception ignored) {

        }
        return response +"|" + cookie;
    }
    private static HttpURLConnection getHttpURLConnection(String accessToken) throws IOException {
        URL url = new URL(com.example.selenium.constants.URL.URL_LOGIN);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setDoOutput(true);
        String urlParameters = "access_token="+ accessToken;
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = urlParameters.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        return connection;
    }



    public Account loginByAccessToken(String accessToken) {
        Account account = new Account();
        account.setActive(false);
        try {
            String[] responseData = readResponseFromRequest(accessToken).split("\\|");
            Gson gson = new Gson();
            User user = gson.fromJson(responseData[0], User.class);
            if (user.getStatus().equals("success")) {
                account.setSession(responseData[1].split(";")[0].trim());
                account.setAccessToken(accessToken);
                account.setBalance(user.getData().getSodu());
                account.setUser(user.getData().getUser());
                account.setActive(true);

                if (!FileHandler.checkFileExist("publicKey.rsa", CurrentDirectory.currentDirectoryFacebook)
                        && !FileHandler.checkFileExist("privateKey.rsa", CurrentDirectory.currentDirectoryFacebook)) {
                    PairKeyRSA.securityKeyPairGenerate();
                }
                if (!FileHandler.checkFileExist("accessToken.dat", CurrentDirectory.currentDirectoryAccessToken)) {
                    EncryptRSA.encryption(accessToken,
                            new File(CurrentDirectory.currentDirectoryAccessToken + "accessToken.dat")
                    );
                }
                // save access token
                return account;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public String getAccessTokenAvailable() {
        if (!FileHandler.checkFileExist("accessToken.dat", CurrentDirectory.currentDirectoryAccessToken)) return null;
        return EncryptRSA.decryption(
                FileHandler.
                        readFile(
                                new File(CurrentDirectory.currentDirectoryAccessToken + "accessToken.dat")
                        )
        );
    }


}
