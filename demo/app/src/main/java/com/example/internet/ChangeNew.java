package com.example.internet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.internet.model.Category;
import com.example.internet.model.News;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class ChangeNew extends AppCompatActivity implements View.OnClickListener{

    private Button back = null, save = null;
    private TextView title = null, content = null, data = null;
    private Spinner category=null,pic=null;

    private  int cid;
    private News[] arr=new News[MainActivity.nn.size()];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_new);
        back = findViewById(R.id.back);
        save = findViewById(R.id.save);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        data = findViewById(R.id.data);
        pic = findViewById(R.id.pic);
        category = findViewById(R.id.category);

        back.setOnClickListener(this);
        save.setOnClickListener(this);
        GetData();
    }
    class TTT extends Thread {
        @Override
        public void run() {
            ChangeNews();
        }
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.back:
                finish();
                break;
            case R.id.save:
                String description=category.getSelectedItem().toString();
                for (Category ss:MainActivity.cc)
                {
                    if(ss.Description.equals(description) )
                        cid=ss.cid;
                }
                TTT ttt=new TTT();
                ttt.start();
                Intent tt=new Intent(ChangeNew.this,MainActivity.class);
                startActivity(tt);
                break;
        }
    }
    private  void   ChangeNews()
    {
        String nameSpace = "http://andorid.News/";//命名空间
        String methodName = "ChangeNew";//调用方法名称
        String soapAction = "http://andorid.News/ChangeNew";//SOAP Action上面两者结合
        String endPoint = "http://192.168.1.3:7777/WebService1.asmx";//提供webService的地址

        SoapObject rpc = new SoapObject(nameSpace, methodName);

        //加入参数
        rpc.addProperty("title",title.getText().toString());
        rpc.addProperty("image",pic.getSelectedItem().toString());
        rpc.addProperty("cid",cid);
        rpc.addProperty("data",data.getText().toString());
        rpc.addProperty("content",content.getText().toString());
        rpc.addProperty("nid",MainActivity.nid);
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
            }
        }
    }


}
