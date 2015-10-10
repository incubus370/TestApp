package com.test.imagefilter.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import com.test.imagefilter.utils.FragmentUtils;

/**
 * Fragment to pick images from image gallery
 */
public class PickImageDialogFragment extends DialogFragment {

    private static final int RESULT_PICK_IMAGE = 1;
    private PickImageCallback pickImageCallback;

    public PickImageDialogFragment() {
        setStyle(STYLE_NO_INPUT, 0);
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        if (requestCode == RESULT_PICK_IMAGE) {
            dispatchPickImageResult(resultCode, data);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        pickImageCallback = FragmentUtils.getParent(this, PickImageCallback.class);
        if (pickImageCallback == null) {
            throw new IllegalArgumentException(
                "At least one fragment's parent should implement PickImageCallback");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        pickImage();
        return super.onCreateDialog(savedInstanceState);
    }

    void pickImage() {
        Intent imageIntent = new Intent(Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        try {
            startActivityForResult(imageIntent, RESULT_PICK_IMAGE);
            return;
        } catch (ActivityNotFoundException e) {
            //Ignore exception. Only check that system can not resolve activity for specified intent
        }
        imageIntent = new Intent(Intent.ACTION_PICK);
        imageIntent.setType("image/*");
        startActivityForResult(imageIntent, RESULT_PICK_IMAGE);
    }

    private void dispatchPickImageResult(int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK && data == null) {
            onPickCancel();
            return;
        }
        Uri imageUri = data.getData();
        if (imageUri == null) {
            onPickCancel();
            return;
        }
        onImageSelected(imageUri);
    }

    private void onImageSelected(Uri uri) {
        pickImageCallback.onImageSelected(this, uri);
        dismiss();
    }

    private void onPickCancel() {
        pickImageCallback.onPickCancel(this);
        dismiss();
    }

    /**
     * Pick image callback interface. At least one fragment's parent should implement this
     * interface
     */
    public interface PickImageCallback {

        /**
         * Callback invoked when content selected
         *
         * @param fragment The Fragment which make this call
         * @param uri Uri of selected content
         */
        void onImageSelected(PickImageDialogFragment fragment, Uri uri);

        /**
         * Callback invoked when selection canceled
         *
         * @param fragment The Fragment which make this call
         */
        void onPickCancel(PickImageDialogFragment fragment);
    }
}
