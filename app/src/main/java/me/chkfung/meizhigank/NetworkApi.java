package me.chkfung.meizhigank;

import me.chkfung.meizhigank.Model.Meizhi;
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

    @GET("api/day/{year}/{month}/{day}")
    Observable<Meizhi> getDay(@Path("year") int year, @Path("month") int month, @Path("day") int day);

//    class Factory {
//        public static NetworkApi create() {
//            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gank.io/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                    .build();
//            return retrofit.create(NetworkApi.class);
//        }
//
//    }

//    class OkHttpFactory {
//        public static OkHttpClient.Builder create() {
//            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
//            if (BuildConfig.DEBUG) {
//                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//                    @Override
//                    public void log(String message) {
//                        Logger.t(5).i(message);
//                    }
//                });
//                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//                okHttpClient.addInterceptor(httpLoggingInterceptor);
//            }
//            return okHttpClient;
//        }
//    }
}
