package fr.melanoxy.mareu;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import fr.melanoxy.mareu.Framents.NewsPageFragment;
import fr.melanoxy.mareu.Framents.ParamPageFragment;
import fr.melanoxy.mareu.Framents.ProfilePageFragment;

public class MainActivity extends AppCompatActivity {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 viewPager;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;

    // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
    private ActionBarDrawerToggle drawerToggle;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;

    String[] mTabsName = {"news", "profile", "param"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configure the action bar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

// This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Configure ViewPager n Tabs
        this.configureViewPagerAndTabs();

        // Find our drawer view
        nvDrawer = findViewById(R.id.nav_view);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

    }

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
        //Get ViewPager from layout
        viewPager = findViewById(R.id.activity_main_viewpager);
        //Set Adapter PageAdapter and glue it together
        viewPager.setAdapter(new PageAdapter(this, getResources().getIntArray(R.array.colorPagesViewPager)));

        TabLayout tabLayout = findViewById(R.id.activity_main_tabs);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(mTabsName[position])
        ).attach();
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
        private int[] colors;

        // 2 - Default Constructor
        public PageAdapter(FragmentActivity fa, int[] colors) {
            super(fa);
            this.colors = colors;
        }

        @Override
        public int getItemCount() {
            return(3); // 3 - Number of page to show
        }


        @Override
        public Fragment createFragment(int position) {
            // 4 - Page to return
            switch (position){
                case 0: //Page number 1
                    return NewsPageFragment.newInstance();
                case 1: //Page number 2
                    return ProfilePageFragment.newInstance();
                case 2: //Page number 3
                    return ParamPageFragment.newInstance();
                default:
                    return null;
        }
        }}}