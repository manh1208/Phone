package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.R;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.Utils.DataUtil;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.Utils.NumberComparator;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.adapter.NumberListDetailAdapter;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.custom.RoundedImageView;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dto.Contact;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.listener.RequestPermissionListener;

import java.util.Collections;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private String mContactId;
    private Contact mContact;
    private ListView mLvNumbers;
    private NumberListDetailAdapter mNumberListDetailAdapter;
    private boolean isFavorite=false;
    private View convertView;
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Danh bạ");
        setSupportActionBar(toolbar);
        this.mContactId = getIntent().getStringExtra("memberId");
        mContact = DataUtil.getINSTANCE(this).getContact(mContactId);
        RoundedImageView imageView = (RoundedImageView) findViewById(R.id.iv_detail_image_toolbar);
//        CollapsingToolbarLayout toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        Uri imgUri = Uri.parse("android.resource://com.superapp.phonenumbernetwork/" + R.drawable.ic_friend);
        imageView.setImageURI(imgUri);
        if (mContact.getPhotoUri() != null && mContact.getPhotoUri().length() > 0) {
            Uri uri = Uri.parse(mContact.getPhotoUri());
            imageView.setImageURI(uri);
        }else{
            imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_friend));
        }
//        toolbar_layout.setBackgroundResource(R.drawable.ic_friend);
//        if (mContact.getPhotoUri() != null && mContact.getPhotoUri().length() > 0) {
//            Bitmap bitmap = DataUtil.loadBitmap(mContact.getPhotoUri());
//            toolbar_layout.set
//        }
        toolbar.setTitle(mContact.getName());

//        TextView txtFullName = (TextView) findViewById(R.id.txt_detail_fullname_toolbar);
//        txtFullName.setText(mContact.getName());


//        mLvNumbers = (ListView)findViewById(R.id.lv_listNumber_detail);
//        mNumberListDetailAdapter = new NumberListDetailAdapter(this,R.layout.item_number_same_operation,mContact.getPhoneNumber());
//        mLvNumbers.setAdapter(mNumberListDetailAdapter);
        LinearLayout layout = (LinearLayout) findViewById(R.id.lv_body_detail);
        List<String> numbers = mContact.getPhoneNumber();
        NumberComparator numberComparator = new NumberComparator();
        Collections.sort(numbers,numberComparator);
        for (final String number: mContact.getPhoneNumber()
             ) {
            final ViewHolder viewHolder = new ViewHolder();
            if (DataUtil.getINSTANCE(this).CheckNumber(number)==1) {
                convertView = LayoutInflater.from(this).inflate(R.layout.item_number_same_operation, null, false);
                viewHolder.txtOperation = (TextView) convertView.findViewById(R.id.txt_operation_name_same);
                viewHolder.txtNumber = (TextView) convertView.findViewById(R.id.txt_number_same);
                viewHolder.btnCall = (ImageButton) convertView.findViewById(R.id.btn_same_call);
                viewHolder.btnSms = (ImageButton) convertView.findViewById(R.id.btn_same_sms);
//                viewHolder.btnFavorite = (ImageButton) convertView.findViewById(R.id.btn_favorite_same);
            }else{
                convertView = LayoutInflater.from(this).inflate(R.layout.item_number_diff_operation, null, false);
                viewHolder.txtOperation = (TextView) convertView.findViewById(R.id.txt_operation_name_diff);
                viewHolder.txtNumber = (TextView) convertView.findViewById(R.id.txt_number_diff);
                viewHolder.btnCall = (ImageButton) convertView.findViewById(R.id.btn_diff_call);
                viewHolder.btnSms = (ImageButton) convertView.findViewById(R.id.btn_diff_sms);
//                viewHolder.btnFavorite = (ImageButton) convertView.findViewById(R.id.btn_favorite_diff);
            }
//            viewHolder.btnFavorite.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ImageButton imageButton = (ImageButton) v;
//                    imageButton.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_star_yellow));
//                }
//            });
            viewHolder.txtOperation.setText(DataUtil.getINSTANCE(mContext).getOperationFromNumber(number));
            viewHolder.txtNumber.setText(number);
            viewHolder.txtNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + number));
                        mContext.startActivity(callIntent);
                    }else{
                        RequestPermissionListener listener = (RequestPermissionListener) mContext;
                        listener.requestCallPhone();
                    }
                }
            });

            viewHolder.txtNumber.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupCopy(viewHolder.txtNumber);
                    return false;
                }
            });

            viewHolder.btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + number));
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
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.putExtra("address",number);
                    sendIntent.setType("vnd.android-dir/mms-sms");
                    startActivity(sendIntent);
                   // Toast.makeText(mContext, "Nhắn tin cho:"+number, Toast.LENGTH_SHORT).show();
                }
            });
            layout.addView(convertView);
        }
        if (mContact.getEmail().size()>0){
            convertView = LayoutInflater.from(this).inflate(R.layout.item_email_detail, null, false);
            ListView listViewEmail = (ListView) convertView.findViewById(R.id.lv_list_email);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.item_list_email,R.id.txt_email_detail,mContact.getEmail());
            listViewEmail.setAdapter(adapter);
            layout.addView(convertView);
        }
    }
    final static class ViewHolder {
        TextView txtOperation;
        TextView txtNumber;
        ImageButton btnCall;
        ImageButton btnSms;
//        ImageButton btnFavorite;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_detail_favorite:
                if (isFavorite){
                    Toast.makeText(DetailActivity.this, "Chức năng sẽ có trong lần cập nhật sau", Toast.LENGTH_SHORT).show();
//                    item.setIcon(R.drawable.ic_favorite);
                    isFavorite = false;
                }else{
                    Toast.makeText(DetailActivity.this, "Chức năng sẽ có trong lần cập nhật sau", Toast.LENGTH_SHORT).show();
//                    item.setIcon(R.drawable.ic_favorite_yellow);
                    isFavorite = true;
                }
                return true;
            case R.id.action_detail_settings:
                Toast.makeText(DetailActivity.this, "Chức năng sẽ có trong lần cập nhật sau", Toast.LENGTH_SHORT).show();
//                showPopupMenu(findViewById(R.id.action_detail_settings));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showPopupMenu(View v){
        PopupMenu popupMenu = new PopupMenu(mContext,v);
        final MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_popup,popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menu_popup_edit:

                        return true;
                    case R.id.menu_popup_delete:

                        return true;

                }
                return false;
            }
        });
    }
    public void showPopupCopy(View v){
        PopupMenu popupMenu = new PopupMenu(mContext,v);
        final MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_copy,popupMenu.getMenu());
        final TextView view = (TextView) v;
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menu_popup_copy:
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Copy to Clipboard", view.getText());
                        clipboard.setPrimaryClip(clip);
                        return true;
                }
                return false;
            }
        });
    }

}
