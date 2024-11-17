package com.example.selenium.repository.AccountRepository;


import com.example.selenium.pojo.AccountSocial;

public interface IAccountRepository {

    public boolean save(AccountSocial account);

    public boolean delete(AccountSocial account);


}
