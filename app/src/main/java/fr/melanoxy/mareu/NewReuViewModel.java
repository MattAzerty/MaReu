package fr.melanoxy.mareu;


import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class NewReuViewModel extends BaseObservable {

    // creating object of Model class
    private ReunionModel mReunionModel;

    // getter and setter methods
    // for place variable

    @Bindable
    public int getPlace() {
        return mReunionModel.getPlace();
    }

    public void setPlace(int place) {
        mReunionModel.setPlace(place);
        notifyPropertyChanged(BR.place);
    }


    // getter and setter methods
    // for subject variable

    @Bindable
    public String getSubject() {
        return mReunionModel.getSubject();
    }

    public void setSubject(String subject) {
        mReunionModel.setSubject(subject);
        notifyPropertyChanged(BR.subject);
    }

    // getter and setter methods
    // for people variable

    @Bindable
    public int getYear() {
        return mReunionModel.getYear();
    }

    public void setYear(int year) {
        mReunionModel.setYear(year);
        notifyPropertyChanged(BR.year);
    }

    @Bindable
    public int getMonth() {
        return mReunionModel.getMonth();
    }

    public void setMonth(int month) {
        mReunionModel.setYear(month);
        notifyPropertyChanged(BR.month);
    }

    @Bindable
    public int getDay() {
        return mReunionModel.getDay();
    }

    public void setDay(int day) {
        mReunionModel.setDay(day);
        notifyPropertyChanged(BR.day);
    }

    @Bindable
    public int getHour() {
        return mReunionModel.getHour();
    }

    public void setHour(int hour) {
        mReunionModel.setYear(hour);
        notifyPropertyChanged(BR.hour);
    }

    @Bindable
    public int getMinute() {
        return mReunionModel.getMinute();
    }

    public void setMinute(int minute) {
        mReunionModel.setYear(minute);
        notifyPropertyChanged(BR.minute);
    }




    // getter and setter methods
    // for people variable

    @Bindable
    public String getPeople() {
        return mReunionModel.getPeople();
    }

    public void setPeople(String people) {
        mReunionModel.setPeople(people);
        notifyPropertyChanged(BR.people);
    }


    // constructor of ViewModel class
    public NewReuViewModel() {

        // instantiating object of
        // model class
        mReunionModel = new ReunionModel(0,0,0,0,0,0,"","");
    }

    /*/public void onDateChanged(int year,int month,int day,int hour,int minute) {
        Log.v("Test_Picker", "$year $month $day $hour $minute");
    }*/

}
