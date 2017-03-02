package com.example.administrator.myshopliang.feature.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myshopliang.R;
import com.example.administrator.myshopliang.base.utils.BaseListAdapter;
import com.example.administrator.myshopliang.network.entity.CategoryHome;
import com.example.administrator.myshopliang.network.entity.Picture;
import com.example.administrator.myshopliang.network.entity.SimpleGoods;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by gqq on 2017/2/28.
 */
// 首页：推荐商品的适配器
public class HomeGoodsAdapter extends BaseListAdapter<CategoryHome,HomeGoodsAdapter.ViewHolder> {

    @Override
    protected int getItemViewLayout() {
        return R.layout.item_home_goods;
    }

    @Override
    protected ViewHolder getItemViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseListAdapter.ViewHolder{

        @BindView(R.id.text_category)
        TextView mTvCategory;

        @BindViews({
                R.id.image_goods_01,
                R.id.image_goods_02,
                R.id.image_goods_03,
                R.id.image_goods_04})
        ImageView[] mImageViews;
        private CategoryHome mCategoryHome;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(int position) {
            mCategoryHome = getItem(position);
            mTvCategory.setText(mCategoryHome.getName());
            final List<SimpleGoods> goodsList = mCategoryHome.getHotGoodsList();
            for (int i = 0; i < mImageViews.length; i++) {

                // 取出商品List里面的商品图片
                Picture picture = goodsList.get(i).getImg();
//                mImageViews[i].setImageResource(R.drawable.image_holder_goods);
                // Picasso加载图片
                Picasso.with(getContext()).load(picture.getLarge()).into(mImageViews[i]);

                final int index = i;
                // 设置点击事件
                mImageViews[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SimpleGoods simpleGoods = goodsList.get(index);
                        Toast.makeText(getContext(), simpleGoods.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        @OnClick(R.id.text_category)
        void onclick(){
            Toast.makeText(getContext(),mCategoryHome.getName() , Toast.LENGTH_SHORT).show();
        }
    }

}
