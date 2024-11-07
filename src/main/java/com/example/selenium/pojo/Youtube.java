package com.example.selenium.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Youtube extends AccountSocial{
    private String chanelID;
    private String name;
}
