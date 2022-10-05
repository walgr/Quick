package com.wpf.app.quicknetwork

import cn.goodjobs.community.user.network.base.BaseRequest
import cn.goodjobs.community.user.network.base.BaseResponse
import cn.goodjobs.community.user.network.base.WpfRealCall
import cn.goodjobs.community.user.network.base.WpfRequest
import cn.goodjobs.community.user.network.helper.RetrofitCreateHelper


/**
 * Created by 王朋飞 on 2022/7/25.
 *
 */
inline fun <reified Service, Data, Fail> request(run: Service.() -> WpfRealCall<BaseResponse<Data>, BaseResponse<Fail>>): BaseRequest<BaseResponse<Data>, BaseResponse<Fail>> {
    return RetrofitCreateHelper.getService<Service>().run(run).enqueue(WpfRequest())
}