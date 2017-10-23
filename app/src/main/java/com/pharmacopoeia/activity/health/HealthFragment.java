package com.pharmacopoeia.activity.health;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.pharmacopoeia.APP;
import com.pharmacopoeia.R;
import com.pharmacopoeia.TestData;
import com.pharmacopoeia.activity.main.MainActivity;
import com.pharmacopoeia.activity.main.adapter.MainSolarViewPagerFragment;
import com.pharmacopoeia.base.BaseActivity;
import com.pharmacopoeia.base.BaseLazyFragment;
import com.pharmacopoeia.bean.cache.User;
import com.pharmacopoeia.bean.model.HealthBean;
import com.pharmacopoeia.bean.model.Item;
import com.pharmacopoeia.bean.reponse.CarouselResponse;
import com.pharmacopoeia.bean.reponse.HealthResponse;
import com.pharmacopoeia.bean.reponse.ShopDetailResponse;
import com.pharmacopoeia.interfaces.adapter.HealthAdapter;
import com.pharmacopoeia.util.DateUtil;
import com.pharmacopoeia.util.SharedUtil;
import com.pharmacopoeia.util.Utils;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.view.PinnedSectionListView;
import com.pharmacopoeia.view.VerticalViewPager;
import com.pharmacopoeia.view.xlistview.XListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xus on 2017/7/12.
 * <p>
 * 养生
 */

public class HealthFragment extends BaseLazyFragment implements AdapterView.OnItemClickListener, XListView.IXListViewListener {
    private PinnedSectionListView mPullRefreshListView;
    private HealthAdapter healthAdapter;
    private boolean isRes = false;

    private List<HealthResponse> res=new ArrayList<>();
    private VerticalViewPager verticalViewPager;

    /*========== 数据相关 ===========*/
    private int mScrollerY; //滚动距离
    private int mAlpha; //透明值

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.health_fragment, container, false);
        initView(mView);
        return mView;
    }

    private void initView(View rootView) {
        LayoutInflater mInflater = LayoutInflater.from(getContext());
        verticalViewPager = (VerticalViewPager) rootView.findViewById(R.id.view_pager);

        Utils.controlViewPagerSpeed(mContext, verticalViewPager, 300);//设置你想要的时间
        View view1 = mInflater.inflate(R.layout.health_fragment_listview_top, null);
        ViewPager viewPager = (ViewPager) view1.findViewById(R.id.view_pager);
        setOnClick(viewPager);
        View view2 = mInflater.inflate(R.layout.health_fragment_listview, null);
        isRes = true;
        mPullRefreshListView = (PinnedSectionListView) view2.findViewById(R.id.pull_refresh_list);
        mPullRefreshListView.setXListViewListener(this);
        mPullRefreshListView.setPullLoadEnable(true);
        mPullRefreshListView.setPullRefreshEnable(true);
        mPullRefreshListView.setOnItemClickListener(this);
        mPullRefreshListView.initF();

        String time;
        SharedPreferences sp = getActivity().getSharedPreferences("refresh_time", Context.MODE_PRIVATE);

        time = sp.getString("rtime", null);
        if (TextUtils.isEmpty(time)) {
            time = DateUtil.getInstance().simY_M_D_H_M_S(new Date().getTime() + "");
        }
        mPullRefreshListView.setRefreshTime(time);


        List<View> list = new ArrayList<>();
        list.add(view1);
        list.add(view2);
        verticalViewPager.setAdapter(new MyPagerAdapter(list));
        verticalViewPager.setCurrentItem(1);
        verticalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    verticalViewPager.setMove(true);
                    ((MainActivity) getActivity()).setBottom(false);
                    mView.setAlpha(positionOffset);
                    ((MainActivity) getActivity()).getBottom().setAlpha(positionOffset);
                    if (positionOffset < 0.3) {
                        isRes = true;
                    }
                } else if (position == 1) {
                    mView.setAlpha(1);
                    ((MainActivity) getActivity()).getBottom().setAlpha(1);
                    if (isRes) {
                        isRes = false;
                        ((MainActivity) getActivity()).getSolar();
                    }
                    verticalViewPager.setMove(false);
                    ((MainActivity) getActivity()).setBottom(true);
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        verticalViewPager.setMove(false);
//        mPullRefreshListView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_MOVE:
//                        verticalViewPager.getParent().requestDisallowInterceptTouchEvent(true);
//                        break;
//                    case MotionEvent.ACTION_DOWN:
//                        verticalViewPager.getParent().requestDisallowInterceptTouchEvent(true);
//                        break;
//                    case MotionEvent.ACTION_UP:
//
//                    case MotionEvent.ACTION_CANCEL:
//
//                        verticalViewPager.getParent().requestDisallowInterceptTouchEvent(false);
//                        break;
//                }
//                return false;
//            }
//        });
    }

    private MainSolarViewPagerFragment mainViewPager;
    private ViewPager viewPager;

    public void getSolar(int count) {
        if (mainViewPager == null) {
            mainViewPager = new MainSolarViewPagerFragment(viewPager, getActivity());
        }
        mainViewPager.setList(count);
    }


    public void setOnClick(ViewPager view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((MainActivity) getActivity()).getViewPager().onTouchEvent(event);
                return false;
            }
        });
        viewPager = view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (healthAdapter.getItem(position - 1) == null) {
            return;
        }
        Item item = healthAdapter.getItem(position - 1);
        int type = item.type;
        HealthResponse healthContentVideoBean = (HealthResponse) item.getObject();
        switch (type) {

            case Item.SECTION:

                break;

            case Item.ITEM:
                Intent intent2 = new Intent();
                intent2.putExtra("id", healthContentVideoBean.getArticleId());
                intent2.setClass(getContext(), ArticleActivity.class);
                startActivity(intent2);
                break;

            case Item.CAROUSEL:

                break;

            case Item.VIDEO:
                Intent intent = new Intent();

                intent.putExtra("authorId", healthContentVideoBean.getAuthorId());
                intent.putExtra("videoId", healthContentVideoBean.getVideoId());

                intent.setClass(getContext(), VideoDetailActivity.class);
                startActivity(intent);
                break;

            case Item.SHOP:
                Intent intent1 = new Intent();
                intent1.putExtra("itemId", healthContentVideoBean.getItemId());
                intent1.setClass(getContext(), ShopActivity.class);
                startActivity(intent1);
                break;
        }

    }

    @Override
    protected void lazyLoad() {
        healthAdapter = new HealthAdapter(mContext);
        mPullRefreshListView.setAdapter(healthAdapter);
        getDataLunBo(true);
        getData(true);
        ((BaseActivity) getActivity()).showPross("正在加载中...");

    }

    private int pageNum = 1;

    private void getDataLunBo(final boolean refre) {

        Map<String, String> map = OkHttpUtil.getFromMap(mContext);
        OkHttpUtil.doGet(getActivity(), UrlUtil.CAROUSEL, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                List<CarouselResponse> list = (ArrayList<CarouselResponse>) o;
                healthAdapter.setList(list);
                healthAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String s) {

            }
        }, CarouselResponse.class);
    }

    private void getData(final boolean refre) {

        if (refre) {
            pageNum = 1;

        }

        Map<String, String> map = OkHttpUtil.getFromMap(mContext);
        map.put("pageNum", pageNum + "");

        OkHttpUtil.doPost(getActivity(), UrlUtil.HOME, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {


                List<HealthResponse> ress = (ArrayList<HealthResponse>) o;
                if (refre) {
                    res.clear();
                }
                res.addAll(ress);

                healthAdapter.setData(TestData.getHealthTimeBeanList(res), true);
                healthAdapter.notifyDataSetChanged();

                if (!refre) {
                    mPullRefreshListView.stopLoadMore();
                    if (ress == null) {
                        mPullRefreshListView.setPullLoadEnable(false);
                    } else if (ress.size() == 0) {
                        mPullRefreshListView.setPullLoadEnable(false);
                    }
                } else {
                    mPullRefreshListView.stopRefresh();
                }

                if (ress != null && ress.size() < 20) {
                    mPullRefreshListView.stopLoadMore();
                    mPullRefreshListView.setPullLoadEnable(false);
                    if (OkHttpUtil.getHomeBottomModel() != null) {
                        mPullRefreshListView.setBottom(OkHttpUtil.getHomeBottomModel().getBottomPic());
                        SharedUtil.saveString("app_bottom_pic", OkHttpUtil.getHomeBottomModel().getBottomPic());

                    }
                } else {
                    mPullRefreshListView.setPullLoadEnable(true);

                }
                healthAdapter.notifyDataSetChanged();
                ((BaseActivity) getActivity()).dissPross();

            }

            @Override
            public void onError(String s) {
                ((BaseActivity) getActivity()).dissPross();
                if (!refre) {
                    mPullRefreshListView.stopLoadMore();
                    pageNum--;
                } else {
                    mPullRefreshListView.stopRefresh();
                }

            }
        }, HealthResponse.class);
    }


    /**
     */
    public class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mListViews.get(arg1));

        }

        @Override
        public void finishUpdate(View arg0) {

        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
            return mListViews.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position,
                                   Object object) {
            super.setPrimaryItem(container, position, object);


        }
    }

    @Override
    public void loadJieQi() {
        verticalViewPager.setCurrentItem(0, true);
        mPullRefreshListView.stopRefresh();
    }

    @Override
    public void onRefresh() {

        String time = DateUtil.getInstance().simY_M_D_H_M_S(new Date().getTime() + "");
        SharedPreferences sp = getActivity().getSharedPreferences("refresh_time", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("rtime", time);

        mPullRefreshListView.setRefreshTime(time);
        getDataLunBo(true);
        getData(true);
    }

    @Override
    public void onLoadMore() {
        pageNum++;
        getData(false);
    }
}
