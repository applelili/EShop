package com.example.administrator.myshopliang.feature.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.myshopliang.R;
import com.example.administrator.myshopliang.feature.search.SearchGoodsActivity;
import com.example.administrator.myshopliang.network.core.ApiPath;
import com.example.administrator.myshopliang.network.core.ResponseEntity;
import com.example.administrator.myshopliang.network.entity.CategoryPrimary;
import com.example.administrator.myshopliang.network.entity.CategoryRsp;
import com.example.administrator.myshopliang.network.EShopClient;
import com.example.administrator.myshopliang.network.core.UICallback;
import com.example.administrator.myshopliang.network.entity.Filter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by gqq on 2017/2/23.
 */

public class CategoryFragment extends Fragment {


    @BindView(R.id.standard_toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.standard_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.list_category)
    ListView mListCategory;
    @BindView(R.id.list_children)
    ListView mListChildren;

    private List<CategoryPrimary> mData;
    private CategoryAdapter mCategoryAdapter;
    private ChildrenAdapter mChildrenAdapter;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 初始化视图的操作
        initView();
    }

    private void initView() {
        initToolbar();
        // ListView的展示
        mCategoryAdapter = new CategoryAdapter();
        mListCategory.setAdapter(mCategoryAdapter);

        mChildrenAdapter = new ChildrenAdapter();
        mListChildren.setAdapter(mChildrenAdapter);
        //拿到数据
        if(mData!=null){
            //可以直接更新ui
            updateCategory();
        }else{
            //去进行网络请求拿到数据
            UICallback uiCallback = new UICallback() {
                @Override
                public void onBusinessResponse(boolean isSucces, ResponseEntity responseEntity) {
                    if (isSucces) {
                        CategoryRsp categoryRsp = (CategoryRsp) responseEntity;
                        mData = categoryRsp.getData();
                        // 数据有了之后，数据给一级分类，默认选择第一条，二级分类才能展示
                        updateCategory();
                    }
                }
            };
            EShopClient.getInstance().enqueue(ApiPath.CATEGORY,null,CategoryRsp.class,uiCallback);
        }

    }
//更新分类数据
    private void updateCategory() {
        mCategoryAdapter.reset(mData);
        //切换展示二级分类
        chooseCategory(0);
    }
//用于根据一级分类的选项展示二级分类的内容
    private void chooseCategory(int position) {
        mListCategory.setItemChecked(position,true);
        mChildrenAdapter.reset(mCategoryAdapter.getItem(position).getChildren());
    }
    //点击一级分类，展示相应二级分类
    @OnItemClick(R.id.list_category)
    public void onItemClick(int position){
        chooseCategory(position);
    }
    //点击二级分类
    @OnItemClick(R.id.list_children)
    public void onChildrenClick(int position){
        // 跳转到搜索页面
        int categoryId = mChildrenAdapter.getItem(position).getId();
        navigateToSearch(categoryId);

    }

    private void navigateToSearch(int categoryId) {
        // 根据id构建Filter，然后跳转页面
        Filter filter = new Filter();
        filter.setCategoryId(categoryId);
        Intent intent = SearchGoodsActivity.getStartIntent(getContext(), filter);
        getActivity().startActivity(intent);
    }

    private void initToolbar() {
        //Fragment显示选项菜单
        setHasOptionsMenu(true);
        // 处理toolbar
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        // 处理actionbar不展示默认的标题
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        // 设置左上方的返回箭头
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbarTitle.setText(R.string.category_title);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_category, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        // 返回箭头
        if (itemId == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }

        if (itemId == R.id.menu_search) {

            int position = mListCategory.getCheckedItemPosition();
            int id = mCategoryAdapter.getItem(position).getId();
            navigateToSearch(id);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
