package fr.melanoxy.mareu.ui.viewpager.ViewPagerFraments;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import fr.melanoxy.mareu.ui.MaReuActivity;
import fr.melanoxy.mareu.ui.list.ReunionsAdapter;
import fr.melanoxy.mareu.R;
import fr.melanoxy.mareu.databinding.FragmentReuPageBinding;
import fr.melanoxy.mareu.ui.events.FragmentEvent;
import fr.melanoxy.mareu.ui.list.MaReuViewModel;
import fr.melanoxy.mareu.ui.list.OnReunionClickedListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReuPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReuPageFragment extends Fragment {


    private FragmentReuPageBinding binding;
    private MaReuActivity mMaReuActivity;

    public static Fragment newInstance() {
        ReuPageFragment fragment = new ReuPageFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //binding FragmentReuPageBinding layout
        binding = FragmentReuPageBinding.inflate(getLayoutInflater());
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

        //RecyclerView recyclerView = view.findViewById(R.id.neighbours_rv);

        ReunionsAdapter adapter = new ReunionsAdapter(new OnReunionClickedListener() {
            @Override
            public void onReunionClicked(long neighbourId) {
                //startActivity(NeighbourDetailActivity.navigate(requireContext(), neighbourId));
                Toast.makeText(getActivity(), "details about my REU", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDeleteReunionClicked(long reunionId) {
                viewModel.onDeleteReunionClicked(reunionId);
            }
        });
        binding.reunionsRv.setAdapter(adapter);

        viewModel.getViewStateLiveData().observe(getViewLifecycleOwner(), reunionsViewStateItems ->
                adapter.submitList(reunionsViewStateItems)
        );

    }


    private void hide() {

        binding.reunionsRv.setAlpha(0);
        binding.reunionsRv.setClickable(false);


        GradientDrawable backgroundGradient = (GradientDrawable)binding.fragmentReuPageRootview.getBackground();
        backgroundGradient.setColor(getResources().getColor(R.color.colorPrimaryDark));

    }

    private void unHide() {
        binding.reunionsRv.setAlpha(1);
        binding.reunionsRv.setClickable(true);


        GradientDrawable backgroundGradient = (GradientDrawable)binding.fragmentReuPageRootview.getBackground();
        backgroundGradient.setColor(getResources().getColor(R.color.white));

    }

    /*@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }*/

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    /**
     * Fired if the user clicks on a delete button
     * @param event
     */
    @Subscribe
    public void onPageScrolled(FragmentEvent event) {
        if(event.position ==1) {
            hide();
        } else {unHide();}
    }
}