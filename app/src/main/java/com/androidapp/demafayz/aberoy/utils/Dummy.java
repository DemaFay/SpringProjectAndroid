package com.androidapp.demafayz.aberoy.utils;

import com.androidapp.demafayz.aberoy.network.entitys.Lecture;
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
//        lecturer.setAge(23);

        List<Lecture> lectures = new ArrayList<Lecture>();
        lectures.add(getLecture(null));
        lectures.add(getLecture(null));

        lecturer.setLectures(lectures);
        return lecturer;
    }

    public static Lecture getLecture(Lecturer lecturer) {
        Lecture lecture = new Lecture();
        lecture.setId(1);
        lecture.setTitle("Lecture title");
        lecture.setLecturer(lecturer);
        return lecture;
    }
}
