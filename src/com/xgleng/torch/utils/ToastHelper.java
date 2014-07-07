package com.xgleng.torch.utils;

import com.xgleng.torch.BaseApplication;

import android.widget.Toast;

/**
 * 保证客户端只存在一个Toast对象，并且最后显示的内容可以马上覆盖之前的，避免感觉无穷无尽的Toast提示
 */
public final class ToastHelper {

    private static Toast mToast = null;

    static {
        mToast = Toast.makeText(BaseApplication.sApplicationContext, "",
                Toast.LENGTH_SHORT);
    }

    public static void showToast(String str) {
        try {
            mToast.setText(str);
            mToast.show();
        } catch (Exception e) {
        }
    }

    public static void showToast(int resId) {
        try {
            mToast.setText(resId);
            mToast.show();
        } catch (Exception e) {
        }
    }
}
