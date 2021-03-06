package com.xiey.im.im.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.github.moduth.blockcanary.internal.BlockInfo;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by HarrysTT on 2017/3/2.
 */

public class GlobalApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        /*initLeakCanary start*/
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        /*initLeakCanary end*/
        /*initBlockCanary start*/
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
        /*initBlockCanary end*/

        Stetho.initializeWithDefaults(this);

        ZXingLibrary.initDisplayOpinion(this);

        Logger.init().logLevel(LogLevel.FULL);

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(String message) {
//                Logger.d("okhttp Log" + message);
//            }
//        });
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()

//                .addInterceptor(interceptor)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public class AppBlockCanaryContext extends BlockCanaryContext {

        /**
         * Implement in your project.
         *
         * @return Qualifier which can specify this installation, like version + flavor.
         */
        public String provideQualifier() {
            return "unknown";
        }

        /**
         * Implement in your project.
         *
         * @return user id
         */
        public String provideUid() {
            return "uid";
        }

        /**
         * Network type
         *
         * @return {@link String} like 2G, 3G, 4G, wifi, etc.
         */
        public String provideNetworkType() {
            return "unknown";
        }

        /**
         * Config monitor duration, after this time BlockCanary will stop, use
         * with {@code BlockCanary}'s isMonitorDurationEnd
         *
         * @return monitor last duration (in hour)
         */
        public int provideMonitorDuration() {
            return -1;
        }

        /**
         * Config block threshold (in millis), dispatch over this duration is regarded as a BLOCK. You may set it
         * from performance of device.
         *
         * @return threshold in mills
         */
        public int provideBlockThreshold() {
            return 1000;
        }

        /**
         * Thread stack dump interval, use when block happens, BlockCanary will dump on main thread
         * stack according to current sample cycle.
         * <p>
         * Because the implementation mechanism of Looper, real dump interval would be longer than
         * the period specified here (especially when cpu is busier).
         * </p>
         *
         * @return dump interval (in millis)
         */
        public int provideDumpInterval() {
            return provideBlockThreshold();
        }

        /**
         * Path to save log, like "/blockcanary/", will save to sdcard if can.
         *
         * @return path of log files
         */
        public String providePath() {
            return "/blockcanary/";
        }

        /**
         * If need notification to notice block.
         *
         * @return true if need, else if not need.
         */
        public boolean displayNotification() {
            return true;
        }

        /**
         * Implement in your project, bundle files into a zip file.
         *
         * @param src  files before compress
         * @param dest files compressed
         * @return true if compression is successful
         */
        public boolean zip(File[] src, File dest) {
            return false;
        }

        /**
         * Implement in your project, bundled log files.
         *
         * @param zippedFile zipped file
         */
        public void upload(File zippedFile) {
            throw new UnsupportedOperationException();
        }


        /**
         * Packages that developer concern, by default it uses process name,
         * put high priority one in pre-order.
         *
         * @return null if simply concern only package with process name.
         */
        public List<String> concernPackages() {
            return null;
        }

        /**
         * Filter stack without any in concern package, used with @{code concernPackages}.
         *
         * @return true if filter, false it not.
         */
        public boolean filterNonConcernStack() {
            return false;
        }

        /**
         * Provide white list, entry in white list will not be shown in ui list.
         *
         * @return return null if you don't need white-list filter.
         */
        public List<String> provideWhiteList() {
            LinkedList<String> whiteList = new LinkedList<>();
            whiteList.add("org.chromium");
            return whiteList;
        }

        /**
         * Whether to delete files whose stack is in white list, used with white-list.
         *
         * @return true if delete, false it not.
         */
        public boolean deleteFilesInWhiteList() {
            return true;
        }

        /**
         * Block interceptor, developer may provide their own actions.
         */
        public void onBlock(Context context, BlockInfo blockInfo) {

        }
    }
}
