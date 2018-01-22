package online.himakeit.readhub.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;

import online.himakeit.readhub.R;
import online.himakeit.readhub.network.ApiClient;
import online.himakeit.readhub.ui.fragment.AuthorAboutFragment;
import online.himakeit.readhub.ui.fragment.MainFragment;
import online.himakeit.readhub.ui.fragment.ReadHubAboutFragment;
import online.himakeit.readhub.ui.fragment.WebClientFragment;
import online.himakeit.readhub.ui.fragment.WebViewFragment;
import online.himakeit.readhub.utils.FragmentUtil;
import online.himakeit.readhub.utils.UpdateUtil;

/**
 * @author：LiXueLong
 * @date:2018/1/5-15:31
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des：MainActivity
 */
public class MainActivity extends BaseActivity {

    public static final String INTENT_EXTRA_KEY_ITEM_URL = "EXTRA_URL";
    public static final String INTENT_EXTRA_KEY_ITEM_TITLE = "EXTRA_TITLE";

    /**
     * 侧边栏视图
     */
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolBar;
    private ActionBarDrawerToggle mActionBarToggle;

    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;

    private boolean mIsHome = false;

    @Override
    protected int getLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        mFragmentManager = getSupportFragmentManager();
        ApiClient.init("https://api.readhub.me");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (null != intent && null != intent.getExtras()) {
            String url = "";
            if (intent.getExtras().containsKey(INTENT_EXTRA_KEY_ITEM_URL)) {
                url = intent.getStringExtra(INTENT_EXTRA_KEY_ITEM_URL);
            }
            String name = "";
            if (intent.getExtras().containsKey(INTENT_EXTRA_KEY_ITEM_TITLE)) {
                name = intent.getStringExtra(INTENT_EXTRA_KEY_ITEM_TITLE);
            }

            switchToWebClientFragment(name, url);
        }
    }

    private void switchToWebClientFragment(String name, String url) {
        if (TextUtils.isEmpty(name) || "".equals(name) || "null".equals(name)) {
            name = getString(R.string.app_name);
        }
        if (!TextUtils.isEmpty(url) && !"".equals(url) && url.equalsIgnoreCase(getString(R.string.link_readhub_page))) {
            mIsHome = true;
        } else {
            mIsHome = false;
        }

        WebClientFragment.setsURL(url);
        switchFragment(name, WebClientFragment.class);
    }

    private void switchFragment(String name, Class<?> clazz) {
        if (TextUtils.isEmpty(name) || "".equals(name)) {
            mToolBar.setTitle(getString(R.string.app_name));
        } else {
            mToolBar.setTitle(name);
        }

        Fragment fragment = FragmentUtil.createFragment(clazz);
        if (fragment.isAdded()) {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(fragment).commit();
        } else {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.frame_content, fragment).commit();
        }

        mCurrentFragment = fragment;
        if (fragment instanceof WebClientFragment) {
            hideMenu(R.integer.menu_order_web);
        } else {
            showMenu(R.integer.menu_order_web);
        }
    }

    private void showMenu(int id) {
        MenuItem item = mNavigationView.getMenu().getItem(getResources().getInteger(id));
        item.setVisible(true);
        item.setChecked(false);
    }

    private void hideMenu(int id) {
        MenuItem item = mNavigationView.getMenu().getItem(getResources().getInteger(id));
        item.setVisible(false);
        item.setChecked(false);
    }

    private void shareToFriend(final Activity activity) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text));
        shareIntent.setType("text/plain");
        activity.startActivity(Intent.createChooser(shareIntent, getString(R.string.share_title)));
    }

    @Override
    protected void initView() {
        mDrawerLayout = customFindViewById(R.id.drawer_layout);
        mNavigationView = customFindViewById(R.id.navigation_view);
        mToolBar = customFindViewById(R.id.toolbar);

        mToolBar.setTitle(R.string.app_name);
        //这句一定要在下面几句之前调用，不然就会出现点击无反应
        setSupportActionBar(mToolBar);
        //ActionBarDrawerToggle配合Toolbar，实现Toolbar上菜单按钮开关效果。
        mActionBarToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.drawer_open, R.string.drawer_close);
        mActionBarToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarToggle);
        setNavigationViewItemClickListener();
        initDefaultFragment();
        // TODO: 2018/1/22 开启更新
        UpdateUtil.checkUpdate(this, false);
    }

    private void setNavigationViewItemClickListener() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_item_go_back:
                        switchFragment(getString(R.string.app_name), MainFragment.class);
                        break;
                    case R.id.navigation_item_home:
                        switchToWebClientFragment(getString(R.string.app_name), getString(R.string.link_readhub_page));
                        break;
                    case R.id.navigation_item_readhub:
                        switchFragment(getString(R.string.menu_key_app), ReadHubAboutFragment.class);
                        break;
                    case R.id.navigation_item_me:
                        switchFragment(getString(R.string.menu_key_me), AuthorAboutFragment.class);
                        break;
                    case R.id.navigation_item_share:
                        shareToFriend(MainActivity.this);
                        break;
                    case R.id.navigation_item_update:
                        // TODO: 2018/1/22 更新功能
                        UpdateUtil.checkUpdate(MainActivity.this, true);
                        break;
                    default:
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawer(Gravity.START);
                return false;
            }
        });
    }

    private void initDefaultFragment() {
        mCurrentFragment = FragmentUtil.createFragment(MainFragment.class);
        mFragmentManager.beginTransaction().add(R.id.frame_content, mCurrentFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
            return;
        }

        /**
         * 如果当前的Fragment是WebViewFragment 则监听返回事件
         */
        if (mCurrentFragment instanceof WebViewFragment) {
            WebViewFragment webViewFragment = (WebViewFragment) mCurrentFragment;
            if (mIsHome && webViewFragment.canGoBack()) {
                webViewFragment.goBack();
            } else {
                switchFragment(getString(R.string.app_name), MainFragment.class);
            }
        } else {
            exitApp();
        }
    }

    private long lastBackKeyDownTick = 0;
    private static final long MAX_DOUBLE_BACK_DURATION = 1500;

    private void exitApp() {
        long currentTick = System.currentTimeMillis();
        if (currentTick - lastBackKeyDownTick > MAX_DOUBLE_BACK_DURATION) {
            // TODO: 2018/1/22 弹一个提示
            Snackbar.make(mDrawerLayout, "再按一次退出", Snackbar.LENGTH_SHORT).show();
            lastBackKeyDownTick = currentTick;
        } else {
            finish();
        }
    }
}
