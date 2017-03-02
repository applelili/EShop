package com.example.administrator.myshopliang.feature.search;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.myshopliang.R;
import com.example.administrator.myshopliang.base.utils.BaseActivity;
import com.example.administrator.myshopliang.network.entity.Filter;
import com.example.administrator.myshopliang.wrapper.ToolbarWrapper;
import com.google.gson.Gson;

/**
 * Created by gqq on 2017/3/2.
 */

// 搜索商品页面
public class SearchGoodsActivity extends BaseActivity {

    private static final String EXTRA_SEARCH_FILTER = "EXTRA_SEARCH_FILTER";

    // 因为需要传递数据，为了规范我们传递的数据内容，所以我们在此页面对外提供一个跳转的方法
    public static Intent getStartIntent(Context context, Filter filter){
        Intent intent = new Intent(context,SearchGoodsActivity.class);
        intent.putExtra(EXTRA_SEARCH_FILTER,new Gson().toJson(filter));
        return intent;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_search_goods;
    }

    @Override
    protected void initView() {

        //toolbar的展示
        new ToolbarWrapper(this);
    }
}
