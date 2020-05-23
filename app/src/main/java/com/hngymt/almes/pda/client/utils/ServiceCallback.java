package com.hngymt.almes.pda.client.utils;

import com.hngymt.almes.pda.client.models.ErrorInfo;
import com.squareup.okhttp.Request;

import java.io.IOException;

public abstract class ServiceCallback {
    public void onSuccess(Object resultObj) {
    }

    public void onError(ErrorInfo errorInfo) {
    }

    public void onFailure(Request request, IOException e) {
    }
}
