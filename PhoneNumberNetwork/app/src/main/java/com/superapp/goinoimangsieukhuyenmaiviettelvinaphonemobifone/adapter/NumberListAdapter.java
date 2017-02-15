package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.R;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.Utils.DataUtil;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.listener.RequestPermissionListener;

import java.util.List;

/**
 * Created by ManhNV on 6/2/2016.
 */
public class NumberListAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private List<String> mNumbers;


    public NumberListAdapter(Context context, int resource, List<String> numbers) {
        super(context, resource, numbers);
        this.mContext = context;
        this.mNumbers = numbers;

    }

    @Override
    public int getCount() {
        return mNumbers.size();
    }

    @Override
    public String getItem(int position) {
        return mNumbers.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_number_list,parent,false);
        }
        TextView txtNumber = (TextView) convertView.findViewById(R.id.txt_number_list_number);
        txtNumber.setText(getItem(position));
        if (DataUtil.getINSTANCE(mContext).CheckNumber(getItem(position))==1){
            txtNumber.setTypeface(Typeface.DEFAULT_BOLD);
        }else{
            txtNumber.setTypeface(Typeface.DEFAULT);
        }
        ImageButton btnCAll = (ImageButton) convertView.findViewById(R.id.btn_number_list_call);
        btnCAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + getItem(position)));
                    mContext.startActivity(callIntent);
                }else{
                    RequestPermissionListener listener = (RequestPermissionListener) mContext;
                    listener.requestCallPhone();
                }
            }
        });
        return convertView;
    }
}
