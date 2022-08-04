package fr.melanoxy.mareu.Framents;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import fr.melanoxy.mareu.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterPageFragment extends Fragment {


    public static FilterPageFragment newInstance() {
        return (new FilterPageFragment());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter_page, container, false);

    }


}