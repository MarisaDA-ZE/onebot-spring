package top.kirisamemarisa.onebotspring.utils;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.Objects;

/**
 * @Author: MarisaDAZE
 * @Description: HttpUtils.描述
 * @Date: 2024/1/21
 */
@SuppressWarnings("deprecation")
@Service
public class HttpUtils {
    private static final OkHttpClient client;

    static {
        client = new OkHttpClient();
    }

    public static String get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            if (!ObjectUtils.isEmpty(body)) {
                return body.string();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String post(String url, String content) {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Unexpected code: " + response);
                return null;
            }
            ResponseBody resBody = response.body();
            if (!ObjectUtils.isEmpty(resBody)) {
                return resBody.string();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
