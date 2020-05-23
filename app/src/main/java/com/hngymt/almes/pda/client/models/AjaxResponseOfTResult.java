package com.hngymt.almes.pda.client.models;

public class AjaxResponseOfTResult<TResult> extends AjaxResponseBase {
    private TResult result;

    public TResult getResult() {
        return result;
    }

    public void setResult(TResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "AjaxResponseOfTResult{" +
                "result=" + result +
                '}';
    }
}
