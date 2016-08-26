package me.chkfung.meizhigank;

import me.chkfung.meizhigank.Model.Day;
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

//    @GET("api/day/{year}/{month}/{day}")
//    Observable<Day> getDay(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    @GET("api/day/{date}")
    Observable<Day> getDay(@Path("date") String date);
}
