package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.R;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.Utils.DataUtil;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.Utils.NumberComparator;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.listener.RequestPermissionListener;

import java.util.Collections;
import java.util.List;

/**
 * Created by ManhNV on 6/2/2016.
 */
public class NumberListDetailAdapter extends ArrayAdapter<String> {
    private NumberComparator numberComparator;
    private Context mContext;
    private List<String> mNumbers;


    public NumberListDetailAdapter(Context context, int resource, List<String> numbers) {
        super(context, resource, numbers);
        this.mContext = context;
        this.mNumbers = numbers;
        numberComparator = new NumberComparator();
        Collections.sort(mNumbers, numberComparator);

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
        ViewHolder viewHolder = new ViewHolder();
        if (convertView==null){
            if (DataUtil.getINSTANCE(mContext).CheckNumber(getItem(position))==1) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_number_same_operation, parent, false);
                viewHolder.txtOperation = (TextView) convertView.findViewById(R.id.txt_operation_name_same);
                viewHolder.txtNumber = (TextView) convertView.findViewById(R.id.txt_number_same);
                viewHolder.btnCall = (ImageButton) convertView.findViewById(R.id.btn_same_call);
                viewHolder.btnSms = (ImageButton) convertView.findViewById(R.id.btn_same_sms);
//                viewHolder.btnFavorite = (ImageButton) convertView.findViewById(R.id.btn_favorite_same);
                convertView.setTag(viewHolder);
            }else{
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_number_diff_operation, parent, false);
                viewHolder.txtOperation = (TextView) convertView.findViewById(R.id.txt_operation_name_diff);
                viewHolder.txtNumber = (TextView) convertView.findViewById(R.id.txt_number_diff);
                viewHolder.btnCall = (ImageButton) convertView.findViewById(R.id.btn_diff_call);
                viewHolder.btnSms = (ImageButton) convertView.findViewById(R.id.btn_diff_sms);
//                viewHolder.btnFavorite = (ImageButton) convertView.findViewById(R.id.btn_favorite_diff);
                convertView.setTag(viewHolder);
            }
        }else{
           viewHolder = (ViewHolder) convertView.getTag();
        }

//        viewHolder.btnFavorite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    ImageButton imageButton = (ImageButton) v;
//                    imageButton.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_yellow));
//
//
//            }
//        });
        viewHolder.txtOperation.setText(DataUtil.getINSTANCE(mContext).getOperationFromNumber(getItem(position)));
        viewHolder.txtNumber.setText(getItem(position));
        viewHolder.txtNumber.setOnClickListener(new View.OnClickListener() {
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

        viewHolder.btnCall.setOnClickListener(new View.OnClickListener() {
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

        viewHolder.btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Nhắn tin cho:"+getItem(position), Toast.LENGTH_SHORT).show();
            }
        });

//        TextView txtNumber = (TextView) convertView.findViewById(R.id.txt_number_list_number);
//        txtNumber.setText(getItem(position));
//        if (DataUtil.getINSTANCE(mContext).CheckNumber(getItem(position))==1){
//            txtNumber.setTypeface(Typeface.DEFAULT_BOLD);
//        }else{
//            txtNumber.setTypeface(Typeface.DEFAULT);
//        }
//        ImageButton btnCAll = (ImageButton) convertView.findViewById(R.id.btn_number_list_call);
//        btnCAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//                    Intent callIntent = new Intent(Intent.ACTION_CALL);
//                    callIntent.setData(Uri.parse("tel:" + getItem(position)));
//                    mContext.startActivity(callIntent);
//                }else{
//                    RequestPermissionListener listener = (RequestPermissionListener) mContext;
//                    listener.requestCallPhone();
//                }
//            }
//        });
        return convertView;
    }

    final static class ViewHolder {
        TextView txtOperation;
        TextView txtNumber;
        ImageButton btnCall;
        ImageButton btnSms;
//        ImageButton btnFavorite;
    }
}
