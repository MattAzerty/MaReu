package fr.melanoxy.mareu.ui.viewpager.ViewPagerFraments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.SeekBar;


import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;

import java.util.Calendar;

import fr.melanoxy.mareu.ui.MaReuActivity;
import fr.melanoxy.mareu.R;
import fr.melanoxy.mareu.databinding.FragmentFilterPageBinding;
import fr.melanoxy.mareu.ui.list.MaReuViewModel;

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

        viewModel.getInfoFilterLiveData().observe(mMaReuActivity, infofilter -> binding.fragmentFilterPageTvResultfilter.setText(infofilter));


        //listener
        bindFilterType(viewModel);//seekBar
        bindFilterDate(viewModel);//TimePicker
        bindFilterRoom(viewModel);//spinner
        bindFilterYear(viewModel);//timePicker


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
                //Date date = binding.singleDayPicker.getDate();
                //String place = String.valueOf(binding.spinnerRoom.getSelectedItem());
                viewModel.onFilterTypeChanged(progress);
                //Toast.makeText(getContext(), String.valueOf(progress), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void bindFilterDate(MaReuViewModel viewModel) {

        SingleDateAndTimePicker.OnDateChangedListener changeListener = (displayed, date) -> viewModel.onFilterDateChanged(date);
        binding.singleDayPicker.addOnDateChangedListener(changeListener);

        //viewModel.getDateLiveData().observe(this, date -> mNewReuBinding.newReuSingleDayPicker.setDefaultDate(date));
    }

    private void bindFilterRoom(MaReuViewModel viewModel) {

        binding.spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String room = parent.getItemAtPosition(position).toString(); //this is your selected item
                viewModel.onFilterRoomChanged(room);
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    private void bindFilterYear(MaReuViewModel viewModel) {

        binding.year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);

                PopupWindow popupWindow = new PopupWindow(popupView,
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


                // Initialize more widgets from `popup_layout.xml`

                Calendar cal = Calendar.getInstance();

                final NumberPicker monthPicker = (NumberPicker) popupView.findViewById(R.id.picker_month);
                final NumberPicker yearPicker = (NumberPicker) popupView.findViewById(R.id.picker_year);

                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);
                monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

                int year = cal.get(Calendar.YEAR);
                yearPicker.setMinValue(year-10);
                yearPicker.setMaxValue(year+10);
                yearPicker.setValue(Integer.valueOf(binding.year.getText().toString()));

                yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i2) {

                        binding.year.setText(Integer.toString(i2));

                    }
                });

                // If the PopupWindow should be focusable
                popupWindow.setFocusable(true);

                // If you need the PopupWindow to dismiss when when touched outside
                popupWindow.setBackgroundDrawable(new ColorDrawable());

                int location[] = new int[2];

                // Get the View's(the one that was clicked in the Fragment) location
                view.getLocationOnScreen(location);

                // Using location, the PopupWindow will be displayed right under anchorView
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,
                        location[1] +view.getLeft(), location[1] + view.getHeight());

            }

        });

    }


}//END