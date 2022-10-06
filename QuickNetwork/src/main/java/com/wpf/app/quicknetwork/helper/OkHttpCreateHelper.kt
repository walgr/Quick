package com.wpf.app.quicknetwork.helper

import okhttp3.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

/**
 * Created by 王朋飞 on 2022/7/21.
 *
 */
object OkHttpCreateHelper {

    private lateinit var okHttpClient: OkHttpClient

    fun newInstance(
        timeOut: Long = 30,
        interceptorList: List<Interceptor>? = null,
        cookieJar: CookieJar? = null,
        sslFactory: SSLSocketFactory? = null,
        trustManager: X509TrustManager? = null,
        hostnameVerifier: HostnameVerifier? = null
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            .readTimeout(timeOut, TimeUnit.SECONDS)
            .writeTimeout(timeOut, TimeUnit.SECONDS)
        sslFactory?.let {
            trustManager?.let {
                builder.sslSocketFactory(sslFactory, trustManager)
            }
        }
        hostnameVerifier?.let {
            builder.hostnameVerifier(it)
        }
        interceptorList?.forEach {
            builder.addInterceptor(it)
        }
        cookieJar?.let {
            builder.cookieJar(it)
        }
        okHttpClient = builder.build()
        return okHttpClient
    }
}