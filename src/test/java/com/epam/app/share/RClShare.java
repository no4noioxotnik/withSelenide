package com.epam.app.share;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.request.HttpRequest;

import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Rustam_Ragimov on 2/13/2018.
 */
public class RClShare {
    public byte[] body = null;
    public HttpRequest httpRequest;
    public HttpResponse httpResponse;
    public HttpMethod method = null;
    public String protocol;
    public String endpoint;
    public String host;
    public int port;
    public URL url;

    public HashMap<String, String> headers = null;
    public HashMap<String, HttpRequest> httpRequests;
    public HashMap<String, HttpResponse> httpResponses;
    public HashMap<String, HashMap<String, Object>> savedParameters;
    public HashMap<String, URL> savedUrls;
    public HashMap<String, UUID> uuidHashMaps;
    public String[] basic;

    public String md5HashSumFileOutgoing = null;
    public String urlStr;
    public HttpResponse<String> response;
    public JsonElement jsonElement;
    public JsonObject jsonObject;

    public RClShare() {
        httpRequests = new HashMap<>();
        httpResponses = new HashMap<>();
        savedParameters = new HashMap<>();
        uuidHashMaps = new HashMap<>();
        savedUrls = new HashMap<>();
    }
}

