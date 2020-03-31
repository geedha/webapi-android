package com.example.app2;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

public class StudentPostTask extends AsyncTask<String,String,String> {

    @Override
    protected String doInBackground(String... data) {
        String id = (String)data[0];
        String name = (String)data[1];
        Log.d("stupost",id + "--"+name);
        HttpTransport TRANSPORT = new NetHttpTransport();
        JsonFactory JSON_FACTORY = new JacksonFactory();
        HttpRequestFactory REQ_FACTORY = TRANSPORT.createRequestFactory();
        GenericUrl genericUrl = new GenericUrl("https://rajaonlineportal.herokuapp.com/add");
        Map<String,Object> dataToPostMap = new LinkedHashMap<>();
        dataToPostMap.put("id",Integer.parseInt(id));
        dataToPostMap.put("name", name);
        HttpContent httpContent = new JsonHttpContent(JSON_FACTORY, dataToPostMap);
        Log.d("stupost","check");
        try {
            Log.d("stupost","check2");
            REQ_FACTORY.buildPostRequest(genericUrl, httpContent).execute();
            Log.d("stupost","req posted");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
