package com.wpf.app.quick.demo.http

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okio.Buffer
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.Reader
import java.io.Writer
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.nio.charset.StandardCharsets

class TestGsonConverterFactory private constructor(private val gson: Gson) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        if (!(type is ParameterizedType && type.rawType is Class<*> && parentIs(
                type.rawType as Class<*>, TestResponse::class.java
            )) && type !is JSONObject && type !is JSONArray
        ) {
            val baseResponse: TestResponse<*> = TestResponse<Any>()
            val adapter = gson.getAdapter(TypeToken.getParameterized(baseResponse.javaClass, type))
            return CustomGsonResponseBodyConverter(gson, adapter)
        }
        val adapter = gson.getAdapter(TypeToken.get(type))
        return CustomGsonResponseBodyConverter(gson, adapter)
    }

    private fun parentIs(curClass: Class<*>, parentClass: Class<*>): Boolean {
        var child: Class<*>? = curClass
        while (child != null) {
            if (parentClass.isInterface) {
                if (child.interfaces.isNotEmpty() && parentIs(child.interfaces[0], parentClass)) {
                    return true
                } else {
                    if (child == parentClass) return true
                }
            } else {
                if (child == parentClass) return true
            }
            child = child.superclass
        }
        return false
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return CustomGsonRequestBodyConverter(gson, adapter)
    }

    internal class CustomGsonRequestBodyConverter<T>(
        private val gson: Gson,
        private val adapter: TypeAdapter<T>
    ) : Converter<T, RequestBody> {
        @Throws(IOException::class)
        override fun convert(value: T): RequestBody {
            val buffer = Buffer()
            val writer: Writer = OutputStreamWriter(buffer.outputStream(), StandardCharsets.UTF_8)
            val jsonWriter = gson.newJsonWriter(writer)
            adapter.write(jsonWriter, value)
            jsonWriter.close()
            return buffer.readByteString().toRequestBody("application/json; charset=UTF-8".toMediaType())
        }
    }

    internal class CustomGsonResponseBodyConverter<T>(
        private val gson: Gson,
        private val adapter: TypeAdapter<T>
    ) : Converter<ResponseBody, T> {
        private val utf8 = StandardCharsets.UTF_8
        @Throws(IOException::class)
        override fun convert(value: ResponseBody): T {
            val response = value.string()
            val httpStatus = gson.fromJson(response, TestResponse::class.java)
            if (!httpStatus.isSuccess()) {
                //处理失败
                return httpStatus as T
            } else {
                //接口成功
            }
            val contentType = value.contentType()
            val charset = if (contentType != null) contentType.charset(utf8) else utf8
            val inputStream: InputStream = ByteArrayInputStream(response.toByteArray())
            val reader: Reader = InputStreamReader(inputStream, charset)
            val jsonReader = gson.newJsonReader(reader)
            return try {
                // 数据解析异常则返回JSONObject对象
                when (val t = adapter.read(jsonReader)) {
                    is JSONObject -> {
                        return JSONObject(response) as T
                    }

                    is JSONArray -> {
                        return JSONArray(response) as T
                    }

                    is TestResponse<*> -> {
                        when ((t as TestResponse<*>).data) {
                            is JSONObject -> {
                                (t as TestResponse<JSONObject?>).data =
                                    JSONObject(Gson().toJson(httpStatus.data))
                            }

                            is JSONArray -> {
                                (t as TestResponse<JSONArray?>).data =
                                    JSONArray(Gson().toJson(httpStatus.data))
                            }

                            is Map<*, *> -> {
                                (t as TestResponse<Map<*, *>?>).data = httpStatus.data as Map<*, *>?
                            }
                        }
                        return t
                    }

                    else -> t
                }
            } catch (e: Exception) {
                val exceptionResponse: TestResponse<*> = TestResponse<Any>()
                exceptionResponse.codeI = "-1"
                exceptionResponse.errorI = e.message
                exceptionResponse as T
            } finally {
                value.close()
            }
        }
    }

    companion object {
        @JvmOverloads
        fun create(gson: Gson = Gson()): TestGsonConverterFactory {
            return TestGsonConverterFactory(gson)
        }
    }
}
