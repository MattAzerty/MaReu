package fr.melanoxy.mareu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import fr.melanoxy.mareu.ViewPagerFraments.FilterPageFragment;
import fr.melanoxy.mareu.ViewPagerFraments.ParamPageFragment;
import fr.melanoxy.mareu.ViewPagerFraments.ReuPageFragment;

public class MainPagerAdapter extends FragmentStateAdapter {


        // 1 - Array of colors that will be passed to PageFragment
        private String[] titles;

        // 2 - Default Constructor
        public MainPagerAdapter(FragmentActivity fa, String[] titles) {
            super(fa);
            this.titles = titles;
        }

        @Override
        public int getItemCount() {
            return(2); // 3 - Number of page to show
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {

            // 4 - Page to return
            switch (position){
                case 0: //Page number 1
                    return ReuPageFragment.newInstance();
                case 1: //Page number 2
                    return FilterPageFragment.newInstance();
                case 2: //Page number 3
                    return ParamPageFragment.newInstance();
                default:
                    return null;
            }
        }}
