package com.volcengine;

import android.content.Context;
import com.volcengine.mars.update.AbsAppCommonContext;
import com.volcengine.mars.update.DeviceWrapper;
import java.util.HashMap;
import java.util.Map;

public class AppCommonContextImpl extends AbsAppCommonContext {
    private static final String HOST_ADDRESS = "https://poc-api.vemarsdev.com/v1/gray_release/package";

    @Override
    public String getCustomUrl() {
        return HOST_ADDRESS;    //这个是青岛机房POC的下发端
    }

    @Override
    public Context getContext() {
        return BaseApplication.getInstance();
    }

    @Override
    public String getStringAppName() {
        return "升级SDK Demo";
    }

    @Override
    public String getChannel() {
//        return isDebugApk() ? "local_test" : "release";
        return "beta";
    }

    @Override
    public String getDeviceId() {
//        DeviceWrapper deviceService = new DeviceWrapper();
//        return deviceService.getDeviceID();

        return "679324334";
    }

    @Override
    public String getCity() {
        return "Shanghai";
    }

    @Override
    public Map<String, String> getCustomKV() {
        Map<String, String> customMap = new HashMap<>();
        customMap.put("user_id", "0000001");
        customMap.put("user_department", "DP09105");
        customMap.put("user_role", "00010002");
        return customMap;
    }
}
