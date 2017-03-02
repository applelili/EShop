package com.example.administrator.myshopliang.feature;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.myshopliang.R;
import com.example.administrator.myshopliang.base.utils.BaseActivity;
import com.example.administrator.myshopliang.base.utils.TestFragment;
import com.example.administrator.myshopliang.feature.category.CategoryFragment;
import com.example.administrator.myshopliang.feature.home.HomeFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EShopMainActivity extends BaseActivity implements OnTabSelectListener {

    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;
    private HomeFragment homeFragment;
    private CategoryFragment mCategoryFragment;
    private TestFragment mCartFragment;
    private TestFragment mMineFragment;

    private Fragment mCurrentFragment;


    protected void initView() {
        // alt+enter
        // 设置导航选择的监听事件
        mBottomBar.setOnTabSelectListener(this);
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_eshop_main;
    }

    // 底部导航栏某一项选择的时候触发
    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId){
            case R.id.tab_home:
                if (homeFragment==null){
                    homeFragment = HomeFragment.newInstance();
                }
                // 切换Fragment
                switchfragment(homeFragment);

                break;
            case R.id.tab_category:

                if (mCategoryFragment==null){
                    mCategoryFragment = CategoryFragment.newInstance();
                }
                switchfragment(mCategoryFragment);

                break;
            case R.id.tab_cart:
                if (mCartFragment==null){
                    mCartFragment = TestFragment.newInstance("CartFragment");
                }
                switchfragment(mCartFragment);
                break;
            case R.id.tab_mine:

                if (mMineFragment==null){
                    mMineFragment = TestFragment.newInstance("MineFragment");
                }
                switchfragment(mMineFragment);

                break;
            default:
                throw new UnsupportedOperationException("unsupport");
        }
    }


    // 作用：切换Fragment
    private void switchfragment(Fragment target) {
        // show hide的方式

        if (mCurrentFragment==target) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (mCurrentFragment!=null){
            transaction.hide(mCurrentFragment);
        }
        if (target.isAdded()){
            // 如果已经添加到FragmentManager里面，就展示
            transaction.show(target);
        }else {
            // 为了方便找到Fragment，我们是可以设置Tag
            String tag ;
            if(target instanceof TestFragment){
                tag=((TestFragment) target).getArgumentText();
            }else {//将类名作为tag
                tag=target.getClass().getName();
            }

            // 添加Fragment并设置Tag
            transaction.add(R.id.layout_container,target,tag);
        }

        transaction.commit();

        mCurrentFragment=target;

    }
    // 恢复因为系统重启造成的Fragmentmanager里面恢复的Fragmenti
    private void retrieveFragment() {
        FragmentManager manager = getSupportFragmentManager();
        homeFragment = (HomeFragment) manager.findFragmentByTag(homeFragment.getClass().getName());
        mCategoryFragment = (CategoryFragment) manager.findFragmentByTag(CategoryFragment.class.getName());
        mCartFragment = (TestFragment) manager.findFragmentByTag("CartFragment");
        mMineFragment = (TestFragment) manager.findFragmentByTag("MineFragment");
    }

    // 处理返回键
    @Override
    public void onBackPressed() {
        if (mCurrentFragment!=homeFragment){

            // 如果不是在首页，就切换首页上
            mBottomBar.selectTabWithId(R.id.tab_home);
            return;
        }
        // 是首页，我们不去关闭，退到后台运行
        moveTaskToBack(true);
    }
}
