package fr.melanoxy.mareu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import fr.melanoxy.mareu.NewReuFragment.DialogFragment;
import fr.melanoxy.mareu.databinding.ActivityNewReuBinding;

public class NewReuActivity extends AppCompatActivity implements DialogFragment.OnInputListener{

    // Initialize variables
    private ActivityNewReuBinding mNewReuBinding;
    private NewReuViewModel mViewModel;
    public String mInput;
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
        mNewReuBinding.setViewModel(new NewReuViewModel());
        mNewReuBinding.executePendingBindings();

        //mViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(NewReuViewModel.class);

//change programmatically a variable
        mNewReuBinding.getViewModel().setPeople("nom1@entreprise.com;\nnom2@entreprise.com;\nnom3@entreprise.com;");


        mNewReuBinding.newReuAddPeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                String myMessage = mNewReuBinding.people.getEditableText().toString();
                bundle.putString("message", myMessage );
                DialogFragment fragInfo = new DialogFragment();
                fragInfo.setArguments(bundle);
                fragInfo.show(getFragmentManager(),
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


    private void setPeopleToTextView()
    {

        String mPeopleText=mNewReuBinding.people.getEditableText().toString();

        // Check condition
        if(mPeopleText.contains("nom1@entreprise.com;\nnom2@entreprise.com;\nnom3@entreprise.com;"))
        {
            // Text is in initial state
            mNewReuBinding.people.setText(mInput);
        }
        else
        {
            // when text is not in initial state

            mNewReuBinding.people.setText(mPeopleText+"\n"+mInput);
        }
    }
}