package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.HashSet;
import java.util.Set;

public class SelectActivity extends AppCompatActivity {

    private GridView gridView;
    private String[] str=new String[]
            {       "1-1","1-2","1-3","1-4","","","1-7","1-8","1-9","1-10",
                    "2-1","","","","","","","","","2-10",
                    "3-1","","3-3","","3-5","3-6","","3-8","","3-10",
                    "4-1","","4-3","","4-5","4-6","","4-8","","4-10",
                    "5-1","","5-3","","5-5","5-6","","5-8","","5-10",
                    "6-1","","6-3","","6-5","6-6","","6-8","","6-10",
                    "7-1","","7-3","","7-5","7-6","","7-8","","7-10",
                    "8-1","","8-3","","8-5","8-6","","8-8","","8-10",
                    "9-1","","","","","","","","","9-10",
                    "10-1","10-2","10-3","10-4","10-5","10-6","10-7","10-8","10-9","10-10",};
    private TextView show_select;
    private Button confirm;
    private Set<String> placeset=new HashSet<>();
    public static String get_used_url="http://47.103.53.249:8080/ParkingServlet/GetOccupiedPlaces?Start=800&End=90000";
    public static String create_order_url="http://47.103.53.249:8080/ParkingServlet/UpdateOrder?Mode=insert&Uindex=";
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String a=new String(msg.obj.toString());

                    a=a.substring(0,a.length()-2);
                    String[] temp=a.split(";");
                    System.out.println(temp[0]);
                    for(int i=0;i<temp.length;i++){
                        String[] temp1=temp[i].split("#");
                        if(temp1[1].equals("1")||temp1[1].equals("0")){
                            System.out.println("true");
                            for(int b=0;b<temp1.length;b++){
                                String[] temp2=temp1[0].split("_");
                                int a1=Integer.parseInt(temp2[0]);
                                int a2=Integer.parseInt(temp2[1]);
                                if(a2!=10){
                                    int a3=(a1-1)*10+a2-1;
                                    str[a3]="unable";



                                }
                            }
                        }
                    }
                    placeset.clear();
                    Gridview adapter=new Gridview(str,SelectActivity.this,show_select,placeset, handler);
                    gridView.setAdapter(adapter);
                    break;
                case 2:
                    String str = "";
                    for(String ss: placeset) str += ss + ";";
                    show_select.setText(str);
                    break;
                case 3:
                    Intent it=getIntent();
                    String uindex= it.getStringExtra("uindex");
                    String start=it.getStringExtra("start");
                    String end=it.getStringExtra("end");
                    String license=it.getStringExtra("license");
                    System.out.println(uindex+start+end+license);
                    String[] str1;
                    String place;
                    //public static String create_order_url="http://47.103.53.249:8080/ParkingServlet/UpdateOrder?Mode=insert&Uindex=";
                    for(String ss:placeset){
                        int c=Integer.parseInt(ss);
                        if(c<=10){
                            place="1_"+c;
                            new DO_order().execute(create_order_url+uindex+"&Pindex="+place+"&Start="+start+"&End="+end+"&Status=0&License="+license);
                        }else if (c%10==0){
                            int c1=c/10;
                            place=c1+"_10";
                            new DO_order().execute(create_order_url+uindex+"&Pindex="+place+"&Start="+start+"&End="+end+"&Status=0&License="+license);


                        }else{
                            int c1=c/10;
                            int c2=c%10;
                            place=c1+"_"+c2;
                            new DO_order().execute(create_order_url+uindex+"&Pindex="+place+"&Start="+start+"&End="+end+"&Status=0&License="+license);

                        }
                    }
                 Intent it3=new Intent(SelectActivity.this,finalActivity.class);
                    it3.putExtra("uindex",uindex);
                    startActivity(it3);

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        gridView=findViewById(R.id.grid_pot);
        show_select=findViewById(R.id.show_edit);
        confirm=findViewById(R.id.confirm);
        Intent it=getIntent();
        String uindex= it.getStringExtra("uindex");
        String start=it.getStringExtra("start");
        String end=it.getStringExtra("end");
        String license=it.getStringExtra("license");
        System.out.println(uindex+start+end+license);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormalDialog();
            }
        });
        new GET_Map().execute(get_used_url);





    }

    class GET_Map extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
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
            System.out.println(order1);
            Message message = new Message();
            message.what = 1;
            message.obj = order1;
            handler.sendMessage(message);
        }
    }

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(SelectActivity.this);

        normalDialog.setTitle("确认信息");
        normalDialog.setMessage("确认预约车位吗？");
        normalDialog.setNegativeButton("确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Message msg1=new Message();
                        msg1.what=3;
                        handler.sendMessage(msg1);
                    }
                });
        normalDialog.setPositiveButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        // 显示
        normalDialog.show();
    }
    class DO_order extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
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
            System.out.println(order1);
        }
    }




}
