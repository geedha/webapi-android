package com.example.app2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText userid;
    EditText username;
    Button getButton;
    Button postButton;
    Button viewAllButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userid = (EditText)findViewById(R.id.stuid);
        username = (EditText)findViewById(R.id.name);
        getButton = (Button)findViewById(R.id.get);
        postButton = (Button)findViewById(R.id.post);
        viewAllButton = (Button)findViewById(R.id.viewall);

        viewAllButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AllStudents.class);
                startActivity(i);
            }
        });

        getButton.setOnClickListener(new View.OnClickListener(){
            String result = null;
            @Override
            public void onClick(View v) {
                MyTask task = new MyTask();
                try {
                    String id = userid.getText().toString();
                    result = task.execute(id).get();
                    ObjectMapper objectMapper = new ObjectMapper();

                    try {
                        Student stu = objectMapper.readValue(result,Student.class);
                        Toast.makeText(getApplicationContext(),stu.toString(),Toast.LENGTH_LONG).show();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            }
        });

        postButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String id = userid.getText().toString();
                String name = username.getText().toString();
                StudentPostTask studentPostTask = new StudentPostTask();
                try {
                    String status = studentPostTask.execute(id, name).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    protected  class MyTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {

            String str="https://rajaonlineportal.herokuapp.com/get/"+(String)params[0];
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try
            {
                URL url = new URL(str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(line);
                }

                return stringBuffer.toString();
            }
            catch(Exception ex)
            {
                Log.e("App", "yourDataTask", ex);
                return null;
            }
            finally
            {
                if(bufferedReader != null)
                {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String response)
        {
            if(response != null)
            {
                try {
                    Log.e("App", "Success: " + response);
                } catch (Exception ex) {
                    Log.e("App", "Failure", ex);
                }
            }
        }
    }
}
