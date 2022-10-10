package com.wpf.app.quick.demo.http

import com.wpf.app.quick.demo.http.model.首页文章
import com.wpf.app.quicknetwork.base.WpfCommonCall
import com.wpf.app.quicknetwork.base.WpfRealCall
import retrofit2.http.GET
import retrofit2.http.Path

interface TestApi {

    @GET("/article/list/{page}/json")
    fun 首页文章列表(
        @Path("page") path: Int
    ): WpfCommonCall<首页文章>
}