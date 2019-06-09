package com.example.myapplication;

import android.content.Intent;
import android.support.transition.Transition;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MapActivity extends AppCompatActivity {
    public static String dateSourceStr="2010-1-1 00";
    public static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH");

    public EditText year;
    public EditText month;
    public EditText day;
    public EditText hour;
    public EditText year1;
    public EditText month1;
    public EditText day1;
    public EditText hour1;
    public EditText chepai;
    public String getyear;
    public String getmonth;
    public String getday;
    public String gethour;
    public String getyear1;
    public String getmonth1;
    public String getday1;
    public String gethour1;
    public String license;
    private Button to_yuyue;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        year=findViewById(R.id.year);
        month=findViewById(R.id.month);
        day=findViewById(R.id.day);
        hour=findViewById(R.id.hour);
        year1=findViewById(R.id.year1);
        month1=findViewById(R.id.month1);
        day1=findViewById(R.id.day1);
        hour1=findViewById(R.id.hour1);
        chepai=findViewById(R.id.chepai);
        to_yuyue=findViewById(R.id.to_yuyue);
        to_yuyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start_str;
                String end_str;
                getyear=year.getText().toString();
                getmonth=month.getText().toString();
                getday=day.getText().toString();
                gethour=hour.getText().toString();
                getyear1=year1.getText().toString();
                getmonth1=month1.getText().toString();
                getday1=day1.getText().toString();
                gethour1=hour1.getText().toString();
                license=chepai.getText().toString();
                int a=Integer.parseInt(gethour);
                int b=Integer.parseInt(gethour1);
                if(a<10){
                    start_str=getyear+"-"+getmonth+"-"+getday+" "+"0"+gethour;
                }else {
                    start_str = getyear + "-" + getmonth + "-" + getday + " " + gethour;
                }
                if(b<10){
                    end_str=getyear1+"-"+getmonth1+"-"+getday1+" "+"0"+gethour1;
                }else {
                    end_str=getyear1+"-"+getmonth1+"-"+getday1+" "+gethour1;
                }
                try {
                    Date start_date=format.parse(start_str);
                    Date end_date=format.parse(end_str);
                    Date datesource=format.parse(dateSourceStr);
                    long hours1=(start_date.getTime()-datesource.getTime())/(1000*60*60);
                    long hours2=(end_date.getTime()-datesource.getTime())/(1000*60*60);
                    int hours11=(int)hours1;
                    int hours12=(int)hours2;
                    String start_hour=""+hours11;
                    String end_hour=""+hours12;

                    Intent it=getIntent();
                    String userid=it.getStringExtra("userid");
                    String passworid=it.getStringExtra("password");
                    String uindex=it.getStringExtra("uindex");
                    String balance=it.getStringExtra("balance");


                    Intent it1=new Intent(MapActivity.this,SelectActivity.class);
                    it1.putExtra("start",start_hour);
                    it1.putExtra("end",end_hour);
                    it1.putExtra("license",license);
                    it1.putExtra("uindex",uindex);
                    startActivity(it1);






                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });



        Intent it=getIntent();
        String userid=it.getStringExtra("userid");
        String passworid=it.getStringExtra("password");
        String uindex=it.getStringExtra("uindex");
        String balance=it.getStringExtra("balance");
        System.out.println(userid+passworid+uindex+balance);











    }
}
