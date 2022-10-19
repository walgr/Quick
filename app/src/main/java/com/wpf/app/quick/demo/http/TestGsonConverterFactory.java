package com.wpf.app.quick.demo.http;

import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import org.json.JSONArray;
import org.json.JSONObject;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created by wg on 2017/5/9.
 */

public class TestGsonConverterFactory extends Converter.Factory {

    private final Gson gson;

    private TestGsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    public static TestGsonConverterFactory create() {
        return create(new Gson());
    }

    public static TestGsonConverterFactory create(Gson gson) {
        return new TestGsonConverterFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(@NonNull Type type, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        if (!(type instanceof ParameterizedType && ((ParameterizedType) type).getRawType() instanceof Class && parentIs((Class<?>) ((ParameterizedType) type).getRawType(), TestResponse.class)) && !(type instanceof JSONObject) && !(type instanceof JSONArray)) {
            TestResponse<?> baseResponse = new TestResponse<>();
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.getParameterized(baseResponse.getClass(), type));
            return new CustomGsonResponseBodyConverter<>(gson, adapter);
        }
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomGsonResponseBodyConverter<>(gson, adapter);
    }

    private boolean parentIs(Class<?> curClass, Class<?> parentClass) {
        Class<?> child = curClass;
        while (child != null) {
            if (parentClass.isInterface()) {
                if (child.getInterfaces().length > 0 && parentIs(child.getInterfaces()[0], parentClass)) {
                    return true;
                } else {
                    if (child == parentClass) return true;
                }
            } else {
                if (child == parentClass) return true;
            }
            child = child.getSuperclass();
        }
        return false;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(@NonNull Type type, @NonNull Annotation[] parameterAnnotations, @NonNull Annotation[] methodAnnotations, @NonNull Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomGsonRequestBodyConverter<>(gson, adapter);
    }

    static final class CustomGsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private final Charset UTF_8 = StandardCharsets.UTF_8;

        private final Gson gson;
        private final TypeAdapter<T> adapter;

        CustomGsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(@NonNull T value) throws IOException {
            Buffer buffer = new Buffer();
            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            JsonWriter jsonWriter = gson.newJsonWriter(writer);
            adapter.write(jsonWriter, value);
            jsonWriter.close();
            return RequestBody.Companion.create(buffer.readByteString(), MEDIA_TYPE);
        }
    }

    static final class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final Gson gson;
        private final TypeAdapter<T> adapter;
        private final Charset UTF_8 = StandardCharsets.UTF_8;

        CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            String response = value.string();
            TestResponse<?> httpStatus = gson.fromJson(response, TestResponse.class);
            if (!httpStatus.isSuccess()) {
                //处理失败
                return (T) httpStatus;
            } else {
                //接口成功
            }
            MediaType contentType = value.contentType();
            Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
            InputStream inputStream = new ByteArrayInputStream(response.getBytes());
            Reader reader = new InputStreamReader(inputStream, charset);
            JsonReader jsonReader = gson.newJsonReader(reader);

            try {
                T t = adapter.read(jsonReader);
                // 数据解析异常则返回jsonobject对象
                if (t instanceof JSONObject) {
                    return (T) new JSONObject(response);
                } else if (t instanceof JSONArray) {
                    return (T) new JSONArray(response);
                } else if (t instanceof TestResponse) {
                    if (((TestResponse<?>) t).getData() instanceof JSONObject) {
                        ((TestResponse<JSONObject>) t).setData(new JSONObject(new Gson().toJson(httpStatus.getData())));
                    } else if (((TestResponse<?>) t).getData() instanceof JSONArray) {
                        ((TestResponse<JSONArray>) t).setData(new JSONArray(new Gson().toJson(httpStatus.getData())));
                    } else if (((TestResponse<?>) t).getData() instanceof Map) {
                        ((TestResponse<Map>) t).setData((Map) httpStatus.getData());
                    }
                    return (T) t;
                }
                return t;
            } catch (Exception e) {
                TestResponse exceReponse = new TestResponse();
                exceReponse.setErrorCode("-1");
                exceReponse.setErrorMsg(e.getMessage());
                return (T) exceReponse;
            } finally {
                value.close();
            }
        }
    }
}
