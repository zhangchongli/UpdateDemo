package com.volcengine.update;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.volcengine.mars.update.IUpdateForceExit;
import com.volcengine.BaseApplication;

public class UpdateForceExitImpl implements IUpdateForceExit {
    @Override
    public void forceExitApp(@NonNull Context context) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(
                new Intent(BaseApplication.ACTION_EXIT_APP));
    }
}
