package com.wpf.app.quicknetwork

import com.wpf.app.quicknetwork.base.*
import com.wpf.app.quicknetwork.call.RealCall
import com.wpf.app.quicknetwork.helper.RetrofitCreateHelper


/**
 * Created by 王朋飞 on 2022/7/25.
 *
 */
inline fun <reified Service, Data, Fail> request(run: Service.() -> RealCall<Data, Fail>): BaseRequest<Data, Fail> {
    return RetrofitCreateHelper.getService<Service>().run(run).enqueue(WpfRequest())
}