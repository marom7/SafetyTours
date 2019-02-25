package com.cloud.marom.safetytours.server;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class PostRequest extends StringRequest {

    private Map<String, String> mParams;
    Response.Listener<String> mListener;
    Response.ErrorListener mErrorListener;

    public PostRequest(String url, Map<String, String> params, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, url, listener, errorListener);
        mListener = listener;
        mErrorListener = errorListener;
        mParams = params;

    }


    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {

            parsed = new String(response.data, "charset=UTF-8");

        }
        catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public Map<String, String> getParams() {
        return mParams;
    }
}

