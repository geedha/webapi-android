package com.example.app2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.api.client.http.HttpTransport;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AllStudents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_students);
        String[] data = {"apple","mango"};
        StudentGetAllTask getAllTask = new StudentGetAllTask();
        try {
            List<String> studentsNameList = getAllTask.execute().get();
            data = studentsNameList.toArray(new String[0]);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.activity_listview,data);
        ListView listView = (ListView)findViewById(R.id.stulist);
        listView.setAdapter(arrayAdapter);
    }

}
