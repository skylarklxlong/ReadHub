package online.himakeit.readhub.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import online.himakeit.readhub.MyApp;
import online.himakeit.readhub.R;
import online.himakeit.readhub.module.firim.AppUpdateInfo;
import online.himakeit.readhub.network.ApiClient;
import online.himakeit.readhub.network.IFirImService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author：LiXueLong
 * @date：2018/1/22
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class UpdateUtil {

    public static void checkUpdate(final Activity activity, boolean isShowToast) {
        ApiClient.create(IFirImService.class).getTheLastAppInfo()
                .enqueue(new Callback<AppUpdateInfo>() {
                    @Override
                    public void onResponse(Call<AppUpdateInfo> call, Response<AppUpdateInfo> response) {
                        if (response.isSuccessful()) {
                            final AppUpdateInfo appUpdateInfo = response.body();
                            if (appUpdateInfo != null) {
                                int newVersion = Integer.parseInt(appUpdateInfo.getBuild());
                                if (MyApp.getVersionCode() < newVersion) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                    builder.setTitle(activity.getString(R.string.game_update_notnew_Title));
                                    builder.setMessage(appUpdateInfo.getChangelog());
                                    builder.setPositiveButton(activity.getString(R.string.game_update_notnew_update_btn), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            String url = appUpdateInfo.getUpdate_url();
                                            Intent intent = new Intent(Intent.ACTION_VIEW);
                                            intent.setData(Uri.parse(url));
                                            activity.startActivity(intent);
                                        }
                                    });
                                    builder.setNegativeButton(activity.getString(R.string.game_update_notnew_cancle_btn), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.create().show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AppUpdateInfo> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

        if (!isShowToast) {
            // TODO: 2018/1/22 弹提示框
            return;
        }
    }

}
