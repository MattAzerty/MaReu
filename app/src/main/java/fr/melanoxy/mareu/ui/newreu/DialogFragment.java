package fr.melanoxy.mareu.ui.newreu;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

import fr.melanoxy.mareu.ui.NewReuActivity;
import fr.melanoxy.mareu.R;

public class DialogFragment extends android.app.DialogFragment {

    private NewReuViewModel model;

    public interface OnInputListener {
        void sendInput(String input);
    }

    public OnInputListener mOnInputListener;

    private EditText mInput;
    private TextView mActionOk, mActionCancel, errorField;
    private NewReuActivity mNewReuActivity;
    private String previousEntry;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_dialog, container, false);
        mActionCancel
                = view.findViewById(R.id.action_cancel);
        mActionOk = view.findViewById(R.id.action_ok);
        mInput = view.findViewById(R.id.input);
        errorField = view.findViewById(R.id.error_message);

        //ViewModel of NewReuActivity associated here
        mNewReuActivity = (NewReuActivity) getActivity();
        NewReuViewModel viewModel = new ViewModelProvider(mNewReuActivity).get(NewReuViewModel.class);


        viewModel.getPeopleLiveData().observe(mNewReuActivity, people -> previousEntry = people);
        viewModel.getErrorPeopleMutableLiveData().observe(mNewReuActivity, errorMessage -> errorField.setText(errorMessage));
        viewModel.getIsOkButtonEnabledMutableLiveData().observe(mNewReuActivity, status -> mActionOk.setVisibility(status));
        //Log.v("previousEntry", previousEntry);


        mActionCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getDialog().dismiss();
                    }
                });

        mInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.onPeopleChanged(s.toString(), previousEntry);
            }
        });

        mActionOk.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //get input mail
                        String input = mInput.getText().toString().toLowerCase(Locale.ROOT).trim();

                        //Update livedata
                        viewModel.onPeopleAdded(previousEntry, input);
                        getDialog().dismiss();
                    }

                });

        return view;
    }


}