package com.example.selenium.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Facebook extends AccountSocial{

    private String facebookID;
    private String name;
    private String type;

}
