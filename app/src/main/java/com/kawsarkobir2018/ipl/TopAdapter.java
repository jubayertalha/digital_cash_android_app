package com.kawsarkobir2018.ipl;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP-NPC on 06/12/2017.
 */

public class TopAdapter extends ArrayAdapter<UserInfo> {

    List<UserInfo> topList = new ArrayList<UserInfo>();
    Activity context;
    String email;

    public TopAdapter(@NonNull Activity context, @NonNull List<UserInfo> objects,String email) {
        super(context, R.layout.top_listitem, objects);

        this.context = context;
        topList = objects;
        this.email = email;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.top_listitem,null,true);

        TextView nameView = (TextView) view.findViewById(R.id.topName);
        TextView pointView = (TextView) view.findViewById(R.id.topPoint);
        TextView positionView = (TextView) view.findViewById(R.id.topPosition);

        UserInfo userInfo = topList.get(position);

        String name = userInfo.name;
        Integer point = userInfo.totalPoint;
        String sPoint = String.valueOf(point)+" Royal";

        nameView.setText(name);
        pointView.setText(sPoint);
        positionView.setText(String.valueOf(position+1));

        if(email.equals(userInfo.email)){
            nameView.setTextColor(Color.parseColor("#2d912d"));
        }

        return view;
    }
}
