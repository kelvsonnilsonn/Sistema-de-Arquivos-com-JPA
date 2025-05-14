package com.orizon.webdriver.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.Duration;

@Getter
@Setter
public class Plan {

    private long id;
    private String name;
    private String userSpace;
    private Duration duration;
    private Date aquisitionDate;

    public Plan(String name, String userSpace){
        this.name = name;
        this.userSpace = userSpace;
    }

}