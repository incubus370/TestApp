package com.test.imagefilter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

/**
 * Created by Home on 06.10.2015.
 */
public class BaseActivity extends AppCompatActivity {

    private boolean active;

    public boolean isActive() {
        return active;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        active = true;
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        active = true;
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        active = false;
    }

    @Override
    protected void onStart() {
        active = true;
        super.onStart();
    }

    protected void showMessage(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (isActive()) {
            Snackbar.make(getWindow().getDecorView(), message, Snackbar.LENGTH_LONG).show();
        }
    }
}
