package com.example.myapplication;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Set;


public class Gridview extends BaseAdapter implements OnClickListener {
    private LayoutInflater inflater;
    private String[] arrayString;
    private TextView textView;
    private Set<String> placeSet;
    private Handler handler;

    public Gridview(String[] args, Activity activity,TextView textView,Set<String> placeSet,Handler handler)
    {
        arrayString=args;
        inflater=activity.getLayoutInflater();
        this.textView = textView;
        this.handler=handler;
        this.placeSet=placeSet;
    }


    @Override
    public int getCount() {
        return arrayString.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.layout,null);
        Button button=convertView.findViewById(R.id.grid_button);
        if("".equals(arrayString[position])){
            button.setVisibility(convertView.INVISIBLE);
        }else if("unable".equals(arrayString[position])){
            int a=position+1;
            button.setEnabled(false);
            button.setBackgroundResource(R.color.colorPrimary);
            button.setText(""+a);
        }else{
            int a=position+1;
            button.setText(""+a);
        }
        button.setOnClickListener(this);

        return convertView;

    }

    @Override
    public void onClick(View v) {
        String s = ((Button)v).getText().toString();
        textView.setText(s);
        if(placeSet.contains(s)) {
            placeSet.remove(s);
            (v).setActivated(false);
        } else {
            placeSet.add(s);
            (v).setActivated(true);
        }
        Message msg=new Message();
        msg.what=2;
        handler.sendMessage(msg);
    }

}
