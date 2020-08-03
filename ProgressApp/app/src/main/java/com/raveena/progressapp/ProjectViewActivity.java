package com.raveena.progressapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.raveena.progressapp.databases.MusicDatabaseHandler;
import com.raveena.progressapp.databases.NotesDatabaseHandler;
import com.raveena.progressapp.models.MusicDBModel;
import com.raveena.progressapp.models.NotesDBModel;

import java.util.ArrayList;
import java.util.List;

public class ProjectViewActivity extends AppCompatActivity {

    TextView projectName, description, dateAdded, completionStatus, favoriteStatus;
    MusicDBModel model;
    Button addNote;
    ListView notesLV;
    ArrayAdapter arrayAdapter;
    EditText noteET;
    NotesDatabaseHandler ndb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_view);


        projectName = findViewById(R.id.projectNameTV);
        description = findViewById(R.id.descriptionTV);
        dateAdded = findViewById(R.id.startDateTV);
        completionStatus = findViewById(R.id.completionTV);
        addNote = findViewById(R.id.addNoteBtn);
        notesLV = findViewById(R.id.notesLV);
        noteET = findViewById(R.id.notesET);

        Bundle extra = getIntent().getExtras();
        String name = "";
        if (extra != null) {
            name = extra.getString("CLICKED_PROJECT_NAME");
            Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
        }
        getModelInfo(name);
        projectName.setText(model.getProjectName());
        description.setText(model.getDescription());
        dateAdded.setText(model.getDateAdded());
        if (model.isComplete()) {
            completionStatus.setText("Finished");
        } else {
            completionStatus.setText("Ongoing");
        }

        ndb = new NotesDatabaseHandler(getApplicationContext());
        updateNotesLV();

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteToAdd = noteET.getText().toString();
                NotesDBModel notesDBModel = new NotesDBModel(-1, model.musicID, noteToAdd);
                ndb.addNoteModel(notesDBModel);
                updateNotesLV();
                arrayAdapter.notifyDataSetChanged();
                noteET.setText("");
            }
        });



    }

    public void getModelInfo(String name) {
        MusicDatabaseHandler mdb = new MusicDatabaseHandler(getApplicationContext());
        List<MusicDBModel> list = mdb.getFullDBList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProjectName().equals(name)) {
                model = list.get(i);
            }
        }
    }

    public void updateNotesLV() {
        if (ndb.getFullDBList().size() == 0) {
            return;
        }
        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, ndb.getFullDBList());
        notesLV.setAdapter(arrayAdapter);
    }



}
