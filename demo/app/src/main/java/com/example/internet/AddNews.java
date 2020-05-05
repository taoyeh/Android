package com.example.internet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internet.model.Category;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class AddNews extends AppCompatActivity implements View.OnClickListener {

    private Button add=null,cancel=null;
    private String pic=null;
    private int cid;
    class TTT extends Thread {
        @Override
        public void run() {
            AddNew();
        }
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
            case R.id.add:
                String description=category.getSelectedItem().toString();
                for (Category ss:MainActivity.cc)
                {
                    if(ss.Description.equals(description) )
                        cid=ss.cid;
                }
                pic= image.getSelectedItem().toString();
                TTT www=new TTT();
                www.start();
                finish();
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }

    private TextView title=null,content=null,data=null;
    private Spinner category=null,image=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        add=findViewById(R.id.add);
        cancel=findViewById(R.id.cancel);
        title=findViewById(R.id.title);
        content=findViewById(R.id.content);
        data=findViewById(R.id.data);
        category=findViewById(R.id.category);
        image=findViewById(R.id.image);
        add.setOnClickListener(this);
        cancel.setOnClickListener(this);


    }

    private  void AddNew()
    {
        String nameSpace = "http://andorid.News/";//命名空间
        String methodName = "InsertNew";//调用方法名称
        String soapAction = "http://andorid.News/InsertNew";//SOAP Action上面两者结合
        String endPoint = "http://192.168.1.3:7777/WebService1.asmx";//提供webService的地址

        SoapObject rpc = new SoapObject(nameSpace, methodName);

        //加入参数
        rpc.addProperty("title",title.getText().toString());
        rpc.addProperty("image",pic);
        rpc.addProperty("cid",cid);
        rpc.addProperty("data",data.getText().toString());
        rpc.addProperty("content",content.getText().toString());
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
        SoapObject object = (SoapObject) envelope.bodyIn;//获取返回的数据
        String flag= object.getProperty(0).toString();//获取返回的结果
    }
}
