package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.R;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.Utils.DataUtil;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dto.Contact;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.listener.RequestPermissionListener;

import java.util.HashMap;

/**
 * Created by ManhNV on 6/3/2016.
 */
public class SplashActivity extends Activity implements RequestPermissionListener{
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1990;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACT = 1991;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1992;
//    private ProgressDialog pDialog;
    private Handler updateBarHandler;
    private HashMap<String, Contact> contacts;
    private Context mContext = this;
    private int counter=0;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            getData();
        }else{
            requestContactList();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

        }else{
           requestPhoneState();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    Toast.makeText(SplashActivity.this, "Ứng dụng không được cấp quyền đọc trạng thái sim", Toast.LENGTH_SHORT).show();
                }
                break;
            case MY_PERMISSIONS_REQUEST_READ_CONTACT:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   getData();

                } else {
                    Toast.makeText(this, "Ứng dụng không được cấp quyền đọc dữ liệu", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case MY_PERMISSIONS_REQUEST_CALL_PHONE:
                if (grantResults.length>0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }else{
                    Toast.makeText(this, "Ứng dụng không được cấp quyền gọi điện", Toast.LENGTH_SHORT).show();
                }
                break;
        }


    }

    private void getData(){
//        pDialog = new ProgressDialog(this);
//        pDialog.setMessage("Đang lấy dữ liệu danh bạ...");
//        pDialog.setCancelable(false);
//        pDialog.show();
        updateBarHandler =new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                contacts = createContact();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DataUtil.getINSTANCE(mContext).setContacts(contacts);
//                        pDialog.setMessage("Đang chuẩn bị dữ liệu danh bạ...");
                    }
                });
                updateBarHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        pDialog.cancel();
                        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                        mContext.startActivity(intent);
                        finish();
                    }

                }, 100);
            }
        }).start();

    }


    private HashMap<String, Contact> createContact() {
        HashMap<String, Contact> contactList = new HashMap<String, Contact>();
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;
        String PHOTO = ContactsContract.Contacts.PHOTO_THUMBNAIL_URI;
        ContentResolver contentResolver = this.getContentResolver();
        cursor = contentResolver.query(CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            counter=0;
            while (cursor.moveToNext()) {
//                updateBarHandler.post(new Runnable() {
//                    public void run() {
////                        pDialog.setMessage("Đọc dữ liệu danh bạ : "+ counter++ +"/"+cursor.getCount());
//                    }
//                });
                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                String phoneNumber = "";
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                    Contact contact = new Contact(contact_id, name);
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        contact.setPhoneNumber(phoneNumber);
                    }
                    phoneCursor.close();
                    String photo = cursor.getString(cursor.getColumnIndex(PHOTO));
                    if (photo != null && photo.length() > 0) {
                        contact.setPhotoUri(photo);
                    }

                    String email="";
                    Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,null,Phone_CONTACT_ID+" = ?",new String[]{contact_id},null);
                    while (emailCursor.moveToNext()) {
                        email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
                        contact.setEmail(email);
                    }
                    contactList.put(contact_id, contact);
                }
            }

        }

        return contactList;
    }

    @Override
    public void requestCallPhone() {
        if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            String[] perms = {"android.permission.CALL_PHONE"};

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(perms, MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        }
    }

    @Override
    public void requestContactList() {
        if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ) {
            String[] perms = {"android.permission.READ_CONTACTS"};

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(perms, MY_PERMISSIONS_REQUEST_READ_CONTACT);
            }
        }
    }

    @Override
    public void requestPhoneState() {
        if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            String[] perms = {"android.permission.READ_PHONE_STATE"};

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(perms, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }
        }
    }
}
