package me.chkfung.meizhigank.Dagger.Module;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.customtabs.CustomTabsIntent;

import dagger.Module;
import dagger.Provides;
import me.chkfung.meizhigank.Contract.GankContract;
import me.chkfung.meizhigank.Dagger.PresenterScope;
import me.chkfung.meizhigank.R;

/**
 * Created by Fung on 26/08/2016.
 */
@Module
public class GankPresenterModule {
    private GankContract.View view;


    public GankPresenterModule(GankContract.View view) {
        this.view = view;
    }

    @PresenterScope
    @Provides
    GankContract.View provideGankContractView() {
        return view;
    }

    @PresenterScope
    @Provides
    CustomTabsIntent provideCustomTabIntent(GankContract.View view) {
        Context mContext = view.getContext();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(mContext.getResources().getColor(R.color.colorPrimary))
                .setShowTitle(true);

        // An example intent that sends an email.
        Intent actionIntent = new Intent(Intent.ACTION_SEND);
        actionIntent.setType("*/*");
        actionIntent.putExtra(Intent.EXTRA_EMAIL, "example@example.com");
        actionIntent.putExtra(Intent.EXTRA_SUBJECT, "example");
        PendingIntent pi = PendingIntent.getActivity(mContext, 0, actionIntent, 0);
        Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_share);
        builder.setActionButton(icon, "send email", pi, true);

        builder.setStartAnimations(mContext, R.anim.right_in, R.anim.right_out);
        builder.setExitAnimations(mContext, R.anim.right_in_back, R.anim.right_out_back);
        builder.setCloseButtonIcon(
                BitmapFactory.decodeResource(mContext.getResources(), android.R.drawable.ic_menu_close_clear_cancel));
        CustomTabsIntent customTabsIntent = builder.build();
        return customTabsIntent;

    }
}
