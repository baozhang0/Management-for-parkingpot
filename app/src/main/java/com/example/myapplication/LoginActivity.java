package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class LoginActivity extends AppCompatActivity {

    public EditText user_id;
    public EditText ps_word;
    public Button login;
    public Button Register;
    public TextView show;
    public static final String login_index_url="http://47.103.53.249:8080/ParkingServlet/CheckUser?";
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String a = new String(msg.obj.toString());
                    a = a.substring(0, a.length() - 2);
                    if (a.equals("fail")) {
                        showfailDialog();
                    } else {
                        String user = user_id.getText().toString();
                        showNormalDialog(user,a);
                    }
                    break;
                case 2:
                    String b=new String(msg.obj.toString());
                    b=b.substring(0,b.length()-2);
                    if(b.equals("fail")){
                        showfailDialog_re();
                    }else{
                        shownormalDialog_re();
                    }
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user_id=findViewById(R.id.user_id);
        ps_word=findViewById(R.id.psword);
        login=findViewById(R.id.to_login);
        show=findViewById(R.id.back);
        Register=findViewById(R.id.register);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = user_id.getText().toString();
                String password = ps_word.getText().toString();
                getlogin(username, password);
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("请输入用户名和密码");
                View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog,null);
                builder.setView(view);
                final EditText username1=view.findViewById(R.id.username);
                final EditText password1=view.findViewById(R.id.password);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String a=username1.getText().toString();
                        String b=password1.getText().toString();
                        To_register(a,b);

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

    }


    public void getlogin(final String id, final String pswd) {
        new Thread(new Runnable() {
            @Override
            public void run() {


                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(login_index_url + "UserID=" + id + "&Password=" + pswd + "&Mode=isExist");
                HttpResponse httpResponse;
                try {
                    httpResponse = httpClient.execute(httpGet);
                    HttpEntity entity = httpResponse.getEntity();
                    String response = EntityUtils.toString(entity, "utf-8");
                    Message message = new Message();
                    message.what = 1;
                    message.obj = response;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void  To_register(final String id, final String pswd) {
        new Thread(new Runnable() {
            @Override
            public void run() {


                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(login_index_url + "UserID=" + id + "&Password=" + pswd + "&Mode=register");
                HttpResponse httpResponse;
                try {
                    httpResponse = httpClient.execute(httpGet);
                    HttpEntity entity = httpResponse.getEntity();
                    String response = EntityUtils.toString(entity, "utf-8");
                    Message message = new Message();
                    message.what = 2;
                    message.obj = response;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showNormalDialog(final String username,final String uindex){

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(LoginActivity.this);

        normalDialog.setTitle("登录信息");
        normalDialog.setMessage("成功！您的用户名为"+username);
        normalDialog.setPositiveButton("个人信息",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent it=new Intent(LoginActivity.this, UserActivity.class);
                        it.putExtra("userid",user_id.getText().toString());
                        it.putExtra("password",ps_word.getText().toString());
                        it.putExtra("uindex",uindex);
                        startActivity(it);

                    }
                });
        normalDialog.setNegativeButton("预约车位",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent it=new Intent(LoginActivity.this, MainActivity.class);
                        it.putExtra("userid",user_id.getText());
                        startActivity(it);
                    }
                });
        // 显示
        normalDialog.show();
    }

    private void showfailDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(LoginActivity.this);

        normalDialog.setTitle("登录失败");
        normalDialog.setMessage("用户名或密码错误");
        normalDialog.setPositiveButton("重试",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });

        // 显示
        normalDialog.show();
    }

    private void showfailDialog_re(){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(LoginActivity.this);
        normalDialog.setTitle("注册失败");
        normalDialog.setMessage("用户名已存在");
        normalDialog.setPositiveButton("返回",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });

        // 显示
        normalDialog.show();
    }

    private void shownormalDialog_re(){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(LoginActivity.this);

        normalDialog.setTitle("注册成功！");
        normalDialog.setPositiveButton("返回",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });

        // 显示
        normalDialog.show();
    }
}