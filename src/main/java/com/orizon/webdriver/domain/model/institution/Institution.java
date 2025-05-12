package com.orizon.webdriver.domain.model.institution;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@AllArgsConstructor
public class Institution {
    private final int id;
    private String name;
    private String socialCause;
    private int fkUser;
    private Address address;

}
