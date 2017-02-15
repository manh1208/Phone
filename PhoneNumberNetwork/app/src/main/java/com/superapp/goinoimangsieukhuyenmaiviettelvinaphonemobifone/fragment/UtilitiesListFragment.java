package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.R;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.adapter.UtitiliesAdapter;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dto.Utilities;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.listener.IUtilitiesListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ManhNV on 6/14/2016.
 */
public class UtilitiesListFragment extends Fragment {
    private LinearLayout lvFirstUtility;
    private LinearLayout lvSecondUtility;
    private ListView lvUtilities;
    private UtitiliesAdapter utitiliesAdapter;
    private List<Utilities> utilities;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_utilities_list,container,false);
        createList();
        utitiliesAdapter = new UtitiliesAdapter(getActivity(),R.layout.item_list_utilities,utilities);
        lvUtilities  = (ListView) v.findViewById(R.id.lv_utilities);
        lvUtilities.setAdapter(utitiliesAdapter);
        lvUtilities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IUtilitiesListener listener = (IUtilitiesListener) getParentFragment();
                listener.changeFragment(utitiliesAdapter.getItem(position).getFragment());
            }
        });
//        lvFirstUtility = (LinearLayout) v.findViewById(R.id.layout_necessary);
//        lvFirstUtility.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                IUtilitiesListener listener = (IUtilitiesListener) getParentFragment();
//                listener.changeFragment(new NecessaryNumberFragment());
//            }
//        });
//        lvSecondUtility = (LinearLayout) v.findViewById(R.id.layout_about_us);
//        lvFirstUtility.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                IUtilitiesListener listener = (IUtilitiesListener) getParentFragment();
//                listener.changeFragment(new NecessaryNumberFragment());
//            }
//        });
        return v;
    }

    private void createList() {
        utilities = new ArrayList<>();
        utilities.add(new Utilities(R.drawable.img_utilities,"Số điện thoại cần thiết","Tra cứu số điện thoại taxi, cứu hộ...",new NecessaryNumberFragment()));
        utilities.add(new Utilities(R.drawable.ic_about,"Giới thiệu","Về chúng tôi, game hay, ...",new AboutUsFragment()));
    }


}
