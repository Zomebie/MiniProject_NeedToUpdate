package com.example.zomebie.miniproject01;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class PagerAdapter extends FragmentStatePagerAdapter {




    MainActivity mainActivity;

    public PagerAdapter(FragmentManager fm, MainActivity mainActivity)
    {
        super(fm);
        this.mainActivity=mainActivity;

    }

    @Override
    public Fragment getItem(int position)
    {


        switch (position)
        {

            case 0:
                return FourthFragment.newInstance(); // MUSIC
            case 1:
                return ThirdFragment.newInstance(); // MY PLAYLIST
            case 2:
                return FirstFragment.newInstance(); // MY VOCA
            case 3:
                return SecondFragment.newInstance(); // MEMO
            default:
                return null;

        }





    }

    @Override
    public int getCount()
    {
        return 4;
    }


    @Override
    public CharSequence getPageTitle(int position)
    {

        switch (position)
        {
            case 0:
                return "MUSIC";
            case 1:
                return "MY PLAYLIST";
            case 2:
                return "MY VOCA";
            case 3:
                return "MEMO";

        }
        return null;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}
