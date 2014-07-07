package com.xgleng.torch.activity;

import java.io.IOException;
import java.util.List;

import com.xgleng.torch.R;
import com.xgleng.torch.utils.ToastHelper;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
    private ToggleButton mFlashlightHeader;
    private ToggleButton mFlashlightBody;
    private Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFlashlightHeader = (ToggleButton) findViewById(R.id.flashlight_header);
        mFlashlightBody = (ToggleButton) findViewById(R.id.flashlight_body);
        mFlashlightBody
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                            boolean isChecked) {
                        if (null == mCamera) {
                            ToastHelper.showToast("无法获取相机");
                            return;
                        }

                        Camera.Parameters parameters = mCamera.getParameters();
                        if (isChecked) {
                            List<?> list = parameters.getSupportedFlashModes();
                            if (null != list
                                    && (list.contains(Camera.Parameters.FLASH_MODE_TORCH) || list
                                            .contains(Camera.Parameters.FLASH_MODE_ON))) {
                                parameters
                                        .setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                                parameters.setPreviewSize(640, 480);
                                mCamera.setParameters(parameters);
                                mCamera.startPreview();
                                mFlashlightHeader.setChecked(isChecked);
                            } else {
                                ToastHelper.showToast("相机不支持闪光灯");
                            }
                        } else {
                            parameters
                                    .setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                            mCamera.setParameters(parameters);
                            mFlashlightHeader.setChecked(isChecked);
                        }

                    }
                });

        SurfaceView surface = (SurfaceView) findViewById(R.id.dummy_surface);
        SurfaceHolder holder = surface.getHolder();
        holder.addCallback(new SurfaceCallback());
    }

    private class SurfaceCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (null != mCamera) {
                return;
            }

            mCamera = Camera.open();
            if (null == mCamera) {
                ToastHelper.showToast("无法获取相机");
                return;
            }

            try {
                mCamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
                ToastHelper.showToast("无法获取相机");
                if (null != mCamera) {
                    mCamera.release();
                    mCamera = null;
                }
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (null != mCamera) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        }

    }
}
