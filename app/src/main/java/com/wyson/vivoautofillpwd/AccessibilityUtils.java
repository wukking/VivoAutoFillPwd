package com.wyson.vivoautofillpwd;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;

import java.util.List;

public class AccessibilityUtils {

    public static boolean isStartAccessibilityService(Context context, String name){
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> serviceInfoList = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for (AccessibilityServiceInfo info : serviceInfoList) {
            String id = info.getId();
            Log.d("AccessibilityUtils","all -->" + id);
            if (id.contains(name)) {
                return true;
            }
        }
        return false;
    }
}
