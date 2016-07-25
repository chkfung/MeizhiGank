package me.chkfung.meizhigank;

import me.chkfung.meizhigank.Model.Meizhi;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Fung on 21/07/2016.
 */

public interface NetworkApi {
    @GET("api/data/福利/10/{page}")
    Call<Meizhi> getMeizhi(@Path("page") int page);


    class Factory {
        public static NetworkApi create() {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gank.io/")
                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(NetworkApi.class);
        }
    }
}
