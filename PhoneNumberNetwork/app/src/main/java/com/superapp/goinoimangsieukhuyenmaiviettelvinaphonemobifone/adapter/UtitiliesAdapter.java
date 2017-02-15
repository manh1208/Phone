package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.R;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dto.Utilities;

import java.util.List;

/**
 * Created by ManhNV on 7/11/2016.
 */
public class UtitiliesAdapter extends ArrayAdapter<Utilities> {
    private Context mContext;
    private List<Utilities> utilities;

    public UtitiliesAdapter(Context context, int resource, List<Utilities> objects) {
        super(context, resource, objects);
        mContext = context;
        utilities = objects;
    }

    @Override
    public Utilities getItem(int position) {
        return utilities.get(position);
    }

    @Override
    public int getCount() {
        return utilities.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_utilities,parent,false);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.iv_utilities_image);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.txt_utilities_title);
            viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.txt_utilities_description);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Utilities utilities = getItem(position);
        viewHolder.ivImage.setImageResource(utilities.getImage());
        viewHolder.txtTitle.setText(utilities.getTitle());
        viewHolder.txtDescription.setText(utilities.getDescription());
        return convertView;
    }

    private class ViewHolder{
        ImageView ivImage;
        TextView txtTitle;
        TextView txtDescription;
    }
}
