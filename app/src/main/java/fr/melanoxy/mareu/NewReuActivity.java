package fr.melanoxy.mareu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import fr.melanoxy.mareu.NewReuFragment.DialogFragment;
import fr.melanoxy.mareu.databinding.ActivityNewReuBinding;
import fr.melanoxy.mareu.databinding.FragmentDialogBinding;

public class NewReuActivity extends AppCompatActivity implements DialogFragment.OnInputListener{

    // Initialize variables
    private ActivityNewReuBinding mNewReuBinding;
    private NewReuViewModel viewModel;
    public String mInput;
    private String initPeople = "nom1@entreprise.com;\nnom2@entreprise.com;\nnom3@entreprise.com;";
    private String editedPeople ="";

    @Override public void sendInput(String input)
    {
        mInput = input.trim()+";";
        setPeopleToTextView();
    }


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

// ViewModel updates the Model after observing changes in the View
        mNewReuBinding= DataBindingUtil.setContentView(this,R.layout.activity_new_reu);

       // viewModel = new ViewModelProvider(this).get(NewReuViewModel.class);

        // model will also update the view
        // via the ViewModel
        //mNewReuBinding.setViewModel(new NewReuViewModel());
        //mNewReuBinding.executePendingBindings();

/*
        mNewReuBinding.sujet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get text from edit text
                String sText=mNewReuBinding.sujet.getText().toString().trim();

                // Check condition
                if(!sText.equals(""))
                {
                    // when text is not empty
                    // set text on text view
                    mNewReuBinding.sujet.setText(sText);
                }
                else
                {
                    // When text is empty
                    // Display Toast
                    Toast.makeText(getApplicationContext()
                            ,"Please enter text",Toast.LENGTH_SHORT).show();
                }
            }
        });*/


        mNewReuBinding.newReuAddPeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open Dialog fragment
                DialogFragment dialog
                        = new DialogFragment();
                dialog.show(getFragmentManager(),
                        "MyCustomDialog");

            }
        });

        mNewReuBinding.newReuRemovePeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get people input
                String mAllPeople=mNewReuBinding.people.getEditableText().toString();

                String[] people = mAllPeople.split("\n");

                for (int i=0; i<people.length-1; i++ ){
                    editedPeople = editedPeople + people[i]+"\n";
                }

                mNewReuBinding.people.setText(editedPeople.trim());

                editedPeople="";
            }
        });


    }

    /*@BindingAdapter("people")
    public static void setPeople(View view,String people) {
        //mNewReuBinding.people.setText(initPeople);
            Toast.makeText(view.getContext(), people, Toast.LENGTH_SHORT).show();
    }*/



    private void setPeopleToTextView()
    {

        String mPeopleText=mNewReuBinding.people.getEditableText().toString();

        // Check condition
        if(!mPeopleText.equals(""))
        {
            // when text is not in initial state
            // set a new people on text view
            mNewReuBinding.people.setText(mPeopleText+"\n"+mInput);
        }
        else
        {
            // Text is in initial state
            // Make a new field for people
            mNewReuBinding.people.setText(mInput);
        }

    }


}