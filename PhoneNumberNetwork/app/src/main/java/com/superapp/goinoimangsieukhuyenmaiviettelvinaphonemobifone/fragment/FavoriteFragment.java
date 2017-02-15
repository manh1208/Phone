package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.R;

/**
 * Created by ManhNV on 6/1/2016.
 */
public class FavoriteFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite,container,false);

        return v;
    }
}
