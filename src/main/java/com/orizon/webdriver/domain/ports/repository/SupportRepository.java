package com.orizon.webdriver.domain.ports.repository;

import com.orizon.webdriver.domain.model.Support;

public interface SupportRepository {
    void addSupport(Support supportRequest);
    void checkSupport(long id);
    void getAllSupports();
}
