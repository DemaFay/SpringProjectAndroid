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

    private Date dateOfBirth;

    private String sex;

    private String maritalStatus;

    private String education;

    private String target;

    private List<HighSchool> highSchools;

    private List<AdditionalEducation> additionalEducations;

    private List<Experience> experiences;

    private List<Lecture> lectures;

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<HighSchool> getHighSchools() {
        return highSchools;
    }

    public void setHighSchools(List<HighSchool> highSchools) {
        this.highSchools = highSchools;
    }

    public List<AdditionalEducation> getAdditionalEducations() {
        return additionalEducations;
    }

    public void setAdditionalEducations(List<AdditionalEducation> additionalEducations) {
        this.additionalEducations = additionalEducations;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }
}
