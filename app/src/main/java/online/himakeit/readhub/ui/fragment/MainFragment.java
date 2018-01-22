package online.himakeit.readhub.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import online.himakeit.readhub.R;
import online.himakeit.readhub.ui.adapter.MainViewPagerAdapter;

/**
 * @author：LiXueLong
 * @date：2018/1/20
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class MainFragment extends BaseFragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    protected void initView() {
        mTabLayout = customFindViewById(R.id.tab_main);
        mViewPager = customFindViewById(R.id.viewpager);

        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.page_key_topic));
        titles.add(getString(R.string.page_key_news));
        titles.add(getString(R.string.page_key_developer));
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getmContext(), titles, getFragmentManager());
        mViewPager.setAdapter(mainViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
