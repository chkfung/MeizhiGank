package me.chkfung.meizhigank.Dagger.Module;

import dagger.Module;
import dagger.Provides;
import me.chkfung.meizhigank.Contract.MeizhiContract;
import me.chkfung.meizhigank.Dagger.PresenterScope;

/**
 * Created by Fung on 29/08/2016.
 */
@Module
public class MeizhiPresenterModule {
    private MeizhiContract.View view;

    public MeizhiPresenterModule(MeizhiContract.View view) {
        this.view = view;
    }

    @PresenterScope
    @Provides
    MeizhiContract.View provideView() {
        return view;
    }

//    @PresenterScope
//    @Provides
//    @Named("DownloadProgress")
//    Interceptor provideInterceptor() {
//        Interceptor trackProgressInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                return null;
//            }
//        };
//        return trackProgressInterceptor;
//    }
//
//    @PresenterScope
//    @Provides
//    ResponseBody provideResponseBody(){
//        ProgressResponseBody progressResponseBody = new ProgressResponseBody();
//        return progressResponseBody;
//    }
//
//    class ProgressResponseBody extends ResponseBody{
//        @Override
//        public MediaType contentType() {
//            return null;
//        }
//
//        @Override
//        public long contentLength() {
//            return 0;
//        }
//
//        @Override
//        public BufferedSource source() {
//            return null;
//        }
//    }

}
