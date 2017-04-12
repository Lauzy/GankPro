package com.freedom.lauzy.gankpro.contract;

/**
 * 可设置Contract来放置MVP的接口，减少类文件数量
 * 此项目暂未使用
 */
public interface Contract {

    //下为简单实例
    /*interface HomeView extends BaseView {
        void initData(HomeEntity homeEntity);

        void refreshData(HomeEntity homeEntity);

        void initErr(Exception e);

        void refreshErr(Exception e);
    }

    interface IHomePresenter {
        void initData();

        void refreshData();
    }

    interface IHomeModel {
        void getHomeData(Context context, HomeListener homeListener);
    }

    interface HomeListener {
        void onSuccess(HomeEntity homeEntity);

        void onError(Exception e);
    }*/
}
