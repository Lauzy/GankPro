package com.freedom.lauzy.gankpro.model;

import android.content.Context;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.function.entity.LibEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lauzy on 2017/3/24.
 */

public class LibsModel {
    public List<LibEntity> getLibsData(Context context) {
        List<LibEntity> libEntities = new ArrayList<>();
        String[] libNames = context.getResources().getStringArray(R.array.LibsArr);
        String[] libLinks = context.getResources().getStringArray(R.array.LibsLinkArr);
        for (int i = 0; i < libNames.length; i++) {
            LibEntity libEntity = new LibEntity(libNames[i], libLinks[i]);
            libEntities.add(libEntity);
        }
        return libEntities;
    }
}
