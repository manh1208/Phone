package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.R;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.Utils.DataUtil;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.activity.SplashActivity;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.adapter.NumberSearchAdapter;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dto.Contact;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.listener.RequestPermissionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ManhNV on 6/1/2016.
 */
public class KeyPadFragment extends Fragment implements View.OnClickListener {
    private static final int REQUEST_CODE_ADD = 1999;
    private TextView txtOne;
    private TextView txtTwo;
    private TextView txtThree;
    private TextView txtFour;
    private TextView txtFive;
    private TextView txtSix;
    private TextView txtSeven;
    private TextView txtEight;
    private TextView txtNine;
    private TextView txtZero;
    private TextView txtStar;
    private TextView txtThang;
    private ImageButton btnDelete;
    private EditText txtInputNumber;
    private ListView lvListSearch;
    private NumberSearchAdapter numberSearchAdapter;
    private List<Contact> contactList;
    private ImageButton btnCall;
    private ImageButton btnHide;
    private ImageButton btnAddUser;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout2;

    public KeyPadFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_keypad, container, false);

        txtOne = (TextView) v.findViewById(R.id.dialpad_one);
        txtTwo = (TextView) v.findViewById(R.id.dialpad_two);
        txtThree = (TextView) v.findViewById(R.id.dialpad_three);
        txtFour = (TextView) v.findViewById(R.id.dialpad_four);
        txtFive = (TextView) v.findViewById(R.id.dialpad_five);
        txtSix = (TextView) v.findViewById(R.id.dialpad_six);
        txtSeven = (TextView) v.findViewById(R.id.dialpad_seven);
        txtEight = (TextView) v.findViewById(R.id.dialpad_eight);
        txtNine = (TextView) v.findViewById(R.id.dialpad_nine);
        txtZero = (TextView) v.findViewById(R.id.dialpad_zero);
        txtStar = (TextView) v.findViewById(R.id.dialpad_star);
        txtThang = (TextView) v.findViewById(R.id.dialpad_thang);
        btnDelete = (ImageButton) v.findViewById(R.id.dialpad_delete);
        btnCall = (ImageButton) v.findViewById(R.id.btn_call_dialpad);
        btnCall.setOnClickListener(this);
        txtOne.setOnClickListener(this);
        txtTwo.setOnClickListener(this);
        txtThree.setOnClickListener(this);
        txtFour.setOnClickListener(this);
        txtFive.setOnClickListener(this);
        txtSix.setOnClickListener(this);
        txtSeven.setOnClickListener(this);
        txtEight.setOnClickListener(this);
        txtNine.setOnClickListener(this);
        txtZero.setOnClickListener(this);
        txtStar.setOnClickListener(this);
        txtThang.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        txtInputNumber = (EditText) v.findViewById(R.id.txt_keypad_input_number);
        lvListSearch = (ListView) v.findViewById(R.id.lv_list_dialpad);
        numberSearchAdapter = new NumberSearchAdapter(getActivity(), R.layout.item_list_search_dialpad, new ArrayList<Contact>());
        lvListSearch.setAdapter(numberSearchAdapter);
        lvListSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = numberSearchAdapter.getItem(position);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + contact.getPhoneNumber().get(0)));
                    getActivity().startActivity(callIntent);
                }else{
                    RequestPermissionListener listener = (RequestPermissionListener) getActivity();
                    listener.requestCallPhone();
                }
            }
        });
        btnHide = (ImageButton) v.findViewById(R.id.btn_hide_keypad);
        btnHide.setOnClickListener(this);
        linearLayout = (LinearLayout) v.findViewById(R.id.lv_keypad_layout);
        linearLayout2= (LinearLayout) v.findViewById(R.id.keypad);
        btnAddUser = (ImageButton) v.findViewById(R.id.btn_dialpad_adduser);
        btnAddUser.setOnClickListener(this);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtInputNumber.getWindowToken(), 0);
        txtInputNumber.setFocusable(false);
        txtInputNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 0)
                    numberSearchAdapter.setContacts(search());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtZero.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String s = txtInputNumber.getText().toString();
                txtInputNumber.setText(s + "+");
                return false;
            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String s = txtInputNumber.getText().toString();
        String s2;
        switch (id) {
            case R.id.dialpad_one:
                txtInputNumber.setText(s + "1");
                break;
            case R.id.dialpad_two:
                txtInputNumber.setText(s + "2");
                break;
            case R.id.dialpad_three:
                txtInputNumber.setText(s + "3");
                break;
            case R.id.dialpad_four:
                txtInputNumber.setText(s + "4");
                break;
            case R.id.dialpad_five:
                txtInputNumber.setText(s + "5");
                break;
            case R.id.dialpad_six:
                txtInputNumber.setText(s + "6");
                break;
            case R.id.dialpad_seven:
                txtInputNumber.setText(s + "7");
                break;
            case R.id.dialpad_eight:
                txtInputNumber.setText(s + "8");
                break;
            case R.id.dialpad_nine:
                txtInputNumber.setText(s + "9");
                break;
            case R.id.dialpad_zero:
                txtInputNumber.setText(s + "0");
                break;
            case R.id.dialpad_star:
                txtInputNumber.setText(s + "*");
                break;
            case R.id.dialpad_thang:
                txtInputNumber.setText(s + "#");
                break;
            case R.id.dialpad_delete:
                if (s.length() > 0) {
                    txtInputNumber.setText(s.substring(0, s.length() - 1));
                }
                break;
            case R.id.btn_call_dialpad:
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    String number="";
                    for(char c : txtInputNumber.getText().toString().toCharArray()) {

                        if(c == '#')
                            number += Uri.encode("#");
                        else
                            number += c;
                    }
                    callIntent.setData(Uri.parse("tel:" + number));
                    getActivity().startActivity(callIntent);
                }else{
                    RequestPermissionListener listener = (RequestPermissionListener) getActivity();
                    listener.requestCallPhone();
                }
                break;
            case  R.id.btn_hide_keypad:
                int visibile = linearLayout.getVisibility();
                switch (visibile){
                    case View.GONE:
                        TranslateAnimation animate = new TranslateAnimation(0,0,linearLayout.getHeight(),0);
                        animate.setDuration(500);
//                        animate.setFillAfter(true);
                        linearLayout2.startAnimation(animate);
                        linearLayout.setVisibility(View.VISIBLE);
                        break;
                    case View.VISIBLE:
                        TranslateAnimation animate2 = new TranslateAnimation(0,0,0, linearLayout.getHeight());

                                //linearLayout.getHeight());
                        animate2.setDuration(500);
                        animate2.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                linearLayout.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
//                        animate.setFillAfter(true);
                        linearLayout2.startAnimation(animate2);
//                        linearLayout.setVisibility(View.GONE);
                        break;
                }

                break;
            case R.id.btn_dialpad_adduser:
                Intent intentInsertEdit = new Intent(Intent.ACTION_INSERT);
                intentInsertEdit.setType(ContactsContract.Contacts.CONTENT_TYPE);
                startActivityForResult(intentInsertEdit,REQUEST_CODE_ADD);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_ADD && resultCode== Activity.RESULT_OK){
            Intent intent = new Intent(getActivity(), SplashActivity.class);
            startActivity(intent);
        }
    }


    private List<Contact> search() {
        String s = txtInputNumber.getText().toString();
        List<Contact> allList = DataUtil.getINSTANCE(getActivity()).getContacts();
        List<Contact> newList = new ArrayList<Contact>();
        if (s.length() > 0) {

            for (Contact item : allList
                    ) {
                for (String number : item.getPhoneNumber()
                        ) {
                    String num = number;
                    num= num.replaceAll("[^0-9]", "");
                    num=num.replaceFirst("84","0");
                    if (num.substring(1,2).equals("0")){
                        num = num.substring(1,num.length()-1);
                    }
                    if (num.contains(s)) {
                        Contact contact = new Contact();
                        contact.setName(item.getName());
                        contact.setPhoneNumber(number);
                        newList.add(contact);
                    }
                }
            }
        }
        return newList;
    }

    @Override
    public void onResume() {

        super.onResume();

    }
}
