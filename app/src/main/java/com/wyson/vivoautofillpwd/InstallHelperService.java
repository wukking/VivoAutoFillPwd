package com.wyson.vivoautofillpwd;

import android.accessibilityservice.AccessibilityService;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

public class InstallHelperService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode == null) {
            return;
        }

        if (event.getPackageName().equals("com.vivo.secime.service")){
            String pwd = PreferenceUtils.getString(getApplicationContext(),
                    PreferenceUtils.KEY_PASSWORD, "");
            if (!TextUtils.isEmpty(pwd)){
                fillPwd(rootNode,pwd);
            }
        }else if (event.getPackageName().equals("com.android.packageinstaller")){
            install(rootNode);
        }
    }

    private void install(AccessibilityNodeInfo rootNode) {
        List<AccessibilityNodeInfo> nodeInfoList = new ArrayList<>();
        nodeInfoList.addAll(rootNode.findAccessibilityNodeInfosByText("继续安装"));
        nodeInfoList.addAll(rootNode.findAccessibilityNodeInfosByText("安装"));
        nodeInfoList.addAll(rootNode.findAccessibilityNodeInfosByText("打开"));

        for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    private void fillPwd(AccessibilityNodeInfo rootNode, String password) {
        AccessibilityNodeInfo editPwd = rootNode.findFocus(AccessibilityNodeInfo.FOCUS_INPUT);
        if (editPwd == null){
            return;
        }

        if (editPwd.getPackageName().equals("com.bbk.account")
                && editPwd.getClassName().equals("android.widget.EditText")){
            Bundle arguments = new Bundle();
            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                    password);
            editPwd.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT,arguments);
        }
        List<AccessibilityNodeInfo> nodeInfoList =rootNode.findAccessibilityNodeInfosByText("确定");
        for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    @Override
    public void onInterrupt() {
    }
}
