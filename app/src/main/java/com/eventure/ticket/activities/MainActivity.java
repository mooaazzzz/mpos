package com.eventure.ticket.activities;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.eventure.ticket.Fragment.DayClosingFragment;
import com.eventure.ticket.Fragment.LogoutFragment;
import com.eventure.ticket.Fragment.MainFragment;
import com.eventure.ticket.Fragment.RideTransactionFragment;
import com.eventure.ticket.Fragment.SearchFragment;
import com.eventure.ticket.R;
import com.eventure.ticket.viewPager.CustomViewPager;
import com.eventure.ticket.viewPager.MyPageAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    CustomViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    MyPageAdapter myPageAdapter;
    List<Fragment> fragmentList = new ArrayList<>();
    MainFragment mainFragment;
    RideTransactionFragment rideTransaction;
    DayClosingFragment dayClosing;
    SearchFragment search;
    LogoutFragment logout;
    int role = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        viewPagerAdapter();
        bottomNavigation();

    }

    private void initView() {
        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        mainFragment = new MainFragment().getInstance();
        rideTransaction = new RideTransactionFragment().getInstance();
        dayClosing = new DayClosingFragment().getInstance();
        search = new SearchFragment().getInstance();
        logout = new LogoutFragment().getInstance();
        if (GlobalState.getInstance().dataList.getLogin().get(0).getRole().equals(role)) {
            bottomNavigationView.inflateMenu(R.menu.menu_bottom_navigation);
        } else {
            bottomNavigationView.inflateMenu(R.menu.menu_bottom_navigation_with_ride);
        }
    }

    private void viewPagerAdapter() {
        myPageAdapter = new MyPageAdapter(getSupportFragmentManager(), getFragments());
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(myPageAdapter);
        viewPager.setOffscreenPageLimit(5);
    }

    private List<Fragment> getFragments() {
        fragmentList.add(mainFragment);
        if (GlobalState.getInstance().dataList.getLogin().get(0).getRole().equals(role)) {
            fragmentList.add(rideTransaction);
        }
        fragmentList.add(dayClosing);
        fragmentList.add(search);
        fragmentList.add(logout);
        return fragmentList;
    }

    private void bottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.menu_eee_home:

                case R.id.menu_eee_ride_transaction:

                case R.id.menu_eee_day_closing:

                case R.id.menu_eee_search:

                case R.id.menu_eee_more:
                    viewPager.setCurrentItem(menuItem.getOrder(), false);
                    break;
            }
            return true;
        });
    }
}