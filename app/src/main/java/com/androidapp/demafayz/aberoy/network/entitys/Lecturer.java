package com.androidapp.demafayz.aberoy.network.entitys;

/**
 * Created by DemaFayz on 12.11.2016.
 */
import java.util.Date;
import java.util.List;

public class Lecturer {

    private Long id;

    private String firstName;

    private String lastName;

    private String patronymic;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
