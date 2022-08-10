package fr.melanoxy.mareu.NewReuFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import fr.melanoxy.mareu.R;
import fr.melanoxy.mareu.databinding.ActivityNewReuBinding;

public class DialogFragment
        extends android.app.DialogFragment {


    public interface OnInputListener {
        void sendInput(String input);
    }

    public OnInputListener mOnInputListener;

    private EditText mInput;
    private TextView mActionOk, mActionCancel;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(
                R.layout.fragment_dialog, container, false);
        mActionCancel
                = view.findViewById(R.id.action_cancel);
        mActionOk = view.findViewById(R.id.action_ok);
        mInput = view.findViewById(R.id.input);


        mActionCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override public void onClick(View v)
                    {
                        getDialog().dismiss();
                    }
                });

        mActionOk.setOnClickListener(
                new View.OnClickListener() {
                    @Override public void onClick(View v)
                    {
                        //TODO:API 21 on Attach not called ?
                        mOnInputListener = (OnInputListener)getActivity();
                        //get input mail
                        String input = mInput.getText().toString().toLowerCase(Locale.ROOT);

                        int syntaxError = (!(input.contains("@entreprise.com"))) ? 1 : 0;



            if(syntaxError==0){
                        mOnInputListener.sendInput(input);
                        getDialog().dismiss();
            }
            else{
                String dialogError = "erreur";

                /*switch(10*syntaxError + duplicateError){

                    case 1:dialogError = "erreur de syntaxe ";
                        break;
                    case 2:dialogError = "déjà présent ";
                        break;

                    default:
                        dialogError = "erreur";
                }*/

                mInput.setText(dialogError);}

                    }
                });

        return view;
    }

    @Override public void onAttach(Context context)
    {
        super.onAttach(context);
        try {
            mOnInputListener
                    = (OnInputListener)getActivity();
        }
        catch (ClassCastException e) {

        }
    }
}
