package fr.melanoxy.mareu;

import androidx.annotation.Nullable;

public class ReunionModel {

    @Nullable
    String place,date,subject,people;

    // constructor to initialize
    // the variables
    public ReunionModel(String place, String date, String subject, String people){
        this.place = place;
        this.date = date;
        this.subject = subject;
        this.people = people;
    }

    // getter and setter methods
    // for place variable

    @Nullable
    public String getPlace() {
        return place;
    }

    public void setPlace(@Nullable String place) {
        this.place = place;
    }



    // getter and setter methods
    // for date variable

    @Nullable
    public String getDate() {
        return date;
    }

    public void setDate(@Nullable String date) {
        this.date = date;
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
