package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.NativeAd;
import com.squareup.picasso.Picasso;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.R;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.custom.RoundedImageView;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dialog.NumberListDialog;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dto.Contact;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.listener.RequestPermissionListener;

import java.util.List;

/**
 * Created by ManhNV on 5/30/2016.
 */
public class ContactListAdapter extends ArrayAdapter<Contact> implements SectionIndexer {
    private Context mContext;
    private List<Contact> mContactList;
    private List<NativeAd.Image> mImageList;
    private ImageButton mCallPhone;
    AdRequest adRequest;

    public ContactListAdapter(Context context, int textViewResourceId, List<Contact> contacts) {
        super(context, textViewResourceId, contacts);
        this.mContext = context;
        this.mContactList = contacts;
        adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
    }

    @Override
    public long getItemId(int position) {
        return mContactList.get(position).getId().hashCode();
    }

    @Override
    public int getCount() {
        return mContactList.size();
    }

    @Override
    public Contact getItem(int position) {
        return mContactList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_contact_list, parent, false);
        }
        TextView txtFullName = (TextView) convertView.findViewById(R.id.txt_contactlist_fullname);
        final Contact contact = getItem(position);
        txtFullName.setText(contact.getName());
        RoundedImageView imageView = (RoundedImageView) convertView.findViewById(R.id.iv_contactlist_image);

        if (contact.getPhotoUri()!=null){
            Picasso.with(mContext)
                    .load(Uri.parse(contact.getPhotoUri()))
                    .placeholder(R.drawable.ic_friend)
                    .error(R.drawable.ic_friend)
                    .into(imageView);
        }else{
            imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_friend));
        }

//        Uri imgUri = Uri.parse("android.resource://com.superapp.phonenumbernetwork/" + R.drawable.ic_friend);
//        imageView.setImageURI(imgUri);
//        if (contact.getPhotoUri() != null && contact.getPhotoUri().length() > 0) {
//            Uri uri = Uri.parse(contact.getPhotoUri());
//            imageView.setImageURI(uri);
//        } else {
//            imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_friend));
//        }

        int section = getSectionForPosition(position);
        TextView tvLetter = (TextView) convertView.findViewById(R.id.txt_separators);
//        AdView mAdView = (AdView) convertView.findViewById(R.id.adView);
        //If the current position is equal to the classification of the first letter of the Char position, is believed to be the first to appear
        if (position == getPositionForSection(section)) {
            tvLetter.setVisibility(View.VISIBLE);
            tvLetter.setText(getCharAtPosition(position) + "");
//            mAdView.setVisibility(View.VISIBLE);
//            mAdView.loadAd(adRequest);

        } else {
            tvLetter.setVisibility(View.GONE);
//            mAdView.setVisibility(View.GONE);
        }

        mCallPhone = (ImageButton) convertView.findViewById(R.id.btn_contactlist_call);
        mCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String s="";
//                for (String item : contact.getPhoneNumber()
//                     ) {
//                    s+=item+";";
//                }
                if (contact.getPhoneNumber().size() == 1) {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + contact.getPhoneNumber().get(0)));
                        mContext.startActivity(callIntent);
                    } else {
                        RequestPermissionListener listener = (RequestPermissionListener) mContext;
                        listener.requestCallPhone();
                    }

                } else {
                    NumberListDialog dialog = new NumberListDialog(mContext, R.style.DialogSlideAnim, contact.getPhoneNumber());
                    dialog.setTitle("Gọi nhanh cho " + contact.getName());
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.BOTTOM;
                    wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    window.setAttributes(wlp);

                    dialog.show();

                }
            }
        });

        return convertView;
    }

    public void setContactList(List<Contact> contactList) {
        this.mContactList = contactList;
        notifyDataSetChanged();
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mContactList.get(i).getName().toUpperCase();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar < 65 || firstChar > 90) {
                firstChar = '#';
            }
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        int charat = mContactList.get(position).getName().toUpperCase().charAt(0);
        if (charat >= 65 && charat <= 90) {
            return charat;
        } else {
            return 35;
        }
    }

    private char getCharAtPosition(int position) {
        char charat = mContactList.get(position).getName().toUpperCase().charAt(0);
        if (charat >= 65 && charat <= 90) {
            return charat;
        } else {
            return 35;
        }
    }

    public void setmImageList(List<NativeAd.Image> mImageList) {
        this.mImageList = mImageList;
        notifyDataSetChanged();
    }
}
