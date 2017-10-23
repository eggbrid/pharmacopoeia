package com.pharmacopoeia;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Environment;
import android.text.TextUtils;


import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.pharmacopoeia.activity.main.LoginActivity;
import com.pharmacopoeia.activity.main.MainActivity;
import com.pharmacopoeia.bean.cache.User;
import com.pharmacopoeia.util.CrashHandler;
import com.pharmacopoeia.util.db.DBUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by xus on 2016/11/8.
 */

public class APP extends Application {
    public static APP app;

    public static APP getInstance() {
        return app;
    }

    private static User user = null;

    public synchronized static User getUser(Context context) {
        if (user == null) {
            user = DBUtil.getInstance(context).getUser();
        }
        return user;
    }

    public synchronized static boolean isLogin(Context context) {
        if (getUser(context) == null) {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            return false;
        } else {
            return true;
        }
    }

    public static void setUser(User user) {
        APP.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
        app = this;
        OkHttpUtil.initHttpUtil();
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        if (processAppName == null || !processAppName.equalsIgnoreCase(app.getPackageName())) {
            return;
        }
        initImageLoader();
        initTypeface();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        if(getUser(this) != null){
            Set<String> strings=new HashSet<>();
            strings.add(getUser(this).getUserCode());
            JPushInterface.addTags(this,1,strings);
        }
    }

    private void initTypeface() {





//        try {
//            Field field = Typeface.class.getDeclaredField("SERIF");
//            field.setAccessible(true);
//            field.set(null, Typeface.createFromAsset(getAssets(), "fonts/light.ttc"));
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
    }

    public void initImageLoader() {
        String filePath = Environment.getExternalStorageDirectory() + "/Android/data/" + this.getPackageName() + "/cache/";

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = this.getExternalFilesDir("cache");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            filePath=dir.getPath();
        }
//        String filePath = "Android/data/com.com.pharmacopoeia/files/cache/img/";
        File cacheDir = StorageUtils.getOwnCacheDirectory(this, filePath);// 获取到缓存的目录地址
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.threadPoolSize(3);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.memoryCache(new WeakMemoryCache());
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.memoryCacheExtraOptions(800, 600);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.diskCache(new UnlimitedDiskCache(cacheDir));
        ImageLoader.getInstance().init(config.build());
        L.writeLogs(false);
    }


    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

}
