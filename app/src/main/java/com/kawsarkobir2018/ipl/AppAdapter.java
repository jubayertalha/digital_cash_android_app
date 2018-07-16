package com.kawsarkobir2018.ipl;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by HP-NPC on 15/11/2017.
 */

public class AppAdapter extends ArrayAdapter<InstallApp> {
    Activity context;
    List appArray;
    UserInfo userInfo;
    public AppAdapter(@NonNull Activity context, @NonNull List<InstallApp> objects,UserInfo userInfo) {
        super(context, R.layout.app_list, objects);
        this.context = context;
        Collections.reverse(objects);
        appArray = objects;
        this.userInfo = userInfo;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.app_list,null,true);
        TextView appName = (TextView) item.findViewById(R.id.appName);
        ImageView appImg = (ImageView) item.findViewById(R.id.appImage);
        InstallApp installApp = (InstallApp) appArray.get(position);
        appName.setText(installApp.name);
        String imgurl = installApp.imgurl;
        Picasso.with(context).load(imgurl).into(appImg);
        return item;
    }
}
