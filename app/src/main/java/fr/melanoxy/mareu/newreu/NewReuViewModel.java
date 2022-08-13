package fr.melanoxy.mareu.newreu;




import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.melanoxy.mareu.events.SingleLiveEvent;
import fr.melanoxy.mareu.repo.ReunionRepository;

public class NewReuViewModel extends ViewModel {

//INIT


    // Injected thanks to ViewModelFactory
    @NonNull
    private final ReunionRepository reunionRepository;

    // Default value for create reu is false : button should not be enabled at start
    private final MutableLiveData<Boolean> isAddButtonEnabledMutableLiveData = new MutableLiveData<>(false);


    //close activity SingleLiveEvent
    // Check https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
    private final SingleLiveEvent<Void> closeActivitySingleLiveEvent = new SingleLiveEvent<>();




//CONSTRUCTOR

    public NewReuViewModel(@NonNull ReunionRepository reunionRepository) {
        this.reunionRepository = reunionRepository;
    }

    // getter for isSaveButtonEnable? Returns a LiveData, not a MutableLiveData. This is an extra security (ask about "immutability" your mentor)
    public LiveData<Boolean> getIsAddButtonEnabledLiveData() {
        return isAddButtonEnabledMutableLiveData;
    }

    //getter for closeActivitySingleLiveEvent
    public SingleLiveEvent<Void> getCloseActivitySingleLiveEvent() {
        return closeActivitySingleLiveEvent;
    }

    //method called to set CreateButton state
    public void onSubjectChanged(String subject) {
        isAddButtonEnabledMutableLiveData.setValue(!subject.isEmpty());
    }

    public void onAddButtonClicked(
            @NonNull String date,
            @Nullable String place,
            @Nullable String subject,
            @Nullable String people
    ) {
        // Add the reunion to the repository...
        reunionRepository.addReunion(date, place, subject, people);
        // ... and close the Activity !
        closeActivitySingleLiveEvent.call();
    }

    public void onCancelButtonClicked() {
        // Close the Activity
        closeActivitySingleLiveEvent.call();
    }

}
