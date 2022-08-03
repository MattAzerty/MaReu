package fr.melanoxy.mareu;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;

import fr.melanoxy.mareu.Framents.ReuPageFragment;
import fr.melanoxy.mareu.Framents.ParamPageFragment;
import fr.melanoxy.mareu.Framents.FilterPageFragment;

public class MainActivity extends AppCompatActivity {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 viewPager;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private LinearLayout mLinearLayout;

    // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
    //private ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Configure the action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);


// Display an hamburger on the left side of the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_white_24dp);
//Round corners toolbar
        float radius = getResources().getDimension(R.dimen.default_corner_radius);
        MaterialShapeDrawable toolbarBackground = (MaterialShapeDrawable) toolbar.getBackground();
        toolbarBackground.setShapeAppearanceModel(
                toolbarBackground.getShapeAppearanceModel()
                        .toBuilder()
                        .setBottomRightCorner(CornerFamily.ROUNDED,radius)
                        .setBottomLeftCorner(CornerFamily.ROUNDED,radius)
                        .build()
        );


        //Configure ViewPager n Tabs
        this.configureViewPagerAndTabs();


        // Find our drawer view
        nvDrawer = findViewById(R.id.nav_view);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

    }


    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }


    /*@Override
    public void onResume() {
        super.onResume();

        if (this != null) {
            this.setTitle(getString(R.string.app_name));
        }
    }*/



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // Instantiate a ViewPager2 and a PagerAdapter and Tabs
    private void configureViewPagerAndTabs(){

        String[] titles = getResources().getStringArray(R.array.Titles);
        //Get ViewPager from layout
        viewPager = findViewById(R.id.activity_main_viewpager);


        //Animation settings for viewPager
        viewPager.setPageTransformer(new PageTransformer());

        //Set Adapter PageAdapter and glue it together
        viewPager.setAdapter(new PageAdapter(this, getResources().getStringArray(R.array.Titles)));

        //Action depending of fragment selected
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                mLinearLayout = findViewById(R.id.fragment_reu_page_rootview);
                Drawable background = mLinearLayout.getBackground();

            if(position ==1 ) {
                //mLinearLayout.setBackgroundColor(Color.parseColor("#B2000000"));
                if (background instanceof ShapeDrawable) {
                    ((ShapeDrawable)background).getPaint().setColor(ContextCompat.getColor(getApplicationContext(),R.color.pink_dark));
                } else if (background instanceof GradientDrawable) {
                    ((GradientDrawable)background).setColor(ContextCompat.getColor(getApplicationContext(),R.color.pink_dark));
                } else if (background instanceof ColorDrawable) {
                    ((ColorDrawable)background).setColor(ContextCompat.getColor(getApplicationContext(),R.color.pink_dark));
                }
            }else{
                //mLinearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                if (background instanceof ShapeDrawable) {
                    ((ShapeDrawable)background).getPaint().setColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                } else if (background instanceof GradientDrawable) {
                    ((GradientDrawable)background).setColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                } else if (background instanceof ColorDrawable) {
                    ((ColorDrawable)background).setColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                }
            }

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

        //Get ViewPager from layout
        viewPager = findViewById(R.id.activity_main_viewpager);


        switch(menuItem.getItemId()) {
            case R.id.activity_main_drawer_news:
                viewPager.setCurrentItem(0);
                break;
            case R.id.activity_main_drawer_profile:
                viewPager.setCurrentItem(1);
                break;
            case R.id.activity_main_drawer_settings:
                viewPager.setCurrentItem(2);
                break;
            default:
                viewPager.setCurrentItem(0);
        }



        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
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