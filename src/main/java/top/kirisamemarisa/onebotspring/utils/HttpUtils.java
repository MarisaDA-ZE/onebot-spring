package top.kirisamemarisa.onebotspring.utils;

import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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


    /**
     * 替换URL的域名代理
     *
     * @param url   资源地址
     * @param proxy 域名代理（不要协议头、尾部的斜杠，形如 "i.kmarisa.icu"）
     * @return 替换代理后的URL地址
     */
    public static String setUrlProxy(String url, String proxy) {

        int schemeEnd = url.indexOf("://") + 3;
        int domainEnd = url.indexOf('/', schemeEnd);
        if (domainEnd == -1) {
            domainEnd = url.length();
        }
        String scheme = url.substring(0, schemeEnd);
        String pathAndQuery = url.substring(domainEnd);
        return scheme + proxy + pathAndQuery;
    }
}
