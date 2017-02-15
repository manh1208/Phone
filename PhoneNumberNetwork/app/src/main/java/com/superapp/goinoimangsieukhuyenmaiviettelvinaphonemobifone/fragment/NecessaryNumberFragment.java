package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.R;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.Utils.DataUtil;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.adapter.ExpandableListNumberNecessaryAdapter;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dto.NecessaryNumberCity;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dto.NecessaryNumberItem;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.listener.RequestPermissionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ManhNV on 6/14/2016.
 */
public class NecessaryNumberFragment extends Fragment {
    ExpandableListNumberNecessaryAdapter adapter;
    ExpandableListView expandableListView;
    private Context mContext;
    private Spinner citySpinner;
    private List<NecessaryNumberCity> necessaryNumberCities;

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_necessary_number, container, false);
        necessaryNumberCities = new ArrayList<NecessaryNumberCity>();
        mContext = getParentFragment().getActivity();
        citySpinner = (Spinner) v.findViewById(R.id.spin_city);
        createSpinner();
//        NecessaryNumberItem item = new NecessaryNumberItem(1,"Cảnh sát","113","","");
//        NecessaryNumberItem item2 = new NecessaryNumberItem(2,"Cứu thương","115","","");
//        NecessaryNumberItem item3 = new NecessaryNumberItem(3,"Cứu hỏa","114","","");
//        NecessaryNumberGroup group = new NecessaryNumberGroup();
//        group.setName("Khẩn cấp");
//        group.setData(item);
//        group.setData(item2);
//        group.setData(item3);
//        necessaryNumberGroups.add(group);
//        group = new NecessaryNumberGroup();
//        group.setName("Taxi");
//        group.setData(new NecessaryNumberItem(1,"MaiLinh","000","",""));
//        group.setData(new NecessaryNumberItem(2,"Vinasun","002","",""));
//        group.setData(new NecessaryNumberItem(3,"Airport","003","",""));
//        necessaryNumberGroups.add(group);
        necessaryNumberCities = DataUtil.getINSTANCE(mContext).getNecessaryNumberCities();

        expandableListView = (ExpandableListView) v.findViewById(R.id.lv_expanded_menu);
        adapter = new ExpandableListNumberNecessaryAdapter(mContext, necessaryNumberCities.get(2-citySpinner.getSelectedItemPosition()).getData(), expandableListView);
        expandableListView.setAdapter(adapter);
        int count = adapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                final NecessaryNumberItem item = (NecessaryNumberItem) adapter.getChild(groupPosition, childPosition);
                if (item.getPrice().trim().toUpperCase().equals("UNKNOW")) {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + item.getPhone()));
                        mContext.startActivity(callIntent);
                    } else {
                        RequestPermissionListener listener = (RequestPermissionListener) mContext;
                        listener.requestCallPhone();
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    LayoutInflater inflater = getParentFragment().getActivity().getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialog_call_necessary_number, null);
                    TextView name = (TextView) view.findViewById(R.id.txt_dialog_name);
                    TextView phone = (TextView) view.findViewById(R.id.txt_dialog_phone);
                    TextView price = (TextView) view.findViewById(R.id.txt_dialog_price);
                    TextView unit = (TextView) view.findViewById(R.id.txt_dialog_unit);
                    name.setText(item.getName());
                    phone.setText(item.getPhone());
                    price.setText(item.getPrice());
                    unit.setText(item.getUnit());
                    builder.setView(view)
                            .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
//                                    players.get(position).setName(etPlayerName.getText().toString());
//                                    notifyDataSetChanged();
                                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                                        callIntent.setData(Uri.parse("tel:" + item.getPhone()));
                                        mContext.startActivity(callIntent);
                                    } else {
                                        RequestPermissionListener listener = (RequestPermissionListener) mContext;
                                        listener.requestCallPhone();
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });

                    builder.create();
                    builder.show();
                }
                return false;
            }
        });
        return v;
    }

    private void createSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(mContext, R.array.array_city, R.layout.item_spinner);
        arrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        citySpinner.setAdapter(arrayAdapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.setmNecessaryNumberGroups(necessaryNumberCities.get(2-position).getData());
                int count = adapter.getGroupCount();
                for (int i = 0; i < count; i++) {
                    expandableListView.expandGroup(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
