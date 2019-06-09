package com.example.myapplication.bean;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

public class MyButton {
    private Button button;
    private final Order orderB;
    private Handler handler;



    public MyButton(Button button, final Order order, final Handler handler) {
        this.button = button;
        this.orderB = order;
        this.handler = handler;


        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status=order.getStatus();
                String Oindex=order.getOindex();
                Message msg=new Message();
                if(status==0){
                    msg.what=2;
                    msg.obj=Oindex;
                    handler.sendMessage(msg);
                }else if(status==2){
                    msg.what=3;
                    msg.obj=Oindex;
                    handler.sendMessage(msg);
                }

            }
        });
    }
}
