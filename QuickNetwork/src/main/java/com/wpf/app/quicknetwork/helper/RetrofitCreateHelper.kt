package com.wpf.app.quicknetwork.helper

import com.wpf.app.quicknetwork.call.RealCallAdapterFactory
import com.wpf.app.quicknetwork.call.CommonCallAdapterFactory
import com.wpf.app.quicknetwork.call.NoResponseCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import kotlin.reflect.KClass

/**
 * Created by 王朋飞 on 2022/7/21.
 *
 */
object RetrofitCreateHelper {

    private val retrofitMap: MutableMap<String, Retrofit> = mutableMapOf()
    private val retrofitServiceMap: MutableMap<KClass<*>, Retrofit> = mutableMapOf()
    val serviceMap: MutableMap<KClass<*>, Any> = mutableMapOf()

    /**
     * 存储已经初始化过的Retrofit
     */
    fun putInitedRetrofit(baseUrl: String, serviceCls: KClass<*>, retrofit: Retrofit) {
        retrofitMap[baseUrl] = retrofit
        retrofitServiceMap[serviceCls] = retrofit
    }

    fun putInitService(serviceCls: KClass<*>, service: Any) {
        serviceMap[serviceCls] = service
    }

    fun createService(serviceCls: KClass<*>): Any {
        return retrofitServiceMap[serviceCls]?.create(serviceCls.java)
            ?: throw RuntimeException("请先调用newInstance初始化Retrofit")
    }

    fun newInstance(
        baseUrl: String,
        serviceCls: KClass<*>,
        callFactoryList: List<CallAdapter.Factory>? = null,
        converterFactoryList: List<Converter.Factory>? = null,
        okHttp: OkHttpClient = OkHttpCreateHelper.newInstance()
    ): Retrofit {
        var retrofit = retrofitMap[baseUrl]
        if (retrofit == null) {
            val retrofitBuilder =
                Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RealCallAdapterFactory.create())
                    .addCallAdapterFactory(NoResponseCallAdapterFactory.create())
                    .addCallAdapterFactory(CommonCallAdapterFactory.create())
                    .client(okHttp)
            callFactoryList?.forEach {
                retrofitBuilder.addCallAdapterFactory(it)
            }
            converterFactoryList?.forEach {
                retrofitBuilder.addConverterFactory(it)
            }
            retrofit = retrofitBuilder.build()
            retrofitMap[baseUrl] = retrofit
            retrofitServiceMap[serviceCls] = retrofit
        }
        return retrofit!!
    }

    fun getRetrofit(baseUrl: String): Retrofit? {
        return retrofitMap[baseUrl]
    }

    inline fun <reified T> getService(): T {
        val cls = T::class
        var service: Any? = serviceMap[cls]
        if (service == null) {
            service = createService(cls)
            serviceMap[T::class] = service
        }
        return service as T
    }
}