package fr.melanoxy.mareu.ui.viewpager.ViewPagerFraments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.melanoxy.mareu.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParamPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParamPageFragment extends Fragment {

    public static ParamPageFragment newInstance() {
        return (new ParamPageFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_param_page, container, false);
    }
}