package com.example.myapplication;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.bean.MyButton;
import com.example.myapplication.bean.Order;

import org.w3c.dom.Text;

import java.util.List;

public class Listview extends ArrayAdapter {
    private final int layout1;
    private LayoutInflater inflater;
    private Handler handler;

    public Listview(Context context, int layout, List<Order> obj, Handler handler){
        super(context,layout,obj);
        layout1=layout;
        this.handler = handler;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Order order=(Order)getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(layout1,null);
        TextView oindex_text=view.findViewById(R.id.oindex_text);
        TextView pindex_text=view.findViewById(R.id.pindex_text);
        TextView licence_text=view.findViewById(R.id.licence_text);
        TextView start_text=view.findViewById(R.id.start_text);
        TextView end_text=view.findViewById(R.id.end_text);
        TextView status_text=view.findViewById(R.id.status_text);
        Button button=view.findViewById(R.id.action);
        MyButton myButton = new MyButton(button, order, handler);

        oindex_text.setText(" "+position);
        pindex_text.setText(" "+order.getPindex());
        licence_text.setText(" "+order.getLicence()+" ");
        start_text.setText(order.getStartString()+" ");
        end_text.setText(order.getEndString()+" ");
        switch (order.getStatus()){
            case 0:
                status_text.setText("已预约");
                button.setText("取消");
                break;
            case 1:
                status_text.setText("停车中");
                button.setVisibility(convertView.INVISIBLE);
                break;
            case 2:
                status_text.setText("待付费");
                button.setText("缴费");
                break;
            case 3:
                status_text.setText("已付费");
                button.setText("已完成");
                break;
            case 4:
                status_text.setText("已取消");
                button.setVisibility(convertView.INVISIBLE);
                break;
        }
        return view;
    }

}
