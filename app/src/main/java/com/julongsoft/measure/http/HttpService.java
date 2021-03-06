package com.julongsoft.measure.http;


import com.julongsoft.measure.entity.HomeDataBean;
import com.julongsoft.measure.entity.MoneyData;
import com.julongsoft.measure.entity.MoneyDetialData;
import com.julongsoft.measure.entity.MoneySignListData;
import com.julongsoft.measure.entity.PeriodListData;
import com.julongsoft.measure.entity.SectionDetial;
import com.julongsoft.measure.entity.UserData;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Tao on 2016/11/16.
 */

public interface HttpService {

    /*基础url*/
//      public static final String baseUrl = "http://192.168.7.150:8082/plat/";
//    public static final String baseUrl = "http://60.8.218.156:7000/plat/"; //正式
//    public static final String baseUrl = "http://60.8.218.156:7001/plat_xa_app/"; //正式
//    public static final String baseUrl = "http://27.128.206.151:7001/plat_xa_app/"; //正式
    public static final String baseUrl = "http://27.128.206.151:7002/plat_xa_app/"; //正式
//    public static final String baseUrl = "http://192.168.7.104:8083/plat/"; //测试
//    public static final String baseUrl = "http://60.8.218.142:8089/plat/"; //测试


    /***
     * 登录接口
     * @param code 用户名
     * @param password  密码
     * @return
     */
    @POST("a/app/jlzf/login")
    Call<BaseResultData<UserData>> getLogin(@Query("code") String code,
                                            @Query("password") String password
    );

    /***
     * 修改密码
     * @return
     */
    @POST("a/app/jlzf/updatePass/{token}")
    Call<BaseResultData<String>> updatePassword(@Path("token") String token,
                                                @Query("body") String body
    );


    /***
     * 退出登录
     * @return
     */
    @POST("a/app/jlzf/loginOut/{token}")
    Call<BaseResultData<String>> exitLogin(@Path("token") String token
    );

    /***
     * 工作列表接口
     * @return
     */
    @POST("a/app/jlzf/period/list/{token}/{page}")
    Call<BaseResultData<PeriodListData>> getWorkListData(@Path("token") String token,
                                                         @Path("page") int page,
                                                         @Query("segmentId") String segmentId,
                                                         @Query("num") String num,
                                                         @Query("signState") String state
    );

    /***
     * 资金列表接口
     * @return
     */
    @POST("a/app/zjsj/list/{token}")
    Call<BaseResultData<List<MoneyData>>> getMoneyListData(@Path("token") String token,
                                                           @Body RequestBody requestBody
    );

    /***
     * 资金明细签字列表接口
     * @return
     */
    @GET("a/app/zjsj/signList/{token}")
    Call<BaseResultData<List<MoneySignListData>>> getMoneySignListData(@Path("token") String token,
                                                                       @Query("note") String note
    );

    /***
     * 资金详情列表接口
     * @return
     */
    @GET("a/app/zjsj/sum/{token}")
    Call<BaseResultData<MoneyDetialData>> getMoneySumData(@Path("token") String token,
                                                          @Query("note") String note
    );


    /***
     * 工作列表详情接口
     * @return
     */
    @POST("a/app/jlzf/period/detail/{token}/{id}")
    Call<BaseResultData<SectionDetial>> getWorkListDetial(@Path("token") String token,
                                                          @Path("id") int id
    );

    /***
     * 首页图表数据
     * @return
     */
    @POST("a/app/jlzf/period/chartInfo/{token}")
    Call<BaseResultData<List<HomeDataBean>>> getHomeData(@Path("token") String token
    );


    /***
     * 流程通过接口
     * @return
     */
    @POST("a/app/jlzf/period/sign/{token}/{periodId}/{id}")
    Call<BaseResultData<Boolean>> sign(@Path("token") String token,
                                       @Path("periodId") int periodId,
                                       @Path("id") int id,
                                       @Query("idea") String idea,
                                       @Query("repeal") int repeal
    );

    /***
     * 签字通过接口
     * @return
     */
    @POST("a/app/jlzf/period/signResult/{token}")
    Call<BaseResultData<List<HomeDataBean>>> signResult(@Path("token") String token
    );

    /***
     * 资金签字
     * @return
     */
    @POST("a/app/zjsj/sign/{token}/{id}")
    @FormUrlEncoded
    Call<BaseResultData> signResult(@Path("token") String token, @Path("id") int id, @Field("repeal") int repeal,@Field("idea") String idea
    );
}
