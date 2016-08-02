package me.chkfung.meizhigank;

import me.chkfung.meizhigank.Model.Meizhi;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Fung on 21/07/2016.
 */

public interface NetworkApi {


    @GET("api/data/福利/{amount}/{page}")
    Observable<Meizhi> getMeizhi(@Path("amount") int meizhiCount, @Path("page") int page);

    @GET("api/data/Android/{amount}/{page}")
    Observable<Meizhi> getAndroid(@Path("amount") int meizhiCount, @Path("page") int page);

    class Factory {
        public static NetworkApi create() {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gank.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(NetworkApi.class);
        }
    }

    class OkHttpFactory {
        public static OkHttpClient create() {
            OkHttpClient okHttpClient = new OkHttpClient();
            return okHttpClient;
        }
    }
}
