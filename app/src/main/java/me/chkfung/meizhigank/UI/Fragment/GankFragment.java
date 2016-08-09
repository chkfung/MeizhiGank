package me.chkfung.meizhigank.UI.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import me.chkfung.meizhigank.R;

/**
 * Created by Fung on 08/08/2016.
 */

public class GankFragment extends DialogFragment {
    //    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_gank,container,false);
//    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.fragment_gank);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        getDialog().getWindow().setLayout(
//                (int)(displayMetrics.widthPixels*0.8),
//                (int)(displayMetrics.heightPixels*0.8));
    }
}
