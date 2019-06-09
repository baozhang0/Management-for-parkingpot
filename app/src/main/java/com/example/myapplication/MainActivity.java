package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.entitiy.QRCodeUtil;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView mImageView = findViewById(R.id.iv);
        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap("https://www.baidu.com", 480, 480);
        mImageView.setImageBitmap(mBitmap);
        TextView textView=findViewById(R.id.userid);
        Intent intent=getIntent();
        String userid=intent.getStringExtra("email");
        textView.setText("欢迎回来"+userid);

        Button back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(it);
            }
        });
        Button yuyue=findViewById(R.id.chewei);
        yuyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this,MapActivity.class);
                startActivity(it);
            }
        });






    }
}
