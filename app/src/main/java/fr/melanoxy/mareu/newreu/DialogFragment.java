package fr.melanoxy.mareu.newreu;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import fr.melanoxy.mareu.NewReuActivity;
import fr.melanoxy.mareu.R;

public class DialogFragment
        extends android.app.DialogFragment {

    private NewReuViewModel model;

    public interface OnInputListener {
        void sendInput(String input);
    }

    public OnInputListener mOnInputListener;

    private EditText mInput;
    private TextView mActionOk, mActionCancel;
    private NewReuActivity mActivityName;


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

        mActivityName = (NewReuActivity) getActivity();

        Bundle bundle = this.getArguments();
        String previousEntry = bundle.getString("message");


        mActionCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getDialog().dismiss();
                    }
                });

        mActionOk.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO:API 21 on Attach not called ?
                        mOnInputListener = (OnInputListener) getActivity();
                        //get input mail
                        String input = mInput.getText().toString().toLowerCase(Locale.ROOT).trim();
                        // 1=error
                        int duplicateError = (previousEntry.contains(input)) ? 1 : 0;
                        int syntaxError = (!(input.contains("@entreprise.com"))) ? 1 : 0;


                        if (syntaxError == 0 && duplicateError == 0) {
                            mOnInputListener.sendInput(input);
                            getDialog().dismiss();
                        } else {

                            String dialogError;

                            switch (10 * syntaxError + duplicateError) {

                                case 10:
                                    dialogError = "Erreur de syntaxe ";
                                    break;
                                case 1:
                                    dialogError = "Déjà présent ";
                                    break;

                                default:
                                    dialogError = "erreur";
                            }

                            mInput.setText(dialogError);
                        }

                    }
                });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputListener
                    = (OnInputListener) getActivity();
        } catch (ClassCastException e) {

        }
    }

    /*public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(getActivity()).get(NewReuViewModel.class);
                    model.getPeople();;
    }*/


}