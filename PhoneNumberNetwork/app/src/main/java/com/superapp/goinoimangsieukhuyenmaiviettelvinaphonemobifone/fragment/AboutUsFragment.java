package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.R;
import com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.Utils.DataUtil;

/**
 * Created by ManhNV on 7/11/2016.
 */
public class AboutUsFragment extends Fragment implements View.OnClickListener {
    LinearLayout llRate;
    LinearLayout llHotGame;
    LinearLayout llLikeUs;
    LinearLayout llAboutUs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about_us, container, false);
        llRate = (LinearLayout) v.findViewById(R.id.layout_rate);
        llRate.setOnClickListener(this);
        llHotGame = (LinearLayout) v.findViewById(R.id.layout_hot_game);
        llHotGame.setOnClickListener(this);
        llLikeUs = (LinearLayout) v.findViewById(R.id.layout_like_us);
        llLikeUs.setOnClickListener(this);
        llAboutUs = (LinearLayout) v.findViewById(R.id.layout_about_us);
        llAboutUs.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.layout_rate:
                Uri uri;
                Intent intent;
//                uri = Uri.parse("http://www.amazon.com/gp/mas/dl/android?p="+ DataUtil.BUNDLEID);
//                uri=Uri.parse("http://appvn.com/android/details?id="+ DataUtil.BUNDLEID);
//                uri=Uri.parse("http://play.google.com/store/apps/details?id="+DataUtil.BUNDLEID);
                 uri = Uri.parse("http://apps.samsung.com/mercury/topApps/topAppsDetail.as?productId=000000758087");
//                uri = Uri.parse("https://play.google.com/store/apps/dev?id=5225382885613170851&hl=en");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.layout_hot_game:
//                uri =Uri.parse("http://www.bestappsforphone.com/kindlegameofthemonth");
//                uri=Uri.parse("http://www.bestappsforphone.com/appotagameofthemonth");
//                 uri=Uri.parse("http://www.bestappsforphone.com/gameofthemonth");
                   uri = Uri.parse("http://www.bestappsforphone.com/samsunggameofthemonth");
//                uri = Uri.parse("https://play.google.com/store/apps/dev?id=5225382885613170851&hl=en");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                this.startActivity(intent);
                break;
            case R.id.layout_like_us:
                uri = Uri.parse("https://www.facebook.com/supercoolappteam");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.layout_about_us:
                uri = Uri.parse("http://www.bestappsforphone.com");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }
    }
}
