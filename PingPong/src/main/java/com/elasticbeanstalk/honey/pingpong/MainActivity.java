package com.elasticbeanstalk.honey.pingpong;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.pingpong.Game;

import java.util.Locale;

public class MainActivity extends FragmentActivity implements MainFragment.MainFragmentListener{
    private static final int MAIN_POSITION = 0;
    private static final int HISTORY_POSITION = 1;
    private static final int PERKS_POSITION = 2;

    private MainFragment mMainFragment;
    private PingPongStatsFragment mStatsFragment;
    private PerksFragment mPerksFragment;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    private MainFragment.MainFragmentListener getThisActivity(){
        return this;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case MAIN_POSITION:
                    mMainFragment = new MainFragment();
                    mMainFragment.setMainFragmentListener(getThisActivity());
                    return mMainFragment;
                case HISTORY_POSITION:
                     mStatsFragment = new PingPongStatsFragment();
                    return mStatsFragment;
                case PERKS_POSITION:
                    mPerksFragment = new PerksFragment();
                    return mPerksFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    @Override
    public void onSuccess(Game game) {
        //when we successfully save a game, we have to update the lifetime series, so we don't have to make another network call
        mStatsFragment.updateStats(game);
    }
}
