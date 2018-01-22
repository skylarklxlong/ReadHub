package online.himakeit.readhub.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import online.himakeit.readhub.R;
import online.himakeit.readhub.ui.fragment.ReadHubDevFragment;
import online.himakeit.readhub.ui.fragment.ReadHubTechFragment;
import online.himakeit.readhub.ui.fragment.ReadHubTopicFragment;

/**
 * @author：LiXueLong
 * @date：2018/1/20
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private final List<String> mTitleList;
    private final Context mContext;

    public MainViewPagerAdapter(Context context, List<String> mtitles, FragmentManager fm) {
        super(fm);
        mTitleList = mtitles;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return createFragmentByTitle(mTitleList.get(position));
    }

    @Override
    public int getCount() {
        return mTitleList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }

    private Fragment createFragmentByTitle(String title) {
        Fragment result = null;

        if (title.equals(mContext.getString(R.string.page_key_topic))) {
            result = new ReadHubTopicFragment();
        } else if (title.equals(mContext.getString(R.string.page_key_news))) {
            result = new ReadHubDevFragment();
        } else if (title.equals(mContext.getString(R.string.page_key_developer))) {
            result = new ReadHubTechFragment();
        }
        return result;
    }
}
