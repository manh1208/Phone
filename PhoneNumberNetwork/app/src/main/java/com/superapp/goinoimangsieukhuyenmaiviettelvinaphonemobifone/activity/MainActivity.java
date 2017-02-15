package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.R;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.adapter.TabAdapter;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.application.AnalyticsApplication;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.listener.RequestPermissionListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RequestPermissionListener{
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1990;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACT = 1991;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1992;
    private MenuItem mSearchAction;
    private boolean isSearchOpened=false;
    private EditText edtSeach;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static int int_items = 5;
    private Tracker mTracker;
    private List<NativeAd.Image> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Main Menu");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());




        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
//                tabLayout.getTabAt(1).setIcon(R.drawable.ic_phone);
                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        int position = tab.getPosition();
                        switch (position){
                            case 0:
                                viewPager.setCurrentItem(tab.getPosition());
                                break;
                            case 1:
                                viewPager.setCurrentItem(tab.getPosition());
                                break;
                            case 2:
                                viewPager.setCurrentItem(tab.getPosition());
                                break;
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }
        });



    }

//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
////        mSearchAction = menu.findItem(R.id.action_search);
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        switch (id){
//            case R.id.action_settings:
//                return true;
//
//            case R.id.action_search:
////                handleMenuSearch();
//                return true;
//    }
//
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        for (Fragment frag : fm.getFragments()) {
            if (frag.isVisible()) {
                FragmentManager childFm = frag.getChildFragmentManager();
                if (childFm.getBackStackEntryCount() > 0) {
                    childFm.popBackStack();
                    return;
                }
            }
        }
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    Toast.makeText(MainActivity.this, "Ứng dụng không được cấp quyền đọc trạng thái sim", Toast.LENGTH_SHORT).show();
                }
                break;
            case MY_PERMISSIONS_REQUEST_READ_CONTACT:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(MainActivity.this, "Ứng dụng không được cấp quyền đọc dữ liệu", Toast.LENGTH_SHORT).show();
                }
                break;
            case MY_PERMISSIONS_REQUEST_CALL_PHONE:
                if (grantResults.length>0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }else{
                    Toast.makeText(MainActivity.this, "Ứng dụng không được cấp quyền gọi điện", Toast.LENGTH_SHORT).show();
                }
                break;
        }


    }

    @Override
    public void requestCallPhone() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            String[] perms = {"android.permission.CALL_PHONE"};

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(perms, MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        }
    }

    @Override
    public void requestContactList() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ) {
            String[] perms = {"android.permission.READ_CONTACTS"};

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(perms, MY_PERMISSIONS_REQUEST_READ_CONTACT);
            }
        }
    }

    @Override
    public void requestPhoneState() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            String[] perms = {"android.permission.READ_PHONE_STATE"};

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(perms, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }
        }
    }



}
