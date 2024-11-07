package com.example.selenium.pojo;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Tiktok extends AccountSocial{
    private String tiktokID;
    private String name;

}
