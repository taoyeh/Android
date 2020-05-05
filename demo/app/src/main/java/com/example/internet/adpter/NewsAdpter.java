package com.example.internet.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.internet.R;
import com.example.internet.model.News;


import java.util.List;

public class NewsAdpter  extends ArrayAdapter<News> {

    private  int rid;
    public  NewsAdpter(Context context, int resource, List<News> objects){
       super(context,resource,objects);
        rid=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        News data=getItem(position);  //获取position项的News实例
        View view= LayoutInflater.from(getContext()).inflate(rid,parent,false);
        TextView newid=view.findViewById(R.id.newid);
        TextView title=view.findViewById(R.id.title);
        TextView content=view.findViewById(R.id.content);
        TextView ndata=view.findViewById(R.id.datatime);
        newid.setText(data.nid+"");
        title.setText(data.title);
        content.setText("\u3000\u3000"+data.content);
        ndata.setText(data.data);
        return view;
    }
}
