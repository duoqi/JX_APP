package com.julongsoft.measure.http;

import android.content.Intent;

import com.julongsoft.measure.global.GlobalApplication;
import com.julongsoft.measure.utils.PrefUtil;
import com.julongsoft.measure.utils.Print;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by duoqi.tao on 2017/8/17.
 */

public abstract class HttpResultCallback<T extends BaseResultData> implements Callback<T> {


    private static final String TAG = "HttpResultCallback";

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        Print.e(TAG + "server", response.raw().code());

        if (response.raw().code() == 200) {//200是服务器有合理响应

            Print.e(TAG, response.body().code);

            if (response.body().code == 0) {

                onSuc(response);
            } else if (response.body().code == 221) {
                //用户令牌过期
                outLogin();
            } else if (response.body().code == 222) {
                //用户令牌不存在
                outLogin();
            } else {
                onFail(response.body().getError());
            }

        } else {//失败响应
            onFailure(call, new RuntimeException("response error,detail = " + response.raw().toString()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t instanceof SocketTimeoutException) {
            onFail("网络失败");
        } else if (t instanceof ConnectException) {
            onFail("网络连接异常，请检查网络");
        } else if (t instanceof RuntimeException) {
            onFail("运行时异常");
        }
        onFail(t.getMessage());
    }

    public abstract void onSuc(Response<T> response);

    public abstract void onFail(String message);

    public void outLogin() {
        boolean isLogin = PrefUtil.getBoolean(GlobalApplication.getContext(), "isLogin", false);
        if (isLogin) {
            Intent intent = new Intent();
            intent.setAction("exit_app");
            GlobalApplication.getContext().sendBroadcast(intent);

        }


    }

}
