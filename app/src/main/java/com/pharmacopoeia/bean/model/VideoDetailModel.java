package com.pharmacopoeia.bean.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 52243 on 2017/7/27.
 */

public class VideoDetailModel {

    private ArrayList<HealthContentArticleBean> healthContentArticleBeen;
    private ArrayList<HealthContentShopBean> healthContentShopBeen;
    private ArrayList<ShopCommentModel> shopCommentModels;

    public ArrayList<HealthContentArticleBean> getHealthContentArticleBeen() {
        return healthContentArticleBeen;
    }

    public void setHealthContentArticleBeen(ArrayList<HealthContentArticleBean> healthContentArticleBeen) {
        this.healthContentArticleBeen = healthContentArticleBeen;
    }

    public ArrayList<HealthContentShopBean> getHealthContentShopBeen() {
        return healthContentShopBeen;
    }

    public void setHealthContentShopBeen(ArrayList<HealthContentShopBean> healthContentShopBeen) {
        this.healthContentShopBeen = healthContentShopBeen;
    }

    public ArrayList<ShopCommentModel> getShopCommentModels() {
        return shopCommentModels;
    }

    public void setShopCommentModels(ArrayList<ShopCommentModel> shopCommentModels) {
        this.shopCommentModels = shopCommentModels;
    }
}
