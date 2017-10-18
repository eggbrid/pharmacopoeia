package com.pharmacopoeia;

import com.mob.tools.utils.Data;
import com.pharmacopoeia.bean.model.HealthBean;
import com.pharmacopoeia.bean.model.HealthContentArticleBean;
import com.pharmacopoeia.bean.model.HealthContentShopBean;
import com.pharmacopoeia.bean.model.HealthContentVideoBean;
import com.pharmacopoeia.bean.model.HealthTimeBean;
import com.pharmacopoeia.bean.model.ShopCommentModel;
import com.pharmacopoeia.bean.model.VideoDetailModel;
import com.pharmacopoeia.bean.model.WebBeanModel;
import com.pharmacopoeia.bean.reponse.HealthResponse;
import com.pharmacopoeia.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Luhao on 2016/6/12.
 */
public class TestData {

    public static int getSize(List<HealthTimeBean> list, boolean isFrist) {
        int i = 0;
        if (isFrist) {
            i++;
        }
        if (list != null && list.size() != 0) {
            i += list.size();
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getHealthResponses() != null) {
                    i += list.get(j).getHealthResponses().size();
                }
            }
        }
        return i;
    }

    public static List<HealthTimeBean> getHealthTimeBeanList(List<HealthResponse> res) {
        List<HealthTimeBean> list = new ArrayList<>();
        if (res != null) {
            int size = res.size();
            String time = null;
            List<HealthResponse> healthResponses = null;
            for (int i = 0; i < size; i++) {


                HealthTimeBean healthTimeBean = new HealthTimeBean();
                HealthResponse healthResponse = res.get(i);
                Date date = DateUtil.getInstance().parseY_M_D_H_M_S(healthResponse.getLastTime());

                healthTimeBean.setTime(DateUtil.getInstance().simDD_SMM_SYY(date));

                if (time == null) {

                    time = getTime(healthResponse.getLastTime());

                    healthResponses = new ArrayList<>();
                    healthResponses.add(healthResponse);
                    healthTimeBean.setHealthResponses(healthResponses);
                    list.add(healthTimeBean);

                } else {
                    String t = getTime(healthResponse.getLastTime());
                    if (time.equals(t)) {
                        healthResponses.add(healthResponse);
                    } else {
                        healthResponses = new ArrayList<>();
                        healthResponses.add(healthResponse);
                        healthTimeBean.setHealthResponses(healthResponses);
                        list.add(healthTimeBean);
                    }
                    time = t;
                }


            }
        }

        return list;
    }

    public static String getTime(String time) {
        String[] strings = time.split(" ");
        return strings[0];
    }

    /**
     * 商品
     *
     * @return
     */
    public static ArrayList<HealthContentShopBean> getMoreListShop() {
        ArrayList<HealthContentShopBean> arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HealthContentShopBean healthContentShopBean = new HealthContentShopBean();
            healthContentShopBean.setId(i + "");
            healthContentShopBean.setList(getList(2));
            healthContentShopBean.setShopBuys("100人已用");
            healthContentShopBean.setShopMoney("200");
            healthContentShopBean.setShopName("大力丸,十全大力丸");
            healthContentShopBean.setShopUrl("http://imgsrc.baidu.com/baike/pic/item/0d968f23fd274d1bac34deff.jpg");
            arrayList.add(healthContentShopBean);
        }
        return arrayList;
    }

    /**
     * 商品
     *
     * @return
     */
    public static ArrayList<HealthContentShopBean> getListShop() {
        ArrayList<HealthContentShopBean> arrayList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            HealthContentShopBean healthContentShopBean = new HealthContentShopBean();
            healthContentShopBean.setId(i + "");
            healthContentShopBean.setList(getList(2));
            healthContentShopBean.setShopBuys("100人已用");
            healthContentShopBean.setShopMoney("200");
            healthContentShopBean.setShopName("大力丸,十全大力丸");
            healthContentShopBean.setShopUrl("http://imgsrc.baidu.com/baike/pic/item/0d968f23fd274d1bac34deff.jpg");
            arrayList.add(healthContentShopBean);
        }
        return arrayList;
    }


    /**
     * 视频
     *
     * @return
     */
    public static ArrayList<HealthContentVideoBean> getListVideo(String video) {
        ArrayList<HealthContentVideoBean> arrayList = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            HealthContentVideoBean healthContentVideoBean = new HealthContentVideoBean();
            healthContentVideoBean.setId(i + "");
            healthContentVideoBean.setCompanyName("同仁堂");
            healthContentVideoBean.setSelects("400");
            healthContentVideoBean.setCompanyUrl("http://img4.imgtn.bdimg.com/it/u=1904576765,801430751&fm=26&gp=0.jpg");
            healthContentVideoBean.setCompanyVideo("http://img0.imgtn.bdimg.com/it/u=3074975953,1037799635&fm=26&gp=0.jpg");
            healthContentVideoBean.setVideo(video);
            arrayList.add(healthContentVideoBean);
        }
        return arrayList;
    }

    /**
     * 文章
     *
     * @return
     */
    public static ArrayList<HealthContentArticleBean> getListArticle() {
        ArrayList<HealthContentArticleBean> articleBeen = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            HealthContentArticleBean healthContentArticleBean = new HealthContentArticleBean();
            healthContentArticleBean.setArticleId(i + "");
            healthContentArticleBean.setArticleTitle("经常步行的人，最终会有这样的结果！");
            healthContentArticleBean.setTags("癌症,去痛去肿");
            healthContentArticleBean.setArticleView("100");
            healthContentArticleBean.setUrl("http://img2.imgtn.bdimg.com/it/u=1573684747,549798751&fm=26&gp=0.jpg");
            healthContentArticleBean.setAuthorName("刘旭");
            articleBeen.add(healthContentArticleBean);
        }

        return articleBeen;
    }

    public static List<String> getList(int type) {
        List<String> list = new ArrayList<>();
        if (type == 1) {
            list.add("癌症");
            list.add("去痛去肿");
        } else {
            list.add("长力气");
            list.add("补气");
        }
        return list;
    }

    /**
     * 轮播图
     *
     * @return
     */
    public static ArrayList<WebBeanModel> getListWeb() {
        ArrayList<WebBeanModel> webBeanModelList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            WebBeanModel webBeanModel = new WebBeanModel();
            webBeanModel.setTitle("北京百度");
            webBeanModel.setUrl("http://www.baidu.com");
            if (i == 0) {
                webBeanModel.setPic("http://spe.zwbk.org/file/upload/201405/29/10-26-23-14-10.jpg");
            } else if (i == 1) {
                webBeanModel.setPic("http://pic.bestb2b.com/6a1951867a90c455dad0bd9207dab35b.jpg");
            } else {
                webBeanModel.setPic("http://pic.bestb2b.com/6a1951867a90c455dad0bd9207dab35b.jpg");
            }
            webBeanModelList.add(webBeanModel);
        }
        return webBeanModelList;
    }

    /**
     * 评论
     */
    public static ArrayList<ShopCommentModel> getListShopComment() {
        ArrayList<ShopCommentModel> arrayList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ShopCommentModel shopCommentModel = new ShopCommentModel();
            shopCommentModel.setUserId("" + i);
            shopCommentModel.setId("" + i);
            shopCommentModel.setComment("中医很注重养生的");
            shopCommentModel.setTime("一个小时前");
            shopCommentModel.setUserUrl("http://img2.imgtn.bdimg.com/it/u=1573684747,549798751&fm=26&gp=0.jpg");
            shopCommentModel.setUserName("刘旭");
            arrayList.add(shopCommentModel);
        }
        return arrayList;
    }

    /**
     * 商品详情页
     *
     * @return
     */
    public static VideoDetailModel getVideoDetailModel() {
        VideoDetailModel videoDetailModel = new VideoDetailModel();
        videoDetailModel.setHealthContentArticleBeen(getListArticle());
        videoDetailModel.setHealthContentShopBeen(getListShop());
        videoDetailModel.setShopCommentModels(getListShopComment());
        return videoDetailModel;
    }


}
