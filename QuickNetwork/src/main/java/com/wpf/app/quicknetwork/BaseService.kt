package com.wpf.app.quicknetwork

import com.wpf.app.quicknetwork.base.BaseRequest
import com.wpf.app.quicknetwork.base.BaseResponse
import com.wpf.app.quicknetwork.base.WpfRealCall
import com.wpf.app.quicknetwork.base.WpfRequest
import com.wpf.app.quicknetwork.helper.RetrofitCreateHelper


/**
 * Created by 王朋飞 on 2022/7/25.
 *
 */
inline fun <reified Service, Data, Fail> request(run: Service.() -> WpfRealCall<BaseResponse<Data>, BaseResponse<Fail>>): BaseRequest<BaseResponse<Data>, BaseResponse<Fail>> {
    return RetrofitCreateHelper.getService<Service>().run(run).enqueue(WpfRequest())
}