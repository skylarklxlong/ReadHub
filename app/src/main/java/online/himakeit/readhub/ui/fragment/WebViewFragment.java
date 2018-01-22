package online.himakeit.readhub.ui.fragment;

import android.os.Build;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import online.himakeit.readhub.MyApp;
import online.himakeit.readhub.R;

/**
 * @author：LiXueLong
 * @date：2018/1/20
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public abstract class WebViewFragment extends BaseFragment {

    private ProgressBar mProgressBar;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected WebView mWebView;

    private static final String APP_CACHE_DIRNAME = "/webcache";

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_webview, container, false);
    }

    protected abstract String getLoadUrl();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebView.stopLoading();
        mWebView.clearHistory();
        mWebView.destroy();
        mWebView = null;
    }

    @Override
    protected void initView() {
        mProgressBar = customFindViewById(R.id.progressbar);
        mWebView = customFindViewById(R.id.webview);

        initWebViewSettings();
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mProgressBar.setMax(100);
        mWebView.loadUrl(getLoadUrl());

        mSwipeRefreshLayout = customFindViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetCacheType();
                /**
                 * 重新刷新页面
                 */
                mWebView.loadUrl(getLoadUrl());
            }
        });
    }

    private void initWebViewSettings() {
        /**
         * 屏蔽掉长按事件
         */
        mWebView.requestFocusFromTouch();
        /**
         * 增加webview的调试
         */
        if (Build.VERSION.SDK_INT > 19) {
            if (true) {
                WebView.setWebContentsDebuggingEnabled(true);
            } else {
                WebView.setWebContentsDebuggingEnabled(false);
            }
        }
        WebSettings mWebSettings = mWebView.getSettings();
        /**
         * 设置WebView属性，能够执行Javascript脚本
         */
        mWebSettings.setJavaScriptEnabled(true);
        /**
         * 将图片调整到适合webview的大小
         */
        mWebSettings.setUseWideViewPort(true);
        /**
         * 缩放至屏幕的大小
         */
        mWebSettings.setLoadWithOverviewMode(true);
        /**
         * 开启 DOM storage API 功能
         */
        mWebSettings.setDomStorageEnabled(true);
        /**
         * 开启 database storage API 功能
         */
        mWebSettings.setDatabaseEnabled(true);
        /**
         * 开启 Application Caches 功能
         */
        mWebSettings.setAppCacheEnabled(true);
        /**
         * 设置缓存类型
         */
        resetCacheType();
        /**
         * 设置缓存位置
         */
        String cacheDirPath = MyApp.getContext().getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME;
        /**
         * 设置数据库缓存路径
         */
        mWebSettings.setDatabasePath(cacheDirPath);
        /**
         * 设置  Application Caches 缓存目录
         */
        mWebSettings.setAppCachePath(cacheDirPath);
        /**
         * 支持缩放，默认为true。是下面那个的前提。
         */
        mWebSettings.setSupportZoom(true);
        /**
         * 设置内置的缩放控件
         */
        mWebSettings.setBuiltInZoomControls(true);
        /**
         * 若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。
         * 支持内容重新布局
         */
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        /**
         * 多窗口
         */
        mWebSettings.supportMultipleWindows();
        /**
         * 当webview调用requestFocus时为webview设置节点
         */
        mWebSettings.setNeedInitialFocus(true);
        /**
         * 支持通过JS打开新窗口
         */
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        /**
         * 支持自动加载图片
         */
        mWebSettings.setLoadsImagesAutomatically(true);
        /**
         * 设置编码格式
         */
        mWebSettings.setDefaultTextEncodingName("UTF-8");
    }

    public boolean canGoBack() {
        if (mWebView != null) {
            if (mWebView.getUrl().equalsIgnoreCase(getLoadUrl())) {
                mWebView.clearHistory();
                return false;
            } else {
                return mWebView.canGoBack();
            }
        } else {
            return false;
        }
    }

    public void goBack() {
        if (mWebView != null) {
            mWebView.goBack();
        }
    }

    private void resetCacheType() {
        /**
         * 设置缓存类型
         */
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    /**
     * WebViewClient就是帮助WebView处理各种通知、请求事件的。
     */
    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) {
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    /**
     * WebChromeClient是辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
     */
    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (mProgressBar != null) {
                mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    if (!mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                }
            }
            super.onProgressChanged(view, newProgress);
        }

        /**
         * 获取Web页中的title用来设置自己界面中的title
         * 当加载出错的时候，比如无网络，这时onReceiveTitle中获取的标题为 找不到该网页,
         * 因此建议当触发onReceiveError时，不要使用获取到的title
         *
         * @param view
         * @param title
         */
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }


        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            return true;
        }

        @Override
        public void onCloseWindow(WebView window) {
        }

        /**
         * 处理confirm弹出框
         *
         * @param view
         * @param url
         * @param message
         * @param defaultValue
         * @param result
         * @return
         */
        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult
                result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        /**
         * 处理prompt弹出框
         *
         * @param view
         * @param url
         * @param message
         * @param result
         * @return
         */
        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }
    }
}
