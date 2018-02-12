package com.example.laura.project.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laura.project.R;

import java.util.ArrayList;

/**
 * Created by Laura on 13.11.2017.
 */

public class PartnersAdapter extends BaseAdapter
{
    private static class ItemHolder
    {
        public ImageView logo;
        public TextView comp;

    }

    private ArrayList<String> partners;
    private Context context;
    private LayoutInflater layoutInflater;

    public PartnersAdapter(ArrayList<String> part, Context context)
    {
        this.partners = part;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {//the no of iteems in collection
        return partners.size();
    }

    @Override
    public Object getItem(int position) {//the pos
        return partners.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder holder;
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_parteners, parent, false);//create conv view
            holder= new ItemHolder();
            ImageView picture = (ImageView) convertView.findViewById(R.id.ivLogo);
            TextView name = (TextView) convertView.findViewById(R.id.tvName);

            holder.logo = picture;
            holder.comp = name;

            convertView.setTag(holder);
        }
        else {
            holder = (ItemHolder) convertView.getTag();
        }

        String s = partners.get(position);
        holder.comp.setText(s);
        String nnn = "img"+(position+1);
        holder.logo.setImageResource(context.getResources().
                getIdentifier(nnn,"drawable",context.getPackageName()));

        return convertView;
    }
}
