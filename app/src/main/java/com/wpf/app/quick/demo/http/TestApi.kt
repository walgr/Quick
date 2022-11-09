package com.wpf.app.quick.demo.http

import com.wpf.app.quick.demo.http.call.TestCommonCall
import com.wpf.app.quick.demo.http.call.TestNoResponseCall
import com.wpf.app.quick.demo.http.model.首页文章
import retrofit2.http.GET
import retrofit2.http.Path

interface TestApi {

    @GET("/article/list/{page}/json")
    fun 首页文章列表(
        @Path("page") page: Int
    ): TestCommonCall<首页文章>
}