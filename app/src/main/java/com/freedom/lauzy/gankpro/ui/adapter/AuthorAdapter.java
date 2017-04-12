package com.freedom.lauzy.gankpro.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.function.entity.AuthorEntity;

import java.util.List;

public class AuthorAdapter extends BaseQuickAdapter<AuthorEntity, BaseViewHolder> {

    public AuthorAdapter(int layoutResId, List<AuthorEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AuthorEntity item) {
        helper.setText(R.id.txt_author_tip, item.getTip()).setText(R.id.txt_author_url, item.getUrl());
    }
}
