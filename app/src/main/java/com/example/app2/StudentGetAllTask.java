package com.example.app2;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentGetAllTask extends AsyncTask<Void,Void, List<String>>{

    @Override
    protected List<String> doInBackground(Void... voids) {
        List<String> allStudents = new ArrayList<>();
        HttpTransport TRANSPORT = new NetHttpTransport();
        HttpRequestFactory REQ_FACTORY = TRANSPORT.createRequestFactory();
        GenericUrl genericUrl = new GenericUrl("https://rajaonlineportal.herokuapp.com/get/all");
        HttpRequest httpRequest;
        try {
            httpRequest = REQ_FACTORY.buildGetRequest(genericUrl);
            HttpResponse httpResponse = httpRequest.execute();
            String s = httpResponse.parseAsString();
            ObjectMapper mapper = new ObjectMapper();
            Student[] students = mapper.readValue(s, Student[].class);
            for(Student x:students){
                allStudents.add(x.getName());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return allStudents;
    }
}
