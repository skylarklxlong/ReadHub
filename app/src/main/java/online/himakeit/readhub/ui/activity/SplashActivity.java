package online.himakeit.readhub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import online.himakeit.readhub.MyApp;
import online.himakeit.readhub.R;

/**
 * @author：LiXueLong
 * @date：2018/1/19
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class SplashActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startApp(getIntent());
    }

    private void startApp(final Intent intent) {
        MyApp.getmHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                intent.setClass(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 2000);
    }
}
