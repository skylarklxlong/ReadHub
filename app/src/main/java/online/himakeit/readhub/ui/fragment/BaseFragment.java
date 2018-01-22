package online.himakeit.readhub.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author：LiXueLong
 * @date：2018/1/20
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public abstract class BaseFragment extends Fragment {

    private View mContentView;
    private Context mContext;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = setContentView(inflater, container);
        mContext = getContext();
        mProgressDialog = new ProgressDialog(getmContext());
        mProgressDialog.setCanceledOnTouchOutside(false);
        init();
        initView();
        initData();
        return mContentView;
    }

    protected void initData() {

    }

    protected void init() {

    }

    protected void initView() {
    }

    protected <T extends View> T customFindViewById(int id) {
        return (T) mContentView.findViewById(id);
    }

    protected abstract View setContentView(LayoutInflater inflater, ViewGroup container);

    public View getmContentView() {
        return mContentView;
    }

    public Context getmContext() {
        return mContext;
    }

    public ProgressDialog getmProgressDialog() {
        return mProgressDialog;
    }
}
