package online.himakeit.readhub.network;

import online.himakeit.readhub.config.Config;
import online.himakeit.readhub.module.firim.AppUpdateInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * @author：LiXueLong
 * @date：2018/1/22
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public interface IFirImService {
    /**
     * 获取fir.im中的Love的最新版本
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET(Config.URL_AppUpdateInfo)
    Call<AppUpdateInfo> getTheLastAppInfo();
}
