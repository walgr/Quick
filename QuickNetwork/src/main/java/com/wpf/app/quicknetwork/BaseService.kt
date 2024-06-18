package com.wpf.app.quicknetwork

import com.wpf.app.quicknetwork.base.BaseRequest
import com.wpf.app.quicknetwork.base.WpfRequest
import com.wpf.app.quicknetwork.call.RealCall
import com.wpf.app.quicknetwork.helper.RetrofitCreateHelper


/**
 * Created by 王朋飞 on 2022/7/25.
 *
 */
inline fun <reified Service,reified Data,reified Fail> request(run: Service.() -> RealCall<Data, Fail>): BaseRequest<Data, Fail> {
    return RetrofitCreateHelper.getService<Service>().run(run).enqueue(WpfRequest())
}

inline fun <reified Service,reified Data,reified Fail> requestCls(cls: Class<Service>, run: Service.() -> RealCall<Data, Fail>): BaseRequest<Data, Fail> {
    return RetrofitCreateHelper.getServiceT(cls).run(run).enqueue(WpfRequest())
}