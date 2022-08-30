package fr.melanoxy.mareu.ViewPagerFraments;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;


import java.util.Date;

import fr.melanoxy.mareu.MaReuActivity;
import fr.melanoxy.mareu.R;
import fr.melanoxy.mareu.databinding.FragmentFilterPageBinding;
import fr.melanoxy.mareu.databinding.FragmentReuPageBinding;
import fr.melanoxy.mareu.list.MaReuViewModel;
import fr.melanoxy.mareu.list.OnReunionClickedListener;
import fr.melanoxy.mareu.list.ReunionsAdapter;
import fr.melanoxy.mareu.newreu.NewReuViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterPageFragment extends Fragment {

    private FragmentFilterPageBinding binding;
    private MaReuActivity mMaReuActivity;


    public static FilterPageFragment newInstance() {
        return (new FilterPageFragment());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //binding FragmentReuPageBinding layout
        binding = FragmentFilterPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        // Inflate the layout for this fragment
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //ViewModel of MaReuActivity associated here on the fragment
        mMaReuActivity = (MaReuActivity) getActivity();
        MaReuViewModel viewModel = new ViewModelProvider(mMaReuActivity).get(MaReuViewModel.class);


        //Filter type listener
        bindFilterType(viewModel);


    }

    private void bindFilterType(MaReuViewModel viewModel) {
        binding.sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                Date date = binding.singleDayPicker.getDate();
                String place = String.valueOf(binding.spinnerRoom.getSelectedItem());
                viewModel.onFilterTypeChanged(progress, date, place);
                Toast.makeText(getContext(), String.valueOf(progress), Toast.LENGTH_LONG).show();
            }
        });
    }

}//END