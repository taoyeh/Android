package com.example.internet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internet.adpter.NewsAdpter;
import com.example.internet.model.Category;
import com.example.internet.model.News;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int tid = parent.getId();
        switch (tid) {
            case R.id.news_list:
                TextView textView= view.findViewById(R.id.newid);
                nid=Integer.parseInt(textView.getText().toString().trim());

                Intent tt=new Intent(MainActivity.this,NewLook.class);
                startActivity(tt);
                break;
            case R.id.category:
                for (int i = 0; i < category.getChildCount(); i++) {
                    TextView tv = category.getChildAt(i).findViewById(R.id.description);
                    tv.setTextColor(Color.WHITE);
                }
                TextView ccid = view.findViewById(R.id.category);
                cid = Integer.parseInt(ccid.getText().toString().trim());
                TextView mm = view.findViewById(R.id.description);
                mm.setTextColor(Color.RED);
                ww2 = new TTT2();
                ww2.start();
                break;
        }
    }

    //捕捉
    private Button refresh = null,add=null;
    private HorizontalScrollView hor = null;
    private LinearLayout linear = null;
    private ListView news_list = null;
    private GridView category = null;
    //用于处理数据
    private String result = "";
    public static int nid = -1, cid = 1;
    public static List<Category> cc = new ArrayList<Category>();
    public static List<News> nn = new ArrayList<News>();

    //线程处理
    private TTT1 ww1 = null; //获取所有的category
    private TTT2 ww2 = null; //获取所有的news
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    CategoryJsontoList(result);
                    ShowCategory();
                    break;
                case 2:
                    NewssontoList(result);
                    ShowNews();
                    break;
            }
        }
    };


    //json转化为list对象
    public static void CategoryJsontoList(String jsonString) {
        try {
            //最外层是一个数组[]，所以使用JSONArray
            JSONArray jsonarr = new JSONArray(jsonString);
            for (int i = 0; i < jsonarr.length(); i++) {
                //遍历数组获得数组中Json对象。
                Category category = new Category();
                JSONObject jsonObject = jsonarr.getJSONObject(i);
                //获取到Json对象，就可以直接通过Key获取Value
                category.cid = jsonObject.getInt("id");
                category.Description = jsonObject.getString("description");
                cc.add(category);
            }
        } catch (Exception e) {

        }
    }
    public static void NewssontoList(String jsonString) {
        nn.clear();
        String pic="http://192.168.1.3:7777/pic/";
        try {
            //最外层是一个数组[]，所以使用JSONArray
            JSONArray jsonarr = new JSONArray(jsonString);
            for (int i = 0; i < jsonarr.length(); i++) {
                //遍历数组获得数组中Json对象。
                News news = new News();
                JSONObject jsonObject = jsonarr.getJSONObject(i);
                //获取到Json对象，就可以直接通过Key获取Value
                news.nid = jsonObject.getInt("id");
                news.cid = jsonObject.getInt("cid");
                news.title = jsonObject.getString("title");
                news.image =  pic+jsonObject.getString("front_image");
                news.data = jsonObject.getString("publish_date");
                news.content =jsonObject.getString("content");
                if(cid==1)
                nn.add(news);
                else if(cid==news.cid) nn.add(news);
            }
        } catch (Exception e) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refresh = findViewById(R.id.rotate);
        add=findViewById(R.id.add);
        linear = findViewById(R.id.linear);
        hor = findViewById(R.id.hor);
        news_list = findViewById(R.id.news_list);
        category = findViewById(R.id.category);

        refresh.setOnClickListener(this);
        add.setOnClickListener(this);
        news_list.setOnItemClickListener(this);
        category.setOnItemClickListener(this);
        ww1 = new TTT1();
        ww1.start();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
            case R.id.rotate:
                ww2 = new TTT2();
                ww2.start();
                break;
            case R.id.add:
                Intent tt=new Intent(MainActivity.this,AddNews.class);
                startActivity(tt);
                break;
        }

    }

    class TTT1 extends Thread {
        @Override
        public void run() {
            GetAllCategory();
        }
    }
    class TTT2 extends Thread {
        @Override
        public void run() {
            GetAllNews();
        }
    }

    private void GetAllCategory() {
        String nameSpace = "http://andorid.News/";//命名空间
        String methodName = "GetAllCategroy";//调用方法名称
        String soapAction = "http://andorid.News/GetAllCategroy";//SOAP Action上面两者结合
        String endPoint = "http://192.168.1.3:7777/WebService1.asmx";//提供webService的地址

        SoapObject rpc = new SoapObject(nameSpace, methodName);

        //加入参数
        //rpc.addProperty("id",id)
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
        result = object.getProperty(0).toString();//获取返回的结果

        //线程传输数据
        Message mm = Message.obtain();
        mm.what = 1;
        handler.sendMessage(mm);
    }

    private void ShowCategory() {
        //定义一个List用来存放HashMap对象
        final List<HashMap<String, Object>> categories = new ArrayList<HashMap<String, Object>>();
        //分割新闻字符串,这是在准备数据
        for (Category ss : cc) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("cid", ss.cid);
            hashMap.put("description", ss.Description);
            categories.add(hashMap);
        }
        SimpleAdapter titleAdapter = new SimpleAdapter(this,
                categories, R.layout.category_item,
                new String[]{"cid", "description"}, new int[]{R.id.category, R.id.description});

        category.setSelector(new ColorDrawable(Color.TRANSPARENT));   //设置背景为透明
        int width = categories.size() * 200;   //网格长度
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT); //获取布局参数

        category.setLayoutParams(params);  //设置参数
        category.setAdapter(titleAdapter);  //设置Adapter
    }

    private void GetAllNews()
    {
        String nameSpace = "http://andorid.News/";//命名空间
        String methodName = "GetAllNews";//调用方法名称
        String soapAction = "http://andorid.News/GetAllNews";//SOAP Action上面两者结合
        String endPoint = "http://192.168.1.3:7777/WebService1.asmx";//提供webService的地址

        SoapObject rpc = new SoapObject(nameSpace, methodName);

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
        result = object.getProperty(0).toString();//获取返回的结果

        //线程传输数据
        Message mm = Message.obtain();
        mm.what = 2;
        handler.sendMessage(mm);
    }

    private  void ShowNews()
    {
        NewsAdpter adpter=new NewsAdpter(this,R.layout.news_layout,nn);
        news_list.setAdapter(adpter);
    }
}
