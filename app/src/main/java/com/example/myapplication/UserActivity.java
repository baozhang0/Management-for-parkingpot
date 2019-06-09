package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.bean.Order;
import com.example.myapplication.entitiy.QRCodeUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class UserActivity extends AppCompatActivity {
    public TextView username;
    public TextView balance;
    public Button charge;
    public Button exit;
    public Button show_QRcode;
    public ImageView QRcode;
    public Button to_order;
    public Button to_orderlist;
    public static final String user_index_url = "http://47.103.53.249:8080/ParkingServlet/CheckUser?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        balance = findViewById(R.id.balance);
        charge = findViewById(R.id.charge);
        exit = findViewById(R.id.exit);
        show_QRcode = findViewById(R.id.QRcode);
        QRcode=findViewById(R.id.iamgeQR);
        to_order=findViewById(R.id.to_order);
        username=findViewById(R.id.username);
        to_orderlist=findViewById(R.id.to_orderlist);
        //FOR SHOW BALANCE
        Intent it = getIntent();
        final String userid = it.getStringExtra("userid");
        username.setText(userid);
        final String password = it.getStringExtra("password");
        final String uindex=it.getStringExtra("uindex");
        String for_balance = user_index_url + "UserID=" + userid + "&Password=" + password + "&Mode=getbalance";
        new Showbalance().execute(for_balance);
        //END BALANCE

        to_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it2=new Intent(UserActivity.this, MapActivity.class);
                it2.putExtra("userid",userid);
                it2.putExtra("password",password);
                it2.putExtra("uindex",uindex);
                it2.putExtra("balance",balance.getText().toString());
                startActivity(it2);
            }
        });
        to_orderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it3=new Intent(UserActivity.this, OrderActivity.class);
                it3.putExtra("uindex",uindex);
                startActivity(it3);
            }
        });

        //FOR SHOW QRCODE
        show_QRcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(uindex, 480, 480);
                QRcode.setImageBitmap(mBitmap);
            }
        });
        //END SHOW QRCODE
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it1=new Intent(UserActivity.this,LoginActivity.class);
                startActivity(it1);
            }
        });






    }
    //FOR CHARGE
    public void alert(View view){
        final EditText et=new EditText(this);
        new AlertDialog.Builder(this).setTitle("请输入金额")
                .setView(et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String money=et.getText().toString();
                String money1=balance.getText().toString();
                System.out.println(money1);
                Intent it = getIntent();
                String userid = it.getStringExtra("userid");
                username.setText(userid);
                String password = it.getStringExtra("password");
                String index=it.getStringExtra("uindex");
                double int_money=Double.parseDouble(money);
                double int_money1=Double.parseDouble(money1);
                double int_money2=int_money+int_money1;
                String money_final=""+int_money2;
                System.out.println(money_final);
                String charge_url= user_index_url + "UserID=" + userid + "&Password=" + password + "&Mode=update&Uindex="+index+"&Balance="+money_final;
                new Showbalance().execute(charge_url);
                String for_balance = user_index_url + "UserID=" + userid + "&Password=" + password + "&Mode=getbalance";
                new Showbalance().execute(for_balance);
            }
        }).setNegativeButton("取消",null).show();

    }
    //END CHARGE



   class Showbalance extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response;
            String balance = null;
            try {
                response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                balance = EntityUtils.toString(entity, "utf-8");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return balance;
        }

        @Override
        protected void onPostExecute(String balance1) {
            super.onPostExecute(balance1);
            String a=balance.getText().toString();
            balance.setText(balance1);
        }
    }
}








