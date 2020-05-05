package com.example.internet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.internet.model.Category;
import com.example.internet.model.News;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NewLook extends AppCompatActivity implements View.OnClickListener {


    private Button back = null, delete = null, pre = null, next = null,change=null;
    private TextView title = null, content = null, data = null, category = null;
    private ImageView pic = null;


    private News[] arr=new News[MainActivity.nn.size()];
    private String picpath="";
    private Bitmap bm=null;
    private int index=-1;

    class TTT1 extends Thread {
        @Override
        public void run() {
            DeleteNew();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_look);

        back = findViewById(R.id.back);
        delete = findViewById(R.id.delete);
        pre = findViewById(R.id.pre);
        next = findViewById(R.id.next);
        change= findViewById(R.id.change);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        data = findViewById(R.id.data);
        pic = findViewById(R.id.pic);
        category = findViewById(R.id.category);

        back.setOnClickListener(this);
        delete.setOnClickListener(this);
        change.setOnClickListener(this);
        pre.setOnClickListener(this);
        next.setOnClickListener(this);

        for (Category ss : MainActivity.cc) {
            if (ss.cid == MainActivity.cid) {
                category.setText(ss.Description);
                break;
            }
        }

        GetData();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.back:
                finish();
                break;
            case R.id.delete:
                TTT1 ttt=new TTT1();
                ttt.start();
                finish();
                break;
            case R.id.pre:
                GoDown();
                break;
            case R.id.next:
                Goup();
                break;
            case R.id.change:
                Intent tt=new Intent(NewLook.this,ChangeNew.class);
                startActivity(tt);
                break;
        }
    }
    private  void DeleteNew()
    {
        String nameSpace = "http://andorid.News/";//命名空间
        String methodName = "DeleteNew";//调用方法名称
        String soapAction = "http://andorid.News/DeleteNew";//SOAP Action上面两者结合
        String endPoint = "http://192.168.1.3:7777/WebService1.asmx";//提供webService的地址

        SoapObject rpc = new SoapObject(nameSpace, methodName);

        //加入参数
        rpc.addProperty("id",arr[index].nid);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.bodyOut = rpc;
        envelope.dotNet = true;              //设置是否调用于doNet开发的WebService
        envelope.setOutputSoapObject(rpc); //等价于envelope.bodyOut=rpc

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    pic.setImageBitmap(bm);
                    break;
            }
        }
    };
    class TTT extends Thread {
        @Override
        public void run() {
            GetPic();
        }
    }

    private void GetPic()
    {
        try
        {
            URL url=new URL(picpath);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();

            InputStream inputStream=httpURLConnection.getInputStream();
            bm= BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            Message mm = Message.obtain();
            mm.what = 1;
            handler.sendMessage(mm);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private  void GetData()
    {
        for (int i =0;i<MainActivity.nn.size();i++)
        {
            arr[i] = MainActivity.nn.get(i);
            if(arr[i].nid==MainActivity.nid)
            {
                title.setText(arr[i].title);
                content.setText("\u3000\u3000"+arr[i].content);
                data.setText(arr[i].data);
                picpath=arr[i].image;
                index=i;
            }
        }
        TTT work=new TTT();
        work.start();
    }

    private  void  Goup()
    {
        if(index<arr.length-1) index++;
        else index=arr.length-1;
        title.setText(arr[index].title);
        content.setText("\u3000\u3000"+arr[index].content);
        data.setText(arr[index].data);
        picpath=arr[index].image;

        TTT work=new TTT();
        work.start();
    }

    private  void  GoDown()
    {
        if(index>0) index--;
        else index=0;
        title.setText(arr[index].title);
        content.setText("\u3000\u3000"+arr[index].content);
        data.setText(arr[index].data);
        picpath=arr[index].image;

        TTT work=new TTT();
        work.start();
    }
}
