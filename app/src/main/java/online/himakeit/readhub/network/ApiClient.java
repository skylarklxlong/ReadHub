package online.himakeit.readhub.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author：LiXueLong
 * @date：2018/1/22
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class ApiClient {

    private static String baseUrl = "";
    private static Retrofit retrofit = null;

    public static void init(String baseUrl) {
        setBaseUrl(baseUrl);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
                .build();

        Gson gson = new GsonBuilder().create();

        ApiClient.retrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    public static <T> T create(final Class<T> serviceCls) {
        if (ApiClient.retrofit == null) {
            throw new IllegalStateException("ApiClient.retrofit is null, your should call ApiClient.init it before use");
        }
        return retrofit.create(serviceCls);
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        ApiClient.baseUrl = baseUrl;
    }
}
