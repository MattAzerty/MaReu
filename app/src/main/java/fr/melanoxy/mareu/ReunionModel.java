package fr.melanoxy.mareu;

import androidx.annotation.Nullable;

public class ReunionModel {

    @Nullable
    String subject,people;
    int place;
    int year;
    int month;
    int day;
    int hour;
    int minute;



    // constructor to initialize
    // the variables
    public ReunionModel(int place, int year, int month, int day, int hour, int minute, String subject, String people){
        this.place = place;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.subject = subject;
        this.people = people;
    }

    // getter and setter methods
    // for place variable

    @Nullable
    public int getPlace() {
        return place;
    }

    public void setPlace(@Nullable int place) {
        this.place = place;
    }

    // getter and setter methods
    // for Year variable

    @Nullable
    public int getYear() {
        return year;
    }

    public void setYear(@Nullable int year) {
        this.year = year;
    }

    // getter and setter methods
    // for Month variable

    @Nullable
    public int getMonth() {
        return month;
    }

    public void setMonth(@Nullable int month) {
        this.month = month;
    }

    // getter and setter methods
    // for Day variable

    @Nullable
    public int getDay() {
        return day;
    }

    public void setDay(@Nullable int day) {
        this.day = day;
    }

    // getter and setter methods
    // for Hour variable

    @Nullable
    public int getHour() {
        return hour;
    }

    public void setHour(@Nullable int hour) {
        this.hour = hour;
    }

    // getter and setter methods
    // for Minute variable

    @Nullable
    public int getMinute() {
        return minute;
    }

    public void setMinute(@Nullable int minute) {
        this.minute = minute;
    }

    // getter and setter methods
    // for subject variable

    @Nullable
    public String getSubject() {
        return subject;
    }

    public void setSubject(@Nullable String subject) {
        this.subject = subject;
    }


    @Nullable
    public String getPeople() {
        return people;
    }

    public void setPeople(@Nullable String people) {
        this.people = people;
    }


}
