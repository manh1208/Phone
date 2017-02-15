package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.Utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.gson.Gson;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dto.Contact;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dto.NecessaryNumberCity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ManhNV on 5/30/2016.
 */
public class DataUtil {
    private static DataUtil INSTANCE = null;
    public static final String BUNDLEID="com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone";
    private Context mContext;
    private HashMap<String, Contact> contacts;
    private  static String mCurrentOperation;
    private static HashMap<String, String> mMapOperation;
    private List<NecessaryNumberCity> necessaryNumberCities;


    private DataUtil(Context context) {
        mContext = context;
        getCurrentOperation();
        createMapOperation();
    }

    private void createMapOperation() {
        mMapOperation = new HashMap<String, String>();

        mMapOperation.put("090", "Mobifone");
        mMapOperation.put("093", "Mobifone");
        mMapOperation.put("0122", "Mobifone");
        mMapOperation.put("0126", "Mobifone");
        mMapOperation.put("0121", "Mobifone");
        mMapOperation.put("0128", "Mobifone");
        mMapOperation.put("0120", "Mobifone");
        mMapOperation.put("091", "Vinaphone");
        mMapOperation.put("094", "Vinaphone");
        mMapOperation.put("0123", "Vinaphone");
        mMapOperation.put("0125", "Vinaphone");
        mMapOperation.put("0127", "Vinaphone");
        mMapOperation.put("096", "Viettel");
        mMapOperation.put("097", "Viettel");
        mMapOperation.put("098", "Viettel");
        mMapOperation.put("0162", "Viettel");
        mMapOperation.put("0163", "Viettel");
        mMapOperation.put("0164", "Viettel");
        mMapOperation.put("0165", "Viettel");
        mMapOperation.put("0166", "Viettel");
        mMapOperation.put("0167", "Viettel");
        mMapOperation.put("0168", "Viettel");
        mMapOperation.put("0169", "Viettel");
        mMapOperation.put("095", "Sfone");
        mMapOperation.put("092", "Vietnamobile");
        mMapOperation.put("0186", "Vietnamobile");
        mMapOperation.put("0188", "Vietnamobile");
        mMapOperation.put("0199", "Beeline");
    }

    public static synchronized DataUtil getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DataUtil(context);
        }
        return INSTANCE;
    }

    public List<Contact> getContacts() {
        return new ArrayList<>(contacts.values());
    }

    public Contact getContact(String Id) {

        return contacts.get(Id);
    }

    public void getCurrentOperation() {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        mCurrentOperation = tm.getNetworkOperatorName();
    }

    public String getmCurrentOperation() {
        return mCurrentOperation;
    }

    public boolean setContacts(HashMap<String, Contact> contacts) {
        this.contacts = contacts;
        return true;
    }

    public static Bitmap loadBitmap(String url) {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }

    public static int CheckNumber(String number) {
        String operation = getOperationFromNumber(number);
        if (operation.toUpperCase().trim().length()<0){
            return -1;
        }else
        if (operation.toUpperCase().trim().equals(mCurrentOperation.toUpperCase().trim())){
            return 1;
        }else{
            return 0;
        }
    }

    public static String getOperationFromNumber(String number) {
        String operation = "";
        if (number.length()>4) {
            number= number.replaceAll("[^0-9]", "");
            number=number.replaceFirst("84","0");
            if (number.substring(1,2).equals("0")){
                number = number.substring(1,number.length()-1);
            }
            if (mMapOperation.containsKey(number.substring(0, 3))) {
                operation = mMapOperation.get(number.substring(0, 3));
            } else if (mMapOperation.containsKey(number.substring(0, 4))) {
                operation = mMapOperation.get(number.substring(0, 4));
            }
        }
       return operation;
    }

    public String getJsonObject(){
        String json = "";

        Log.d("DataUtils", "Get JSON data language");
        json = loadJSONFromAsset("json/necessaryNumber.json");
        return json;
    }

    private String loadJSONFromAsset(String jsonFile) {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open(jsonFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
    private void CreateListCity(String json){
        necessaryNumberCities = new ArrayList<NecessaryNumberCity>();

        try {
            JSONObject jsonArrObject = new JSONObject(json);
            JSONArray jsonArr = jsonArrObject.getJSONArray("body");
            JSONObject jsonObj = null;
            Gson gson = new Gson();
            for (int i = 0; i < jsonArr.length(); i++) {
                jsonObj = jsonArr.getJSONObject(i);
                necessaryNumberCities.add(gson.fromJson(jsonObj.toString(), NecessaryNumberCity.class));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<NecessaryNumberCity> getNecessaryNumberCities() {
        if (necessaryNumberCities==null){
            CreateListCity(getJsonObject());
        }
        return necessaryNumberCities;
    }


    public static Bitmap getBitmapFromAsset(Context context, String strName)
    {
        AssetManager assetManager = context.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }
}
