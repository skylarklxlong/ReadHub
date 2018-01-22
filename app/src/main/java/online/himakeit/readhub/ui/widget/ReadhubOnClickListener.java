package online.himakeit.readhub.ui.widget;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import online.himakeit.readhub.ui.activity.MainActivity;

/**
 * @author：LiXueLong
 * @date：2018/1/22
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class ReadhubOnClickListener implements View.OnClickListener, View.OnLongClickListener {

    boolean mViewIsShow = false;
    private View mView;
    private String shareTitle = "";
    private String shareSummary = "";
    private String jumpUrl = "";

    public ReadhubOnClickListener(View mView, String shareTitle, String shareSummary) {
        this.mView = mView;
        this.shareTitle = shareTitle;
        this.shareSummary = shareSummary;
    }

    public ReadhubOnClickListener(String shareTitle, String shareSummary, String jumpUrl) {
        this.shareTitle = shareTitle;
        this.shareSummary = shareSummary;
        this.jumpUrl = jumpUrl;
    }

    @Override
    public void onClick(View view) {
        if (null == mView) {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            intent.putExtra(MainActivity.INTENT_EXTRA_KEY_ITEM_URL, jumpUrl);
            intent.putExtra(MainActivity.INTENT_EXTRA_KEY_ITEM_TITLE, shareTitle);
            view.getContext().startActivity(intent);
        } else {
            if (mViewIsShow) {
                mView.setVisibility(View.GONE);
                mViewIsShow = false;
            } else {
                mView.setVisibility(View.VISIBLE);
                mViewIsShow = true;
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (TextUtils.isEmpty(shareSummary) || TextUtils.isEmpty(shareTitle)) {
            return false;
        }
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "标题：" + shareTitle + "\n 概述：" + shareSummary);
        sendIntent.setType("text/plain");
        view.getContext().startActivity(Intent.createChooser(sendIntent, shareTitle));
        return true;
    }
}
