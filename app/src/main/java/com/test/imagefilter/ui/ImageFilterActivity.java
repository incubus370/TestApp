package com.test.imagefilter.ui;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.test.imagefilter.R;
import com.test.imagefilter.ui.filter.ImageFilterFragmentBuilder;
import com.test.imagefilter.utils.BitmapUtils;
import icepick.Icepick;
import icepick.State;
import java.io.File;
import org.apache.commons.io.FileUtils;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public class ImageFilterActivity extends BaseActivity
    implements PickImageDialogFragment.PickImageCallback, IImageFilterActivity {

    private static final String IMAGE_FILE_NAME = "img%s.tmp";
    private static final String IMAGE_FILTER_FRAGMENT_TAG = "image_filter";
    private static final String PICK_IMAGE_FRAGMENT_TAG = "pick_image";
    private static final String TAG = ImageFilterActivity.class.getSimpleName();
    @State
    String absolutePathToLoadedFile;
    @Bind(R.id.activity_content)
    View activityContent;
    @Bind(R.id.pick_image_button)
    Button pickImageButton;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @State
    Uri remoteImageUri;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private Subscription downloadImageSubscription = Subscriptions.empty();
    private boolean showImageFilterOnStart;

    @Override
    public void onImageSelected(PickImageDialogFragment fragment, final Uri uri) {
        if (uri.equals(remoteImageUri)) {
            showMessage(getString(R.string.message_file_already_loaded));
            return;
        }
        setProgressVisibility(true);
        downloadImageSubscription = loadImageFromUri(uri, !hasLoadedFile(),
            getString(R.string.error_unable_to_load_image_file),
            getString(R.string.error_file_is_not_image_file)).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<String>() {

                String absolutePathToLoadedFile;
                Uri remoteImageUri = uri;

                @Override
                public void onCompleted() {
                    onFileLoadComplete(absolutePathToLoadedFile, remoteImageUri);
                }

                @Override
                public void onError(Throwable e) {
                    onFileLoadFailed(e);
                }

                @Override
                public void onNext(String absolutePathToLoadedFile) {
                    this.absolutePathToLoadedFile = absolutePathToLoadedFile;
                }
            });
    }

    @Override
    public void onPickCancel(PickImageDialogFragment fragment) {
        if (!hasLoadedFile()) {
            setPickImageButtonVisibility(true);
        }
    }

    public void pickImage() {
        setPickImageButtonVisibility(false);
        new PickImageDialogFragment().show(getSupportFragmentManager(), PICK_IMAGE_FRAGMENT_TAG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_filter);
        ButterKnife.bind(this);
        Icepick.restoreInstanceState(this, savedInstanceState);
        toolbar.setTitle(R.string.app_name);
        activityContent.setVisibility(View.VISIBLE);
        boolean firstLaunch = savedInstanceState == null;
        if (firstLaunch) {
            pickImage();
        } else if (!hasLoadedFile()) {
            setPickImageButtonVisibility(true);
        }
    }

    @Override
    protected void onDestroy() {
        downloadImageSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (showImageFilterOnStart) {
            showImageFilter(absolutePathToLoadedFile);
        }
    }

    @OnClick(R.id.pick_image_button)
    void pickImageButtonClick() {
        pickImage();
    }

    private boolean hasLoadedFile() {
        return !TextUtils.isEmpty(absolutePathToLoadedFile);
    }

    /**
     * Load content to internal storage from specified uri, and check that is valid image file
     *
     * @param uri Uri of selected content
     * @param clearFilesDirOnLoad Flag to clear internal storage before content was loaded
     */
    private Observable<String> loadImageFromUri(final Uri uri, final boolean clearFilesDirOnLoad,
        final String copyFileErrorMessage, final String fileIsNotImageErrorMessage) {
        return Observable.create(new rx.Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                File filesDir = getFilesDir();
                String tempFileName =
                    String.format(IMAGE_FILE_NAME, String.valueOf(System.currentTimeMillis()));
                File destinationFile = new File(filesDir, tempFileName);
                try {
                    final ContentResolver contentResolver = getContentResolver();
                    if (clearFilesDirOnLoad) {
                        FileUtils.cleanDirectory(filesDir);
                    } else {
                        FileUtils.deleteQuietly(destinationFile);
                    }
                    FileUtils.copyInputStreamToFile(contentResolver.openInputStream(uri),
                        destinationFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    FileUtils.deleteQuietly(destinationFile);
                    subscriber.onError(new Throwable(copyFileErrorMessage));
                    Log.d(TAG, "Could not load image", e);
                    return;
                }
                if (!BitmapUtils.isValidImageFile(destinationFile)) {
                    FileUtils.deleteQuietly(destinationFile);
                    subscriber.onError(new Throwable(fileIsNotImageErrorMessage));
                    return;
                }
                subscriber.onNext(destinationFile.getAbsolutePath());
                subscriber.onCompleted();
            }
        });
    }

    private void onFileLoadComplete(String absolutePathToLoadedFile, Uri remoteImageUri) {
        this.remoteImageUri = remoteImageUri;
        this.absolutePathToLoadedFile = absolutePathToLoadedFile;
        showImageFilter(absolutePathToLoadedFile);
        setProgressVisibility(false);
    }

    private void onFileLoadFailed(Throwable e) {
        if (!hasLoadedFile()) {
            setPickImageButtonVisibility(true);
        }
        showMessage(e.getMessage());
        setProgressVisibility(false);
    }

    private void setPickImageButtonVisibility(boolean visible) {
        if (visible) {
            pickImageButton.setVisibility(View.VISIBLE);
        } else {
            pickImageButton.setVisibility(View.GONE);
        }
    }

    private void setProgressVisibility(boolean visible) {
        if (visible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showImageFilter(String filePathToOpen) {
        if (isActive()) {
            showImageFilterOnStart = false;
            Fragment fragment = new ImageFilterFragmentBuilder(filePathToOpen).build();
            FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment, IMAGE_FILTER_FRAGMENT_TAG).commit();
            activityContent.setVisibility(View.GONE);
        } else {
            showImageFilterOnStart = true;
        }
    }
}
