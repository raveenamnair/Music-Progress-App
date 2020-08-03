package com.raveena.progressapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.raveena.progressapp.databases.MusicDatabaseHandler;
import com.raveena.progressapp.models.MusicDBModel;

import java.util.ArrayList;
import java.util.List;

public class ProjectListViewActivity extends AppCompatActivity {

    ListView projectListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list_view);


        projectListView = findViewById(R.id.projectListView);
        MusicDatabaseHandler mdb = new MusicDatabaseHandler(getApplicationContext());
        List<MusicDBModel> projects = mdb.getFullDBList();
        if (projects.size() != 0) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),
                    android.R.layout.simple_list_item_1, projects);
            projectListView.setAdapter(arrayAdapter);
        }


        projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent goToProjectView = new Intent(getApplicationContext(), ProjectViewActivity.class);
                MusicDBModel musicDBModelClicked = (MusicDBModel) parent.getItemAtPosition(position);
                String projectName = musicDBModelClicked.getProjectName();
                goToProjectView.putExtra("CLICKED_PROJECT_NAME", projectName);
                startActivity(goToProjectView);
            }
        });
    }


}
