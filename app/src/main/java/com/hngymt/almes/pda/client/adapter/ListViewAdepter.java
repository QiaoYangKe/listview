package com.hngymt.almes.pda.client.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListViewAdepter extends SimpleAdapter {

    public ListViewAdepter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.mUnfilteredDat = (ArrayList<Map<String, ?>>) data;
    }

    private ArrayList<Map<String, ?>> mUnfilteredDat;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView textView1 = view.findViewById(android.R.id.text1);
        TextView textView2 = view.findViewById(android.R.id.text2);
        textView1.setText(Html.fromHtml(mUnfilteredDat.get(position).get("text1").toString()));
        textView2.setText(Html.fromHtml(mUnfilteredDat.get(position).get("text2").toString()));
        return view;

    }
}
