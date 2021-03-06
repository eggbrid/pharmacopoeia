package com.pharmacopoeia.activity.health;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pharmacopoeia.APP;
import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.health.adapter.ImageAdapter;
import com.pharmacopoeia.base.BaseActivity;
import com.pharmacopoeia.base.BaseLazyFragment;
import com.pharmacopoeia.bean.cache.User;
import com.pharmacopoeia.bean.model.Commentbean;
import com.pharmacopoeia.bean.model.ShopPropertiesModel;
import com.pharmacopoeia.bean.reponse.ShopDetailResponse;
import com.pharmacopoeia.bean.reponse.VideoDetailResponse;
import com.pharmacopoeia.interfaces.adapter.ShopDetailExplainAdapter;
import com.pharmacopoeia.interfaces.adapter.ShopDetailMethodAdapter;
import com.pharmacopoeia.util.ImageLoaderUtil;
import com.pharmacopoeia.util.T;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.view.CustomGridView;
import com.pharmacopoeia.view.CustomListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by 52243 on 2017/7/21.
 */

public class ShopDetailFragment extends BaseLazyFragment {

    private ScrollView scroll;
    private CustomListView list;
    private CustomGridView grid_view;
    private ImageView shop_image, image_user, image_from, image_element, image_people, image_method;//商品照片,评论用户头像，处方来源,成分，适用人群,视频照片 食用方法
    private View health_shop_detail_from_fragment_item, health_shop_detail_method_fragment_item, health_shop_detail_element_fragment_item, health_shop_detail_people_fragment_item, health_shop_detail_video_fragment_item;//商品照片,评论用户头像，处方来源,成分，适用人群,视频照片 食用方法
    private CustomListView bjp;

    private TextView text_name, text_comment, time, p_info;//評論人,评论内容,时间

    private JCVideoPlayerStandard videoplayer;
    private ShopDetailMethodAdapter shopDetailMethodAdapter;

    private ShopDetailExplainAdapter shopDetailExplainAdapter;

    private View lin_com;//评论view


    private TextView shop_name, text_tags;//商品名称,标签

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.health_shop_detail_fragment, container, false);
        list = (CustomListView) mView.findViewById(R.id.list);
        grid_view = (CustomGridView) mView.findViewById(R.id.grid_view);
        scroll = (ScrollView) mView.findViewById(R.id.scroll);
        shop_image = (ImageView) mView.findViewById(R.id.image);
        image_user = (ImageView) mView.findViewById(R.id.image_user);
        image_from = (ImageView) mView.findViewById(R.id.image_from);
        image_element = (ImageView) mView.findViewById(R.id.image_element);
        image_people = (ImageView) mView.findViewById(R.id.image_people);
        bjp = (CustomListView) mView.findViewById(R.id.bjp);
        shop_name = (TextView) mView.findViewById(R.id.shop_name);
        image_method = (ImageView) mView.findViewById(R.id.image_method);
        lin_com = mView.findViewById(R.id.lin_com);
        p_info = (TextView) mView.findViewById(R.id.p_info);
        text_name = (TextView) mView.findViewById(R.id.text_name);
        text_comment = (TextView) mView.findViewById(R.id.text_comment);
        time = (TextView) mView.findViewById(R.id.time);

        text_tags = (TextView) mView.findViewById(R.id.text_tags);
        videoplayer = (JCVideoPlayerStandard) mView.findViewById(R.id.videoplayer);

        health_shop_detail_from_fragment_item = mView.findViewById(R.id.health_shop_detail_from_fragment_item);
        health_shop_detail_method_fragment_item = mView.findViewById(R.id.health_shop_detail_method_fragment_item);
        health_shop_detail_element_fragment_item = mView.findViewById(R.id.health_shop_detail_element_fragment_item);
        health_shop_detail_people_fragment_item = mView.findViewById(R.id.health_shop_detail_people_fragment_item);
        health_shop_detail_video_fragment_item = mView.findViewById(R.id.health_shop_detail_video_fragment_item);

        return mView;

    }


    private void getData() {
        ((BaseActivity) getActivity()).showPross("正在加载商品详情");
        Map<String, String> map = OkHttpUtil.getFromMap(mContext);
        if (APP.getInstance().getUser(mContext) != null) {
            User user = APP.getInstance().getUser(mContext);
            map.put("userCode", user.getUserCode());
        }
        map.put("itemId", ((ShopActivity) getActivity()).itemId);

        OkHttpUtil.doPost(getActivity(), UrlUtil.SHOPDETAIL, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                ((BaseActivity) getActivity()).dissPross();
                ShopDetailResponse res = (ShopDetailResponse) o;
                if (res != null) {
                    scroll.setVisibility(View.VISIBLE);
                    ShopApplyFragment.appContent = res.getApplication();
                    setData(res);
                }
            }

            @Override
            public void onError(String s) {
                ((BaseActivity) getActivity()).dissPross();
                T.show(getActivity(), s);
            }
        }, ShopDetailResponse.class);
    }

    public void setData(ShopDetailResponse res) {

        shop_name.setText(res.getItemName());
        text_tags.setText(res.getTags());
        ImageLoaderUtil.getInstance().loadNomalImage(res.getDetailPics(), shop_image);

        if (res.getCommentList() != null && res.getCommentList().size() != 0) {
//            ImageLoaderUtil.getInstance().loadCircleImage("", image_user);
            image_user.setImageResource(R.drawable.default_avatar);
            lin_com.setVisibility(View.VISIBLE);
            Commentbean commentbean = res.getCommentList().get(0);
            text_name.setText(commentbean.getNickName());
            text_comment.setText(commentbean.getCommentContent());
            time.setText(commentbean.getCreateTime());
        } else {
            lin_com.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(res.getPresSourcePic())) {
            health_shop_detail_from_fragment_item.setVisibility(View.GONE);
        } else {
            if ("1".equals(res.getItemType())) {
                health_shop_detail_from_fragment_item.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(res.getPresSourcePic())) {
                    ImageAdapter adapter = new ImageAdapter(getActivity(), res.getPresSourcePic().split(","));
                    bjp.setAdapter(adapter);
                }

            } else {
                health_shop_detail_from_fragment_item.setVisibility(View.VISIBLE);
                ImageLoaderUtil.getInstance().loadNomalImage(res.getPresSourcePic(), image_from);
            }
        }
        health_shop_detail_people_fragment_item = mView.findViewById(R.id.health_shop_detail_people_fragment_item);
        if (TextUtils.isEmpty(res.getConstituentPic())) {
            health_shop_detail_method_fragment_item.setVisibility(View.GONE);
        } else {
            health_shop_detail_method_fragment_item.setVisibility(View.VISIBLE);
            ImageLoaderUtil.getInstance().loadNomalImage(res.getConstituentPic(), image_element);
        }
        if (TextUtils.isEmpty(res.getEdibleMethodPic())) {
            health_shop_detail_element_fragment_item.setVisibility(View.GONE);
        } else {
            health_shop_detail_element_fragment_item.setVisibility(View.VISIBLE);
            ImageLoaderUtil.getInstance().loadNomalImage(res.getEdibleMethodPic(), image_method);
        }
        if (TextUtils.isEmpty(res.getForPeoplePic())) {
            health_shop_detail_people_fragment_item.setVisibility(View.GONE);
        } else {
            health_shop_detail_people_fragment_item.setVisibility(View.VISIBLE);
            ImageLoaderUtil.getInstance().loadNomalImage(res.getForPeoplePic(), image_people);
        }
        if (TextUtils.isEmpty(res.getVideoUrl())) {

            health_shop_detail_video_fragment_item.setVisibility(View.GONE);
        } else {
            videoplayer.setUp(res.getVideoUrl()
                    , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
            JCVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            ImageLoaderUtil.getInstance().loadNomalImage(res.getVideoPic(), videoplayer.thumbImageView);
            health_shop_detail_video_fragment_item.setVisibility(View.VISIBLE);
        }


//        videoplayer.setUp("http://mvideo.spriteapp.cn/video/2017/0629/d5c26b9c-5c92-11e7-a4e7-1866daeb0df1cut_wpc.mp4"
//                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
//        JCVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
//        ImageLoaderUtil.getInstance().loadNomalImage("http://img1.imgtn.bdimg.com/it/u=3070256308,1996322359&fm=11&gp=0.jpg", videoplayer.thumbImageView);

//        setGridView();
        setExplain(res);

    }

    @Override
    protected void lazyLoad() {
        getData();
    }

//    public void setGridView() {
//        shopDetailMethodAdapter = new ShopDetailMethodAdapter(getContext(), null);
//        grid_view.setAdapter(shopDetailMethodAdapter);
//        List<String> arrayList = new ArrayList<>();
//        arrayList.add("http://img1.imgtn.bdimg.com/it/u=3070256308,1996322359&fm=11&gp=0.jpg");
//        arrayList.add("http://img4.imgtn.bdimg.com/it/u=2391873113,657542441&fm=26&gp=0.jpg");
//        arrayList.add("http://img4.imgtn.bdimg.com/it/u=2581142571,991828975&fm=26&gp=0.jpg");
//        shopDetailMethodAdapter.setList(arrayList);
//        shopDetailMethodAdapter.notifyDataSetChanged();
//    }


    public void setExplain(ShopDetailResponse res) {

        shopDetailExplainAdapter = new ShopDetailExplainAdapter(getContext(), null);
        list.setAdapter(shopDetailExplainAdapter);


        if (res.getProperties() != null && res.getProperties().size() != 0) {
            int size = res.getProperties().size();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View headerView = layoutInflater.inflate(R.layout.health_shop_detail_explain_fragment_header, null);
            TextView text_name = (TextView) headerView.findViewById(R.id.text_name);
            list.addHeaderView(headerView);
            list.addFooterView(layoutInflater.inflate(R.layout.health_shop_detail_explain_fragment_footer, null));

            text_name.setText(res.getItemName());
            List<ExplainModel> arrList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                ExplainModel explainModel = new ExplainModel();
                ShopPropertiesModel shopPropertiesModel = res.getProperties().get(i);
                explainModel.setName(shopPropertiesModel.getPropertyKey());
                explainModel.setDesc(shopPropertiesModel.getPropertyValue());
                arrList.add(explainModel);
            }
            shopDetailExplainAdapter.setList(arrList);
            shopDetailExplainAdapter.notifyDataSetChanged();
            p_info.setVisibility(View.VISIBLE);
            list.setVisibility(View.VISIBLE);
        } else {
            p_info.setVisibility(View.GONE);
            list.setVisibility(View.GONE);
        }
    }

    public class ExplainModel {
        private String name;
        private String desc;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
