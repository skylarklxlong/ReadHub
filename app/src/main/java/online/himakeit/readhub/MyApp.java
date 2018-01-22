package online.himakeit.readhub;

import android.app.Application;
import android.os.Handler;

/**
 * @author：LiXueLong
 * @date：2018/1/19
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class MyApp extends Application {

    static MyApp myApp = null;
    static Handler mHandler = null;

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        mHandler = new Handler();
    }

    public static MyApp getContext() {
        return myApp;
    }

    public static Handler getmHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }
}
