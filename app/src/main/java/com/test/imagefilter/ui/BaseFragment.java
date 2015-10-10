package com.test.imagefilter.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.test.imagefilter.utils.FragmentUtils;

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this); // read @Arg fields
    }

    protected void showMessage(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (isVisible()) {
            Snackbar.make(getActivity().getWindow().getDecorView(), message, Snackbar.LENGTH_LONG)
                .show();
        }
    }
}