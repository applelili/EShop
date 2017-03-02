package com.example.administrator.myshopliang.feature.category;

import android.view.View;
import android.widget.TextView;


import com.example.administrator.myshopliang.R;
import com.example.administrator.myshopliang.base.utils.BaseListAdapter;
import com.example.administrator.myshopliang.network.entity.CategoryBase;

import butterknife.BindView;

/**
 * Created by gqq on 2017/2/24.
 */

// 子分类的适配器
public class ChildrenAdapter extends BaseListAdapter<CategoryBase,ChildrenAdapter.ViewHolder> {

    @Override
    protected int getItemViewLayout() {
        return R.layout.item_children_category;
    }

    @Override
    protected ViewHolder getItemViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseListAdapter.ViewHolder{
        @BindView(R.id.text_category)
        TextView mTextCategory;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(int position) {
            mTextCategory.setText(getItem(position).getName());
        }
    }
}
