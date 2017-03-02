package com.example.administrator.myshopliang.wrapper;

import android.content.Context;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2017/2/28.
 */
//吐司的包装类

public class ToastWrapper {

    private static  Toast mtoast;
        private static Context mContext;
    public static void init(Context context){
        mContext=context;
        mtoast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        mtoast.setDuration(Toast.LENGTH_SHORT);
    }
    public static void show(int resId,Object... args){
        String text=mContext.getString(resId,args);
        mtoast.setText(text);
        mtoast.show();
    }
    public static void show(CharSequence charSequence,Object... args){
        String text = String.format(charSequence.toString(),args);
        mtoast.setText(text);
        mtoast.show();
    }
}






















