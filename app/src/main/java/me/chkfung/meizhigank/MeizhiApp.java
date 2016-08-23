package me.chkfung.meizhigank;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

import java.io.IOException;

import me.chkfung.meizhigank.Dagger.Component.DaggerNetComponent;
import me.chkfung.meizhigank.Dagger.Component.NetComponent;
import me.chkfung.meizhigank.Dagger.Module.AppModule;
import me.chkfung.meizhigank.Dagger.Module.NetModule;
import me.chkfung.meizhigank.Util.ConnectionUtil;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Fung on 21/07/2016.
 */

public class MeizhiApp extends Application {

    private NetworkApi networkApi;
    private Scheduler defaultSubscribeScheduler;
    private OkHttpClient okHttpClient;
    private Cache cache;
    private HttpLoggingInterceptor httpLoggingInterceptor;
    private Interceptor interceptor;
    private GsonConverterFactory gsonConverterFactory;
    private NetComponent netComponent;

    public static MeizhiApp get(Context context) {
        return (MeizhiApp) context.getApplicationContext();
    }

    /**
     * Get Retrofit Singleton
     *
     * @return Retrofit Interface
     */
    public NetworkApi getNetworkApi() {
        if (networkApi == null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gank.io/")
                    .addConverterFactory(getGsonFactory())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
            networkApi = retrofit.create(NetworkApi.class);
        }
        return networkApi;
    }

    /**
     * Get OkHttpClient Singleton
     *
     * @return OkHttpClient
     */
    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            okHttpClientBuilder.cache(getCache());
            okHttpClientBuilder.addInterceptor(getHttpLoggingInterceptor());
            okHttpClientBuilder.addInterceptor(getCachingInterceptor());
            okHttpClient = okHttpClientBuilder.build();
        }
        return okHttpClient;
    }

    public Scheduler getDefaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null)
            defaultSubscribeScheduler = Schedulers.io();
        return defaultSubscribeScheduler;
    }

    private Cache getCache() {
        int cacheSize = 10 * 1024 * 1024;//10mb
        if (cache == null)
            cache = new Cache(getCacheDir(), cacheSize);
        return cache;
    }

    /**
     * Log Network Response
     *
     * @return Logging Interceptor
     */
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        if (httpLoggingInterceptor == null) {
            httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Logger.t(5).i(message);
                }
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return httpLoggingInterceptor;
    }

    /**
     * Cache Are available when Network Connection is not available
     *
     * @return Caching Interceptor
     */
    private Interceptor getCachingInterceptor() {
        if (interceptor == null) {
            interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (ConnectionUtil.isNetworkAvailable(getApplicationContext())) {
                        request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                    } else {
                        request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                    }
                    return chain.proceed(request);
                }
            };
        }
        return interceptor;
    }

    private GsonConverterFactory getGsonFactory() {
        if (gsonConverterFactory == null) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
            gsonConverterFactory = GsonConverterFactory.create(gson);
        }
        return gsonConverterFactory;
    }

    public void CustomTabLaunch(Context mcontext, Uri url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(mcontext.getResources().getColor(R.color.colorPrimary))
                .setShowTitle(true);

        // An example intent that sends an email.
        Intent actionIntent = new Intent(Intent.ACTION_SEND);
        actionIntent.setType("*/*");
        actionIntent.putExtra(Intent.EXTRA_EMAIL, "example@example.com");
        actionIntent.putExtra(Intent.EXTRA_SUBJECT, "example");
        PendingIntent pi = PendingIntent.getActivity(mcontext, 0, actionIntent, 0);
        Bitmap icon = BitmapFactory.decodeResource(mcontext.getResources(), android.R.drawable.ic_menu_share);
        builder.setActionButton(icon, "send email", pi, true);

        builder.setStartAnimations(mcontext, R.anim.right_in, R.anim.right_out);
        builder.setExitAnimations(mcontext, R.anim.right_in_back, R.anim.right_out_back);
        builder.setCloseButtonIcon(
                BitmapFactory.decodeResource(mcontext.getResources(), android.R.drawable.ic_menu_close_clear_cancel));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl((Activity) mcontext, url);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        Logger.init("Tibber Say");
        netComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("http://gank.io/"))
                .build();
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }
}
