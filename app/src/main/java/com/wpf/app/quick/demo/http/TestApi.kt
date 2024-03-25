package com.wpf.app.quick.demo.http

import com.wpf.app.quick.annotations.request.GenerateRequest
import com.wpf.app.quick.demo.wanandroid.model.HomePage
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@GenerateRequest(fileName = "TestService")
interface TestApi {

    @GET("/article/list/{page}/json")
    fun homePageList(
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int = 10,
    ): TestCommonCall<HomePage>
}
