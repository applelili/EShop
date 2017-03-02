package com.example.administrator.myshopliang.network;

import com.example.administrator.myshopliang.network.entity.CategoryRsp;
import com.example.administrator.myshopliang.network.entity.HomeBannerRsp;
import com.example.administrator.myshopliang.network.entity.HomeCategoryRsp;
import com.example.administrator.myshopliang.network.entity.SearchReq;
import com.example.administrator.myshopliang.network.entity.SearchRsp;
import com.google.gson.Gson;

import org.junit.Test;

import okhttp3.Call;
import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/2/24.
 */
public class EShopClientTest {
    @Test
    public void getSearch() throws Exception {

   /*     SearchReq searchReq = new SearchReq();
        Call call = EShopClient.getInstance().getSearch(searchReq);
        Response response = call.execute();
        String json = response.body().string();
        SearchRsp searchRsp = new Gson().fromJson(json, SearchRsp.class);*/
        SearchReq searchReq=new SearchReq();
        SearchRsp searchRsp=EShopClient.getInstance().execute("/search",searchReq,SearchRsp.class);
        // 断言方法：为我们做一个判断
        assertTrue(searchRsp.getStatus().isSucceed());
    }

    @Test
    public void getHomeBanner() throws Exception {

        HomeBannerRsp homeBannerRsp=EShopClient.getInstance().execute("/home/data", null, HomeBannerRsp.class);

        assertTrue(homeBannerRsp.getStatus().isSucceed());
    }

    @Test
    public void getHomeCategory() throws Exception {

       CategoryRsp categoryRsp=EShopClient.getInstance().execute("/home/category", null, CategoryRsp.class);
        assertTrue(categoryRsp.getStatus().isSucceed());
    }

    @Test
    public void getCategory() throws Exception {
        CategoryRsp categoryRsp = EShopClient.getInstance().execute("/category", null, CategoryRsp.class);
        assertTrue(categoryRsp.getStatus().isSucceed());
    }

}













