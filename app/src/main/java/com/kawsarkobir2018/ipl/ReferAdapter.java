package com.kawsarkobir2018.ipl;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by HP-NPC on 16/11/2017.
 */

public class ReferAdapter extends ArrayAdapter<String>{

    Activity context;
    ArrayList<String> nameList;

    public ReferAdapter(@NonNull Activity context, @NonNull ArrayList<String> objects) {
        super(context, R.layout.referrer_list, objects);

        this.context = context;
        Collections.reverse(objects);
        nameList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.referrer_list,null,true);

        TextView nameTextView = (TextView) view.findViewById(R.id.refferedTextView);

        String name = nameList.get(position);
        nameTextView.setText(name);

        return view;
    }
}
