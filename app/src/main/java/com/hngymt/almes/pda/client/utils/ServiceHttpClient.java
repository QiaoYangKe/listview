package com.hngymt.almes.pda.client.utils;

import android.content.Intent;
import android.os.Environment;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.hngymt.almes.pda.client.BaseActivity;
import com.hngymt.almes.pda.client.LoginActivity;
import com.hngymt.almes.pda.client.models.AjaxResponseOfTResult;
import com.hngymt.almes.pda.client.models.ErrorInfo;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ServiceHttpClient extends AppCompatActivity {
    private final MediaType JSON;

    private final OkHttpClient client;
    private final Gson gson;

    private String accessToken = "";
    private String encryptedAccessToken = "";

    private static class RequestInstance {
        private static final ServiceHttpClient instance = new ServiceHttpClient();
    }

    private ServiceHttpClient() {
        JSON = MediaType.parse("application/json");

        client = new OkHttpClient();
        gson = new Gson();
    }

    public static ServiceHttpClient getInstance() {
        return RequestInstance.instance;
    }

    public void Post(String url, Object data, final Type resultType, final BaseActivity activity, final ServiceCallback callback) {
        RequestBody body = RequestBody.create(JSON, gson.toJson(data));

        Request.Builder requestBuilder = new Request.Builder()
                .url(ConfigHelper.getInstance().getServiceRootUrl(activity) + url)
                .post(body);

        if (accessToken != null && !accessToken.equals("")) {
            requestBuilder.header("Authorization", "Bearer " + accessToken);
        }
        final Request request = requestBuilder.build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if (activity != null) {
                    EasyToast.showText(activity, e.getLocalizedMessage(), Toast.LENGTH_LONG);
                }
                callback.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (401 == response.code()) {
                    EasyToast.showText(activity, "用户信息已过期", Toast.LENGTH_LONG);
                    Intent intent = new Intent(activity, LoginActivity.class);
                    startActivity(intent);
                } else {
                    AjaxResponseOfTResult resp = gson.fromJson(response.body().string(), AjaxResponseOfTResult.class);
                    if (resp.getSuccess()) {
                        if (resp.getResult() == null) {
                            callback.onSuccess(null);
                        } else {
                            callback.onSuccess(gson.fromJson(gson.toJson(resp.getResult()), resultType));
                        }
                    } else {
                        final ErrorInfo errorInfo = gson.fromJson(gson.toJson(resp.getError()), ErrorInfo.class);
                        if (activity != null) {
                            EasyToast.showText(activity, errorInfo.getMessage() + "\n" + errorInfo.getDetails(), Toast.LENGTH_LONG);
                        }
                        callback.onError(errorInfo);
                    }
                }
            }
        });
    }

    public void Get(String url, Object param, final Type resultType, final BaseActivity activity, final ServiceCallback callback) {
        if (null != param) {
            try {
                if (null!=getQueryString(param)){
                    url = url + "?" + getQueryString(param);
                }
            } catch (IllegalAccessException e) {
                EasyToast.showText(activity, e.getLocalizedMessage(), Toast.LENGTH_LONG);
            }
        }
        Request.Builder requestBuilder = new Request.Builder()
                .url(ConfigHelper.getInstance().getServiceRootUrl(activity) + url)
                .get();
        if (accessToken != null && !accessToken.equals("")) {
            requestBuilder.header("Authorization", "Bearer " + accessToken);
        }
        Request request = requestBuilder.build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                if (activity != null) {
                    EasyToast.showText(activity, e.getLocalizedMessage(), Toast.LENGTH_LONG);
                }
                callback.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (401 == response.code()) {
                    EasyToast.showText(activity, "用户信息已过期", Toast.LENGTH_LONG);
                    Intent intent = new Intent(activity, LoginActivity.class);
                    startActivity(intent);
                } else {
                    AjaxResponseOfTResult resp = gson.fromJson(response.body().string(), AjaxResponseOfTResult.class);
                    if (resp.getSuccess()) {
                        if (resp.getResult() == null) {
                            callback.onSuccess(null);
                        } else {
                            callback.onSuccess(gson.fromJson(gson.toJson(resp.getResult()), resultType));
                        }
                    } else {
                        final ErrorInfo errorInfo = gson.fromJson(gson.toJson(resp.getError()), ErrorInfo.class);
                        if (activity != null) {
                            EasyToast.showText(activity, errorInfo.getMessage() + "\n" + errorInfo.getDetails(), Toast.LENGTH_LONG);
                        }
                        callback.onError(errorInfo);
                    }
                }
            }
        });
    }

    public void Download(String url, final BaseActivity activity, final ServiceCallback callback) {
        final Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if (activity != null) {
                    EasyToast.showText(activity, e.getLocalizedMessage(), Toast.LENGTH_LONG);
                }
                callback.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String appPathName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/almes-wms-pda-client.apk";
                if (response != null) {
                    InputStream is = response.body().byteStream();
                    FileOutputStream fos = new FileOutputStream(new File(appPathName));
                    int len = 0;
                    byte[] buffer = new byte[2048];
                    while (-1 != (len = is.read(buffer))) {
                        fos.write(buffer, 0, len);
                    }
                    fos.flush();
                    fos.close();
                    is.close();
                    callback.onSuccess(appPathName);
                }
            }
        });
    }


    public boolean hasToken() {
        return null != accessToken && !"".equals(accessToken);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEncryptedAccessToken() {
        return encryptedAccessToken;
    }

    public void setEncryptedAccessToken(String encryptedAccessToken) {
        this.encryptedAccessToken = encryptedAccessToken;
    }

    public static String getQueryString(Object param) throws IllegalAccessException {
        Class<? extends Object> c = param.getClass();
        List fieldsList = new ArrayList<Field[]>();
        while (c != null) {
            Field[] declaredFields = c.getDeclaredFields();
            fieldsList.add(declaredFields);
            c = c.getSuperclass();
        }
        Map<String, Object> map = new TreeMap<>();

        for (Object fields : fieldsList) {
            Field[] f = (Field[]) fields;
            for (Field field : f) {
                field.setAccessible(true);
                String name = field.getName();
                Object value = field.get(param);
                if (value != null) {
                    map.put(name, value);
                }
            }
        }
        Set<Map.Entry<String, Object>> set = map.entrySet();
        Iterator<Map.Entry<String, Object>> it = set.iterator();
        StringBuffer sb = new StringBuffer();
        while (it.hasNext()) {
            Map.Entry<String, Object> e = it.next();
            sb.append(e.getKey()).append("=").append(e.getValue()).append("&");
        }
        if (sb.length() > 1) {
            return sb.deleteCharAt(sb.length() - 1).toString();
        } else {
            return null;
        }
    }
}


