package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.R;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.adapter.NumberListAdapter;

import java.util.List;

/**
 * Created by ManhNV on 6/2/2016.
 */
public class NumberListDialog extends Dialog {
    private Context mContext;
    private List<String> mNumbers;
    private ListView mLvNumbers;
    private NumberListAdapter mNumberListAdapter;

    public NumberListDialog(Context context, int themeResId,List<String> numbers) {
        super(context, themeResId);

        mContext = context;
        mNumbers = numbers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_number_list);
        mLvNumbers = (ListView)findViewById(R.id.lv_number_list);
        mNumberListAdapter = new NumberListAdapter(mContext,R.layout.item_number_list,mNumbers);
        mLvNumbers.setAdapter(mNumberListAdapter);

    }
}
