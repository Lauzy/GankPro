package com.freedom.lauzy.gankpro.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.function.entity.CollectionEntity;

import java.util.List;

/**
 * Created by Lauzy on 2017/3/23.
 */

public class CollectionAdapter extends BaseQuickAdapter<CollectionEntity, BaseViewHolder> {
    public CollectionAdapter(int layoutResId, List<CollectionEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final CollectionEntity item) {
        helper.setText(R.id.txt_collection_content, item.getDesc());
    }
}
