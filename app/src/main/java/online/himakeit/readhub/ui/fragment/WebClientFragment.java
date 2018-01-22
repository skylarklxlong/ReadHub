package online.himakeit.readhub.ui.fragment;

import android.text.TextUtils;

import online.himakeit.readhub.R;

/**
 * @author：LiXueLong
 * @date：2018/1/20
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class WebClientFragment extends WebViewFragment {

    private static String mURL = "";

    @Override
    protected String getLoadUrl() {
        if (!TextUtils.isEmpty(mURL) && !"".equals(mURL) && !"null".equals(mURL) && (mURL.startsWith("http") || !mURL.startsWith("https"))) {
            return mURL;
        } else {
            return getString(R.string.link_readhub_page);
        }
    }

    @Override
    protected void initView() {
        super.initView();
    }

    public static void setsURL(String mURL) {
        WebClientFragment.mURL = mURL;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            /**
             * 不在最前端界面显示
             */
        } else {
            /**
             * 重新显示到最前端
             */
            mWebView.stopLoading();
            mWebView.removeAllViews();
            mWebView.loadUrl(getLoadUrl());
        }
    }
}
