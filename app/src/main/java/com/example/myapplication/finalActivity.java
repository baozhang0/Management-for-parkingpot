package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class finalActivity extends AppCompatActivity {
    private Button show_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        show_order=findViewById(R.id.show_order);
        show_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getintent=getIntent();
                String uindex=getintent.getStringExtra("uindex");
                Intent it=new Intent(finalActivity.this,OrderActivity.class);
                it.putExtra("uindex",uindex);
                startActivity(it);
            }
        });


    }
}
