package com.androidapp.demafayz.aberoy.network.entitys;

/**
 * Created by DemaFayz on 27.11.2016.
 */

public class Lecture {

    private Long id;

    private Lecturer lecturer;

    private String title;

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

