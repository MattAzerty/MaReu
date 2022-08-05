package fr.melanoxy.mareu.Framents;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import fr.melanoxy.mareu.ListAdapter;
import fr.melanoxy.mareu.R;
import fr.melanoxy.mareu.databinding.ActivityMainBinding;
import fr.melanoxy.mareu.databinding.FragmentReuPageBinding;
import fr.melanoxy.mareu.events.FragmentEvent;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReuPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReuPageFragment extends Fragment {

    String[] names = {"hello", "world", "world-1", "world-2", "world-3", "world-4"};
    //RecyclerView mRecyclerView;
    //LinearLayout mLinearLayout;
    private FragmentReuPageBinding binding;

    public static ReuPageFragment newInstance() {
        return (new ReuPageFragment());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //binding FragmentReuPageBinding layout
        binding = FragmentReuPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.fragmentReuPageRecyclerview.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.fragmentReuPageRecyclerview.setAdapter(new ListAdapter(names));

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*if (savedInstanceState != null) {
            viewPager.setCurrentItem(savedInstanceState.getInt("pageItem", 0));

        }*/

    }


    private void hide() {

        binding.fragmentReuPageRecyclerview.setAlpha(0);
        binding.fragmentReuPageRecyclerview.setClickable(false);


        GradientDrawable backgroundGradient = (GradientDrawable)binding.fragmentReuPageRootview.getBackground();
        backgroundGradient.setColor(getResources().getColor(R.color.colorPrimaryDark));

    }

    private void unHide() {
        binding.fragmentReuPageRecyclerview.setAlpha(1);
        binding.fragmentReuPageRecyclerview.setClickable(true);


        GradientDrawable backgroundGradient = (GradientDrawable)binding.fragmentReuPageRootview.getBackground();
        backgroundGradient.setColor(getResources().getColor(R.color.white));

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Toast.makeText(getContext(), "SaveInstance", Toast.LENGTH_LONG).show();
        //outState.putInt("pageItem", viewPager.getCurrentItem());
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getContext(), "OnResumeReuFrag", Toast.LENGTH_LONG).show();
    }
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