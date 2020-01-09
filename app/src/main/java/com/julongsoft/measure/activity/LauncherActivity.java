package com.julongsoft.measure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.julongsoft.measure.R;
import com.julongsoft.measure.entity.UserData;
import com.julongsoft.measure.utils.PrefUtil;

/**
 * Created by tao on 2017/7/26.
 */

public class LauncherActivity extends BaseActivity {

    private RelativeLayout llLauncher;
    private boolean isLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        isLogin = PrefUtil.getBoolean(this, "isLogin", false);

        llLauncher = (RelativeLayout) findViewById(R.id.rl_launcher);
        AlphaAnimation animation = new AlphaAnimation(0, 1.0f);
        animation.setDuration(2000);
        llLauncher.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isLogin) {
                    enterHome();
                } else {
                    enterLogin();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    protected void enterHome() {
        // 进入主界面
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        // 进入主界面后关闭当前界面.在主界面中点击返回键时直接退出程序
        finish();
    }

    private void enterLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
