package com.example.administrator.myshopliang.feature.home;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myshopliang.R;
import com.example.administrator.myshopliang.base.utils.BaseFragment;
import com.example.administrator.myshopliang.network.EShopClient;
import com.example.administrator.myshopliang.network.core.ApiPath;
import com.example.administrator.myshopliang.network.core.ResponseEntity;
import com.example.administrator.myshopliang.network.core.UICallback;
import com.example.administrator.myshopliang.network.entity.Banner;
import com.example.administrator.myshopliang.network.entity.HomeBannerRsp;
import com.example.administrator.myshopliang.network.entity.HomeCategoryRsp;
import com.example.administrator.myshopliang.network.entity.Picture;
import com.example.administrator.myshopliang.network.entity.SimpleGoods;
import com.example.administrator.myshopliang.widgets.banner.BannerAdapter;
import com.example.administrator.myshopliang.widgets.banner.BannerLayout;
import com.example.administrator.myshopliang.wrapper.PtrWrapper;
import com.example.administrator.myshopliang.wrapper.ToastWrapper;
import com.example.administrator.myshopliang.wrapper.ToolbarWrapper;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.GrayscaleTransformation;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by gqq on 2017/2/27.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.standard_toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.standard_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.list_home_goods)
    ListView mHomeGoods;
    @BindView(R.id.standard_refresh_layout)
    PtrFrameLayout mRefreshLayout;

    private ImageView[] mIvPromotes = new ImageView[4];
    private TextView mMTvPromoteGoods;
    private BannerAdapter<Banner> mBannerAdapter;
    private HomeGoodsAdapter mGoodsAdapter;
    private PtrWrapper mPtrWrapper;

    private boolean mBannerRefreshed = false;
    private boolean mCategoryRefreshed = false;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        //利用Toolbar的包装类
        new ToolbarWrapper(this).setCustomTitle(R.string.home_title);
        //利用刷新的包装类
        mPtrWrapper = new PtrWrapper(this, false) {
            @Override
            protected void onRefresh() {
                getHomeData();
            }

            @Override
            protected void onLoadMore() {

            }
        };
        mPtrWrapper.postRefreshDelayed(0);

        // 设置适配器
        mGoodsAdapter = new HomeGoodsAdapter();
        mHomeGoods.setAdapter(mGoodsAdapter);

        // ListView的头布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.partial_home_header,mHomeGoods,false);

        // 找到头布局里面的控件
        BannerLayout bannerLayout = ButterKnife.findById(view,R.id.layout_banner);
        // 数据和视图的绑定
        // TODO: 2017/2/28 图片展示待实现
        mBannerAdapter=new BannerAdapter<Banner>() {
            @Override
            protected void bind(ViewHolder holder, Banner data) {
                // 数据和视图的绑定
//                holder.mImageView.setImageResource(R.drawable.image_holder_banner);
                Picasso.with(getContext()).load(data.getPicture().getLarge()).into(holder.mImageView);
            }
        };
        bannerLayout.setAdapter(mBannerAdapter);
        // 促销商品
        mIvPromotes[0] = ButterKnife.findById(view,R.id.image_promote_one);
        mIvPromotes[1] = ButterKnife.findById(view,R.id.image_promote_two);
        mIvPromotes[2] = ButterKnife.findById(view,R.id.image_promote_three);
        mIvPromotes[3] = ButterKnife.findById(view,R.id.image_promote_four);

        // 促销单品的TextView
        mMTvPromoteGoods = ButterKnife.findById(view, R.id.text_promote_goods);

        mHomeGoods.addHeaderView(view);
    }


    private void getHomeData() {

        // 轮播图和促销单品的数据
        UICallback bannerCallback = new UICallback() {
            @Override
            public void onBusinessResponse(boolean isSucces, ResponseEntity responseEntity) {
                Toast.makeText(getContext(), "相应", Toast.LENGTH_SHORT).show();

                if (isSucces){
                    mBannerRefreshed = true;
                    // 数据拿到了，首先给bannerAdapter,另外是给促销单品
                    HomeBannerRsp bannerRsp = (HomeBannerRsp) responseEntity;
                    mBannerAdapter.reset(bannerRsp.getData().getBanners());
                    setPromoteGoods(bannerRsp.getData().getGoodsList());
                }
                if (mBannerRefreshed && mCategoryRefreshed){
                    //两个接口都拿到数据之后，停止刷新
                    mPtrWrapper.stopRefresh();
                }
            }
        };

        // 首页分类商品和推荐
        UICallback categoryCallback = new UICallback() {
            @Override
            public void onBusinessResponse(boolean isSucces, ResponseEntity responseEntity) {

                if(isSucces){
                    mCategoryRefreshed = true;
                    HomeCategoryRsp categoryRsp = (HomeCategoryRsp) responseEntity;
                    mGoodsAdapter.reset(categoryRsp.getData());
                }
                if (mBannerRefreshed && mCategoryRefreshed){
                    //两个接口都拿到数据之后，停止刷新
                    mPtrWrapper.stopRefresh();
                }
            }
        };
        EShopClient.getInstance().enqueue(ApiPath.HOME_DATA,null,HomeBannerRsp.class,bannerCallback);
        EShopClient.getInstance().enqueue(ApiPath.HOME_CATEGORY,null,HomeCategoryRsp.class,categoryCallback);
    }



    //设置促销单品的展示
    private void setPromoteGoods(List<SimpleGoods> goodsList) {
        mMTvPromoteGoods.setVisibility(View.VISIBLE);
        for (int i = 0; i < mIvPromotes.length; i++) {
            mIvPromotes[i].setVisibility(View.VISIBLE);
            final SimpleGoods simpleGoods = goodsList.get(i);
            Picture picture = simpleGoods.getImg();
//            mIvPromotes[i].setImageResource(R.drawable.image_holder_goods);

            // 圆形、灰度
            Picasso.with(getContext()).load(picture.getLarge())
                    .transform(new CropCircleTransformation())// 圆形
                    .transform(new GrayscaleTransformation())// 灰度
                    .into(mIvPromotes[i]);

            mIvPromotes[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastWrapper.show(simpleGoods.getName());
                }
            });
        }
    }

}
