package com.julongsoft.measure.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by duoqi.tao on 2017/4/27.
 */

public class HttpManager {

    private volatile static HttpManager instance;

    private static final int CONNECTION_TIME_OUT = 10;
    private static final int READ_TIME_OUT = 10;
    private static final int WRITE_TIME_OUT = 10;
    private HttpService httpService;

    //获取单例
    public synchronized static HttpManager getInstance() {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    private HttpManager() {


//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //手动创建一个OkHttpClient并设置超时时间等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS);

        //设置重连
        builder.retryOnConnectionFailure(true);

        /*创建retrofit对象*/
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
//                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpService.baseUrl)
                .build();

        httpService = retrofit.create(HttpService.class);

    }

    public HttpService getHttpService() {
        return httpService;
    }
}
