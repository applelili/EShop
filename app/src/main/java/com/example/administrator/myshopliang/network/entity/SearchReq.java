package com.example.administrator.myshopliang.network.entity;


import com.example.administrator.myshopliang.network.core.RequestParam;
import com.google.gson.annotations.SerializedName;

// 搜索商品的请求体
public class SearchReq extends RequestParam {

    @SerializedName("filter") private Filter mFilter;

    @SerializedName("pagination") private Pagination mPagination;

    public void setFilter(Filter filter) {
        mFilter = filter;
    }

    public void setPagination(Pagination pagination) {
        mPagination = pagination;
    }
}
