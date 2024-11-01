package com.example.selenium.service;

import com.example.selenium.pojo.Account;

public interface ILoginService {

    public Account loginByAccessToken(String accessToken);

}
