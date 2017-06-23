package de.java2enterprise.onlineshop;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class TestController implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<String> days;

    private String day;

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @PostConstruct
    public void init() {
        days = new ArrayList<>();
        days.add("Montag");
        days.add("Dienstag");
        days.add("Mittwoch");
        days.add("Donnerstag");
        days.add("Freitag");
    }
}
