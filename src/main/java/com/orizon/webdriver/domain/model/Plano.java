package com.orizon.webdriver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.Duration;

@Getter
@Setter
@AllArgsConstructor
public class Plano {

    private final int id;
    private String name;
    private String userSpace;
    private Duration duration;
    private Date aquisitionDate;

}