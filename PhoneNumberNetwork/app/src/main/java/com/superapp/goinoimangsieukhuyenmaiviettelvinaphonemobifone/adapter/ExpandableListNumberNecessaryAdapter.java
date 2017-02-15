package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.R;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.Utils.DataUtil;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dto.NecessaryNumberGroup;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dto.NecessaryNumberItem;

import java.util.List;

/**
 * Created by ManhNV on 6/14/2016.
 */
public class ExpandableListNumberNecessaryAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<NecessaryNumberGroup> mNecessaryNumberGroups;
    private ExpandableListView mExpandableListView;

    public ExpandableListNumberNecessaryAdapter(Context mContext, List<NecessaryNumberGroup> mNecessaryNumberGroups, ExpandableListView listView) {
        this.mContext = mContext;
        this.mNecessaryNumberGroups = mNecessaryNumberGroups;
        this.mExpandableListView = listView;
    }

    @Override
    public int getGroupCount() {
        return mNecessaryNumberGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mNecessaryNumberGroups.get(groupPosition).getData().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mNecessaryNumberGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mNecessaryNumberGroups.get(groupPosition).getData().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return mNecessaryNumberGroups.get(groupPosition).hashCode();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return mNecessaryNumberGroups.get(groupPosition).getData().get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = new GroupViewHolder();
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_expandable_group_name,parent,false);
            groupViewHolder.txtGroupName = (TextView) convertView.findViewById(R.id.txt_expandable_group_name);
            groupViewHolder.ivExpanded = (ImageView) convertView.findViewById(R.id.ic_expanded);
            groupViewHolder.ivIcon = (ImageView)convertView.findViewById(R.id.iv_group_icon);
            convertView.setTag(groupViewHolder);
        }else{
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
//        convertView.setPadding(0,0,0,20);
//        mExpandableListView.setDividerHeight(20);
        NecessaryNumberGroup group = (NecessaryNumberGroup) getGroup(groupPosition);
//        mExpandableListView.expandGroup(groupPosition);
        groupViewHolder.txtGroupName.setText(group.getName());
        if (isExpanded){
            groupViewHolder.ivExpanded.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_up));
        }else{
            groupViewHolder.ivExpanded.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_down));
        }
        Bitmap icon = DataUtil.getBitmapFromAsset(mContext,"icon/"+group.getIcon());
        if (icon!=null) {
            groupViewHolder.ivIcon.setVisibility(View.VISIBLE);
            groupViewHolder.ivIcon.setImageBitmap(icon);
        }else{
            groupViewHolder.ivIcon.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = new ChildViewHolder();
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_expandable_item,parent,false);
            childViewHolder.txtName = (TextView) convertView.findViewById(R.id.txt_expandable_item_name);
            childViewHolder.txtNumber = (TextView) convertView.findViewById(R.id.txt_expandable_item_number);
            convertView.setTag(childViewHolder);
        }else{
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
//        if (!isLastChild) {
//            mExpandableListView.setDividerHeight(0);
//        }else{
//            mExpandableListView.setDividerHeight(20);
//        }
        NecessaryNumberItem item = (NecessaryNumberItem) getChild(groupPosition,childPosition);
        childViewHolder.txtName.setText(item.getName());
        childViewHolder.txtNumber.setText(item.getPhone());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private final class ChildViewHolder{
        TextView txtName;
        TextView txtNumber;
    }

    private final class GroupViewHolder{
        TextView txtGroupName;
        ImageView ivIcon;
        ImageView ivExpanded;
    }

    public void setmNecessaryNumberGroups(List<NecessaryNumberGroup> mNecessaryNumberGroups) {
        this.mNecessaryNumberGroups = mNecessaryNumberGroups;
        notifyDataSetChanged();
    }
}
