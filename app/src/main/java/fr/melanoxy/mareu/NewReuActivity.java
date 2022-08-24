package fr.melanoxy.mareu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.melanoxy.mareu.newreu.DialogFragment;
import fr.melanoxy.mareu.databinding.ActivityNewReuBinding;
import fr.melanoxy.mareu.newreu.NewReuViewModel;
import fr.melanoxy.mareu.utils.ViewModelFactory;

public class NewReuActivity extends AppCompatActivity {

    // Initialize variables
    private ActivityNewReuBinding mNewReuBinding;
    private NewReuViewModel mViewModel;
    private String editedPeople ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //full screen mode

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_new_reu);

        /*getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);*/

        //viewBinding
        mNewReuBinding = ActivityNewReuBinding.inflate(getLayoutInflater());
        View view = mNewReuBinding.getRoot();
        setContentView(view);


        //Associating ViewModel with the Activity
        NewReuViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(NewReuViewModel.class);


//Bind data with viewModel NewReuViewModel and viewBinding


        //SingleLiveEvent to close the activity
        viewModel.getCloseActivitySingleLiveEvent().observe(this, aVoid -> finish());

        //Spinner listener
        bindDate(viewModel);
        //TextChanged listener
        bindSubject(viewModel);
        bindPeople(viewModel);
        //ClickListener
        bindCancelButton(viewModel);
        //ConstantConditions
        bindAddButton(viewModel);


        mNewReuBinding.newReuAddPeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment dialog = new DialogFragment();
                dialog.show(getFragmentManager(),
                        "MyCustomDialog");

            }
        });

/*
        mNewReuBinding.newReuRemovePeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get people input
                String mAllPeople=mNewReuBinding.people.getEditableText().toString();

                if(mAllPeople.contains(initPeople)) {
                    editedPeople = "";
                    mNewReuBinding.people.setText(initPeople);

                }else{
                    String[] people = mAllPeople.split("\n");

                    for (int i = 0; i < people.length - 1; i++) {
                        editedPeople = editedPeople + people[i] + "\n";
                    }

                    mNewReuBinding.people.setText(editedPeople.trim());

                    editedPeople = "";
                } }
        });*/


    }

    private void bindDate(NewReuViewModel viewModel) {

        SingleDateAndTimePicker.OnDateChangedListener changeListener = (displayed, date) -> viewModel.onDateChanged(date);
        mNewReuBinding.newReuSingleDayPicker.addOnDateChangedListener(changeListener);

        viewModel.getDateLiveData().observe(this, date -> mNewReuBinding.newReuSingleDayPicker.setDefaultDate(date));
    }

    private void bindSubject(NewReuViewModel viewModel) {
        mNewReuBinding.subject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.onSubjectChanged(s.toString());
            }
        });
    }

    private void bindPeople(NewReuViewModel viewModel) {

        viewModel.getPeopleLiveData().observe(this, people -> mNewReuBinding.people.setText(people));
    }

    private void bindCancelButton(NewReuViewModel viewModel) {
        mNewReuBinding.newReuCancelReuButton.setOnClickListener(v -> viewModel.onCancelButtonClicked());
    }


    private void bindAddButton(NewReuViewModel viewModel) {

        mNewReuBinding.newReuAddReuButton.setOnClickListener(v -> viewModel.onAddButtonClicked(
                getDateSelected(),
                getSelectedItem(),
                mNewReuBinding.subject.getText().toString(),
                mNewReuBinding.people.getText().toString()
        ));
        viewModel.getIsAddButtonEnabledLiveData().observe(this, isAddButtonEnabled -> mNewReuBinding.newReuAddReuButton.setEnabled(isAddButtonEnabled));
    }

    private String getDateSelected(){

        Date date = mNewReuBinding.newReuSingleDayPicker.getDate();
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");  ;
        String strDate = dateFormat.format(date);
        return strDate;

    }

    private String getSelectedItem(){

        String state = mNewReuBinding.newReuSpinnerPlace.getSelectedItem().toString();
        return state;
    }

}