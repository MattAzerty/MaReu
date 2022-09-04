package fr.melanoxy.mareu;


import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Method;

import fr.melanoxy.mareu.databinding.ActivityMainBinding;
import fr.melanoxy.mareu.events.FragmentEvent;
import fr.melanoxy.mareu.list.MaReuViewModel;
import fr.melanoxy.mareu.utils.ViewModelFactory;
import fr.melanoxy.mareu.viewpager.PageTransformer;

public class MaReuActivity extends AppCompatActivity {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */

// initialize variables
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //binding ActivityMain layout
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Associating ViewModel with the Activity
        MaReuViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MaReuViewModel.class);

        //Configure ViewPager
        this.configureViewPagerAndTabs();

        //Configure the action bar
        setSupportActionBar(binding.toolbar);

        // Setup drawer view
        setupDrawerContent(binding.navView);



// Display an hamburger on the left side of the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_white_24dp);


//Round corners toolbar
        float radius = getResources().getDimension(R.dimen.default_corner_radius);
        MaterialShapeDrawable toolbarBackground = (MaterialShapeDrawable) binding.toolbar.getBackground();
        toolbarBackground.setShapeAppearanceModel(
                toolbarBackground.getShapeAppearanceModel()
                        .toBuilder()
                        //.setBottomRightCorner(CornerFamily.ROUNDED,radius)
                        .setBottomLeftCorner(CornerFamily.ROUNDED,radius)
                        .build()
        );


//Listener for adding a new reunion activity
        binding.activityMainFabAddReu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("MainActivity", "Fab pressed");
                Intent UserInfoIntent = new Intent(view.getContext(), NewReuActivity.class);
                //UserInfoIntent.putExtra("message_key", nb);
                view.getContext().startActivity(UserInfoIntent);


            }

        });


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
                    if (binding.activityMainViewpager.getCurrentItem() == 0) {
                        binding.activityMainViewpager.setCurrentItem(1);
                    }else{onBackPressed();}
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    // Instantiate a ViewPager2 and a PagerAdapter
    private void configureViewPagerAndTabs(){

        String[] titles = getResources().getStringArray(R.array.Titles);

        //Animation settings for viewPager
        binding.activityMainViewpager.setPageTransformer(new PageTransformer());

        //Set Adapter PageAdapter and glue it together
        binding.activityMainViewpager.setAdapter(new MainPagerAdapter(this, getResources().getStringArray(R.array.Titles)));

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
                MaReuActivity.this.getSupportActionBar().setTitle(titles[position]);

                if (position == 0) {
                    binding.activityMainFabAddReu.show();
                } else {
                    binding.activityMainFabAddReu.hide();
                }

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

        //Associating ViewModel with the Activity
        MaReuViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MaReuViewModel.class);

        switch(menuItem.getItemId()) {
            case R.id.activity_main_drawer_news:
                binding.activityMainViewpager.setCurrentItem(0);
                break;
            case R.id.activity_main_drawer_filter:
                binding.activityMainViewpager.setCurrentItem(1);
                break;
            case R.id.activity_main_drawer_clear:
                viewModel.onClearReunionsClicked();
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
}