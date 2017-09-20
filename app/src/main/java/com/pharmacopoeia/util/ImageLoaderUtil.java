package com.pharmacopoeia.util;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by xus on 2016/7/29.
 */
public class ImageLoaderUtil {
    private static ImageLoaderUtil util;

    public synchronized static ImageLoaderUtil getInstance() {
        if (util == null) {
            util = new ImageLoaderUtil();
        }
        return util;
    }


    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .displayer(new CircleBitmapDisplayer())
            .build();

    public void loadCircleImage(String url, ImageView imageView) {
        url = isUsIP(url);

        ImageLoader.getInstance().displayImage(url, imageView, options);
    }



    public void loadCircleImage(String url, ImageView imageView,ImageLoadingListener imageLoadingListener) {
        url = isUsIP(url);
        DisplayImageOptions deOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new CircleBitmapDisplayer())
                .build();
        ImageLoader.getInstance().displayImage(url, imageView, deOptions,imageLoadingListener);
    }
    public void loadCircleImage(String url, ImageView imageView, int failId) {
        url = isUsIP(url);

        DisplayImageOptions deOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new CircleBitmapDisplayer())
                .showImageOnFail(failId)
                .showImageForEmptyUri(failId)
                .showImageOnLoading(failId)
                .build();
        ImageLoader.getInstance().displayImage(url, imageView, deOptions);
    }

    public void loadConnerImage(String url, ImageView imageView, int failId, int c) {
        url = isUsIP(url);

        DisplayImageOptions deOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new RoundedBitmapDisplayer(c))
                .showImageOnFail(failId)
                .showImageForEmptyUri(failId)
                .showImageOnLoading(failId)
                .build();
        ImageLoader.getInstance().displayImage(url, imageView, deOptions);
    }

    public void loadNomalImage(String url, ImageView imageView, int failId) {
        url = isUsIP(url);

        DisplayImageOptions deOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(failId)
                .showImageForEmptyUri(failId)
                .showImageOnLoading(failId)
                .build();
        ImageLoader.getInstance().displayImage(url, imageView, deOptions);
    }
    public void loadNomalImage(String url, ImageView imageView) {
        url = isUsIP(url);

        DisplayImageOptions deOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)

                .build();
        ImageLoader.getInstance().displayImage(url, imageView, deOptions);
    }
    public void loadNomalImage(String url, ImageView imageView,ImageLoadingListener imageLoadingListener) {
        url = isUsIP(url);
        DisplayImageOptions deOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoader.getInstance().displayImage(url, imageView, deOptions,imageLoadingListener);
    }

    public void loadNomalImageFriend(String url, ImageView imageView, int failId) {
        url = isUsIP(url);

        DisplayImageOptions deOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(failId)
                .showImageForEmptyUri(failId)
                .showImageOnLoading(failId)
                .build();

        ImageLoader.getInstance().displayImage(url, imageView, deOptions);
    }


    public void loadNomalImageFriendLOad(String url, ImageView imageView, int failId) {
        url = isUsIP(url);

        DisplayImageOptions deOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(failId)
                .showImageForEmptyUri(failId)
                .showImageOnLoading(failId)
                .build();

        ImageLoader.getInstance().displayImage(url, imageView, deOptions);
    }



    public String isUsIP(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }

        return url;
    }

    public interface OnGetBitMap {
        void onGetBitMap(Bitmap bitmap);
    }
}
