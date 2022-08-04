package fr.melanoxy.mareu;


import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Method;

import fr.melanoxy.mareu.Framents.ReuPageFragment;
import fr.melanoxy.mareu.Framents.ParamPageFragment;
import fr.melanoxy.mareu.Framents.FilterPageFragment;
import fr.melanoxy.mareu.databinding.ActivityMainBinding;
import fr.melanoxy.mareu.events.FragmentEvent;

public class MainActivity extends AppCompatActivity {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */


    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity", "OnCreate");

        //binding ActivityMain layout
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        //Configure the action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup drawer view
        setupDrawerContent(binding.navView);



// Display an hamburger on the left side of the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_white_24dp);


//Round corners toolbar
        float radius = getResources().getDimension(R.dimen.default_corner_radius);
        MaterialShapeDrawable toolbarBackground = (MaterialShapeDrawable) toolbar.getBackground();
        toolbarBackground.setShapeAppearanceModel(
                toolbarBackground.getShapeAppearanceModel()
                        .toBuilder()
                        //.setBottomRightCorner(CornerFamily.ROUNDED,radius)
                        .setBottomLeftCorner(CornerFamily.ROUNDED,radius)
                        .build()
        );


        //Configure ViewPager n Tabs
        this.configureViewPagerAndTabs();

    }


    @Override
    public void onBackPressed() {
        if (binding.activityMainViewpager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            binding.activityMainViewpager.setCurrentItem(binding.activityMainViewpager.getCurrentItem() - 1);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pageItem", binding.activityMainViewpager.getCurrentItem());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        binding.activityMainViewpager.setCurrentItem(savedInstanceState.getInt("pageItem", 0));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

       if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            EventBus.getDefault().post(new FragmentEvent(binding.activityMainViewpager.getCurrentItem()));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar, menu);

        // To show icons in the actionbar's overflow menu:
        //if(featureId == Window.FEATURE_ACTION_BAR && menu != null){
        if(menu.getClass().getSimpleName().equals("MenuBuilder")){
            try{
                Method m = menu.getClass().getDeclaredMethod(
                        "setOptionalIconsVisible", Boolean.TYPE);
                m.setAccessible(true);
                m.invoke(menu, true);
            }
            catch(NoSuchMethodException e){
                Log.e(TAG, "onMenuOpened", e);
            }
            catch(Exception e){
                throw new RuntimeException(e);
            }
        }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("MainActivity", "OnResume");

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                binding.drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.filter:

                binding.activityMainViewpager.setCurrentItem(1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // Instantiate a ViewPager2 and a PagerAdapter and Tabs
    private void configureViewPagerAndTabs(){

        String[] titles = getResources().getStringArray(R.array.Titles);

        //Animation settings for viewPager
        binding.activityMainViewpager.setPageTransformer(new PageTransformer());

        //Set Adapter PageAdapter and glue it together
        binding.activityMainViewpager.setAdapter(new PageAdapter(this, getResources().getStringArray(R.array.Titles)));

        binding.activityMainViewpager.setOffscreenPageLimit(2);

        //Action depending of fragment selected
        binding.activityMainViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

               EventBus.getDefault().post(new FragmentEvent(position));

                }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Set title of Activity based on the position of Fragment
                MainActivity.this.getSupportActionBar().setTitle(titles[position]);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

            }
        });


    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {


        switch(menuItem.getItemId()) {
            case R.id.activity_main_drawer_news:
                binding.activityMainViewpager.setCurrentItem(0);
                break;
            case R.id.activity_main_drawer_profile:
                binding.activityMainViewpager.setCurrentItem(1);
                break;
            case R.id.activity_main_drawer_settings:
                binding.activityMainViewpager.setCurrentItem(2);
                break;
            default:
                binding.activityMainViewpager.setCurrentItem(0);
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        binding.drawerLayout.closeDrawers();
    }

    private class PageAdapter extends FragmentStateAdapter {

        // 1 - Array of colors that will be passed to PageFragment
        private String[] titles;

        // 2 - Default Constructor
        public PageAdapter(FragmentActivity fa, String[] titles) {
            super(fa);
            this.titles = titles;
        }

        @Override
        public int getItemCount() {
            return(2); // 3 - Number of page to show
        }


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
        }}}