package com.volcengine;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.bytedance.applog.AppLog;
import com.bytedance.applog.InitConfig;
import java.util.List;

public class BaseApplication extends Application{
    public static final String TAG = "BaseApplication";

    public static BaseApplication sApplication;
    public static String sProcessName;
    public static boolean sIsMainProcess;
    public static Context sBaseContext;
    public static final String ACTION_EXIT_APP = "com.ss.android.platform.app.action.exit_app";
    private Boolean debugApk;

    public static BaseApplication getInstance() {
        return sApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        sBaseContext = base;
        sApplication = this;
        sProcessName = getProcessName(base);
        sIsMainProcess = base.getPackageName().equals
                (sProcessName);
        super.attachBaseContext(base);
    }
    public static String getProcessName(Context cxt) {
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return "";
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppLog.init(this, new InitConfig("220911", "local_test"));
    }

    private boolean isDebugApk() {
        if (debugApk != null) {
            return debugApk;
        }
        debugApk = (sBaseContext.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        return debugApk;
    }
}
