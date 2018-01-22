package online.himakeit.readhub.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author：LiXueLong
 * @date：2018/1/22
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class DownloadManager {

    public static void setBitmapToImgView(final ImageView imgView, final String imgUrl) {
        if (!TextUtils.isEmpty(imgUrl)) {
            ThreadManager.getmInstance().start(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(imgUrl);
                        final Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
                        if (bitmap != null) {
                            imgView.post(new Runnable() {
                                @Override
                                public void run() {
                                    imgView.setImageBitmap(bitmap);
                                }
                            });
                        }
                    } catch (MalformedURLException e) {
                        /**
                         * URL转换出错
                         */
                        e.printStackTrace();
                    } catch (IOException e) {
                        /**
                         * IO流异常
                         */
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
