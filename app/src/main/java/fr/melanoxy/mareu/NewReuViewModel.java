package fr.melanoxy.mareu;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class NewReuViewModel extends BaseObservable {

    // creating object of Model class
    private ReunionModel mReunionModel;

    /*//encapsulated ReunionModel inside a LiveData. Every time ReunionModel object
    // is changed it will be observed in the Activity and the appropriate action would be taken.
    private MutableLiveData<ReunionModel> reuMutableLiveData;

    LiveData<ReunionModel> getReunion() {
        if (reuMutableLiveData == null) {
            reuMutableLiveData = new MutableLiveData<>();
        }

        return reuMutableLiveData;
    }*/


    // getter and setter methods
    // for place variable

    @Bindable
    public String getPlace() {
        return mReunionModel.getPlace();
    }

    public void setPlace(String place) {
        mReunionModel.setPlace(place);
        notifyPropertyChanged(BR.place);
    }

    // getter and setter methods
    // for date variable

    @Bindable
    public String getDate() {
        return mReunionModel.getDate();
    }

    public void setDate(String date) {
        mReunionModel.setDate(date);
        notifyPropertyChanged(BR.date);
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
    public String getPeople() {
        return mReunionModel.getPeople();
    }

    public void setPeople(String people) {
        mReunionModel.setSubject(people);
        notifyPropertyChanged(BR.people);
    }



    // constructor of ViewModel class
    public NewReuViewModel() {

        // instantiating object of
        // model class
        mReunionModel = new ReunionModel("","","","");
    }

}
