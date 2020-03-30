package com.example.app2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText userid;
    Button getButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userid = (EditText)findViewById(R.id.stuid);
        getButton = (Button)findViewById(R.id.get);

        getButton.setOnClickListener(new View.OnClickListener(){
            String result = null;
            @Override
            public void onClick(View v) {
                MyTask task = new MyTask();
                try {
                    String id = userid.getText().toString();
                    result = task.execute(id).get();

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
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
