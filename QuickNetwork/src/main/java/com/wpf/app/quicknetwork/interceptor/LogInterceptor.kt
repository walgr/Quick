package com.wpf.app.quicknetwork.interceptor

import com.wpf.app.quickutil.LogUtil.e
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

/**
 * Created by 王朋飞 on 2021/9/13.
 */
class LogInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val startTime = System.currentTimeMillis()
        val request: Request = chain.request()
        val response: Response = chain.proceed(chain.request())
        val mediaType = response.body!!.contentType()
        val content = response.body!!.string()
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
        e(TAG, "\n")
        e(TAG, "----------Start----------------")
        e(TAG, "| $request")
        val method = request.method
        if ("POST" == method) {
            val sb = StringBuilder()
            if (request.body is FormBody) {
                val body = request.body as FormBody?
                for (i in 0 until body!!.size) {
                    sb.append(body.encodedName(i)).append("=").append(body.encodedValue(i)).append(",")
                }
                sb.delete(sb.length - 1, sb.length)
                e(TAG, "| RequestParams:{$sb}")
            }
        }
        val result = decodeUnicode(content)
        e(TAG, "| Response:$result")
        e(TAG, "----------End:" + duration + "ms----------")
        return response.newBuilder()
            .body(content.toResponseBody(mediaType))
            .build()
    }

    /*
     * unicode编码转中文
     */
    private fun decodeUnicode(dataStr: String): String {
        try {
            if (!dataStr.contains("\\u")) return dataStr
            val buffer = StringBuilder()
            val unicodeBuffer = StringBuilder()
            var isUnicode = false
            for (start in dataStr.indices) {
                var start1Char = 0.toChar()
                if (start + 1 < dataStr.length) {
                    start1Char = dataStr[start + 1]
                }
                val startChar = dataStr[start]
                if ('\\' == startChar && 'u' == start1Char) {
                    if (isUnicode) {
                        buffer.append(unicodeToString(unicodeBuffer.toString()))
                        unicodeBuffer.delete(0, unicodeBuffer.length)
                    }
                    isUnicode = true
                    unicodeBuffer.append(startChar)
                } else {
                    if (isUnicode) {
                        unicodeBuffer.append(startChar)
                    } else {
                        buffer.append(startChar)
                    }
                }
                if (isUnicode) {
                    if ('\"' == start1Char || unicodeBuffer.length >= 6) {
                        buffer.append(unicodeToString(unicodeBuffer.toString()))
                        unicodeBuffer.delete(0, unicodeBuffer.length)
                        isUnicode = false
                    }
                }
            }
            return buffer.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dataStr
    }

    private fun unicodeToString(unicode: String): String {
        val letter = unicode.replace("\\u", "").toInt(16).toChar() // 16进制parse整形字符串。
        return Character.valueOf(letter).toString()
    }

    companion object {
        var TAG = "接口"
    }
}