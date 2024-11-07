package com.example.selenium.service.login;

import com.example.selenium.pojo.Account;

public interface ILoginService {

    public Account loginByAccessToken(String accessToken);

}
