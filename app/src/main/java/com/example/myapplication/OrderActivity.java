package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.myapplication.bean.Order;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private ListView listView1;
    private List<Order> orderList=new ArrayList<>();
    private String get_order_url="http://47.103.53.249:8080/ParkingServlet/GetOrders?Uindex=";



    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
             switch(msg.what) {
                 case 1:
                     String order1=new String(msg.obj.toString());
                     String a=order1.substring(0,order1.length()-2);
                     System.out.println(a);
                     if(a.equals("none")){
                         System.out.println(a);
                     }
                     else{
                         String[] temp=a.split(";");
                         for(int i=0;i<temp.length;i++){
                             String[] temp1=temp[i].split("#");
                             int start=Integer.parseInt(temp1[3]);
                             int end=Integer.parseInt(temp1[4]);
                             int status=Integer.parseInt(temp1[5]);
                             Order order=new Order(temp1[0],temp1[1],temp1[2],start,end,status,temp1[6]);
                             orderList.add(order);
                             Listview listview=new Listview(OrderActivity.this,R.layout.listview1,orderList, handler);
                             listView1.setAdapter(listview);
                         }
                     }
                     break;
                 case 2:
                     String orderplace=msg.obj.toString();

                     String url1="http://47.103.53.249:8080/ParkingServlet/UpdateOrder?Mode=updateStatus&Oindex="+orderplace+"&Status=4";
                     new Change_order().execute(url1);
                     break;
                 case 3:
                     String orderplace1=msg.obj.toString();
                     String url2="http://47.103.53.249:8080/ParkingServlet/UpdateOrder?Mode=updateStatus&Oindex="+orderplace1+"&Status=3";
                     new Change_order().execute(url2);
                 case 4:
                     Intent it1=getIntent();
                     String a4=it1.getStringExtra("uindex");
                     new Get_order_change().execute(get_order_url+a4+"&Mode=all");
                     break;
                 case 5:
                     List<Order> orderList2=new ArrayList<>();
                     String order2=new String(msg.obj.toString());
                     String a1=order2.substring(0,order2.length()-2);
                     String[] temp2=a1.split(";");
                     for(int i=0;i<temp2.length;i++){
                         String[] temp1=temp2[i].split("#");
                         int start=Integer.parseInt(temp1[3]);
                         int end=Integer.parseInt(temp1[4]);
                         int status=Integer.parseInt(temp1[5]);
                         Order order=new Order(temp1[0],temp1[1],temp1[2],start,end,status,temp1[6]);
                         orderList2.add(order);
                         Listview listview=new Listview(OrderActivity.this,R.layout.listview1,orderList2, handler);
                         listView1.setAdapter(listview);
                     }
             }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent it =getIntent();
        String a = it.getStringExtra("uindex");
        //private String get_order_url="http://47.103.53.249:8080/ParkingServlet/GetOrders?Uindex=1&Mode=all";
        listView1=findViewById(R.id.listview);
        new Get_order().execute(get_order_url+a+"&Mode=all");
    }

    class Get_order extends AsyncTask<String,Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String url=strings[0];
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response;
            String order = null;
            try {
                response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                order = EntityUtils.toString(entity, "utf-8");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return order;
        }
        @Override
        protected void onPostExecute(String order1){
            super.onPostExecute(order1);
            Message message = new Message();
            message.what = 1;
            message.obj = order1;
            handler.sendMessage(message);
        }
    }

    class Get_order_change extends AsyncTask<String,Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String url=strings[0];
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response;
            String order = null;
            try {
                response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                order = EntityUtils.toString(entity, "utf-8");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return order;
        }
        @Override
        protected void onPostExecute(String order1){
            super.onPostExecute(order1);
            Message message = new Message();
            message.what = 5;
            message.obj = order1;
            handler.sendMessage(message);
        }
    }

    class Change_order extends AsyncTask<String,Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String url=strings[0];
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response;
            String order = null;
            try {
                response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                order = EntityUtils.toString(entity, "utf-8");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return order;
        }
        @Override
        protected void onPostExecute(String order1){
            super.onPostExecute(order1);
            Message message = new Message();
            message.what = 4;
            handler.sendMessage(message);
        }
    }
}
