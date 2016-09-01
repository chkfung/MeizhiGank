package me.chkfung.meizhigank.Dagger.Module;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.chkfung.meizhigank.NetworkApi;
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
 * Created by Fung on 12/08/2016.
 */
@Module
public class NetModule {

    private final String mBaseUrl;

    public NetModule() {
        mBaseUrl = "http://gank.io/";
    }

    @Provides
    @Singleton
    NetworkApi provideNetworkApi(Retrofit retrofit) {
        return retrofit.create(NetworkApi.class);
    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache, Interceptor cachingInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(cache);
        builder.addInterceptor(cachingInterceptor);
        return builder.build();
    }

    @Provides
    @Singleton
    Cache provideCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;//10 mb
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(mBaseUrl)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        return retrofitBuilder.build();
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonFactory() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
        return GsonConverterFactory.create(gson);
    }

    /**
     * Log Network Response
     *
     * @return Logging Interceptor
     */
    @Provides
    @Singleton
    HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.t(5).i(message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    /**
     * Cache Are available when Network Connection is not available
     *
     * @return Caching Interceptor
     */
    @Provides
    @Singleton
    Interceptor getCachingInterceptor(final Application mApp) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (ConnectionUtil.isNetworkAvailable(mApp)) {
                    request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                } else {
                    request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                }
                return chain.proceed(request);
            }
        };
    }

    @Provides
    @Singleton
    Scheduler provideScheduler() {
        return Schedulers.io();
    }


}
