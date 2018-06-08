package com.zdh.readhub.feature.main;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.zdh.readhub.R;
import com.zdh.readhub.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends BaseActivity {

    private static final String TAG = "ReadHubLog";

    public static final int PERMISSION_STORAGE = 1;
    private boolean isFinish = false; // 防止误触返回退出
    private Runnable mRequestPermissionActions; //请求运行时权限成功后的回调，由 requestPermissionWithAction() 传入。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.frame_main, MainFragment.newInstance(), true, true);
        }
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            if (isFinish) {
                // 结束这个Activity的生命周期
                finish();
            } else {
                isFinish = true;
                Toast.makeText(this, R.string.finish_reminder, Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        isFinish = false;
                    }
                }).start();
            }
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    public void requestPermissionWithAction(String permission, int requestCode, Runnable action) {
        mRequestPermissionActions = action;
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            mRequestPermissionActions.run();
            return;
        }
        ActivityCompat.requestPermissions(this, new String[] {permission}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mRequestPermissionActions.run();
            return;
        }
        Toast.makeText(this, R.string.permission_failure_reminder, Toast.LENGTH_SHORT).show();
    }
}
