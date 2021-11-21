package com.ofg.test1;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


public class ListItemAdapter extends BaseAdapter {
    private List<Map<String, String>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public ListItemAdapter(Context context, List<Map<String, String>> data){
        this.data = data;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public class Info{
        public TextView name;
        public TextView address;
        public Icon icon;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Info info = new Info();
        convertView = layoutInflater.inflate(R.layout.listviewitem, null);
        info.name = (TextView) convertView.findViewById(R.id.name);
        int deviceTypeImg = Integer.valueOf(data.get(position).get("icon"));
        Drawable drawable = context.getResources().getDrawable(deviceTypeImg);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        info.name.setText((String) data.get(position).get("name"));
        info.name.setCompoundDrawables(drawable, null, null, null);

        return convertView;
    }
}
