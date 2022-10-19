package com.wpf.app.quicknetwork.coverter;

import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import com.wpf.app.quicknetwork.base.BaseResponseS;
import com.wpf.app.quicknetwork.base.BaseResponseI;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by wg on 2017/5/9.
 */

public class WpfGsonConverterFactory extends Converter.Factory {

    private final Gson gson;

    private WpfGsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    public static WpfGsonConverterFactory create() {
        return create(new Gson());
    }

    public static WpfGsonConverterFactory create(Gson gson) {
        return new WpfGsonConverterFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(@NonNull Type type, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        if (!(type instanceof BaseResponseI) && !(type instanceof JSONObject) && !(type instanceof JSONArray)) {
            BaseResponseS baseResponse = new BaseResponseS(type);
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.getParameterized(baseResponse.getClass(), type));
            return new CustomGsonResponseBodyConverter<>(gson, adapter);
        }
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomGsonResponseBodyConverter<>(gson, adapter);
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
            BaseResponseS httpStatus = gson.fromJson(response, BaseResponseS.class);
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
                } else if (t instanceof BaseResponseS) {
                    if (((BaseResponseS<?>) t).getData() instanceof JSONObject) {
                        ((BaseResponseS<JSONObject>) t).setData(new JSONObject(new Gson().toJson(((BaseResponseS<?>) httpStatus).getData())));
                    } else if (((BaseResponseS<?>) t).getData() instanceof JSONArray) {
                        ((BaseResponseS<JSONArray>) t).setData(new JSONArray(new Gson().toJson(((BaseResponseS<?>) httpStatus).getData())));
                    } else if (((BaseResponseS<?>) t).getData() instanceof Map) {
                        ((BaseResponseS<Map>) t).setData((Map) httpStatus.getData());
                    }
                    return (T) t;
                }
                return t;
            } catch (Exception e) {
                BaseResponseS exceReponse = new BaseResponseS();
                exceReponse.setCode("-1");
                exceReponse.setErrorMessage(e.getMessage());
                return (T) exceReponse;
            } finally {
                value.close();
            }
        }
    }
}
