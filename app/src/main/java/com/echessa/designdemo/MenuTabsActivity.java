package com.echessa.designdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.echessa.designdemo.DBUtils.Ordered;
import com.echessa.designdemo.fragment.CategoriesFragment;
import com.echessa.designdemo.fragment.FavoriteFragment;
import com.echessa.designdemo.fragment.MenuOfCategoriesFragment;
import com.echessa.designdemo.fragment.RootFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MenuTabsActivity extends AppCompatActivity{

    private Toolbar toolbar;

    private ViewPager viewPager;

    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tabs);


        // set Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        // Set ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setUpViewPage(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);


    }


    private void setUpViewPage(ViewPager viewPage){

        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new FavoriteFragment(),"Món ưa thích");

        adapter.addFragment(new RootFragment(),"Thể loại");

        viewPage.setAdapter(adapter);
    }

    class ViewPageAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<Fragment>();

        private final List<String> mFragmentTitleList = new ArrayList<String>();

        public ViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {return mFragmentTitleList.get(position) ;}
    }


    // event icon back
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == android.R.id.home ) {
//            if(!(R.layout.fragment_menuofcategories == 2130968611)) {
//                FragmentTransaction trans = getSupportFragmentManager()
//                        .beginTransaction();
//
//                trans.replace(R.id.frame_root, new CategoriesFragment());
//                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                trans.addToBackStack(null);
//
//                trans.commit();
//            }
            /*Intent intent = new Intent(MenuTabsActivity.this,MainActivity.class);
            startActivity(intent);*/
                this.finish();


        }
        return true;

    }


    // setup font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
