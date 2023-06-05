package com.wpf.app.quicknetwork.base


/**
 * Created by 王朋飞 on 2022/7/22.
 *
 */
abstract class JobRequest<SResponse, FResponse>(val context: RequestCoroutineScope? = null): BaseRequest<SResponse, FResponse>()