package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.formats.NativeAd;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.R;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.Utils.DataUtil;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.activity.DetailActivity;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.activity.SplashActivity;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.adapter.ContactListAdapter;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dto.Contact;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.listener.RequestPermissionListener;
import com.revmob.RevMob;
import com.revmob.RevMobAdsListener;
import com.revmob.ads.banner.RevMobBanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ManhNV on 6/1/2016.
 */
public class AllContactFragment extends Fragment {
    private static int REQUEST_CODE_ADD = 1994;
    private ListView mLvContactList;
    private ContactListAdapter contactListAdapter;
    private List<Contact> mContactList;
    private ListView sideList;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    private TextView mCurrentOperator;
    private ImageButton mCallPhone;
    private TextView mOwnName;
    private List<NativeAd.Image> images;
    private EditText txtSearch;
    private ImageButton btnAdd;

    //    AdRequest adRequest;
    private RevMob revmob;
    private RevMobBanner banner;
    private View v;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.fragment_all_contact, container, false);
        mLvContactList = (ListView) v.findViewById(R.id.lv_contactlist);
        sideList = (ListView) v.findViewById(R.id.side_bar);
        mContactList = new ArrayList<Contact>();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            createList();
        } else {
            RequestPermissionListener listener = (RequestPermissionListener) getActivity();
            listener.requestContactList();
        }

//        adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .build();
//        AdView mAdView = (AdView) v.findViewById(R.id.adView);
//        mAdView.loadAd(adRequest);
        startRevMobSession();


        View header = ((LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.item_header_all_contact, null, false);
        mCurrentOperator = (TextView) header.findViewById(R.id.txt_contactlist_nhamang);
        String user = Build.MODEL;
        mOwnName = (TextView) header.findViewById(R.id.txt_own_name);
        mOwnName.setText(user);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            getCurrentSim();
        } else {
            RequestPermissionListener listener = (RequestPermissionListener) getActivity();
            listener.requestPhoneState();
        }
        contactListAdapter = new ContactListAdapter(getActivity(), R.layout.item_contact_list, mContactList);
        mLvContactList.addHeaderView(header);
        mLvContactList.setAdapter(contactListAdapter);
        mLvContactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    position = position - 1;
                    Contact contact = contactListAdapter.getItem(position);
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("memberId", contact.getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        createSlideBar();
        txtSearch = (EditText) v.findViewById(R.id.txt_search_contact);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    Toast.makeText(getActivity(), "ĐAng search nè con chó....", Toast.LENGTH_SHORT).show();
                contactListAdapter.setContactList(search(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnAdd = (ImageButton) v.findViewById(R.id.btn_add_contact);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleMenuAdd();
            }
        });
        return v;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void createList() {
        mContactList = DataUtil.getINSTANCE(getActivity()).getContacts();
        Collections.sort(mContactList);
    }

    private void getCurrentSim() {
        String n = DataUtil.getINSTANCE(getActivity()).getmCurrentOperation();
        mCurrentOperator.setText(n);
    }


    private void handleMenuAdd() {
        Intent intentInsertEdit = new Intent(Intent.ACTION_INSERT);
        intentInsertEdit.setType(ContactsContract.Contacts.CONTENT_TYPE);
        startActivityForResult(intentInsertEdit, REQUEST_CODE_ADD);
    }

    /*private void handleMenuSearch() {
        ActionBar action = ((MainActivity) getActivity()).getSupportActionBar();
        if (isSearchOpened) {
            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar
            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            createList();
            contactListAdapter.setContactList(mContactList);
            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_search));
            edtSeach.clearFocus();
            isSearchOpened = false;
        } else {
            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.menu_search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText) action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button
            edtSeach.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    Toast.makeText(getActivity(), "ĐAng search nè con chó....", Toast.LENGTH_SHORT).show();
                    contactListAdapter.setContactList(search(s.toString()));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_close));

            isSearchOpened = true;
        }
    }*/

    private List<Contact> search(String keyword) {
        List<Contact> list = new ArrayList<Contact>();

        for (Contact item : mContactList
                ) {
            if (item.getName().toLowerCase().trim().contains(keyword.toLowerCase().trim())) {
                list.add(item);
            }

        }
        return list;
    }

    private void createSlideBar() {

        List<String> sideIndexList = new ArrayList<String>();
        sideIndexList.add("#");
        sideIndexList.add("A");
        sideIndexList.add("B");
        sideIndexList.add("C");
        sideIndexList.add("D");
        sideIndexList.add("E");
        sideIndexList.add("F");
        sideIndexList.add("G");
        sideIndexList.add("H");
        sideIndexList.add("I");
        sideIndexList.add("J");
        sideIndexList.add("K");
        sideIndexList.add("L");
        sideIndexList.add("M");
        sideIndexList.add("N");
        sideIndexList.add("O");
        sideIndexList.add("P");
        sideIndexList.add("Q");
        sideIndexList.add("R");
        sideIndexList.add("S");
        sideIndexList.add("T");
        sideIndexList.add("U");
        sideIndexList.add("V");
        sideIndexList.add("W");
        sideIndexList.add("X");
        sideIndexList.add("Y");
        sideIndexList.add("Z");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_side, sideIndexList);
        sideList.setAdapter(arrayAdapter);
        sideList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int groupposition = contactListAdapter.getPositionForSection(arrayAdapter.getItem(position).charAt(0));
                if (groupposition != -1) {
                    mLvContactList.setSelection(groupposition);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD && resultCode == Activity.RESULT_OK) {
            Intent intent = new Intent(getActivity(), SplashActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public void startRevMobSession() {
        //RevMob's Start Session method:
        revmob = RevMob.startWithListener(getActivity(), new RevMobAdsListener() {
            @Override
            public void onRevMobSessionStarted() {
                loadBanner(); // Cache the banner once the session is started
                Log.i("RevMob","Session Started");
            }
            @Override
            public void onRevMobSessionNotStarted(String message) {
                //If the session Fails to start, no ads can be displayed.
                Log.i("RevMob","Session Failed to Start");
            }
        }, "584fc685ff6b2b3c1cfea8fc");
        if (revmob!=null){
            loadBanner();
        }
        Log.i("Revmob","ahihi");
    }

    public void loadBanner(){
        banner = revmob.preLoadBanner(getActivity(), new RevMobAdsListener(){
            @Override
            public void onRevMobAdReceived() {
                showBanner();
                Log.i("RevMob","Banner Ready to be Displayed"); //At this point, the banner is ready to be displayed.
            }
            @Override
            public void onRevMobAdNotReceived(String message) {
                Log.i("RevMob","Banner Not Failed to Load");
            }
            @Override
            public void onRevMobAdDisplayed() {
                Log.i("RevMob","Banner Displayed");
            }
        });
    }

    public void showBanner(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (banner.getParent()==null){
                    ViewGroup view = (ViewGroup) v.findViewById(R.id.bannerLayout);
                    view.addView(banner);
                }
                banner.show(); //This method must be called in order to display the ad.

            }
        });
    }
}
