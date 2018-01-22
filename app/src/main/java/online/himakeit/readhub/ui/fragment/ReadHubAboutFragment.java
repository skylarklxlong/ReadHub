package online.himakeit.readhub.ui.fragment;

import online.himakeit.readhub.R;

/**
 * @author：LiXueLong
 * @date：2018/1/22
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class ReadHubAboutFragment extends WebViewFragment {
    @Override
    protected String getLoadUrl() {
        return getString(R.string.link_readhub_desc);
    }
}
