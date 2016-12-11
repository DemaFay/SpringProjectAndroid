package com.androidapp.demafayz.aberoy.utils;

import com.androidapp.demafayz.aberoy.network.entitys.Lecturer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DemaFayz on 12.11.2016.
 */
public class Dummy {

    public static Lecturer getLecturer() {
        Lecturer lecturer = new Lecturer();
        lecturer.setId(1l);
        lecturer.setFirstName("First name");
        lecturer.setLastName("Last name");
        lecturer.setDescription("Test Description");
        return lecturer;
    }
}
