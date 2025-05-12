package model;

import java.sql.Date;
import java.time.Duration;

public class Plano {

    private final int id;
    private String name;
    private String user_space;
    private Duration duration;
    private Date aquisition_date;

    public Plano(int id, String name, String user_space, Duration duration, Date date){
        this.id = id;
        this.name = name;
        this.user_space = user_space;
        this.duration = duration;
        this.aquisition_date = date;
    }
}