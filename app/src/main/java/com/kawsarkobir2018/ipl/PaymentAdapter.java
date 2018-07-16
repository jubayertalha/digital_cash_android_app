package com.kawsarkobir2018.ipl;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by HP-NPC on 17/11/2017.
 */

public class PaymentAdapter extends ArrayAdapter {
    Activity context;
    ArrayList<PaymentHistory> historyList;
    public PaymentAdapter(@NonNull Activity context, @NonNull ArrayList<PaymentHistory> objects) {
        super(context, R.layout.history_list, objects);
        this.context = context;
        //Collections.reverse(objects);
        historyList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.history_list,null,true);

        TextView method = (TextView) view.findViewById(R.id.paymentMethodTextView);
        TextView date = (TextView) view.findViewById(R.id.paymentDateTextView);
        TextView royal = (TextView) view.findViewById(R.id.paymentRoyalTextView);
        TextView input = (TextView) view.findViewById(R.id.paymentInputTextView);
        TextView amount = (TextView) view.findViewById(R.id.paymentAmountTextView);
        TextView proof = (TextView) view.findViewById(R.id.paymentProofTextView);
        LinearLayout ll = view.findViewById(R.id.hsll);

        PaymentHistory history = historyList.get(position);

        String p;

        if (history.paymentProof == 0){
            p = "Pending";
            ll.setBackgroundColor(Color.parseColor("#A6c42c11"));
        }else {
            p = "Paid";
            ll.setBackgroundColor(Color.parseColor("#A62d912d"));
        }

        method.setText(history.paymentMethod);
        date.setText(history.paymentDate);
        royal.setText(history.paymentRoyal+" Royal");
        input.setText(history.paymentInput);
        amount.setText(history.paymentAmount);
        proof.setText(p);

        return view;
    }
}
