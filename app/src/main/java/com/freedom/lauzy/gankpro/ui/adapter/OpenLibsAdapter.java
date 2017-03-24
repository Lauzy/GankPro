package com.freedom.lauzy.gankpro.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.function.entity.LibEntity;

import java.util.List;

/**
 * Created by Lauzy on 2017/3/24.
 */

public class OpenLibsAdapter extends BaseQuickAdapter<LibEntity, BaseViewHolder> {
    public OpenLibsAdapter(int layoutResId, List<LibEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LibEntity item) {
        helper.setText(R.id.txt_libs_item, item.getName());
    }
}
