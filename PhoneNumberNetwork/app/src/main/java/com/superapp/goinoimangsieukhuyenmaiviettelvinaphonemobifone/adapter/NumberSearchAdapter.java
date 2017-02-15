package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.R;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dto.Contact;

import java.util.List;

/**
 * Created by ManhNV on 6/7/2016.
 */
public class NumberSearchAdapter extends ArrayAdapter<Contact> {
    private Context mContext;
    private List<Contact> contacts;

    public NumberSearchAdapter(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.contacts = objects;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Contact getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_list_search_dialpad,parent,false);
            viewHolder.mName = (TextView) convertView.findViewById(R.id.txt_dialpad_list_name);
            viewHolder.mNumber = (TextView) convertView.findViewById(R.id.txt_dialpad_list_number);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Contact contact = getItem(position);
        viewHolder.mName.setText(contact.getName());
        viewHolder.mNumber.setText(contact.getPhoneNumber().get(0));
        return convertView;
    }

    private final class ViewHolder{
        TextView mName;
        TextView mNumber;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }
}
