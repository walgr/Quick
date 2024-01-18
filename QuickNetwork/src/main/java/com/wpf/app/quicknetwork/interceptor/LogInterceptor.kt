package com.wpf.app.quicknetwork.interceptor

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.wpf.app.quickutil.log.LogUtil.e
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException


/**
 * Created by 王朋飞 on 2021/9/13.
 */
class LogInterceptor(context: Context) : Interceptor {

    private val versionName = getPackageInfo(context)?.versionName

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val startTime = System.currentTimeMillis()
        val request = chain.request()
        val response = chain.proceed(chain.request())
        val mediaType = response.body!!.contentType()
        val content = response.body!!.string()
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
        e(TAG, "\n")
        e(TAG, "----------Start----------------")
        //            LogUtil.e(TAG, "| " + request.toString());
        e(TAG, (("| Request:{method=" + request.method) + ", url=" + request.url) + ", version=" + versionName + "}")
        val method = request.method
        //            String headers = request.headers().toString();
//            headers = headers.replaceAll("\n", " ");
//            if (headers.length() > splitSize) {
//                int i = 0;
//                for (String string : splitString(headers)) {
//                    LogUtil.e(TAG, (i++ == 0 ? "| Header:{" : "| ") + string);
//                }
//                System.out.println();
//                LogUtil.e(TAG, "}");
//            } else {
//                LogUtil.e(TAG, "| Header:{" + headers + "}");
//            }
        if ("POST" == method) {
            val sb = java.lang.StringBuilder()
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
        if (result.length > splitSize) {
            var i = 0
            for (string in splitString(result)) {
                e(TAG, (if (i++ == 0) "| Response:" else "| ") + string)
            }
            println()
        } else {
            e(TAG, "| Response:$result")
        }
        //            LogUtil.loge("| Response:" + result);
        e(TAG, "----------End:" + duration + "ms----------")
        return response.newBuilder()
            .body(content.toResponseBody(mediaType))
            .build()
    }

    private fun getPackageInfo(context: Context): PackageInfo? {
        kotlin.runCatching {
            return context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_CONFIGURATIONS
            )
        }
        return null
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

    /**
     * 把字符串分割成最多2048长度的数组
     */
    private val splitSize: Int = 2048
    private fun splitString(str: String): List<String> {
        var strTemp = str
        val result: MutableList<String> = ArrayList()
        while (strTemp.length > splitSize) {
            result.add(strTemp.substring(0, splitSize))
            strTemp = strTemp.substring(splitSize)
        }
        result.add(strTemp)
        return result
    }

    companion object {
        var TAG = "接口"
    }
}