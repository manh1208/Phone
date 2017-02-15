package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.fragment.AllContactFragment;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.fragment.KeyPadFragment;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.fragment.UtilitiesFragment;

/**
 * Created by ManhNV on 6/1/2016.
 */
public class TabAdapter extends FragmentPagerAdapter {
    public TabAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new AllContactFragment();
//            case 1 : return new FavoriteFragment();
            case 1 : return new KeyPadFragment();
            case 2: return new UtilitiesFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String s= null;
        switch (position){
            case 0 :
                s= "Danh bạ";
                break;
//            case 1:
//                s= "Ưu thích";
//                break;
            case 1:
                s= "Quay số";
                break;
            case 2 :
                s= "Tiện ích";
                break;
        }
        return s;
    }
}
