package com.wpf.app.quick.demo.http

import com.wpf.app.quick.demo.http.call.TestCommonCall
import com.wpf.app.quick.demo.wanandroid.model.首页文章
import com.wpf.plugins.activity.GetClass
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import kotlin.reflect.KClass

@GetClass
interface TestApi {

    @GET("/article/list/{page}/json")
    fun 首页文章列表(
        @Path("page") page: Int,
        @Query("pageSize") pageSize: Int = 0,
    ): TestCommonCall<首页文章>
}
