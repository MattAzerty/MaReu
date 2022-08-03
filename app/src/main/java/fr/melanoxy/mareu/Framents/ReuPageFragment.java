package fr.melanoxy.mareu.Framents;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.melanoxy.mareu.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReuPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReuPageFragment extends Fragment {

    public static ReuPageFragment newInstance() {
        return (new ReuPageFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reu_page, container, false);
    }


}