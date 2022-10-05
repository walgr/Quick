package com.wpf.app.quicknetwork.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by 王朋飞 on 2021/9/13.
 */
public class LogInterceptor implements Interceptor {
    public static String TAG = "接口";

    private static final int splitSize = 1024;

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        long startTime = System.currentTimeMillis();
        Request request = chain.request();
        okhttp3.Response response = chain.proceed(chain.request());
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        if (App.DEBUG) {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            LogUtil.e(TAG, "\n");
            LogUtil.e(TAG, "----------Start----------------");
            LogUtil.e(TAG, "| " + request.toString());
            String method = request.method();
            if ("POST".equals(method)) {
                StringBuilder sb = new StringBuilder();
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();
                    for (int i = 0; i < body.size(); i++) {
                        sb.append(body.encodedName(i)).append("=").append(body.encodedValue(i)).append(",");
                    }
                    sb.delete(sb.length() - 1, sb.length());
                    LogUtil.e(TAG, "| RequestParams:{" + sb + "}");
                }
            }
            String result = decodeUnicode(content);
//            if (result.length() > splitSize) {
//                int i = 0;
//                for (String string : splitString(result)) {
//                    LogUtil.e(i == 0 ? TAG : "", (i++ == 0 ? "| Response:" : "") + string);
//                }
//                System.out.println();
//            } else {
                LogUtil.e(TAG, "| Response:" + result);
//            }
//            LogUtil.loge("| Response:" + result);
            LogUtil.e(TAG, "----------End:" + duration + "ms----------");
        }
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }

    /*
     * unicode编码转中文
     */
    private String decodeUnicode(String dataStr) {
        try {
            if (!dataStr.contains("\\u")) return dataStr;
            final StringBuilder buffer = new StringBuilder();
            StringBuilder unicodeBuffer = new StringBuilder();
            boolean isUnicode = false;
            for (int start = 0; start < dataStr.length(); start++) {
                char start_1Char = 0;
                if (start + 1 < dataStr.length()) {
                    start_1Char = dataStr.charAt(start + 1);
                }
                char startChar = dataStr.charAt(start);
                if ('\\' == startChar && 'u' == start_1Char) {
                    if (isUnicode) {
                        buffer.append(unicodeToString(unicodeBuffer.toString()));
                        unicodeBuffer.delete(0, unicodeBuffer.length());
                    }
                    isUnicode = true;
                    unicodeBuffer.append(startChar);
                } else {
                    if (isUnicode) {
                        unicodeBuffer.append(startChar);
                    } else {
                        buffer.append(startChar);
                    }
                }
                if (isUnicode) {
                    if ('\"' == start_1Char || unicodeBuffer.length() >= 6) {
                        buffer.append(unicodeToString(unicodeBuffer.toString()));
                        unicodeBuffer.delete(0, unicodeBuffer.length());
                        isUnicode = false;
                    }
                }
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataStr;
    }

    private String unicodeToString(String unicode) {
        char letter = (char) Integer.parseInt(unicode.replace("\\u", ""), 16); // 16进制parse整形字符串。
        return Character.valueOf(letter).toString();
    }

    /**
     * 把字符串分割成最多512长度的数组
     */
    private List<String> splitString(String str) {
        List<String> result = new ArrayList<>();
        while (str.length() > splitSize) {
            result.add(str.substring(0, splitSize));
            str = str.substring(splitSize);
        }
        result.add(str);
        return result;
    }
}
