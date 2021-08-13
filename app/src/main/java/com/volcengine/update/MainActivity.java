package com.volcengine.update;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.volcengine.mars.update.OnUpdateStatusChangedListener;
import com.volcengine.mars.update.VEUpdate;
import com.volcengine.mars.update.UpdateConfig;
import com.volcengine.mars.update.UpdateDialogStyle;
import com.volcengine.mars.update.UpdateLocalStrategy;
import com.volcengine.mars.update.UpdateStrategyInfo;
import com.volcengine.AppCommonContextImpl;
import com.volcengine.mars.permissions.PermissionsManager;
import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "UpdateHomeActivity";
    private Button mButton;
    private UpdateStrategyInfo updateStrategyInfo;
    private UpdateLocalStrategy updateLocalStrategy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] permissions = new String[]{Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_WIFI_STATE};
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this, permissions,null);
        setContentView(R.layout.activity_main);
        mButton = findViewById(R.id.updateButton);
        UpdateConfig updateConfig = getUpdateConfig();
        VEUpdate.initialize(updateConfig);
        VEUpdate.setCheckSignature(false);
        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                VEUpdate.checkUpdate(UpdateDialogStyle.STYLE_BIG_INNER, new OnUpdateStatusChangedListener() {
                    @Override
                    public void onUpdateStatusChanged(final int status) {
                        boolean isUpdateAvailable = VEUpdate.isRealCurrentVersionOut();
                        Log.d(TAG, "onUpdateStatusChanged status = " + status);
                    }

                    @Override
                    public void saveDownloadInfo(final int size, final String etag, final boolean pre) {
                        Log.d(TAG, "onUpdateStatusChanged saveDownloadInfo = " + pre);
                    }

                    @Override
                    public void updateProgress(final int byteSoFar, final int contentLength, final boolean pre) {
                        Log.d(TAG, "onUpdateStatusChanged updateProgress = " + pre);
                    }

                    @Override
                    public void downloadResult(final boolean isSuccess, final boolean pre) {
                        Log.d(TAG, "onUpdateStatusChanged downloadResult = " + isSuccess);
                    }

                    @Override
                    public void onPrepare(final boolean pre) {
                        Log.d(TAG, "onUpdateStatusChanged onPrepare = " + pre);
                    }
                },false);
            }
        });

    }
    public UpdateConfig getUpdateConfig() {
        int drawableRes = R.drawable.status_icon;

        String authority = "com.mars.android.update";

        if (updateStrategyInfo == null) {
            updateStrategyInfo = new UpdateStrategyInfo();
        }
        updateStrategyInfo.currentActivity = new WeakReference<>(this);
        updateStrategyInfo.updateNewStrategyEnable = true;

        if (updateStrategyInfo.updateDelayTime <= 0) {
            updateStrategyInfo.updateDelayTime = 1 * 24 * 3600 * 1000L;
        } else if (updateStrategyInfo.updateDelayTime >= 6 * 24 * 3600 * 1000L) {
            updateStrategyInfo.updateDelayTime = 6 * 24 * 3600 * 1000L;
        } else {
            updateStrategyInfo.updateDelayTime *= 2;
        }
        if (updateLocalStrategy == null) {
            updateLocalStrategy = new UpdateLocalStrategy();
        }
        updateLocalStrategy.updateFormalStrategyEnable = true;
        updateLocalStrategy.updateDialogBgDownloadCheckboxText = "这里设置了下次自动下载";
        return new UpdateConfig.Builder()
                .setAppCommonContext(new AppCommonContextImpl())
                .setIUpdateForceExit(new UpdateForceExitImpl())
                .setNotifyIcon(drawableRes)
                .setFormalAuthority(authority)
                .setUpdateStrategyInfo(updateStrategyInfo)
                .build();

    }
}