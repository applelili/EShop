package com.example.administrator.myshopliang.widgets.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.administrator.myshopliang.R;

/**
 * Created by Administrator on 2017/3/2.
 */
//搜索空间：组合控件
public class SimpleSearchView  extends LinearLayout {
    public SimpleSearchView(Context context) {
        super(context);
    }

    public SimpleSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //视图和控件的初始化
    public  void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.widget_simple_search_view,this,false);

    }
}






















